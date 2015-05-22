package navigation.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import dto.RatingDto;
import dto.TrackDto;
import dto.TripTipsDto;
import navigation.client.NavigationService;

/**
 * Created by Petikk on 18. 5. 2015.
 */
public class InsertTripTipGui {

    public static FormPanel getInsertTripTipForm() {
        final FormPanel form = new FormPanel();
        VerticalPanel verticalPanel = new VerticalPanel();
        Label lname = new Label("Trip tip name");
        verticalPanel.add(lname);
        final TextBox name = new TextBox();
        name.setName("name");
        verticalPanel.add(name);
        Label ldescription = new Label("Description");
        verticalPanel.add(ldescription);
        final TextArea description = new TextArea();
        description.setName("description");
        verticalPanel.add(description);
        Label ldifficulty = new Label("Difficulty");
        verticalPanel.add(ldifficulty);
        final TextBox difficulty = new TextBox();
        difficulty.setName("difficulty");
        verticalPanel.add(difficulty);
        Label lspectacles = new Label("Spectacles");
        verticalPanel.add(lspectacles);
        final TextBox spectacles = new TextBox();
        spectacles.setName("spectacles");
        verticalPanel.add(spectacles);
        Label ltwisty = new Label("Twisty");
        verticalPanel.add(ltwisty);
        final TextBox twisty = new TextBox();
        twisty.setName("twisty");
        verticalPanel.add(twisty);
        Label lsurface = new Label("Surface quality");
        verticalPanel.add(lsurface);
        final TextBox surface = new TextBox();
        surface.setName("surface");
        verticalPanel.add(surface);
        Label lTrackId = new Label("Track id");
        verticalPanel.add(lTrackId);
        final TextBox trackId = new TextBox();
        trackId.setName("trackId");
        verticalPanel.add(trackId);
        form.setWidget(verticalPanel);

        verticalPanel.add(new Button("Save", new ClickHandler() {

            public void onClick(ClickEvent event) {
                form.submit();
            }
        }));

        form.addSubmitHandler(new FormPanel.SubmitHandler() {

            public void onSubmit(FormPanel.SubmitEvent event) {
                if (description.getText().length() == 0 || difficulty.getText().length() == 0 || name.getText().length() == 0
                        || spectacles.getText().length() == 0 || surface.getText().length() == 0 || twisty.getText().length() == 0
                        || trackId.getText().length() == 0) {
                    Window.alert("Text box must not be empty");
                    event.cancel();
                }
                RatingDto rating = new RatingDto();
                rating.setDifficulty(Integer.parseInt(difficulty.getText()));
                rating.setSpectacle(Integer.parseInt(spectacles.getText()));
                rating.setSurfaceQuality(Integer.parseInt(surface.getText()));
                rating.setTwisty(Integer.parseInt(twisty.getText()));
                TripTipsDto tripTipsDto = new TripTipsDto();
                tripTipsDto.setName(name.getText());
                tripTipsDto.setDescription(description.getText());
                tripTipsDto.setRating(rating);
                TrackDto trackDto = new TrackDto(Long.parseLong(trackId.getText()));
                tripTipsDto.setTrack(trackDto);
                NavigationService.App.getInstance().insertTripTip(tripTipsDto, new InsertTripTipAsyncCallback());
                form.reset();
            }
        });
        return form;
    }

    private static class InsertTripTipAsyncCallback implements AsyncCallback<Void> {
        final HTML html = new HTML();

        public void onFailure(Throwable throwable) {
            RootPanel.get("result").clear();
            RootPanel.get("result").add(html);
            html.setHTML("Failed to insert: " + throwable.getMessage());
        }

        public void onSuccess(Void result) {
            RootPanel.get("result").clear();
            RootPanel.get("result").add(html);
            html.setHTML("Inserted");

        }
    }
}
