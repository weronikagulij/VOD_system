import threads.TimeManagerThread;
import threads.VODitemsThread;
import views.MainView;
import single_classes.*;

import java.util.*;

public class VODmanager {

    VODmanager() {
        GlobalVariables.productsCount = 1;
        GlobalVariables.customersCount = 1;
        GlobalVariables.distributorsCount = 1;
        GlobalVariables.month = 0;
        GlobalVariables.day = 0;
        GlobalVariables.productsList = new HashMap<>();
        GlobalVariables.customersList = new HashMap<>();
        GlobalVariables.distributorsList = new HashMap<>();
//        GlobalVariables.monthlyProfitBalance = new HashMap<>();
        GlobalVariables.randomNumbersManager = new RandomNumbersManager();
//        GlobalVariables.monthlyBalance = 0;
        GlobalVariables.globalBalance = 0;

        // get movies first
        GlobalVariables.database = new DatabaseManager();
    }

    public void startSimulation() {
//        for(int i = 0; i < 4; i ++ ) {
//            GlobalVariables.database.getNextMovie(0);
//        }

//        for(int i = 0; i < 20; i ++) {
//            GlobalVariables.addCustomer();
//        }


        for(int i = 0; i < 2; i ++) {
            GlobalVariables.addDistributor();
        }


        VODitemsThread vodItemsThread = new VODitemsThread();
        vodItemsThread.start();

        TimeManagerThread timeManagerThread = new TimeManagerThread();
        timeManagerThread.start();

        MainView view = new MainView();
        view.run();

//        view.updateMainController();
//        while(view.getMainController() == null) {}
//        System.out.println(view.getMainController());

//        TimeManagerThread timeManagerThread = new TimeManagerThread();
//        timeManagerThread.start();
//
//        VODitemsThread vodItemsThread = new VODitemsThread();
//        vodItemsThread.start();
    }
}
