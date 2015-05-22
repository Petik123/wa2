package navigation.client.ui;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextButtonCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import dto.CoordinateDto;
import dto.TrackDto;
import navigation.client.NavigationService;

import java.util.Collections;
import java.util.List;

/**
 * Created by Petikk on 18. 5. 2015.
 */
public class GetTrackGui {
    public static TrackAsyncCallbackWrapper trackCallbackWrapper;

    public static CellTable<TrackDto> getTrackTable() {
        CellTable<TrackDto> table = getTable();
        ListDataProvider<TrackDto> dataProvider = new ListDataProvider<TrackDto>();
        dataProvider.addDataDisplay(table);
        List<TrackDto> list = dataProvider.getList();
        trackCallbackWrapper = new TrackAsyncCallbackWrapper(list);
//        NavigationService.App.getInstance().getTracks(trackCallbackWrapper.getNewCallback());
        return table;
    }

    private static CellTable<TrackDto> getTable() {
        final CellTable<TrackDto> table = new CellTable<TrackDto>();

        TextColumn<TrackDto> dateColumn = new TextColumn<TrackDto>() {
            @Override
            public String getValue(TrackDto r) {
                return "" + r.getDate();
            }
        };
        table.addColumn(dateColumn, "Date");

        TextColumn<TrackDto> nameColumn = new TextColumn<TrackDto>() {
            @Override
            public String getValue(TrackDto r) {
                return r.getName();
            }
        };
        table.addColumn(nameColumn, "Track's name");

        TextColumn<TrackDto> avgColumn = new TextColumn<TrackDto>() {
            @Override
            public String getValue(TrackDto r) {
                return "" + r.getAverageSpeed();
            }
        };
        table.addColumn(avgColumn, "Average speed");

        TextColumn<TrackDto> maxColumn = new TextColumn<TrackDto>() {
            @Override
            public String getValue(TrackDto r) {
                return "" + r.getMaxSpeed();
            }
        };

        table.addColumn(maxColumn, "Max speed");

        TextColumn<TrackDto> distanceColumn = new TextColumn<TrackDto>() {
            @Override
            public String getValue(TrackDto r) {
                return "" + r.getDistance();
            }
        };

        table.addColumn(distanceColumn, "Distance");

        final TextButtonCell distanceButtonCell = new TextButtonCell();

        final Column<TrackDto, String> distanceButtonColumn = new Column<TrackDto, String>(distanceButtonCell) {
            @Override
            public String getValue(TrackDto object) {
                if (object.getDistance() == null) {
                    distanceButtonCell.setEnabled(true);
                } else {
                    distanceButtonCell.setEnabled(false);
                }
                return "Compute";
            }
        };
        distanceButtonColumn.setFieldUpdater(new FieldUpdater<TrackDto, String>() {
            public void update(final int index, final TrackDto object, String value) {
                object.setDistance(-1.0);
                table.redrawRow(index);
                NavigationService.App.getInstance().getDistance(object, new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                        HTML html = new HTML();
                        RootPanel.get("result").clear();
                        RootPanel.get("result").add(html);
                        html.setHTML("Failed to send request!");
                        object.setDistance(null);
                        table.redrawRow(index);
                    }

                    public void onSuccess(Void result) {
                        final HTML html = new HTML();
                        RootPanel.get("result").clear();
                        RootPanel.get("result").add(html);
                        html.setHTML("Computing...");
                        //wait until result
                        NavigationService.App.getInstance().getTrackByIdUntilDistance(object, new AsyncCallback<TrackDto>() {
                            public void onFailure(Throwable caught) {
                                HTML html = new HTML();
                                RootPanel.get("result").clear();
                                RootPanel.get("result").add(html);
                                html.setHTML("Failed!");
                            }

                            public void onSuccess(TrackDto result) {
                                object.setDistance(result.getDistance());
                                table.redrawRow(index);
                                html.setHTML("");
                            }
                        });
                    }

                });
            }
        });

        table.addColumn(distanceButtonColumn, "");

        TextColumn<TrackDto> durColumn = new TextColumn<TrackDto>() {
            @Override
            public String getValue(TrackDto r) {
                return "" + r.getDuration();
            }
        };

        table.addColumn(durColumn, "Duration");

        TextColumn<TrackDto> leanColumn = new TextColumn<TrackDto>() {
            @Override
            public String getValue(TrackDto r) {
                return "" + r.getLean();
            }
        };

        table.addColumn(leanColumn, "Max lean");

        TextColumn<TrackDto> userIdColumn = new TextColumn<TrackDto>() {
            @Override
            public String getValue(TrackDto r) {
                return "" + r.getUser().getId();
            }
        };
        table.addColumn(userIdColumn, "User Id");

        TextColumn<TrackDto> uNameColumn = new TextColumn<TrackDto>() {
            @Override
            public String getValue(TrackDto r) {
                return "" + r.getUser().getName();
            }
        };
        table.addColumn(uNameColumn, "User's name");

        TextColumn<TrackDto> uSurnameColumn = new TextColumn<TrackDto>() {
            @Override
            public String getValue(TrackDto r) {
                return "" + r.getUser().getSurname();
            }
        };
        table.addColumn(uSurnameColumn, "Surname");

        ButtonCell showCoordinatesButtonCell = new ButtonCell();

        final Column coorButtonColumn = new Column<TrackDto, String>(showCoordinatesButtonCell) {

            @Override
            public String getValue(TrackDto object) {
                return "Coordinates";
            }
        };
        table.addColumn(coorButtonColumn, "Coordinates");

        final DialogWrapper dialogWrapper = new DialogWrapper();
        final DialogBox dialogBox = dialogWrapper.createDialogBox();

        coorButtonColumn.setFieldUpdater(new FieldUpdater<TrackDto, String>() {
            public void update(int index, TrackDto object, String value) {
                dialogBox.show();
                NavigationService.App.getInstance().getTrackByIdWithCoordinates(object, new AsyncCallback<TrackDto>() {
                    public void onFailure(Throwable caught) {
                        dialogBox.hide();
                    }

                    public void onSuccess(TrackDto trackDto) {
                        ListDataProvider<CoordinateDto> dataProvider = new ListDataProvider<CoordinateDto>();
                        dataProvider.addDataDisplay(dialogWrapper.dialogTable);
                        List<CoordinateDto> list = dataProvider.getList();
                        Collections.copy(list, trackDto.getCoordinates());
                    }
                });
            }
        });

        ButtonCell deleteButtonCell = new ButtonCell();

        Column delButtonColumn = new Column<TrackDto, String>(deleteButtonCell) {
            @Override
            public String getValue(TrackDto object) {
                // The value to display in the button.
                return "X";
            }
        };
        table.addColumn(delButtonColumn, "Action");

        delButtonColumn.setFieldUpdater(new FieldUpdater<TrackDto, String>() {
            public void update(int index, TrackDto object, String value) {
                NavigationService.App.getInstance().deleteTrack(object, new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                        HTML html = new HTML();
                        RootPanel.get("result").clear();
                        RootPanel.get("result").add(html);
                        html.setHTML("Failed! " + caught.getMessage());
                    }

                    public void onSuccess(Void result) {
                        NavigationService.App.getInstance().getTracks(trackCallbackWrapper.getNewCallback());
                    }
                });

            }
        });
        return table;
    }

    private static class DialogWrapper {
        private CellTable<CoordinateDto> dialogTable;

        private DialogBox createDialogBox() {
            final DialogBox dialogBox = new DialogBox();
            dialogBox.setText("Coordinates");
            VerticalPanel panel = new VerticalPanel();
            dialogBox.setWidget(panel);
            dialogTable = new CellTable<CoordinateDto>();
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
    public static AsyncCallback<List<TrackDto>> getNewCallback() {
        if (trackCallbackWrapper == null) {
            return null;
        }
        return trackCallbackWrapper.getNewCallback();
    }


    private static class TrackAsyncCallbackWrapper {
        final List<TrackDto> list;
        final HTML html;

        public TrackAsyncCallbackWrapper(List<TrackDto> list) {
            this.list = list;
            this.html = new HTML();
        }

        GetTrackAsyncCallback getNewCallback() {
            RootPanel.get("result").clear();
            RootPanel.get("result").add(html);
            html.setHTML("Loading...");
            return new GetTrackAsyncCallback();
        }

        private class GetTrackAsyncCallback implements AsyncCallback<List<TrackDto>> {

            public void onFailure(Throwable throwable) {
                RootPanel.get("result").clear();
                RootPanel.get("result").add(html);
                html.setHTML("Failed to receive answer from server! " + throwable.getMessage());
            }

            public void onSuccess(List<TrackDto> result) {
                list.removeAll(list);
                Collections.copy(list, result);
                RootPanel.get("result").clear();
                RootPanel.get("result").add(html);
                html.setHTML("");
            }
        }
    }


}
