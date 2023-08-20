import {createContext, useState} from "react";
import {useCookies} from "react-cookie";

const THEME_COOKIE_NAME = "tomara-app-theme";

export const THEME_NAME_LIGHT = "light";
export const THEME_NAME_DARK = "dark";
export const THEME_NAME_DEFAULT = THEME_NAME_LIGHT;

export const booleanToTheme = (bool) => {
    return (bool)? THEME_NAME_DARK: THEME_NAME_LIGHT;
};
export const themeToBoolean = (theme) => {
    if(theme === THEME_NAME_DARK) {
        return true;
    } else if(theme === THEME_NAME_LIGHT) {
        return false;
    }
    throw new Error(`Unknown theme: ${theme}`);
};

export const ThemeContext = createContext({});

export const ThemeProvider = function({children}) {
    const [cookies, setCookie] = useCookies([THEME_COOKIE_NAME]);
    const [theme, setTheme] = useState(cookies[THEME_COOKIE_NAME] || THEME_NAME_DEFAULT);
    const toggleTheme = () => {
        setTheme(prevTheme => {
            const newTheme = (prevTheme === THEME_NAME_LIGHT)? THEME_NAME_DARK: THEME_NAME_LIGHT;
            setCookie(THEME_COOKIE_NAME, newTheme, {path: "/"});
            return newTheme;
        });
    };

    return (
        <ThemeContext.Provider value={{ theme, toggleTheme }}>
            {children}
        </ThemeContext.Provider>
    );
};
