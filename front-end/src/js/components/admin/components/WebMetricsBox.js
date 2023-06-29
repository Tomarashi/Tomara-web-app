import "../../../../css/admin/web-metrics.css";
import {useEffect, useState} from "react";
import {FacebookLoaderWrapped} from "../../loader/FacebookLoader";

const WeekDayHourViewer = function (props) {
    return (
        <div className="web-metrics-box-viewer">
            <p className="web-metrics-box-viewer-header">{props.header || ""}</p>
            <div style={{height: "6px"}} />
            <p className="web-metrics-box-viewer-field">
                სულ: <span>{props.total || 0}</span>
            </p>
            <p className="web-metrics-box-viewer-field">
                ბოლო კვირა: <span>{props.week || 0}</span>
            </p>
            <p className="web-metrics-box-viewer-field">
                ბოლო დღე: <span>{props.day || 0}</span>
            </p>
        </div>
    );
};

const WebMetricsBox = function () {
    const [loading, setLoading] = useState(true);
    const [metrics, setMetrics] = useState({
        views: {total: "-", week: "-", day: "-"},
        uniques: {total: "-", week: "-", day: "-"},
    });

    useEffect(() => {
        fetch("/api/admin/web-metrics/total")
            .then(res => res.json())
            .then(data => {
                setMetrics(data);
            })
            .catch(err => {
                console.error(err);
            })
            .finally(() => {
                setLoading(false);
            });
    }, []);

    return (
        <div className="web-metrics-box-container">
            {(loading)? <FacebookLoaderWrapped />: (
                <>
                    <div style={{height: "18px"}} />
                    <WeekDayHourViewer
                        header="ვიზიტები"
                        total={metrics.views.total}
                        week={metrics.views.week}
                        day={metrics.views.day} />
                    <div style={{height: "20px"}} />
                    <WeekDayHourViewer
                        header="უნიკალური მომხმარებლები"
                        total={metrics.uniques.total}
                        week={metrics.uniques.week}
                        day={metrics.uniques.day} />
                </>
            )}
        </div>
    );
};

export default WebMetricsBox;
