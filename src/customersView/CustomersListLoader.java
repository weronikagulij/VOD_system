package customersView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CustomersListLoader extends Application {

    public void run() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../views/customersView.fxml"));
        primaryStage.setTitle("WERFLIX");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
