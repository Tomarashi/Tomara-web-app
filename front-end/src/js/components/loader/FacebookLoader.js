import "../../../css/loader/facebook-loader.css";

const FacebookLoader = function(props) {
    const className = ["facebook-type-loader", props["className"] || ""];
    return (
        <div className={className.join(" ")}>
            <div /><div /><div />
        </div>
    );
};

export default FacebookLoader;
