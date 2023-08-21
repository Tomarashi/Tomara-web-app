import "../../../css/main/main.css";
import {APP_TITLE_FULL} from "../../Const";
import ReviewCreateView from "../reviews/ReviewCreateView";
import WordsEdit from "../words/WordsEdit";
import InfoHeader from "./InfoHeader";
import {ThemeProvider} from "../ThemeContext";
import InfoBottom from "./InfoBottom";
import {useTheme} from "../ThemeUtils";

const MainContent = function () {
    const [withTheme] = useTheme();

    return (
        <div className="main-background">
            <InfoHeader />
            <div style={{height: 20}} />
            <div className={withTheme("main-content")}>
                <WordsEdit />
                <ReviewCreateView />
            </div>
            <div style={{height: 40}} />
            <InfoBottom />
        </div>
    );
};

const Main = function () {
    document.title = APP_TITLE_FULL;

    return (
        <ThemeProvider>
            <MainContent />
        </ThemeProvider>
    );
};

export default Main;
