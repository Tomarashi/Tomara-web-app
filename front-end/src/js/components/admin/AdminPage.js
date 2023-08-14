import "../../../css/admin/admin.css";
import WebMetricsBox from "./sub/WebMetricsBox";
import LogoutButton from "./sub/LogoutButton";
import TabsWrapper from "./sub/TabsWrapper";
import {APP_TITLE_FULL_ADMIN} from "../../Const";

const AdminPage = function() {
    document.title = APP_TITLE_FULL_ADMIN;

    return (
        <div className="admin-content-background">
            <div className="admin-content-background-left">
                <TabsWrapper />
            </div>
            <div className="admin-content-background-right">
                <div className="admin-content-background-right-column">
                    <WebMetricsBox />
                    <LogoutButton />
                </div>
            </div>
        </div>
    );
};

export default AdminPage;
