import "../../css/loader.css";
import "../../media/logo/logo-loader.png";
import logo from "../../media/logo/logo-loader-min.png";

const Loader = function () {
    return (
        <>
            <div className="loader-container-wave" />
            <div className="loader-container">
                <img src={logo} className="loader-image" alt="Loader Tomara's logo" />
            </div>
        </>
    );
};

export default Loader;
