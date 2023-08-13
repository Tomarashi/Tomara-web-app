import {Route, BrowserRouter, Routes} from "react-router-dom";
import AdminPage from "./components/admin/AdminPage";
import Main from "./components/main/Main";

const App = function () {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Main />} />
                <Route path="/admin" element={<AdminPage />} />
            </Routes>
        </BrowserRouter>
    );
};

export default App;
