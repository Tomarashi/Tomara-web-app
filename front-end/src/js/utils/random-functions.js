import {ASCII_ENG_LETTERS, DIGITS} from "./characters";

const RANDOM_ASCII_LETTERS_N = 8;
const RANDOM_NUMBERS_N = 8;
const INVALID_LENGTH_MESSAGE = "Invalid length";

const randIndexZeroUntilN = (maxValue) => {
    return Math.floor(Math.random() * maxValue);
};

export const randomAsciiLetters = (n = RANDOM_ASCII_LETTERS_N) => {
    if(n <= 0) {
        throw new Error(INVALID_LENGTH_MESSAGE);
    }
    const result = [];
    for(let i = 0; i < n; i++) {
        result.push(ASCII_ENG_LETTERS[randIndexZeroUntilN(ASCII_ENG_LETTERS.length)]);
    }
    return result.join("");
};

export const randomNumber = (n = RANDOM_NUMBERS_N) => {
    if(n <= 0) {
        throw new Error(INVALID_LENGTH_MESSAGE);
    }
    const result = (() => {
        const numbersNoZero = DIGITS.replaceAll("0", "");
        return [numbersNoZero[randIndexZeroUntilN(numbersNoZero.length)]];
    })();
    for(let i = 1; i < n; i++) {
        result.push(DIGITS[randIndexZeroUntilN(DIGITS.length)]);
    }
    return parseInt(result.join(""));
};
