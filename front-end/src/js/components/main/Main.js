import "../../../css/main/main.css";
import {APP_TITLE_FULL} from "../../Const";
import ReviewCreateView from "../reviews/ReviewCreateView";

const Main = function () {
    document.title = APP_TITLE_FULL;

    return (
        <div className="main-background">
            <ReviewCreateView />
        </div>
    );
};

export default Main;
