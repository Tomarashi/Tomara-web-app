import "../../../css/admin/admin.css";
import WebMetricsBox from "./components/WebMetricsBox";
import LogoutButton from "./components/LogoutButton";
import TabsWrapper from "./components/TabsWrapper";

const AdminPage = function() {
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
