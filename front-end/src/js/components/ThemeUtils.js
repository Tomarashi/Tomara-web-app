import * as ThemeContext from "./ThemeContext";
import {useContext} from "react";

const LIGHT_THEME_SUFFIX = "-light";
const DARK_THEME_SUFFIX = "-dark";

const needDefaultThemeForPathname = () => {
    const pathname = window.location.pathname.toLowerCase();
    return pathname.startsWith("/admin");
};

export const useTheme = function() {
    const { theme, toggleTheme } = useContext(ThemeContext.ThemeContext);
    const withTheme = (className) => {
        if(needDefaultThemeForPathname() || theme === ThemeContext.THEME_NAME_LIGHT) {
            return className + LIGHT_THEME_SUFFIX;
        }
        return className + DARK_THEME_SUFFIX;
    };

    return [withTheme, theme, toggleTheme];
};
