package navigation.client.ui;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import dto.CoordinateDto;
import dto.TripTipsDto;
import navigation.client.NavigationService;

import java.util.Collections;
import java.util.List;

/**
 * Created by Petikk on 18. 5. 2015.
 */
public class GetTripTipsGui {
    public static TripTipsAsyncCallbackWrapper tripTipsCallbackWrapper;

    public static CellTable<TripTipsDto> getTripTipsTable() {
        CellTable<TripTipsDto> table = getTable();
        ListDataProvider<TripTipsDto> dataProvider = new ListDataProvider<TripTipsDto>();
        dataProvider.addDataDisplay(table);
        List<TripTipsDto> list = dataProvider.getList();
        tripTipsCallbackWrapper = new TripTipsAsyncCallbackWrapper(list);
        return table;
    }

    private static CellTable<TripTipsDto> getTable() {
        final CellTable<TripTipsDto> table = new CellTable<TripTipsDto>();

        TextColumn<TripTipsDto> nameColumn = new TextColumn<TripTipsDto>() {
            @Override
            public String getValue(TripTipsDto r) {
                return r.getName();
            }
        };
        table.addColumn(nameColumn, "Trip name");

        TextColumn<TripTipsDto> descriptionColumn = new TextColumn<TripTipsDto>() {
            @Override
            public String getValue(TripTipsDto r) {
                return r.getDescription();
            }
        };
        table.addColumn(descriptionColumn, "Description");

        TextColumn<TripTipsDto> difficultyColumn = new TextColumn<TripTipsDto>() {
            @Override
            public String getValue(TripTipsDto r) {
                return "" + r.getRating().getDifficulty();
            }
        };
        table.addColumn(difficultyColumn, "Difficulty");

        TextColumn<TripTipsDto> spectColumn = new TextColumn<TripTipsDto>() {
            @Override
            public String getValue(TripTipsDto r) {
                return "" + r.getRating().getSpectacle();
            }
        };

        table.addColumn(spectColumn, "Spectacles");

        TextColumn<TripTipsDto> twistyColumn = new TextColumn<TripTipsDto>() {
            @Override
            public String getValue(TripTipsDto r) {
                return "" + r.getRating().getTwisty();
            }
        };

        table.addColumn(twistyColumn, "Twisty");

        TextColumn<TripTipsDto> surfaceColumn = new TextColumn<TripTipsDto>() {
            @Override
            public String getValue(TripTipsDto r) {
                return "" + r.getRating().getSurfaceQuality();
            }
        };

        table.addColumn(surfaceColumn, "Surface quality");

        ButtonCell showCoordinatesButtonCell = new ButtonCell();

        final Column coorButtonColumn = new Column<TripTipsDto, String>(showCoordinatesButtonCell) {
            @Override
            public String getValue(TripTipsDto object) {
                return "Coordinates";
            }
        };
        table.addColumn(coorButtonColumn, "Coordinates");


        final DialogWrapper dialogWrapper=new DialogWrapper();
        final DialogBox dialogBox=dialogWrapper.createDialogBox();

        coorButtonColumn.setFieldUpdater(new FieldUpdater<TripTipsDto, String>() {
            public void update(int index, TripTipsDto object, String value) {
                dialogBox.show();
                NavigationService.App.getInstance().getTripTipsByIdWithCoordinates(object, new AsyncCallback<TripTipsDto>() {
                    public void onFailure(Throwable caught) {
                        dialogBox.hide();
                    }

                    public void onSuccess(TripTipsDto TripTipsDto) {
                        ListDataProvider<CoordinateDto> dataProvider = new ListDataProvider<CoordinateDto>();
                        dataProvider.addDataDisplay(dialogWrapper.dialogTable);
                        List<CoordinateDto> list = dataProvider.getList();
                        Collections.copy(list, TripTipsDto.getTrack().getCoordinates());
                    }
                });
            }
        });

        ButtonCell deleteButtonCell = new ButtonCell();

        Column delButtonColumn = new Column<TripTipsDto, String>(deleteButtonCell) {
            @Override
            public String getValue(TripTipsDto object) {
                // The value to display in the button.
                return "X";
            }
        };
        table.addColumn(delButtonColumn, "Action");

        delButtonColumn.setFieldUpdater(new FieldUpdater<TripTipsDto, String>() {
            public void update(int index, TripTipsDto object, String value) {
                NavigationService.App.getInstance().deleteTripTip(object, new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                        HTML html = new HTML();
                        RootPanel.get("result").clear();
                        RootPanel.get("result").add(html);
                        html.setHTML("Failed" + caught.getMessage());
                    }

                    public void onSuccess(Void result) {
                        NavigationService.App.getInstance().getTripTips(tripTipsCallbackWrapper.getNewCallback());
                    }
                });

            }
        });
        return table;
    }

    private static class DialogWrapper{
        private CellTable<CoordinateDto> dialogTable;

        private DialogBox createDialogBox() {
            final DialogBox dialogBox = new DialogBox();
            dialogBox.setText("Coordinates");
            VerticalPanel panel = new VerticalPanel();
            dialogBox.setWidget(panel);
            dialogTable=new CellTable<CoordinateDto>();
            TextColumn<CoordinateDto> latColumn = new TextColumn<CoordinateDto>() {
                @Override
                public String getValue(CoordinateDto r) {
                    return "" + r.getLat();
                }
            };

            dialogTable.addColumn(latColumn, "Lat");

            TextColumn<CoordinateDto> lonColumn = new TextColumn<CoordinateDto>() {
                @Override
                public String getValue(CoordinateDto r) {
                    return "" + r.getLon();
                }
            };

            dialogTable.addColumn(lonColumn, "Lon");
            Button closeButton = new Button(
                    "Close", new ClickHandler() {
                public void onClick(ClickEvent event) {
                    dialogBox.hide();
                }
            });
            panel.add(dialogTable);
            panel.add(closeButton);
            panel.setCellHorizontalAlignment(
                    closeButton, HasHorizontalAlignment.ALIGN_CENTER);

            dialogBox.setGlassEnabled(true);
            dialogBox.setAnimationEnabled(true);
            dialogBox.center();
            dialogBox.hide();
            return dialogBox;
        }

    }


    /**
     * trackCallbackWrapper is created in method getTrackTable!
     *
     * @return null or new Callback
     */
    public static AsyncCallback<List<TripTipsDto>> getNewCallback() {
        if (tripTipsCallbackWrapper == null) {
            return null;
        }
        return tripTipsCallbackWrapper.getNewCallback();
    }


    private static class TripTipsAsyncCallbackWrapper {
        final List<TripTipsDto> list;
        final HTML html;

        public TripTipsAsyncCallbackWrapper(List<TripTipsDto> list) {
            this.list = list;
            this.html = new HTML();
        }

        GetTrackAsyncCallback getNewCallback() {
            RootPanel.get("result").clear();
            RootPanel.get("result").add(html);
            html.setHTML("Loading...");
            return new GetTrackAsyncCallback();
        }

        private class GetTrackAsyncCallback implements AsyncCallback<List<TripTipsDto>> {

            public void onFailure(Throwable throwable) {
                RootPanel.get("result").clear();
                RootPanel.get("result").add(html);
                html.setHTML("Failed to receive answer from server! " + throwable.getMessage());
            }

            public void onSuccess(List<TripTipsDto> result) {
                list.removeAll(list);
                Collections.copy(list, result);
                RootPanel.get("result").clear();
                RootPanel.get("result").add(html);
                html.setHTML("");
            }
        }
    }


}
