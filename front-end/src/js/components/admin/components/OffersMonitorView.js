import "../../../../css/admin/offers-monitor.css";
import {useState} from "react";
import FacebookLoader from "../../loader/FacebookLoader";

const OffersMonitorView = function (props = {}) {
    const [loading, setLoading] = useState(true);
    const styles = {
        width: props.width || "500px",
        height: props.height || "400px",
    };

    return (
        <div style={styles} className="web-metrics-box-container">
            {(() => {
                if(loading) {
                    setLoading(false);
                    return (
                        <div className="offers-monitor-view-loader-wrapper">
                            <FacebookLoader />
                        </div>
                    );
                }
                return null;
            })()}
        </div>
    );
};

export default OffersMonitorView;
