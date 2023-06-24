import "../../../../css/admin/logout-button.css";

const LogoutButton = function () {
    return (
        <div className="logout-button-main">
            <a className="logout-button-inner" href="/logout">
                გასვლა
            </a>
        </div>
    );
};

export default LogoutButton;
