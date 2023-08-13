import "../../../css/utils/centered-text-view.css";

const CenteredTextView = function(props) {
    const text = props.text || "";
    const otherStyles = props.styles || {};
    const styles = {
        fontSize: props.fontSize || 24,
        ...otherStyles,
    };
    return (
        <div style={styles} className="centered-text-view">
            {text}
        </div>
    );
};

export default CenteredTextView;
