import "../../../css/main/main.css";
import {APP_TITLE_FULL} from "../../Const";
import ReviewCreateView from "../reviews/ReviewCreateView";
import WordsEdit from "../words/WordsEdit";
import InfoHeader from "./InfoHeader";
import {ThemeProvider} from "../ThemeContext";

const Main = function () {
    document.title = APP_TITLE_FULL;

    return (
        <ThemeProvider>
            <div className="main-background">
                <InfoHeader />
                <div className="main-content">
                    <ReviewCreateView use-theme={true} />
                </div>
            </div>
        </ThemeProvider>
    );
};

export default Main;
