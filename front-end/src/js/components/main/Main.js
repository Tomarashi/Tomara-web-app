import "../../../css/main/main.css";
import {APP_TITLE_FULL} from "../../Const";
import ReviewCreateView from "../reviews/ReviewCreateView";
import WordsEdit from "../words/WordsEdit";
import InfoHeader from "./InfoHeader";
import * as ThemeContext from "../ThemeContext";
import {ThemeProvider} from "../ThemeContext";
import {useContext} from "react";
import InfoBottom from "./InfoBottom";

const MainContent = function () {
    const { theme } = useContext(ThemeContext.ThemeContext);
    const themed = (clsName) => {
        if(theme === ThemeContext.THEME_NAME_LIGHT) {
            return clsName + "-light";
        }
        return clsName + "-dark";
    };

    return (
        <div className="main-background">
            <InfoHeader />
            <div style={{height: 20}} />
            <div className={themed("main-content")}>
                <WordsEdit use-theme={true} />
                <ReviewCreateView use-theme={true} />
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
