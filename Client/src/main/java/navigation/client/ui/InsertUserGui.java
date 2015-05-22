package navigation.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import dto.UserDto;
import navigation.client.NavigationService;

/**
 * Created by Petikk on 21. 5. 2015.
 */
public class InsertUserGui {

    public static FormPanel getInsertUserForm() {
        final FormPanel form = new FormPanel();
        VerticalPanel verticalPanel = new VerticalPanel();
        Label lname = new Label("Name");
        verticalPanel.add(lname);
        final TextBox name = new TextBox();
        name.setName("name");
        verticalPanel.add(name);
        Label lsurname = new Label("Surname");
        verticalPanel.add(lsurname);
        final TextBox surname = new TextBox();
        surname.setName("surname");
        verticalPanel.add(surname);
        Label lusername = new Label("Username");
        verticalPanel.add(lusername);
        final TextBox username = new TextBox();
        username.setName("username");
        verticalPanel.add(username);
        Label lpassword = new Label("Password");
        verticalPanel.add(lpassword);
        final PasswordTextBox password = new PasswordTextBox();
        password.setName("password");
        verticalPanel.add(password);
        Label lpassword2 = new Label("Password again");
        verticalPanel.add(lpassword2);
        final PasswordTextBox password2 = new PasswordTextBox();
        verticalPanel.add(password2);
        form.setWidget(verticalPanel);

        verticalPanel.add(new Button("Save", new ClickHandler() {

            public void onClick(ClickEvent event) {
                form.submit();
            }
        }));

        form.addSubmitHandler(new FormPanel.SubmitHandler() {

            public void onSubmit(FormPanel.SubmitEvent event) {
                if (name.getText().length() == 0 || surname.getText().length() == 0 || username.getText().length() == 0
                        || password.getText().length() == 0 || password2.getText().length() == 0) {
                    Window.alert("Text box must not be empty");
                    event.cancel();
                    return;
                }
                if (!password.getText().equals(password2.getText())) {
                    Window.alert("Passwords doesn't match!");
                    event.cancel();
                    return;
                }
                UserDto user = new UserDto();
                user.setName(name.getText());
                user.setPassword(password.getText());
                user.setSurname(surname.getText());
                user.setUserName(username.getText());
                NavigationService.App.getInstance().insertUser(user, new InsertUserAsyncCallback());
                form.reset();
            }
        });
        return form;
    }

    private static class InsertUserAsyncCallback implements AsyncCallback<Void> {
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
