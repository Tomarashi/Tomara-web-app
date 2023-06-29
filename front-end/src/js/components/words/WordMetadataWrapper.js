import {randomAsciiLetters} from "../../utils/random-functions";
import {encodeUrlParams} from "../../utils/url-functions";
import {API_WORD_OFFER_DELETE} from "../../Const";


const WORD_TYPE_VALID = 1;
const WORD_TYPE_DELETED = 2;
const VALID_WORD_TYPES = [WORD_TYPE_VALID, WORD_TYPE_DELETED];

const CLASSNAMES_TYPE_MAP = {
    [WORD_TYPE_VALID]: "words-edit-response-list-item-valid",
    [WORD_TYPE_DELETED]: "words-edit-response-list-item-deleted",
};
const CLASSNAMES_CONFIG_TYPE_MAP = {
    [WORD_TYPE_VALID]: "words-edit-response-list-item-valid-config-open",
    [WORD_TYPE_DELETED]: "words-edit-response-list-item-deleted-config-open",
};


export const WordMetadataWrapper = function(wordResponse) {
    const wordId = wordResponse["word_id"];
    const wordGeo = wordResponse["word_geo"];
    const wordType = wordResponse["type"] || 0;
    const wordTypeStr = wordType.toString();

    if(!VALID_WORD_TYPES.includes(wordType)) {
        return null;
    }

    const id = `word-meta-${randomAsciiLetters(16)}-${wordId}`;
    const idConfig = id + "-config";

    let configIsVisible = false;

    const deleteOffer = () => {
        const url = API_WORD_OFFER_DELETE + encodeUrlParams({
            "word_id": wordId,
        });
        fetch(url, {
            method: "POST"
        }).then(res => res.json())
            .then(_ => {
                alert("წაშლის მოთხოვნა წარმატებით გაიგზავნა");
            })
            .catch(err => {
                console.error(err);
            });
    };

    const configContent = (() => {
        if(wordType === WORD_TYPE_VALID) {
            return (
                <div className="words-edit-response-list-item-config-valid">
                    <button onClick={deleteOffer}>
                        <i className="fa fa-trash"></i> წაშლის მოთხოვნა
                    </button>
                </div>
            );
        } else if(wordType === WORD_TYPE_DELETED) {
            // Temporarily unavailable
            return null;
        }
        return null;
    })();

    return (
        <div className="words-edit-response-list-item-wrapper">
            <div
                id={id}
                className={`words-edit-response-list-item ${CLASSNAMES_TYPE_MAP[wordTypeStr]}`}
                style={{
                    cursor: (configContent)? "pointer": "auto"
                }}
                onClick={(configContent === null)? null: (() => {
                    const thisElement = document.getElementById(id);
                    const thisElementConfig = document.getElementById(idConfig);

                    configIsVisible = !configIsVisible;

                    if(configIsVisible) {
                        thisElement.classList.add(CLASSNAMES_CONFIG_TYPE_MAP[wordTypeStr]);
                    } else {
                        thisElement.classList.remove(CLASSNAMES_CONFIG_TYPE_MAP[wordTypeStr]);
                    }
                    thisElementConfig.style.display = (configIsVisible)? "block": "none";
                })}>
                {wordGeo}
            </div>
            {(() => {
                if(configContent) {
                    return (
                        <div
                            id={idConfig}
                            className="words-edit-response-list-item-config"
                            style={{ display: "none" }}>
                            {configContent}
                        </div>
                    );
                }
                return null;
            })()}
        </div>
    );
};
