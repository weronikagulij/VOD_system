package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import single_classes.Subscription;
import single_classes.SubscriptionTypes;
import single_classes.Utility;

import java.net.URL;
import java.util.ResourceBundle;

public class PremiumController implements Initializable {
    public Label subName0;
    public Label res0;
    public Label kids0;
    public TextField devices0;
    public TextField price0;
    public Label subName1;
    public Label res1;
    public Label kids1;
    public TextField devices1;
    public TextField price1;
    public Label subName2;
    public Label res2;
    public Label kids2;
    public TextField devices2;
    public TextField price2;

    public void initLabels() {
        subName0.setText(SubscriptionTypes.getName(0));
        subName1.setText(SubscriptionTypes.getName(1));
        subName2.setText(SubscriptionTypes.getName(2));
        res0.setText(SubscriptionTypes.getResolutionX(0) + " x " + SubscriptionTypes.getResolutionY(0));
        res1.setText(SubscriptionTypes.getResolutionX(1) + " x " + SubscriptionTypes.getResolutionY(1));
        res2.setText(SubscriptionTypes.getResolutionX(2) + " x " + SubscriptionTypes.getResolutionY(2));
        kids0.setText(Boolean.toString(SubscriptionTypes.getKids(0)));
        kids1.setText(Boolean.toString(SubscriptionTypes.getKids(1)));
        kids2.setText(Boolean.toString(SubscriptionTypes.getKids(2)));
        initChanging();
    }

    public void initChanging() {
        devices0.setText(Integer.toString(SubscriptionTypes.getDevices(0)));
        devices1.setText(Integer.toString(SubscriptionTypes.getDevices(1)));
        devices2.setText(Integer.toString(SubscriptionTypes.getDevices(2)));
        price0.setText(Double.toString(SubscriptionTypes.getPrice(0)));
        price1.setText(Double.toString(SubscriptionTypes.getPrice(1)));
        price2.setText(Double.toString(SubscriptionTypes.getPrice(2)));

    }

    public void initialize(URL location, ResourceBundle resources) {
        initLabels();
    }

    public void confirm(ActionEvent actionEvent) {
        if(Utility.checkIfInt(devices0.getText()) != 0
                && Utility.checkIfInt(devices1.getText()) != 0
                && Utility.checkIfInt(devices2.getText()) != 0
                && Utility.checkIfDouble(price0.getText()) != 0
                && Utility.checkIfDouble(price1.getText()) != 0
                && Utility.checkIfDouble(price2.getText()) != 0) {
            SubscriptionTypes.updateBasic(Integer.parseInt(devices0.getText()), Double.parseDouble(price0.getText()));
            SubscriptionTypes.updateFamily(Integer.parseInt(devices1.getText()), Double.parseDouble(price1.getText()));
            SubscriptionTypes.updatePremium(Integer.parseInt(devices2.getText()), Double.parseDouble(price2.getText()));
            initChanging();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update");
            alert.setHeaderText(null);
            alert.setContentText("Values updated successfully.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid input values!");
            alert.showAndWait();
        }
    }

    public void discard(ActionEvent actionEvent) {
        initChanging();
    }
}
