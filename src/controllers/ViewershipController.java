package controllers;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import product.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewershipController implements Initializable {
    public LineChart ViewershipChart;
    Product p;

    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            XYChart.Series series = new XYChart.Series();

            int maxDisplayed = 20;
            int i = 0;

            for(Integer monthly: p.getViewership()) {
                if(p.getViewership().size() - i <= maxDisplayed) {
                    series.getData().add(new XYChart.Data(Integer.toString(i), monthly));
                }

                i++;
            }

            ViewershipChart.setLegendVisible(false);
            ViewershipChart.setTitle("Viewership of " + p.getName());
            ViewershipChart.getData().addAll(series);
        });
    }

    public void setProduct(Product p) {
        this.p = p;
    }
}
