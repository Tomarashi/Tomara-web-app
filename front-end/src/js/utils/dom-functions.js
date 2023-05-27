export const getBody = () => {
    return document.body || document.getElementsByTagName("body")[0];
};

export const getHead = () => {
    return document.head || document.getElementsByTagName("head")[0];
};
