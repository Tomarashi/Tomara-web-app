const charRangeToList = (startChar, endChar, endInclusive = true) => {
    const list = [];
    const endC = endChar.charCodeAt(0) + ((endInclusive)? 1: 0);
    for(let c = startChar.charCodeAt(0); c < endC; c++) {
        list.push(String.fromCharCode(c));
    }
    return list;
};

export const ARROW_KEY_NAMES = ["ArrowLeft", "ArrowUp", "ArrowRight", "ArrowDown"];
export const BACKSPACE_KEY_NAME = "Backspace";
export const DELETE_KEY_NAME = "Delete";
export const DELETE_KEY_NAMES = [BACKSPACE_KEY_NAME, DELETE_KEY_NAME];

export const DIGITS = "0123456789";

export const GEO_CHARS_LIST = charRangeToList("ა", "ჰ");
export const GEO_CHARS_SET = new Set(GEO_CHARS_LIST);

export const ASCII_ENG_LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
export const ENG_UPPER_CHARS_LIST = charRangeToList("A", "Z");
export const ENG_UPPER_CHARS_SET = new Set(ENG_UPPER_CHARS_LIST);

export const ENG_TO_GEO_CHARS_MAP = (() => {
    const SORTED_ENGS = "abgdevzTiklmnopJrstufqRySCcZwWxjh";
    const result = {};
    for(let i = 0; i < SORTED_ENGS.length; i++) {
        result[SORTED_ENGS[i]] = GEO_CHARS_LIST[i];
    }
    return result;
})();
