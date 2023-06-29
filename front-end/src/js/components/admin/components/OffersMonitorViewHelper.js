import React from "react";
import "../../../../css/admin/offers-monitor.css";
import {encodeUrlParams} from "../../../utils/url-functions";

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
            ჯამი - {props.total}, უნიკალური - {props.distinct}
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
        this.state = {
            list: this._checkAndGet(props, "list"),
        };
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
                    this.setState({
                        list: this.state.list.filter(entry => {
                            return entry.offerId !== valueId;
                        }),
                    });
                    const getResponseMsgMap = AbstractOffersMonitorViewOffer._getResponseMsgMap;
                    alert(getResponseMsgMap(data["success_msg"] || ""));
                })
                .catch(console.error);
        }
    }

    render() {
        return (
            <table className="offers-monitor-view-entry-table">
                <tr>
                    <th>სიტყვა</th><th>#</th><th>ქმედება</th>
                </tr>
                {this.state.list.map((entry) => {
                    return (
                        <tr className="offers-monitor-view-entry">
                            <td>{entry.word}</td>
                            <td align="right">{entry.frequency}</td>
                            <td className="offers-monitor-view-entry-action">
                                <button
                                    title="დადასტურება"
                                    onClick={() => this._invokeDecision(entry.offerId, true)}
                                    style={{marginLeft: 5}}
                                    className="offers-monitor-view-entry-action-yes">
                                    <i className="fa fa-check" />
                                </button>
                                <button
                                    title="უარყოფა"
                                    onClick={() => this._invokeDecision(entry.offerId, false)}
                                    style={{marginLeft: 10}}
                                    className="offers-monitor-view-entry-action-no">
                                    <i className="fa fa-remove" />
                                </button>
                            </td>
                        </tr>
                    );
                })}
            </table>
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
