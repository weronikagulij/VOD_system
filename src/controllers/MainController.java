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

import javax.rmi.CORBA.Util;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

        for (Map.Entry<Integer, Distributor> p : GlobalVariables.distributorsList.entrySet()) {
            distributorsTable.getItems().add(p.getValue()); // tutaj blad
        }
    }

    public void refreshCustomers() {
        customersTable.getItems().clear();
        customersTable.getItems().removeAll();
        for (Map.Entry<Integer, Customer> p : GlobalVariables.customersList.entrySet()) {
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
        for (Map.Entry<Integer, Product> p : GlobalVariables.productsList.entrySet()) {
            if (i >= productsTable.getItems().size()) {
                productsTable.getItems().add(new ShortProduct(p.getValue().getId(), p.getValue().getName(), p.getValue().getDistributorId(), p.getValue().getClassName()));
            }

            i++;
        }
    }

    public void addCustomersToTable() {
        int i = 0;
        for (Map.Entry<Integer, Customer> p : GlobalVariables.customersList.entrySet()) {
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
                            dayLabel.setText(Integer.toString(GlobalVariables.day));
                            monthLabel.setText(Integer.toString(GlobalVariables.month));
                            balanceLabel.setText(Integer.toString(GlobalVariables.globalBalance));
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
        for (Map.Entry<Integer, Product> p : GlobalVariables.productsList.entrySet()) {
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

                controller.setProduct(GlobalVariables.productsList.get(shortProduct.getId()));

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
            GlobalVariables.removeProduct(shortProduct.getId());
            refreshProducts("");
            showDeleteMessage("Product");
        } else {
            Utility.showEmptySelectMessage();
        }
    }

    public void addRandomProduct(ActionEvent actionEvent) {
        int id = 0;
        randomButton.setDisable(true);

        // retry to connect 3 times to not wait indefinitely
        for(int i = 0; i < 3; i ++) {
            Object randomKey = GlobalVariables.distributorsList.keySet().toArray()[new Random().nextInt(GlobalVariables.distributorsList.keySet().toArray().length)];
            id = GlobalVariables.addProduct(Integer.parseInt(randomKey.toString()));
            if (id != 0) break;
        }

        Alert alert;
        if(id != 0) {
            Product added = GlobalVariables.productsList.get(id);
            refreshProducts("");

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Added");
            alert.setContentText("Product was successfully added. Details: \n" + added.getId() + " - " + added.getName());
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Product could not be added due to network connection. Try again.");
        }

        alert.setHeaderText(null);
        alert.showAndWait();

        randomButton.setDisable(false);
    }

    // distributors methods
    public void deleteDistributor(ActionEvent actionEvent) {
        Distributor customer = distributorsTable.getSelectionModel().getSelectedItem();

        if(customer != null) {
            GlobalVariables.removeDistributor(customer.getId());
           refreshDistributors();
            showDeleteMessage("Distributor");
        } else {
            Utility.showEmptySelectMessage();
        }
    }

    public void addRandomDistributor(ActionEvent actionEvent) {
        int id = GlobalVariables.addDistributor();
        refreshDistributors();
        showAddMessage("Distributor", id);
    }

    // customers methods
    public void deleteCustomer(ActionEvent actionEvent) {
        Customer customer = customersTable.getSelectionModel().getSelectedItem();

        if(customer != null) {
            GlobalVariables.removeCustomer(customer.getId());
            refreshCustomers();
            showDeleteMessage("Customer");
        } else {
            Utility.showEmptySelectMessage();
        }
    }

    public void addRandomCustomer(ActionEvent actionEvent) {
        int id = GlobalVariables.addCustomer();
        refreshCustomers();
        showAddMessage("Customer", id);
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


    public static void showDeleteMessage(String type) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Delete");
        alert.setHeaderText(null);
        alert.setContentText(type + " was successfully deleted.");
        alert.showAndWait();
    }

    public static void showAddMessage(String type, int id) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add");
        alert.setHeaderText(null);
        alert.setContentText(type + " was successfully added.\nId of added " + type.toLowerCase() + ": " + Integer.toString(id));
        alert.showAndWait();
    }
}
