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
import dto.RefuelDto;
import navigation.client.NavigationService;

import java.util.Collections;
import java.util.List;

/**
 * Created by Petikk on 18. 5. 2015.
 */
public class GetRefuelGui {
    public static RefuelAsyncCallbackWrapper refuelCallbackWrapper;

    public static CellTable<RefuelDto> getRefuelTable() {
        CellTable<RefuelDto> table = getTable();
        ListDataProvider<RefuelDto> dataProvider = new ListDataProvider<RefuelDto>();
        dataProvider.addDataDisplay(table);
        List<RefuelDto> list = dataProvider.getList();
        refuelCallbackWrapper = new RefuelAsyncCallbackWrapper(list);
        return table;
    }

    private static CellTable<RefuelDto> getTable() {

        CellTable<RefuelDto> table = new CellTable<RefuelDto>();
        TextColumn<RefuelDto> dateColumn = new TextColumn<RefuelDto>() {
            @Override
            public String getValue(RefuelDto r) {
                return r.getDate().toString();
            }
        };
        table.addColumn(dateColumn, "Date");

        TextColumn<RefuelDto> amountColumn = new TextColumn<RefuelDto>() {
            @Override
            public String getValue(RefuelDto r) {
                return "" + r.getAmount();
            }
        };
        table.addColumn(amountColumn, "Amount");

        TextColumn<RefuelDto> pricePerUnitColumn = new TextColumn<RefuelDto>() {
            @Override
            public String getValue(RefuelDto r) {
                return "" + r.getPricePerUnit();
            }
        };

        table.addColumn(pricePerUnitColumn, "Price per unit");

        TextColumn<RefuelDto> priceColumn = new TextColumn<RefuelDto>() {
            @Override
            public String getValue(RefuelDto r) {
                return "" + r.getPrice();
            }
        };
        table.addColumn(priceColumn, "Total Price");

        TextColumn<RefuelDto> userIdColumn = new TextColumn<RefuelDto>() {
            @Override
            public String getValue(RefuelDto r) {
                return "" + r.getUserId();
            }
        };
        table.addColumn(userIdColumn, "User Id");

        TextColumn<RefuelDto> uNameColumn = new TextColumn<RefuelDto>() {
            @Override
            public String getValue(RefuelDto r) {
                return "" + r.getuName();
            }
        };
        table.addColumn(uNameColumn, "Name");

        TextColumn<RefuelDto> uSurnameColumn = new TextColumn<RefuelDto>() {
            @Override
            public String getValue(RefuelDto r) {
                return "" + r.getuSurname();
            }
        };
        table.addColumn(uSurnameColumn, "Surname");


        ButtonCell buttonCell = new ButtonCell();

        Column buttonColumn = new Column<RefuelDto, String>(buttonCell) {
            @Override
            public String getValue(RefuelDto object) {
                // The value to display in the button.
                return "X";
            }
        };
        table.addColumn(buttonColumn, "Action");

        buttonColumn.setFieldUpdater(new FieldUpdater<RefuelDto, String>() {
            public void update(int index, RefuelDto object, String value) {
                NavigationService.App.getInstance().deleteRefuel(object, new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                    }

                    public void onSuccess(Void result) {
                        NavigationService.App.getInstance().getRefuels(refuelCallbackWrapper.getNewCallback());
                    }
                });

            }
        });
        return table;
    }

    /**
     * refuelCallbackWrapper is created in method getRefuelTable!
     *
     * @return null or new Callback
     */
    public static AsyncCallback<List<RefuelDto>> getNewCallback() {
        if (refuelCallbackWrapper == null) {
            return null;
        }
        return refuelCallbackWrapper.getNewCallback();
    }


    private static class RefuelAsyncCallbackWrapper {
        final List<RefuelDto> list;
        final HTML html;

        public RefuelAsyncCallbackWrapper(List<RefuelDto> list) {
            this.list = list;
            this.html = new HTML();
        }

        GetRefuelsAsyncCallback getNewCallback() {
            RootPanel.get("result").clear();
            RootPanel.get("result").add(html);
            html.setHTML("Loading...");
            return new GetRefuelsAsyncCallback();
        }

        private class GetRefuelsAsyncCallback implements AsyncCallback<List<RefuelDto>> {

            public void onFailure(Throwable throwable) {
                RootPanel.get("result").clear();
                RootPanel.get("result").add(html);
                html.setHTML("Failed to receive answer from server!" + throwable.getMessage());
            }

            public void onSuccess(List<RefuelDto> result) {
                list.removeAll(list);
                Collections.copy(list, result);
                RootPanel.get("result").clear();
                RootPanel.get("result").add(html);
                html.setHTML("");
            }
        }
    }


}
