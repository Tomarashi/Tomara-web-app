import "../../../css/main/review-create-view.css";
import {useRef, useState} from "react";
import {FacebookLoaderWrapped} from "../loader/FacebookLoader";
import {useTheme} from "../use-theme";

const REVIEW_CONTENT_MAX_LENGTH = 250;
const REVIEW_SEND_BUTTON = "გაგზავნა";

const ENG_MESSAGES_TO_GEO = {
    "Review added successfully": "მიმოხილვა დამატებულია წარმატებით",
};

const ReviewCreateView = function() {
    const textAreaRef = useRef(null);
    const [contentLen, setContentLen] = useState(0);
    const [isInSending, setInSending] = useState(false);
    const [withTheme] = useTheme();

    const getContent = () => (textAreaRef.current.value || "").trimEnd();
    const sendReview = () => {
        if(contentLen > REVIEW_CONTENT_MAX_LENGTH) {
            return;
        }

        setInSending(true);
        const options = {
            method: "POST",
            body: JSON.stringify({
                content: getContent()
            }),
            headers: {
                "Accept": "application/json, text/plain, */*",
                "Content-Type": "application/json;charset=utf-8",
            },
        };
        fetch("/api/reviews/add", options)
            .then(res => res.json())
            .then(data => {
                if(data.hasOwnProperty("success_msg")) {
                    const engMsg = data["success_msg"];
                    if(ENG_MESSAGES_TO_GEO.hasOwnProperty(engMsg)) {
                        alert(ENG_MESSAGES_TO_GEO[engMsg]);
                    }
                }
            })
            .catch(console.error)
            .finally(() => {
                setContentLen(0);
                setInSending(false);
            });
    };

    if(isInSending) {
        return (
            <div className={withTheme("review-create-view-container")}>
                <FacebookLoaderWrapped />
            </div>
        );
    }

    const contentLenStyles = {
        "color": (contentLen <= REVIEW_CONTENT_MAX_LENGTH)? "green": "red",
    };
    return (
        <div className={withTheme("review-create-view-container")}>
            <div className="review-create-view-sub-container">
                <div className={withTheme("review-create-view-counter")}>
                    <span style={contentLenStyles}>
                        {contentLen}
                    </span>{" ".repeat(4 - contentLen.toString().length)}/<span>
                        {REVIEW_CONTENT_MAX_LENGTH}
                    </span>
                </div>
                <textarea
                    className={withTheme("review-create-view-textarea")}
                    ref={textAreaRef}
                    onChange={() => {
                        setContentLen(getContent().length);
                    }} />
                <span className="review-create-view-warning">
                    გაგზავნის შემდეგ, თქვენი რევიუ დაიკარგება
                </span>
                <input
                    className="review-create-view-send-review-button"
                    type="button"
                    value={REVIEW_SEND_BUTTON}
                    disabled={contentLen > REVIEW_CONTENT_MAX_LENGTH}
                    onClick={sendReview} />
            </div>
        </div>
    );
};

export default ReviewCreateView;
