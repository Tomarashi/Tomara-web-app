import "../../../../css/admin/tabs-wrapper.css";
import {Tab, TabList, TabPanel, Tabs} from "react-tabs";
import OffersMonitorView from "./OffersMonitorView";

const TabsWrapper = function () {
    return (
        <Tabs
            defaultIndex={0}
            className="tabs-wrapper-background-tab-main"
            selectedTabClassName="tabs-wrapper-background-tab-list-li-active">
            <TabList className="tabs-wrapper-background-tab-list">
                <Tab>შეთავაზებები</Tab>
                <Tab>Title 2</Tab>
            </TabList>
            <TabPanel>
                <OffersMonitorView />
            </TabPanel>
            <TabPanel>
                <h2>Content 2</h2>
            </TabPanel>
        </Tabs>
    );
};

export default TabsWrapper;
