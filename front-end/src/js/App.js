import {APP_TITLE_FULL} from "./Const";
import WordsEdit from "./components/words/WordsEdit";
import {Route, BrowserRouter, Routes} from "react-router-dom";
import AdminPage from "./components/admin/AdminPage";

const App = function () {
    document.title = APP_TITLE_FULL;

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<WordsEdit />} />
                <Route path="/admin" element={<AdminPage />} />
            </Routes>
        </BrowserRouter>
    );
};

export default App;
