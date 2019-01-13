package controllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import product.Product;
import single_classes.GlobalVariables;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DetailsController implements Initializable {
    public Label durationLabel;
    public Label yearDesc; // to do
    public Label countryLabel;
    public Label distrIdLabel;
    public Label yearLabel; // to do - date here
    public Label typeLabel;
    public Label optionalDesc1;
    public Label optionalDesc2;
    public Label optionalDesc3;
    public Label optionalDesc4;
    public Label optionalLabel1;
    public Label optionalLabel2;
    public Label optionalLabel3;
    public Label optionalLabel4;
    public Label title;
    public Label description;
    public Label idLabel;
    public Label ratingLabel;
    public ImageView image;
    public Label durationDesc;
    public GridPane gridPane;
    Product p;

    public void setProduct(Product p) {
        this.p = p;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {

            Label[] labels = new Label[]{durationLabel, idLabel, countryLabel, yearLabel, typeLabel, distrIdLabel, optionalLabel1, optionalLabel2, optionalLabel3, optionalLabel4};
            for(Label label: labels) {
                label.prefWidthProperty().bind(gridPane.widthProperty().subtract(150));
            }

            description.prefWidthProperty().bind(gridPane.widthProperty());

            Image img = new Image(p.getImageLink());
            image.setImage(img);
            title.setText(p.getName());
            description.setText(p.getDescription());
            idLabel.setText(Integer.toString(p.getId()));

            String price = Float.toString(p.getRating());
            price = price.substring(0, 1) + '.' + price.substring(2, 3);

            ratingLabel.setText(price);
            durationLabel.setText(p.getDurationTime());
            countryLabel.setText(p.getProdCountry());
            distrIdLabel.setText(Integer.toString(p.getDistributorId()));
            typeLabel.setText(p.getClassName());

            gridPane.widthProperty().addListener((obs, oldVal, newVal) -> {
                title.setText(p.getName());
                description.setText(p.getDescription());
            });

            switch (p.getClassName()) {
                case "Series":  optionalDesc1.setText("Actors:");
                                optionalLabel1.setText(p.getActors());
                                optionalDesc2.setText("Numbers of seasons: ");
                                optionalLabel2.setText(Integer.toString(p.getNumberOfSeasons()));
                                optionalDesc3.setText("Genres: ");
                                optionalLabel3.setText(p.getGenres());
                                durationDesc.setText("Duration time of episode: ");
                                yearLabel.setText(Integer.toString(p.getProdYear()));
                                optionalLabel2.getStyleClass().remove("without");
                                optionalDesc2.getStyleClass().remove("without");
                                break;
                case "Movie":   optionalDesc1.setText("Actors:");
                                optionalLabel1.setText(p.getActors());
                                optionalDesc2.setText("Price: ");
                                optionalLabel2.setText(Float.toString(p.getPrice()));
                                optionalDesc3.setText("Genres: ");
                                optionalLabel3.setText(p.getGenres());
                                optionalDesc4.setText("Promotion: ");
                                yearLabel.setText(Integer.toString(p.getProdYear()));
                                if(p.getPromotion() == null) {
                                    optionalLabel4.setText("0");
                                } else {
                                    optionalLabel4.setText(Float.toString(p.getPromotion().getDiscount()));
                                }
                                optionalLabel2.getStyleClass().remove("without");
                                optionalDesc2.getStyleClass().remove("without");
                                optionalLabel3.getStyleClass().remove("without");
                                optionalDesc3.getStyleClass().remove("without");
                                break;
                case "LiveStreaming":   yearDesc.setText("Start date:");
                                        yearLabel.setText(p.getStartDate());
                                        optionalDesc1.setText("Price: ");
                                        optionalLabel1.setText(Float.toString(p.getPrice()));
                                        optionalDesc2.setText("Promotion: ");
                                        if(p.getPromotion() == null) {
                                            optionalLabel2.setText("0");
                                        } else {
                                            optionalLabel2.setText(Float.toString(p.getPromotion().getDiscount()));
                                        }
                                        break;
                case "Event":   optionalDesc1.setText("Special guests:");
                                optionalLabel1.setText(p.getSpecialGuests());
                                optionalDesc2.setText("Entry price: ");
                                optionalLabel2.setText(Float.toString(p.getPrice()));
                                yearDesc.setText("Start date: ");
                                yearLabel.setText(p.getStartDate());
                                break;
            }
        });
    }

    public void showViewership(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/viewership.fxml"));
            Parent parent = fxmlLoader.load();
            ViewershipController controller = fxmlLoader.getController();

            controller.setProduct(p);

            Stage stage = new Stage();
            stage.setTitle("Product viewership");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            System.out.println("There was an error: " + e.getMessage() + e.toString());
        }
    }
}
