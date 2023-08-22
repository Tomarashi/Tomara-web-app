import "../../../css/words/words-edit.css";
import {useRef, useState} from "react";
import {encodeUrlParams} from "../../utils/url-functions";
import FacebookLoader from "../loader/FacebookLoader";
import {
    ARROW_KEY_NAMES, BACKSPACE_KEY_NAME, DELETE_KEY_NAME, DELETE_KEY_NAMES,
    ENG_TO_GEO_CHARS_MAP,
    GEO_CHARS_SET
} from "../../utils/characters";
import {WordMetadataWrapper} from "./WordMetadataWrapper";
import StringsSet from "../../utils/StringSet";
import {useTheme} from "../use-theme";


const INPUT_PLACEHOLDER_VALUE = "მოძებნე...";
const ADD_WORD_TO_DICT_MESSAGE = "დაამატე სიტყვა";

const ARROW_CHARS_STRS_SET = new StringsSet(ARROW_KEY_NAMES);
const DEL_CHARS_STRS_SET = new StringsSet(DELETE_KEY_NAMES);
const OTHER_CHARS_STRS_SET = new StringsSet(["-"]);


String.prototype.equalsIgnoreCase = function(other) {
    if(!other) return false;
    return this.toUpperCase() === other.toUpperCase();
};


const WordsEdit = function() {
    const INPUT_LOADER_ANIM_CLASS_NAME = "words-edit-input-loader-mover-animation";
    const INPUT_LOADER_ANIM_DURATION = 1000;

    const editInputRef = useRef(null);
    const editInputLoaderRef = useRef(null);
    const [isLoading, setIsLoading] = useState(false);
    const [[
        wordsList,
        maxPossibleLimit,
        isExactMatch,
    ], updateWordsList] = useState([null, 0, false]);
    const [withTheme] = useTheme();

    let timeout = null;
    let lastRequestId = null;

    const getInputValue = () => editInputRef.current.value;

    const makeRequest = (editInputValue) => {
        const nLimit = 5000;
        const url = "/api/word/find" + encodeUrlParams({
            "sub_geo_word": editInputValue,
            "request_id": editInputValue,
            "n_limit": nLimit,
        });
        lastRequestId = editInputValue;

        setIsLoading(true);
        fetch(url)
            .then(res => res.json())
            .then((data) => {
                if(lastRequestId === data["request_id"]) {
                    const words = data["words"] || [];
                    const valueIndex = words.indexOf(editInputValue);
                    if(valueIndex >= 0) {
                        words.splice(valueIndex, 1);
                        words.splice(0, 0, editInputValue);
                    }
                    const wrappers = words.map(value => {
                        return <WordMetadataWrapper value={value} />
                    });
                    const nUncut = data["words_n_uncut"] || 0;
                    updateWordsList([wrappers, nUncut, valueIndex >= 0]);
                    setIsLoading(false);
                }
            })
            .catch((err) => {
                updateWordsList([null, 0, false]);
                console.error(err);
            })
            .finally(() => {
                setIsLoading(false);
            });
    };

    const updateInputValue = (targetInput, newChar) => {
        const start = targetInput.selectionStart;
        const end = targetInput.selectionEnd;
        const value = targetInput.value;

        if(!DEL_CHARS_STRS_SET.has(newChar)) {
            targetInput.value = value.substring(0, start) + newChar + value.substring(start);
            targetInput.setSelectionRange(start + 1, start + 1);
        } else if(start !== end) {
            targetInput.value = value.substring(0, start) + value.substring(end);
            targetInput.setSelectionRange(start, start);
        } else if(newChar.equalsIgnoreCase(BACKSPACE_KEY_NAME) && start > 0) {
            targetInput.value = value.substring(0, start - 1) + value.substring(start);
            targetInput.setSelectionRange(start - 1, start - 1);
        } else if(newChar.equalsIgnoreCase(DELETE_KEY_NAME) && start < value.length) {
            targetInput.value = value.substring(0, start) + value.substring(start + 1);
            targetInput.setSelectionRange(start, start);
        }
    };

    const updateInputLoaderAndMakeRequest = () => {
        const element = editInputLoaderRef.current;
        element.classList.remove(INPUT_LOADER_ANIM_CLASS_NAME);
        void element.offsetWidth;   // Simulate invoke
        element.style.animationDuration = INPUT_LOADER_ANIM_DURATION + "ms";
        element.classList.add(INPUT_LOADER_ANIM_CLASS_NAME);
        clearTimeout(timeout);
        timeout = setTimeout(() => {
            const value = getInputValue();
            if(value !== "") {
                makeRequest(value);
            }
        }, INPUT_LOADER_ANIM_DURATION);
    };

    const handleKeyDownFunction = (e) => {
        const key = e.key;
        const keyUpper = e.key.toUpperCase();
        if(ARROW_CHARS_STRS_SET.has(keyUpper)
        || (e.ctrlKey && ["A", "R", "C", "V"].includes(keyUpper))) {
            return;
        }

        if(DEL_CHARS_STRS_SET.has(keyUpper)) {
            updateInputValue(e.target, key);
            if(getInputValue() !== "") {
                updateInputLoaderAndMakeRequest();
            } else {
                updateWordsList([null, 0, false]);
            }
        } else if(
            GEO_CHARS_SET.has(key)
            || OTHER_CHARS_STRS_SET.has(key)
            || ENG_TO_GEO_CHARS_MAP.hasOwnProperty(key)
        ) {
            const engToGeo = ENG_TO_GEO_CHARS_MAP[key];
            updateInputValue(e.target, (engToGeo)? engToGeo: key);
            updateInputLoaderAndMakeRequest();
        }
        e.preventDefault();
    };

    const addWordButtonClicked = () => {
        const url = "/api/word/offer/add" + encodeUrlParams({
            "new_word": getInputValue(),
        });
        fetch(url, {
            method: "post",
        })
            .then(res => res.json())
            .then(_ => {
                alert("სიტყვის დამატების მოთხოვნა წარმატებით გაიგზავნა");
            })
            .catch(console.error);
    };

    return (
        <div className={withTheme("words-edit-container")}>
            <div className="words-edit-input-container">
                <input
                    ref={editInputRef}
                    onKeyDown={handleKeyDownFunction}
                    className={withTheme("words-edit-input")}
                    placeholder={INPUT_PLACEHOLDER_VALUE}
                    type="text" />
                <div className="words-edit-input-loader">
                    <div ref={editInputLoaderRef} className="words-edit-input-loader-mover" />
                </div>
            </div>
            {(() => {
                if(isLoading) {
                    return <FacebookLoader className="words-edit-fetching-loader" />;
                }
                if(wordsList === null) {
                    return null;
                }
                if(wordsList.length === 0 && !isExactMatch) {
                    return (
                        <div
                            onClick={addWordButtonClicked}
                            className="words-edit-empty-response">
                            <div className="words-edit-empty-response-add-word-button">
                                <span>{ADD_WORD_TO_DICT_MESSAGE}</span>
                            </div>
                        </div>
                    );
                }
                return null;
            })()}
            {(isLoading || wordsList === null || wordsList.length === 0)? null: (
                <div className="words-edit-response-list">{wordsList}</div>
            )}
            {(isLoading || wordsList === null || maxPossibleLimit === 0)? null: (
                <div>{`${wordsList.length}/${maxPossibleLimit}`}</div>
            )}
        </div>
    );
};

export default WordsEdit;
