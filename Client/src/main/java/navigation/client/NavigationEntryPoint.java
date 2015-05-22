package navigation.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import dto.RefuelDto;
import dto.TrackDto;
import dto.TripTipsDto;
import dto.UserDto;
import navigation.client.ui.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class NavigationEntryPoint implements EntryPoint {
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        TabPanel tabPanel = new TabPanel();

        //create contents for tabs of tabpanel

        //create titles for tabs
        String tab0Title = "Get Users";
        String tab1Title = "Insert User";
        String tab2Title = "Get Refuels";
        String tab3Title = "Insert Refuel";
        String tab4Title = "Get Tracks";
        String tab5Title = "Record Track";
        String tab6Title = "Get Trip Tips";
        String tab7Title = "Insert Trip Tip";
        String tab8Title = "Send request to compute distance";

        //create tabs
        CellTable<UserDto> userTable = GetUsersGui.getUserTable();
        FormPanel insertUserForm = InsertUserGui.getInsertUserForm();
        CellTable<RefuelDto> table = GetRefuelGui.getRefuelTable();
        FormPanel insertRefuelForm = InsertRefuelGui.getInsertRefuelForm();
        CellTable<TrackDto> trackTable = GetTrackGui.getTrackTable();
        FormPanel insertTrackForm = InsertTrackGui.getInsertTrackForm();
        CellTable<TripTipsDto> tripTipsDtoCellTable = GetTripTipsGui.getTripTipsTable();
        FormPanel insertTripTipForm = InsertTripTipGui.getInsertTripTipForm();

        tabPanel.add(userTable, tab0Title);
        tabPanel.add(insertUserForm, tab1Title);
        tabPanel.add(table, tab2Title);
        tabPanel.add(insertRefuelForm, tab3Title);
        tabPanel.add(trackTable, tab4Title);
        tabPanel.add(insertTrackForm, tab5Title);
        tabPanel.add(tripTipsDtoCellTable, tab6Title);
        tabPanel.add(insertTripTipForm, tab7Title);

        //select first tab
        tabPanel.selectTab(0);
        tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {
            public void onSelection(SelectionEvent<Integer> event) {
                if (event.getSelectedItem() == 0) {
                    NavigationService.App.getInstance().getUsers(GetUsersGui.getNewCallback());
                } else if (event.getSelectedItem() == 2) {
                    NavigationService.App.getInstance().getRefuels(GetRefuelGui.getNewCallback());
                } else if (event.getSelectedItem() == 4) {
                    NavigationService.App.getInstance().getTracks(GetTrackGui.getNewCallback());
                } else if (event.getSelectedItem() == 6) {
                    NavigationService.App.getInstance().getTripTips(GetTripTipsGui.getNewCallback());
                }
                RootPanel.get("result").clear();
            }
        });

        // Add the widgets to the root panel.
        RootPanel.get("container").add(tabPanel);
//
    }
}
