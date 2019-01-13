package productsView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProductsListLoader extends Application {

    public void run() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../views/productView.fxml"));
        primaryStage.setTitle("WERFLIX");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
