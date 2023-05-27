export const encodeUrlParams = (params, withQuestionMark = true) => {
    return ((withQuestionMark)? "?": "") + Object.keys(params).map((name) => {
        return encodeURIComponent(name) + "=" + encodeURIComponent(params[name]);
    }).join("&");
};
