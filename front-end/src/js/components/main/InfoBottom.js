import "../../../css/main/info-bottom.css";
import {useContext} from "react";
import * as ThemeContext from "../ThemeContext";
import {APP_TITLE_ALT, UNI_NAME} from "../../Const";

const InfoBottom = function() {
    const { theme } = useContext(ThemeContext.ThemeContext);
    const themed = (clsName) => {
        if(theme === ThemeContext.THEME_NAME_LIGHT) {
            return clsName + "-light";
        }
        return clsName + "-dark";
    };

    const iconStyles = {
        color: (theme === ThemeContext.THEME_NAME_LIGHT)? "black": "white",
    };
    const mailToUrl = (() => {
        const url = new URL("https://mail.google.com/mail/u/0/?fs=1&tf=cm");
        url.searchParams.set("to", "gmegr18@freeuni.edu.ge");
        url.searchParams.set("subject", "გამარჯობა!");
        return url.toString();
    })();
    return (
        <div className={themed("info-bottom-container")}>
            <div className={themed("info-bottom-center-container")}>
                <div className="info-bottom-title">
                    {APP_TITLE_ALT}
                </div>
                <div className="info-bottom-icons">
                    <a href="https://github.com/Tomarashi" target="_blank">
                        <i className="fa fa-github"
                           style={{
                               fontSize: 38,
                               ...iconStyles,
                           }}
                           title="გადადი პროექტის Github-ზე" />
                    </a>
                    <a href={mailToUrl} target="_blank">
                        <i className="fa fa-envelope"
                           style={{
                               fontSize: 30,
                               ...iconStyles,
                           }}
                           title="მეილის გაგზავნა" />
                    </a>
                </div>
                <div className="info-bottom-texts">
                    <p>საბაკალავრო პროექტი</p>
                    <p>{UNI_NAME}</p>
                </div>
            </div>
        </div>
    );
};

export default InfoBottom;
