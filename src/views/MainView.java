package views;

import controllers.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import single_classes.GlobalVariables;
import single_classes.Utility;
import sun.applet.Main;

public class MainView extends Application {
    private Thread t;
//    private MainController mainController;

    public void run() {
        launch();
    }

    public void updateMainController() {
        System.out.println("updating main controller ");
//        mainController.refreshEverySecond();
//        System.out.println(mainController);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent parent = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();
//        System.out.println(mainController);
        updateMainController();

//        ViewUpdater thread = new ViewUpdater();
//        thread.start(mainController);

        primaryStage.setTitle("WERFLIX");
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

//    public void startThread() {
//        if (t == null) {
//            t = new Thread(this);
////            t.setDaemon(true);
//            t.start();
//        }
//    }

//    public MainController getMainController() {
//        return mainController;
//    }


}
