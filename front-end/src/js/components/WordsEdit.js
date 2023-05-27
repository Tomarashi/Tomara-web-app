import "../../css/words-edit.css";
import {useRef, useState} from "react";
import {encodeUrlParams} from "../utils/url-functions";
import {randomAsciiLetters} from "../utils/random-functions";
import Logger from "../utils/logger";
import {APP_TITLE} from "../Const";
import FacebookLoader from "./loader/FacebookLoader";
import {
    ARROW_KEY_NAMES, BACKSPACE_KEY_NAME, DELETE_KEY_NAME, DELETE_KEY_NAMES,
    ENG_TO_GEO_CHARS_MAP,
    ENG_UPPER_CHARS_SET,
    GEO_CHARS_SET
} from "../utils/characters";
import {stringsEqualsIgnoreCase} from "../utils/string-functions";


const INPUT_PLACEHOLDER_VALUE = "აკრიფე...";
const ADD_WORD_TO_DICT_MESSAGE = "დაამატე სიტყვა";

const FIND_N_LIMIT = 1000;

const WORD_TYPE_VALID = 1;
const WORD_TYPE_DELETED = 2;
const VALID_WORD_TYPES = [WORD_TYPE_VALID, WORD_TYPE_DELETED];

const LOGGER = Logger.newInstance(APP_TITLE);

const API_WORD_FIND = "/api/word/find";
const API_WORD_OFFER_ADD = "/api/word/offer/add";
const API_WORD_OFFER_DELETE = "/api/word/offer/delete";

const listToUpperSet = (list) => {
    return new Set(list.map(v => v.toUpperCase()));
};
const ARROW_UPPER_CHARS_SET = listToUpperSet(ARROW_KEY_NAMES);
const DEL_UPPER_CHARS_SET = listToUpperSet(DELETE_KEY_NAMES);
const OTHER_UPPER_CHARS_SET = new Set(["-"]);


const WordMetadata = function(wordResponse) {
    const wordId = wordResponse["word_id"];
    const wordGeo = wordResponse["word_geo"];
    const wordType = wordResponse["type"] || 0;

    if(!VALID_WORD_TYPES.includes(wordType)) {
        return null;
    }

    const classNames = [
        "words-edit-response-list-item",
        [
            "",
            "words-edit-response-list-item-valid",
            "words-edit-response-list-item-deleted",
        ][wordType]
    ].join(" ");
    const classNameWhenConfigOpened = [
        "",
        "words-edit-response-list-item-valid-config-open",
        "words-edit-response-list-item-deleted-config-open",
    ][wordType];

    const id = `word-meta-${randomAsciiLetters(16)}-${wordId}`;
    const idConfig = id + "-config";

    let configIsVisible = false;

    const deleteOffer = () => {
        const url = API_WORD_OFFER_DELETE + encodeUrlParams({
            "word_id": wordId,
        });
        fetch(url, {
            method: "POST",
        }).then(res => res.text())
            .then((data) => {
                LOGGER.log(data);
            })
            .catch((err) => {
                LOGGER.error(err);
            });
    };

    const configContent = (() => {
        if(wordType === WORD_TYPE_VALID) {
            return (
                <div className="words-edit-response-list-item-config-valid">
                    <button onClick={deleteOffer}>
                        <i className="fa fa-trash"></i> Offer Delete
                    </button>
                </div>
            );
        } else if(wordType === WORD_TYPE_DELETED) {
            return (
                <div className="words-edit-response-list-item-config-deleted">
                    <!-- TODO -->
                </div>
            );
        } else {
            return null;
        }
    })();

    return (
        <div className="words-edit-response-list-item-wrapper">
            <div
                id={id}
                className={classNames}
                onClick={() => {
                    const thisElement = document.getElementById(id);
                    const thisElementConfig = document.getElementById(idConfig);

                    configIsVisible = !configIsVisible;

                    if(configIsVisible) {
                        thisElement.classList.add(classNameWhenConfigOpened);
                    } else {
                        thisElement.classList.remove(classNameWhenConfigOpened);
                    }
                    thisElementConfig.style.display = (configIsVisible)? "block": "none";
                }}>
                {wordGeo}
            </div>
            <div
                id={idConfig}
                className="words-edit-response-list-item-config"
                style={{
                    display: "none",
                }}>
                {configContent}
            </div>
        </div>
    );
};

const WordsEdit = function () {
    const INPUT_LOADER_ANIM_CLASS_NAME = "words-edit-input-loader-mover-animation";
    const INPUT_LOADER_ANIM_DURATION = 1000;

    const editInputRef = useRef(null);
    const editInputLoaderRef = useRef(null);
    const [wordsList, updateWordsList] = useState(null);
    const [isLoading, setLoading] = useState(false);

    let timeout = null;
    let lastRequestId = null;

    const getInputValue = () => {
        return editInputRef.current.value;
    };

    const makeRequest = (editInputValue) => {
        const requestId = randomAsciiLetters(16);
        const url = API_WORD_FIND + encodeUrlParams({
            "sub_geo_word": editInputValue,
            "n_limit": FIND_N_LIMIT - FIND_N_LIMIT + 30,
            "request_id": requestId,
        });
        lastRequestId = requestId;

        setLoading(true);
        fetch(url).then(res => res.json())
            .then((data) => {
                if(lastRequestId === data["request_id"]) {
                    updateWordsList((data["words"] || []).map(value => {
                        return new WordMetadata(value);
                    }));
                }
            })
            .catch((err) => {
                LOGGER.error(err);
            })
            .finally(() => {
                setLoading(false);
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
        if(ARROW_UPPER_CHARS_SET.has(keyUpper)) {
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
            method: "POST",
        }).then(res => res.text())
            .then((data) => {
                LOGGER.log(data);
            })
            .catch((err) => {
                LOGGER.error(err);
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
                    return (
                        <FacebookLoader className="words-edit-fetching-loader" />
                    );
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
