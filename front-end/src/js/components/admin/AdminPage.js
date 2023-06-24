import "../../../css/admin/admin.css";
import WebMetricsBox from "./components/WebMetricsBox";
import LogoutButton from "./components/LogoutButton";
import TabsWrapper from "./components/TabsWrapper";

const AdminPage = function() {
    return (
        <div className="admin-content-background">
            <div className="admin-content-background-left">
                <div className="admin-content-background-left-column">
                    <WebMetricsBox />
                    <LogoutButton />
                </div>
            </div>
            <div />
            <div className="admin-content-background-right">
                <TabsWrapper />
            </div>
        </div>
    );
};

export default AdminPage;
