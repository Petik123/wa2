package navigation.client.ui;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;
import dto.UserDto;
import navigation.client.NavigationService;

import java.util.Collections;
import java.util.List;

/**
 * Created by Petikk on 21. 5. 2015.
 */
public class GetUsersGui {
    public static UserAsyncCallbackWrapper userCallbackWrapper;

    public static CellTable<UserDto> getUserTable() {
        CellTable<UserDto> table = getTable();
        ListDataProvider<UserDto> dataProvider = new ListDataProvider<UserDto>();
        dataProvider.addDataDisplay(table);
        List<UserDto> list = dataProvider.getList();
        userCallbackWrapper = new UserAsyncCallbackWrapper(list);
        NavigationService.App.getInstance().getUsers(userCallbackWrapper.getNewCallback());
        return table;
    }

    private static CellTable<UserDto> getTable() {
        final CellTable<UserDto> table = new CellTable<UserDto>();

        TextColumn<UserDto> idColumn = new TextColumn<UserDto>() {
            @Override
            public String getValue(UserDto r) {
                return "" + r.getId();
            }
        };
        table.addColumn(idColumn, "Id");

        TextColumn<UserDto> nameColumn = new TextColumn<UserDto>() {
            @Override
            public String getValue(UserDto r) {
                return r.getName();
            }
        };
        table.addColumn(nameColumn, "Name");

        TextColumn<UserDto> surnameColumn = new TextColumn<UserDto>() {
            @Override
            public String getValue(UserDto r) {
                return r.getSurname();
            }
        };
        table.addColumn(surnameColumn, "Surname");

        TextColumn<UserDto> usernameColumn = new TextColumn<UserDto>() {
            @Override
            public String getValue(UserDto r) {
                return "" + r.getUserName();
            }
        };
        table.addColumn(usernameColumn, "Username");

        ButtonCell deleteButtonCell = new ButtonCell();

        Column delButtonColumn = new Column<UserDto, String>(deleteButtonCell) {
            @Override
            public String getValue(UserDto object) {
                // The value to display in the button.
                return "X";
            }
        };
        table.addColumn(delButtonColumn, "Action");

        delButtonColumn.setFieldUpdater(new FieldUpdater<UserDto, String>() {
            public void update(int index, UserDto object, String value) {
                NavigationService.App.getInstance().deleteUser(object, new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                    }

                    public void onSuccess(Void result) {
                        NavigationService.App.getInstance().getUsers(userCallbackWrapper.getNewCallback());
                    }
                });

            }
        });
        return table;
    }

    /**
     * trackCallbackWrapper is created in method getUserTable!
     *
     * @return null or new Callback
     */
    public static AsyncCallback<List<UserDto>> getNewCallback() {
        if (userCallbackWrapper == null) {
            return null;
        }
        return userCallbackWrapper.getNewCallback();
    }


    private static class UserAsyncCallbackWrapper {
        final List<UserDto> list;
        final HTML html;

        public UserAsyncCallbackWrapper(List<UserDto> list) {
            this.list = list;
            this.html = new HTML();
        }

        GetUserAsyncCallback getNewCallback() {
            RootPanel.get("result").clear();
            RootPanel.get("result").add(html);
            html.setHTML("Loading...");
            return new GetUserAsyncCallback();
        }

        private class GetUserAsyncCallback implements AsyncCallback<List<UserDto>> {

            public void onFailure(Throwable throwable) {
                RootPanel.get("result").clear();
                RootPanel.get("result").add(html);
                html.setHTML("Failed to receive answer from server! " + throwable.getMessage());
            }

            public void onSuccess(List<UserDto> result) {
                list.removeAll(list);
                Collections.copy(list, result);
                RootPanel.get("result").clear();
                RootPanel.get("result").add(html);
                html.setHTML("");
            }
        }
    }


}
