import React from "react";
import "../../../../css/admin/offers-monitor.css";
import {encodeUrlParams} from "../../../utils/url-functions";
import CenteredTextView from "../../utils/CenteredTextView";

class OfferFrequencyResponseEntry {
    constructor(offerId, word, frequency) {
        this.offerId = offerId;
        this.word = word;
        this.frequency = frequency;
    }

    static from(obj) {
        return new OfferFrequencyResponseEntry(
            obj["offer_id"] || 0,
            obj["word"] || "",
            obj["frequency"] || 0,
        );
    }
}

const OffersMonitorViewCountInfo = function (props) {
    return (
        <div style={{marginTop: 5}} className="offers-monitor-view-count-info">
            ჯამი: {props.total}, უნიკალური: {props.distinct}
        </div>
    );
};

class AbstractOffersMonitorViewOffer extends React.Component {
    static _VALID_TYPES = ["add", "delete"];
    static _RESPONSE_MSG_MAP = {
        "Add offer accepted": "დამატება მიღებულია",
        "Add offer rejected": "დამატება უარყოფილია",
        "Delete offer accepted": "წაშლა მიღებულია",
        "Delete offer rejected": "წაშლა უარყოფილია",
        "": "უცნობი მესიჯი სერვერიდან",
    };

    static _getResponseMsgMap(value) {
        const map = AbstractOffersMonitorViewOffer._RESPONSE_MSG_MAP;
        if(!map.hasOwnProperty(value)) {
            value = "";
        }
        return map[value];
    }

    constructor(props) {
        super(props);
        this.type = this._checkAndGet(props, "type").toLowerCase();
        if(!AbstractOffersMonitorViewOffer._VALID_TYPES.includes(this.type)) {
            throw new Error(`Invalid type: '${this.type}'`);
        }
        this.list = this._checkAndGet(props, "list");
        this.callListener = props["listener"] || function() {};
    }

    _checkAndGet(props, fieldName) {
        if(props.hasOwnProperty(fieldName)) {
            return props[fieldName];
        }
        throw new Error(`Field '${fieldName}' doesn't exist`);
    }

    _buildConfirmMsg(isAccept) {
        const offerTypeMsg = {"add": "ჩამატების", "delete": "წაშლის"}[this.type];
        const invokeTypeMsg = (isAccept)? "დადასტურება": "უარყოფა";
        return `სიტყვის ${offerTypeMsg} მოთხოვნის ${invokeTypeMsg}`;
    }

    _buildRequestUri(valueId, decision) {
        return "/api/admin/offer/resolve/" + this.type + encodeUrlParams({
            id: valueId,
            decision: decision.toString(),
        });
    }

    _invokeDecision(valueId, decision) {
        const confirmMsg = this._buildConfirmMsg(false);
        if(window.confirm(confirmMsg)) {
            const url = this._buildRequestUri(valueId, decision);
            fetch(url, { method: "POST" })
                .then(async res => {
                    const data = await res.json();
                    if(res.status !== 200) {
                        throw new Error(data["error_msg"] || "");
                    }
                    return data;
                })
                .then(data => {
                    const getResponseMsgMap = AbstractOffersMonitorViewOffer._getResponseMsgMap;
                    this.callListener();
                    alert(getResponseMsgMap(data["success_msg"] || ""));
                })
                .catch(console.error);
        }
    }

    render() {
        return (
            <div className="offers-monitor-view-entry-table-wrapper">
                <div className="offers-monitor-view-entry-table">
                    <div className="offers-monitor-view-entry-table-header-row">
                        <div className="offers-monitor-view-entry-table-header-cell">#</div>
                        <div className="offers-monitor-view-entry-table-header-cell">სიტყვა</div>
                        <div className="offers-monitor-view-entry-table-header-cell">Qty.</div>
                        <div className="offers-monitor-view-entry-table-header-cell">ქმედება</div>
                    </div>
                    <div className="offers-monitor-view-entry-table-rows">
                        {(this.list.length === 0)? (
                            <CenteredTextView text="შედეგი არ არის" />
                        ): this.list.map((entry, i) => {
                            return (
                                <div className="offers-monitor-view-entry-table-row">
                                    <div className="offers-monitor-view-entry-table-cell" align="center">
                                        {i + 1}
                                    </div>
                                    <div className="offers-monitor-view-entry-table-cell">
                                        {entry.word || "aaa"}
                                    </div>
                                    <div className="offers-monitor-view-entry-table-cell" align="right">
                                        {entry.frequency}
                                    </div>
                                    <div className="offers-monitor-view-entry-table-cell" align="center">
                                        <button
                                            title="დადასტურება"
                                            className="offers-monitor-view-entry-table-cell-action-yes"
                                            onClick={() => this._invokeDecision(entry.offerId, true)}>
                                            <i className="fa fa-check" />
                                        </button>
                                        <button
                                            title="უარყოფა"
                                            className="offers-monitor-view-entry-table-cell-action-no"
                                            onClick={() => this._invokeDecision(entry.offerId, false)}
                                            style={{marginLeft: 10}}>
                                            <i className="fa fa-remove" />
                                        </button>
                                    </div>
                                </div>
                            );
                        })}
                    </div>
                </div>
            </div>
        );
    }
}

class OffersMonitorViewAddOffer extends AbstractOffersMonitorViewOffer {
    constructor(props) {
        super({type: "add", ...props});
    }
}

class OffersMonitorViewDeleteOffer extends AbstractOffersMonitorViewOffer {
    constructor(props) {
        super({type: "delete", ...props});
    }
}

export {
    OfferFrequencyResponseEntry,
    OffersMonitorViewCountInfo,
    OffersMonitorViewAddOffer,
    OffersMonitorViewDeleteOffer,
};
