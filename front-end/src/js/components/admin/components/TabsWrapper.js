import "../../../../css/admin/tabs-wrapper.css";
import {Tab, TabList, TabPanel, Tabs} from "react-tabs";
import OffersMonitorView from "./OffersMonitorView";
import WordsEdit from "../../words/WordsEdit";
import ReviewView from "./ReviewView";

const TabsWrapper = function () {
    return (
        <Tabs
            defaultIndex={0}
            className="tabs-wrapper-background-tab-main"
            selectedTabClassName="tabs-wrapper-background-tab-list-li-active">
            <TabList className="tabs-wrapper-background-tab-list">
                <Tab>შეთავაზებები</Tab>
                <Tab>სიტყვების ძებნა</Tab>
                <Tab>მიმოხილვები</Tab>
            </TabList>
            <TabPanel>
                <OffersMonitorView />
            </TabPanel>
            <TabPanel>
                <div style={{margin: 0, width: "100%", height: "100%"}}>
                    <WordsEdit />
                </div>
            </TabPanel>
            <TabPanel>
                <ReviewView />
            </TabPanel>
        </Tabs>
    );
};

export default TabsWrapper;
