class StringsSet {
    constructor(initValues = null) {
        this._values = new Set();
        (initValues || []).forEach(v => {
            this.add(v);
        });
    }

    _checkType(value) {
        if(value === undefined || value === null) {
            throw new Error("Value is undefined/null");
        }
        if(typeof value !== "string" && !(value instanceof String)) {
            throw new Error("Type must be string");
        }
    }

    _preValidate(value) {
        return value.toUpperCase();
    }

    add(value) {
        this._checkType(value);
        this._values.add(this._preValidate(value));
    }

    has(value) {
        this._checkType(value);
        return this._values.has(this._preValidate(value));
    }

    size() {
        return this._values.size;
    }
}

export default StringsSet;
