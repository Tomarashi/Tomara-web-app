import "../../../css/words-edit.css";
import {useRef, useState} from "react";
import {encodeUrlParams} from "../../utils/url-functions";
import {randomAsciiLetters} from "../../utils/random-functions";
import {API_WORD_FIND, API_WORD_OFFER_ADD} from "../../Const";
import FacebookLoader from "../loader/FacebookLoader";
import {
    ARROW_KEY_NAMES, BACKSPACE_KEY_NAME, DELETE_KEY_NAME, DELETE_KEY_NAMES,
    ENG_TO_GEO_CHARS_MAP,
    ENG_UPPER_CHARS_SET,
    GEO_CHARS_SET
} from "../../utils/characters";
import {stringsEqualsIgnoreCase} from "../../utils/string-functions";
import {WordMetadataWrapper} from "./WordMetadataWrapper";
import StringsSet from "../../utils/StringSet";


const INPUT_PLACEHOLDER_VALUE = "მოძებნე...";
const ADD_WORD_TO_DICT_MESSAGE = "დაამატე სიტყვა";
const FIND_N_LIMIT = 500;

const ARROW_UPPER_CHARS_SET = new StringsSet(ARROW_KEY_NAMES);
const DEL_UPPER_CHARS_SET = new StringsSet(DELETE_KEY_NAMES);
const OTHER_UPPER_CHARS_SET = new StringsSet(["-"]);


const WordsEdit = function () {
    const INPUT_LOADER_ANIM_CLASS_NAME = "words-edit-input-loader-mover-animation";
    const INPUT_LOADER_ANIM_DURATION = 1000;

    const editInputRef = useRef(null);
    const editInputLoaderRef = useRef(null);
    const [isLoading, setIsLoading] = useState(false);
    const [wordsList, updateWordsList] = useState(null);

    let timeout = null;
    let lastRequestId = null;

    const getInputValue = () => editInputRef.current.value;

    const makeRequest = (editInputValue) => {
        const requestId = randomAsciiLetters(16);
        const url = API_WORD_FIND + encodeUrlParams({
            "sub_geo_word": editInputValue,
            "n_limit": FIND_N_LIMIT,
            "request_id": requestId,
        });
        lastRequestId = requestId;

        setIsLoading(true);
        fetch(url)
            .then(res => res.json())
            .then((data) => {
                if(lastRequestId === data["request_id"]) {
                    const wrappers = (data["words"] || []).map(value => {
                        return new WordMetadataWrapper(value);
                    });
                    setIsLoading(false);
                    updateWordsList(wrappers);
                }
            })
            .catch((err) => {
                updateWordsList(null);
                console.error(err);
            })
            .finally(() => {
                setIsLoading(false);
            });
    };

    const updateInputValue = (targetInput, newChar) => {
        const ind = targetInput.selectionStart;
        const value = targetInput.value;
        if(stringsEqualsIgnoreCase(newChar, BACKSPACE_KEY_NAME)) {
            if(ind > 0) {
                targetInput.value = value.substring(0, ind - 1) + value.substring(ind);
                targetInput.setSelectionRange(ind - 1, ind - 1);
            }
        } else if(stringsEqualsIgnoreCase(newChar, DELETE_KEY_NAME)) {
            if(ind < value.length) {
                targetInput.value = value.substring(0, ind) + value.substring(ind + 1);
                targetInput.setSelectionRange(ind, ind);
            }
        } else {
            targetInput.value = value.substring(0, ind) + newChar + value.substring(ind);
            targetInput.setSelectionRange(ind + 1, ind + 1);
        }
    };

    const updateInputLoaderAndMakeRequest = () => {
        const element = editInputLoaderRef.current;
        element.classList.remove(INPUT_LOADER_ANIM_CLASS_NAME);
        void element.offsetWidth;
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
        if(ARROW_UPPER_CHARS_SET.has(keyUpper)
        || (keyUpper === "R" && e.ctrlKey)) {
            return;
        }
        if(DEL_UPPER_CHARS_SET.has(keyUpper)) {
            updateInputValue(e.target, key);
            if(getInputValue() !== "") {
                updateInputLoaderAndMakeRequest();
            } else {
                updateWordsList(null);
            }
        } else if(GEO_CHARS_SET.has(key) || OTHER_UPPER_CHARS_SET.has(key)) {
            updateInputValue(e.target, key);
            updateInputLoaderAndMakeRequest();
        } else if(ENG_UPPER_CHARS_SET.has(keyUpper)) {
            const engChar = ENG_TO_GEO_CHARS_MAP[key];
            if(engChar !== undefined) {
                updateInputValue(e.target, engChar);
                updateInputLoaderAndMakeRequest();
            }
        }
        e.preventDefault();
    };

    const addWordButtonClicked = () => {
        const url = API_WORD_OFFER_ADD + encodeUrlParams({
            "new_word": getInputValue(),
        });
        fetch(url, {
            method: "post",
        })
            .then(res => res.json())
            .then(_ => {
                alert("სიტყვის დამატების მოთხოვნა წარმატებით გაიგზავნა");
            })
            .catch((err) => {
                console.error(err);
            });
    };

    return (
        <div className="words-edit-container">
            <div className="words-edit-input-container">
                <input
                    ref={editInputRef}
                    onKeyDown={handleKeyDownFunction}
                    className="words-edit-input"
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
                if(wordsList.length === 0) {
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
                return <div className="words-edit-response-list">{wordsList}</div>;
            })()}
        </div>
    );
};

export default WordsEdit;
