import "../../../css/loader/facebook-loader.css";

const FacebookLoader = function(props = {}) {
    const className = ["facebook-type-loader", props["className"] || ""];
    return (
        <div className={className.join(" ")}>
            <div /><div /><div />
        </div>
    );
};

export const FacebookLoaderWrapped = function(props = {}) {
    const classes = ["facebook-type-loader-wrapper", props["className"] || ""];
    return (
        <div className={classes.join(" ")}>
            <FacebookLoader className={props["innerClassName"] || ""} />
        </div>
    );
}

export default FacebookLoader;
