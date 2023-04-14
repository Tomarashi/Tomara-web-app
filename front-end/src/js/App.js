import '../css/App.css';
import {APP_TITLE} from "./Const";
import {useEffect, useState} from "react";

const App = function () {
    const [value, updateValue] = useState("");

    useEffect(() => {
        document.title = APP_TITLE;

        fetch("/api/")
            .then(response => response.text())
            .then(data => {
                updateValue(data);
            });
    }, []);

    return (
        <div className="App">
            <div className="App-header">{value}</div>
        </div>
    );
}

export default App;
