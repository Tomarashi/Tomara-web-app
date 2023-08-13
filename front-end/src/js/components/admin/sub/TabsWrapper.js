import "../../../../css/admin/tabs-wrapper.css";
import React from "react";
import {Tab, TabList, TabPanel, Tabs} from "react-tabs";
import OffersMonitorView from "./OffersMonitorView";
import WordsEdit from "../../words/WordsEdit";
import ReviewAdminView from "./ReviewAdminView";

const TabsContentCenter = function (props) {
    const children = React.Children.toArray(props.children);
    if(children.length !== 1) {
        throw new Error("Component should contain only one child");
    }

    return (
        <div className="tabs-wrapper-background-tab-content-center">
            {children}
        </div>
    );
};

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
                <TabsContentCenter>
                    <OffersMonitorView />
                </TabsContentCenter>
            </TabPanel>
            <TabPanel>
                <TabsContentCenter>
                    <WordsEdit />
                </TabsContentCenter>
            </TabPanel>
            <TabPanel>
                <TabsContentCenter>
                    <ReviewAdminView />
                </TabsContentCenter>
            </TabPanel>
        </Tabs>
    );
};

export default TabsWrapper;
