import "../../../css/main/main.css";
import {APP_TITLE_FULL} from "../../Const";
import ReviewCreateView from "../reviews/ReviewCreateView";
import WordsEdit from "../words/WordsEdit";
import InfoHeader from "./InfoHeader";
import {ThemeProvider} from "../ThemeContext";
import InfoBottom from "./InfoBottom";
import {useTheme} from "../use-theme";
import {getBody} from "../../utils/dom-functions";

const MainContent = function () {
    const [withTheme] = useTheme();

    document.title = APP_TITLE_FULL;
    getBody().className = withTheme("body-theme");

    return (
        <div className="main-background">
            <InfoHeader />
            <div style={{height: 20}} />
            <div className={withTheme("main-content")}>
                <div className={withTheme("main-content-text-0")}>
                    მოძებნე სიტყვა <strong>ტომარაში</strong>
                </div>
                <div className="main-content-words-edit-wrapper">
                    <WordsEdit />
                </div>
                <div className={withTheme("main-content-text-1")}>
                    დაგვიტოვე შენი შეფასება
                </div>
                <div className="main-content-review-create-view-wrapper">
                    <ReviewCreateView />
                </div>
            </div>
            <div style={{height: 40}} />
            <InfoBottom />
        </div>
    );
};

const Main = function () {
    return (
        <ThemeProvider>
            <MainContent />
        </ThemeProvider>
    );
};

export default Main;
