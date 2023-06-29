import "../../../../css/admin/tabs-wrapper.css";
import {Tab, TabList, TabPanel, Tabs} from "react-tabs";
import OffersMonitorView from "./OffersMonitorView";
import WordsEdit from "../../words/WordsEdit";

const TabsWrapper = function () {
    return (
        <Tabs
            defaultIndex={0}
            className="tabs-wrapper-background-tab-main"
            selectedTabClassName="tabs-wrapper-background-tab-list-li-active">
            <TabList className="tabs-wrapper-background-tab-list">
                <Tab>შეთავაზებები</Tab>
                <Tab>სიტყვების ძებნა</Tab>
            </TabList>
            <TabPanel>
                <OffersMonitorView />
            </TabPanel>
            <TabPanel>
                <WordsEdit />
            </TabPanel>
        </Tabs>
    );
};

export default TabsWrapper;
