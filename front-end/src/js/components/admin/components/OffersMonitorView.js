import "../../../../css/admin/offers-monitor.css";
import {useEffect, useState} from "react";
import {FacebookLoaderWrapped} from "../../loader/FacebookLoader";
import {
    OfferFrequencyResponseEntry,
    OffersMonitorViewAddOffer, OffersMonitorViewDeleteOffer,
    OffersMonitorViewCountInfo,
} from "./OffersMonitorViewHelper";

const OffersMonitorView = function () {
    const [loading, setLoading] = useState(true);
    const [values, setValues] = useState({
        addOffer: {total: 0, distinct: 0},
        deleteOffer: {total: 0, distinct: 0},
        addFrequency: [],
        deleteFrequency: [],
    });

    const fetchData = async () => {
        const fetchJson = async (url, opts = {}) => {
            const response = await fetch(url, opts);
            return await response.json();
        };

        const addFrequency = await fetchJson("/api/admin/offer/add/frequency");
        const deleteFrequency = await fetchJson("/api/admin/offer/delete/frequency");
        const offerCount = await fetchJson("/api/admin/statistics/offer/count");

        setValues({
            addOffer: {
                total: offerCount["offer_add_count"]["total"],
                distinct: offerCount["offer_add_count"]["distinct"],
            },
            deleteOffer: {
                total: offerCount["offer_delete_count"]["total"],
                distinct: offerCount["offer_delete_count"]["distinct"],
            },
            addFrequency: (addFrequency["result"] || []).map(OfferFrequencyResponseEntry.from),
            deleteFrequency: (deleteFrequency["result"] || []).map(OfferFrequencyResponseEntry.from),
        });
    };

    const fetchDataSync = () => {
        fetchData()
            .catch(console.error)
            .finally(() => {
                setLoading(false);
            });
    };

    useEffect(fetchDataSync, []);

    return (
        <div className="offers-monitor-view-container">
            {(loading)? <FacebookLoaderWrapped className="offers-monitor-view-loader-wrapper" />: (
                <>
                    <div className="offers-monitor-view-offer-header">
                        <button
                            className="offers-monitor-view-offer-reload"
                            onClick={fetchDataSync}>
                            განახლება
                        </button>
                    </div>
                    <div className="offers-monitor-view-offer-add">
                        <div className="offers-monitor-view-offer-header-name">
                            დამატება
                        </div>
                        <OffersMonitorViewCountInfo
                            total={values.addOffer.total}
                            distinct={values.addOffer.distinct} />
                        <OffersMonitorViewAddOffer
                            list={values.addFrequency} />
                    </div>
                    <div className="offers-monitor-view-offer-delete">
                        <div className="offers-monitor-view-offer-header-name">
                            წაშლა
                        </div>
                        <OffersMonitorViewCountInfo
                            total={values.deleteOffer.total}
                            distinct={values.deleteOffer.distinct} />
                        <OffersMonitorViewDeleteOffer
                            list={values.deleteFrequency} />
                    </div>
                </>
            )}
        </div>
    );
};

export default OffersMonitorView;
