package controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import product.Product;
import single_classes.GlobalVariables;
import single_classes.Utility;
import users.Customer;
import users.Distributor;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    // customers
    public TableView<Customer> customersTable;
    public TableColumn<Customer, Integer> idCustomersCol;
    public TableColumn<Customer, String> subscriptionCol;
    public TableColumn<Customer, String> genreCol;
    public TableColumn<Customer, String> productsCol;
    public TableColumn<Customer, String>  emailCol;

    // distributors
    public TableView<Distributor> distributorsTable;
    public TableColumn<Distributor, Integer> idDistributorsCol;
    public TableColumn<Distributor, Integer> salaryCol;
    public TableColumn<Distributor, Integer> countCol;

    // products
    public TableColumn<ShortProduct, Integer> idProductsCol;
    public TableColumn<ShortProduct, String> titleCol;
    public TableColumn<ShortProduct, Integer> distributorCol;
    public TableColumn<ShortProduct, String> typeCol;
    public TableView<ShortProduct> productsTable;
    public Button randomButton;
    public TextField searchField;

    // all window
    public HBox menuWrapper;
    public HBox menuItem;
    public Label dayLabel;
    public Label monthLabel;
    public Label balanceLabel;


    public void initialize(URL location, ResourceBundle resources) {
        menuItem.prefWidthProperty().bind(menuWrapper.widthProperty().subtract(180));
        initClol();
        refreshAll();
        refreshEverySecond();
    }

    public void initClol() {
        // product
        idProductsCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleCol.prefWidthProperty().bind(productsTable.widthProperty().subtract(93 * 2 + 45 + 2));
        distributorCol.setCellValueFactory(new PropertyValueFactory<>("distributor"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        // customer
        idCustomersCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        subscriptionCol.setCellValueFactory(new PropertyValueFactory<>("subscription"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("favouriteGenre"));
        productsCol.setCellValueFactory(new PropertyValueFactory<>("purchasedProducts"));
        productsCol.prefWidthProperty().bind(customersTable.widthProperty().subtract(44 + 130 + 85 + 97 + 2));

        // distributor
        idDistributorsCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("productsCount"));
        salaryCol.prefWidthProperty().bind(distributorsTable.widthProperty().subtract(45 +156 + 2));
    }

    public void refreshDistributors() {
        distributorsTable.getItems().clear();
        distributorsTable.getItems().removeAll();

        HashMap<Integer, Distributor> distributorsList = (HashMap<Integer, Distributor>) GlobalVariables.instance.getDistributorsList().clone();

        for (Map.Entry<Integer, Distributor> p : distributorsList.entrySet()) {
            distributorsTable.getItems().add(p.getValue());
        }
    }

    public void refreshCustomers() {
        customersTable.getItems().clear();
        customersTable.getItems().removeAll();

        HashMap<Integer, Customer> customersList = (HashMap<Integer, Customer>) GlobalVariables.instance.getCustomersList().clone();

        for (Map.Entry<Integer, Customer> p : customersList.entrySet()) {
            customersTable.getItems().add(p.getValue());
        }
    }

    public void refreshAll() {
        refreshCustomers();
        refreshDistributors();
        refreshProducts("");
    }

    public void addProductsToTable() {
        int i = 0;

        HashMap<Integer, Product> productsList = (HashMap<Integer, Product>) GlobalVariables.instance.getProductsList().clone();

        for (Map.Entry<Integer, Product> p : productsList.entrySet()) {
            if (i >= productsTable.getItems().size()) {
                productsTable.getItems().add(new ShortProduct(p.getValue().getId(), p.getValue().getName(), p.getValue().getDistributorId(), p.getValue().getClassName()));
            }

            i++;
        }
    }

    public void addCustomersToTable() {
        int i = 0;

        HashMap<Integer, Customer> customersList = (HashMap<Integer, Customer>) GlobalVariables.instance.getCustomersList().clone();

        for (Map.Entry<Integer, Customer> p : customersList.entrySet()) {
            if (i >= customersTable.getItems().size()) {
                customersTable.getItems().add(p.getValue());
            }

            i++;
        }
    }

    public void refreshEverySecond() {
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(Utility.getDayTime()),
                        event -> {
                            dayLabel.setText(Integer.toString(GlobalVariables.instance.day));
                            monthLabel.setText(Integer.toString(GlobalVariables.instance.month));
                            balanceLabel.setText(Integer.toString(GlobalVariables.instance.globalBalance));
                            addProductsToTable();
                            addCustomersToTable();
                        }
                )
        );

        timeline.setCycleCount( Animation.INDEFINITE );
        timeline.play();
    }

    // products methods
    public void refreshProducts(String pattern) {
        productsTable.getItems().clear();
        productsTable.getItems().removeAll();

        HashMap<Integer, Product> productsList = (HashMap<Integer, Product>) GlobalVariables.instance.getProductsList().clone();

        for (Map.Entry<Integer, Product> p : productsList.entrySet()) {
            if (p.getValue().getName().toLowerCase().contains(pattern.toLowerCase())
                    || Integer.toString(p.getValue().getId()).equals(pattern.toLowerCase().replace(" ", ""))) {
                productsTable.getItems().add(new ShortProduct(p.getValue().getId(), p.getValue().getName(), p.getValue().getDistributorId(), p.getValue().getClassName()));
            }
        }
    }

    public void viewDetails(ActionEvent actionEvent) {
        productsTable.getSelectionModel();
        ShortProduct shortProduct = productsTable.getSelectionModel().getSelectedItem();

        if(shortProduct != null) {
            // view details
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/productDetails.fxml"));
                Parent parent = fxmlLoader.load();
                DetailsController controller = fxmlLoader.getController();

                controller.setProduct(GlobalVariables.instance.getProductsList().get(shortProduct.getId()));

                Stage stage = new Stage();
                stage.setTitle("Product details");
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException e) {
                System.out.println("There was an error: " + e.getMessage() + e.toString());
            }
        } else {
            Utility.showEmptySelectMessage();
        }
    }

    public void deleteProduct(ActionEvent actionEvent) {
        ShortProduct shortProduct = productsTable.getSelectionModel().getSelectedItem();

        if(shortProduct != null) {
            GlobalVariables.instance.removeProduct(shortProduct.getId());
            refreshProducts("");
            successAlert("Product was succesfully deleted.");
        } else {
            Utility.showEmptySelectMessage();
        }
    }

    public void addRandomProduct(ActionEvent actionEvent) {
        int id = 0;
        randomButton.setDisable(true);

        // to ask: czy to dac do osobnego watku?
        if(GlobalVariables.instance.getDistributorsList().size() == 0) {
            errorAlert("You have no distributors!");
            randomButton.setDisable(false);
            return;
        }

        // retry to connect 3 times to not wait indefinitely
        for(int i = 0; i < 3; i ++) {
            Object randomKey = GlobalVariables.instance.getDistributorsList().keySet().toArray()[new Random().nextInt(GlobalVariables.instance.getDistributorsList().keySet().toArray().length)];
            id = GlobalVariables.instance.addProduct(Integer.parseInt(randomKey.toString()));
            if (id != 0) break;
        }

        if(id != 0) {
            Product added = GlobalVariables.instance.getProductsList().get(id);
            refreshProducts("");

            successAlert("Product was successfully added. Details: \n" + added.getId() + " - " + added.getName());
        } else {
            errorAlert("Product could not be added due to network connection. Try again.");
        }


        randomButton.setDisable(false);
    }

    // distributors methods
    public void deleteDistributor(ActionEvent actionEvent) {
        Distributor customer = distributorsTable.getSelectionModel().getSelectedItem();

        if(customer != null) {
            GlobalVariables.instance.removeDistributor(customer.getId());
           refreshDistributors();
            successAlert("Distributor was succesfully deleted.");
        } else {
            Utility.showEmptySelectMessage();
        }
    }

    public void addRandomDistributor(ActionEvent actionEvent) {
        int id = GlobalVariables.instance.addDistributor();
        refreshDistributors();
        successAlert("Distributor was successfully added.\nId of added distributor: " + Integer.toString(id));
    }

    // customers methods
    public void deleteCustomer(ActionEvent actionEvent) {
        Customer customer = customersTable.getSelectionModel().getSelectedItem();

        if(customer != null) {
            GlobalVariables.instance.removeCustomer(customer.getId());
            refreshCustomers();
            successAlert("Customer was successfully deleted.");
        } else {
            Utility.showEmptySelectMessage();
        }
    }

    public void addRandomCustomer(ActionEvent actionEvent) {
        int id = GlobalVariables.instance.addCustomer();
        refreshCustomers();
        successAlert("Customer was successfully added.\nId of added customer: " + Integer.toString(id));
    }

    public void searchProduct(ActionEvent actionEvent) {
        refreshProducts(searchField.getText());
    }

    public void showPremiumOffer(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/premium.fxml"));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Available subscriptions");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            System.out.println("There was an error: " + e.getMessage() + e.toString());
        }
    }

    private void successAlert(String msg) {
        Alert alert;
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(msg);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void errorAlert(String msg) {
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(msg);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void refreshAllTable(ActionEvent actionEvent) {
        refreshCustomers();
        refreshDistributors();
    }
}
