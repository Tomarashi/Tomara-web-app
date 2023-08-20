import "../../../css/main/info-header.css";
import {useContext} from "react";
import Switch from "react-switch";
import { FaRegSun, FaRegMoon } from 'react-icons/fa';
import * as ThemeContext from "../ThemeContext";

const InfoHeader = function() {
    const { theme, toggleTheme } = useContext(ThemeContext.ThemeContext);
    const updateTheme = (b) => {
        toggleTheme(ThemeContext.booleanToTheme(b));
    };

    return (
        <div className="info-header-container">
            <Switch
                onChange={updateTheme}
                checked={ThemeContext.themeToBoolean(theme)}
                uncheckedIcon={<FaRegSun />}
                checkedIcon={<FaRegMoon />} />
        </div>
    );
};

export default InfoHeader;
