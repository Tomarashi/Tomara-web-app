class Logger {
    static _instance = null;
    static _named_instance = {};
    static Types = {
        Log: 0,
        Info: 1,
        Warn: 2,
        Error: 3,
    }

    static newInstance(loggerName = null) {
        if(loggerName) {
            let instance = Logger._named_instance[loggerName];
            if(!instance) {
                instance = new Logger(loggerName);
                Logger._named_instance[loggerName] = instance;
            }
            return instance;
        } else {
            if (Logger._instance === null) {
                Logger._instance = new Logger();
            }
            return Logger._instance;
        }
    }

    _loggerName = null

    constructor(loggerName = null) {
        this._loggerName = loggerName;
    }

    log(data) {
        this._log(Logger.Types.Log, data);
    }

    info(data) {
        this._log(Logger.Types.Info, data);
    }

    warn(data) {
        this._log(Logger.Types.Warn, data);
    }

    error(data) {
        this._log(Logger.Types.Error, data);
    }

    _log(logType, data) {
        if(logType === Logger.Types.Log) {
            console.log(data);
        } else {
            const output = `${
                ((this._loggerName)? `[${this._loggerName}]`: "")
            }[${
                Logger._logTypeToChar(logType)
            }][${Logger._dateToString()}]: ${data}`;
            if(logType === Logger.Types.Error) {
                console.error(output);
            } else if(logType === Logger.Types.Info) {
                console.log(output);
            } else if(logType === Logger.Types.Warn) {
                console.warn(output);
            }
        }
    }

    static _dateToString() {
        const pad = (num) => {
            num = num.toString();
            if(num.length <= 2) {
                return num.padStart(2, "0");
            } else if(num.length <= 4) {
                return num.padStart(4, "0");
            }
            return num;
        };
        const date = new Date();
        return `${
            date.getFullYear()
        }:${
            pad(date.getMonth() + 1)
        }:${
            pad(date.getDate())
        } ${
            pad(date.getHours())
        }:${
            pad(date.getMinutes())
        }:${
            pad(date.getSeconds())
        }`;
    }

    static _logTypeToChar(logType) {
        switch(logType) {
            case Logger.Types.Info: return "Info";
            case Logger.Types.Warn: return "Warn";
            case Logger.Types.Error: return "Err ";
            default: return "";
        }
    }
}

export default Logger;