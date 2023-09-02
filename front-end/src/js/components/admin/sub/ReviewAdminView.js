import "../../../../css/admin/review-view.css";
import {useState} from "react";
import {FacebookLoaderWrapped} from "../../loader/FacebookLoader";
import CenteredTextView from "../../utils/CenteredTextView";
import {Tooltip} from "react-tooltip";

const PAGE_SIZE = 5;

const formatDateTime = (date) => {
    const padNumber = (num, maxLen = 2) => {
        return num.toString().padStart(maxLen, "0");
    };

    const year = padNumber(date.getFullYear(), 4);
    const month = padNumber(date.getMonth() + 1);
    const day = padNumber(date.getDate());
    const hour = padNumber(date.getHours());
    const minute = padNumber(date.getMinutes());
    return `${year}-${month}-${day} ${hour}:${minute}`;
};

const ReviewAdminView = function () {
    const [maxPageCount, setMaxPageCount] = useState(-1);
    const [
        [currPageIndex, reviews],
        setPageIndexAndReviews
    ] = useState([1, []]);
    const [firstPage, setFirstPage] = useState(true);
    const computeMaxPageCount = (count) => {
        const mod = count % PAGE_SIZE;
        return (count - mod) / PAGE_SIZE + ((mod === 0)? 0: 1);
    };

    if(maxPageCount < 0) {
        fetch("/api/admin/review/count")
            .then(res => res.json())
            .then(data => {
                const count = data["count"] || 0;
                setMaxPageCount(computeMaxPageCount(count));
            });
        return (
            <div className="review-admin-view-container">
                <FacebookLoaderWrapped />
            </div>
        );
    } else if(maxPageCount === 0) {
        return (
            <div className="review-admin-view-container">
                <CenteredTextView text="რევიუები არ მოიძებნა" />
            </div>
        );
    }

    const offsetPage = (offset) => {
        const newPageIndex = (() => {
            const newIndex = currPageIndex + offset;
            return Math.min(maxPageCount, Math.max(1, newIndex));
        })();
        fetch(`/api/admin/review/page?index=${newPageIndex - 1}&size=${PAGE_SIZE}`)
            .then(res => res.json())
            .then(data => {
                const count = data["max_count"] || 0;
                setMaxPageCount(computeMaxPageCount(count));
                setPageIndexAndReviews([newPageIndex, data["reviews"]]);
            });
    };

    const deleteReview = (reviewId) => {
        const url = `/api/admin/review/delete?id=${reviewId}`;
        fetch(url, { method: "POST" })
            .then(res => [res.status, res.json()])
            .then(res => {
                const [statusCode, data] = res;
                if(statusCode === 200) {
                    alert("რევიუ წარმატებით წაიშალა");
                    setMaxPageCount(-1);
                    offsetPage(0);
                } else {
                    const errorMsg = data["error_msg"] || "";
                    if(statusCode === 404 && errorMsg.startsWith("Could not find the review with id")) {
                        alert("რევიუ ვერ მოიძებნა");
                    } else {
                        alert("რევიუ წაშლა ვერ მოხერხდა");
                    }
                }
            });
    };

    if(firstPage) {
        offsetPage(0);
        setFirstPage(false);
    }

    return (
        <div className="review-admin-view-container">
            <div className="review-admin-view-container-header">
                <input
                    value="-5"
                    type="button"
                    disabled={currPageIndex - 5 < 1}
                    className="review-admin-view-page-change-btn"
                    onClick={() => offsetPage(-5)} />
                <input
                    value="-1"
                    type="button"
                    disabled={currPageIndex - 1 < 1}
                    className="review-admin-view-page-change-btn"
                    onClick={() => offsetPage(-1)} />
                <span className="review-admin-view-page-number">
                    {currPageIndex} / {maxPageCount}
                </span>
                <input
                    value="+1"
                    type="button"
                    disabled={currPageIndex + 1 > maxPageCount}
                    className="review-admin-view-page-change-btn"
                    onClick={() => offsetPage(1)} />
                <input
                    value="+5"
                    type="button"
                    disabled={currPageIndex + 5 > maxPageCount}
                    className="review-admin-view-page-change-btn"
                    onClick={() => offsetPage(5)} />
            </div>
            {reviews.map(r => {
                const date = new Date(r["time"]);
                const content = r['content'] || "";
                const toolTipStyles = {
                    backgroundColor: "rgba(154, 0, 0, 0.8)",
                    fontFamily: "bpg-arial, sans-serif",
                };
                return (
                    <div className="review-admin-view-container-review-wrapper">
                        <div className="review-admin-view-container-reviews-content">
                            {content}
                        </div>
                        <div className="review-admin-view-container-reviews-meta">
                            <Tooltip
                                id="review-admin-view-tooltip-id-tooltip"
                                place="top"
                                style={toolTipStyles}
                                variant="info"
                                content={"Review Id: " + r["id"]} />
                            <Tooltip
                                id="review-admin-view-tooltip-date-tooltip"
                                place="top"
                                style={toolTipStyles}
                                variant="info"
                                content={formatDateTime(date)} />

                            <i data-tooltip-id="review-admin-view-tooltip-id-tooltip"
                               className={[
                                   "review-admin-view-container-reviews-id",
                                   "fa", "fa-hashtag",
                               ].join(" ")} />
                            <i data-tooltip-id="review-admin-view-tooltip-date-tooltip"
                                className={[
                                    "review-admin-view-container-reviews-date",
                                    "fa", "fa-calendar",
                                ].join(" ")} />
                            <input
                                type="button"
                                className="review-admin-view-container-reviews-meta-delete"
                                onClick={() => deleteReview(r["id"])}
                                value="წაშლა" />
                        </div>
                    </div>
                );
            })}
        </div>
    );
};

export default ReviewAdminView;
