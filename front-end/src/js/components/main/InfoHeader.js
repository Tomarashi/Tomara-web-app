import "../../../css/main/info-header.css";
import LogoMinImage from "../../../media/logo/logo-loader-min.png";
import Switch from "react-switch";
import { FaSun, FaMoon } from "react-icons/fa";
import * as ThemeContext from "../ThemeContext";
import {APP_TITLE, APP_TITLE_ENG} from "../../Const";
import {useTheme} from "../ThemeUtils";

const LogoHolder = function() {
    const [withTheme] = useTheme();

    return (
        <div className={withTheme("info-header-logo-holder-container")}>
            <img className="info-header-logo-holder-logo" src={LogoMinImage}  alt="ტომარას ლოგო" />
            <div className={withTheme("info-header-logo-holder-title")}>
                <span className="info-header-logo-holder-title-top">{APP_TITLE}</span>
                <span className="info-header-logo-holder-title-bottom">{APP_TITLE_ENG}</span>
            </div>
        </div>
    );
};

const SwitchButton = function() {
    const [withTheme, theme, toggleTheme] = useTheme();
    const updateTheme = (b) => {
        toggleTheme(ThemeContext.booleanToTheme(b));
    };

    return (
        <div className={withTheme("info-header-switch-button-container")}>
            <Switch
                onChange={updateTheme}
                checked={ThemeContext.themeToBoolean(theme)}
                onColor={"#121212"}
                onHandleColor={"#ffffff"}
                offColor={"#87CEEB"}
                offHandleColor={"#ffff00"}
                width={78}
                height={40}
                handleDiameter={40}
                activeBoxShadow="none"
                uncheckedIcon={<FaSun className="info-header-switch-button-sun-icon" />}
                checkedIcon={<FaMoon className="info-header-switch-button-moon-icon" />} />
            <span className={withTheme("info-header-switch-button-text")}>
                {(theme === ThemeContext.THEME_NAME_LIGHT)? "დღე": "ღამე"}
            </span>
        </div>
    );
};

const InfoHeader = function() {
    const [withTheme] = useTheme();

    return (
        <div className={withTheme("info-header-container")}>
            <LogoHolder />
            <SwitchButton />
        </div>
    );
};

export default InfoHeader;
