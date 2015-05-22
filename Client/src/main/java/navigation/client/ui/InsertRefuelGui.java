package navigation.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import dto.RefuelDto;
import navigation.client.NavigationService;

import java.util.Date;

/**
 * Created by Petikk on 18. 5. 2015.
 */
public class InsertRefuelGui {

    public static FormPanel getInsertRefuelForm() {
        final FormPanel form = new FormPanel();
        VerticalPanel verticalPanel = new VerticalPanel();
        Label ldate = new Label("Date");
        verticalPanel.add(ldate);
        final DateBox date = new DateBox();
        date.setValue(new Date());
//        DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy-MM-dd'T'HH:mm:ssZ");
//        date.setFormat(new DateBox.DefaultFormat(dateFormat));
//        date.getTextBox().setName("date");
        verticalPanel.add(date);
        Label lamount = new Label("Amount");
        verticalPanel.add(lamount);
        final TextBox amount = new TextBox();
        amount.setName("amount");
        verticalPanel.add(amount);
        Label lprice = new Label("Price");
        verticalPanel.add(lprice);
        final TextBox price = new TextBox();
        price.setName("price");
        verticalPanel.add(price);
        Label lpricePerUnit = new Label("Price per unit");
        verticalPanel.add(lpricePerUnit);
        final TextBox pricePerUnit = new TextBox();
        pricePerUnit.setName("pricePerUnit");
        verticalPanel.add(pricePerUnit);
        Label luserId = new Label("User Id");
        verticalPanel.add(luserId);
        final TextBox userId = new TextBox();
        userId.setName("userId");
        verticalPanel.add(userId);

        form.setWidget(verticalPanel);

        verticalPanel.add(new Button("Save", new ClickHandler() {

            public void onClick(ClickEvent event) {
                form.submit();
            }
        }));

        form.addSubmitHandler(new FormPanel.SubmitHandler() {

            public void onSubmit(FormPanel.SubmitEvent event) {
                if (userId.getText().length() == 0 || amount.getText().length() == 0 || price.getText().length() == 0
                        || pricePerUnit.getText().length() == 0 || date.getTextBox().getText().length() == 0) {
                    Window.alert("Text box must not be empty");
                    event.cancel();
                }
                RefuelDto refuel = new RefuelDto();
                refuel.setPricePerUnit(Double.parseDouble(pricePerUnit.getText()));
                refuel.setPrice(Double.parseDouble(price.getText()));
                refuel.setDate(date.getValue());
                refuel.setAmount(Double.parseDouble(amount.getText()));
                refuel.setUserId(Long.parseLong(userId.getText()));
                NavigationService.App.getInstance().insertRefuel(refuel, new InsertRefuelsAsyncCallback());
                form.reset();
            }
        });

        final HTML html = new HTML();
        RootPanel.get("result").add(html);

        form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            //        @Override
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                html.setHTML("submit handler" + event.getResults());
                //html.setWordWrap(true);
            }
        });
//        RootPanel.get("container").add(form);
        return form;
    }

    private static class InsertRefuelsAsyncCallback implements AsyncCallback<Void> {
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
