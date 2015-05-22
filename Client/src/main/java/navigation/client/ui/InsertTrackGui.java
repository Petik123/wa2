package navigation.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import dto.CoordinateDto;
import dto.TrackDto;
import dto.UserDto;
import navigation.client.NavigationService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Petikk on 18. 5. 2015.
 */
public class InsertTrackGui {

    public static FormPanel getInsertTrackForm() {
        final FormPanel form = new FormPanel();
        VerticalPanel panel = new VerticalPanel();
        Label lname = new Label("Track name");
        panel.add(lname);
        final TextBox name = new TextBox();
        name.setName("name");
        panel.add(name);
        Label lUserId = new Label("User Id");
        panel.add(lUserId);
        final TextBox userId = new TextBox();
        userId.setName("userId");
        panel.add(userId);

        panel.add(new Button("Save", new ClickHandler() {

            public void onClick(ClickEvent event) {
                form.submit();
            }
        }));

        form.addSubmitHandler(new FormPanel.SubmitHandler() {

            public void onSubmit(FormPanel.SubmitEvent event) {
                TrackDto track = new TrackDto();
                track.setName(name.getValue());
                //fullfill random data
                int i = 1 + Random.nextInt(20 - 1);//num of random coordinates, max 20 coordinates
                List<CoordinateDto> list = new ArrayList<CoordinateDto>(i);
                for (int j = 0; j < i; j++) {
                    list.add(new CoordinateDto(50.4, 48.7));
                }
                track.setCoordinates(list);
                Double maxSpeed = 40 + Random.nextDouble() * (300 - 40);
                track.setMaxSpeed(maxSpeed);
                track.setAverageSpeed(maxSpeed / i);
                track.setDate(new Date());
                track.setDuration(0.2+Random.nextDouble()*5);
                track.setLean(Random.nextDouble() * 50);
                UserDto user = new UserDto(Long.parseLong(userId.getValue()));
                track.setUser(user);
                NavigationService.App.getInstance().insertTrack(track, new InsertTrackAsyncCallback());
                form.reset();
            }
        });
        form.setWidget(panel);
//        RootPanel.get("container").add(form);
        return form;
    }

    private static class InsertTrackAsyncCallback implements AsyncCallback<Void> {
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
