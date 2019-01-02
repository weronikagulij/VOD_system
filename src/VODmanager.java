import single_classes.*;
import threads.TimeManagerThread;
import threads.VODitemsThread;
import java.util.*;

public class VODmanager {

    VODmanager() {
        GlobalVariables.productsCount = 0;
        GlobalVariables.customersCount = 0;
        GlobalVariables.distributorsCount = 0;
        GlobalVariables.month = 0;
        GlobalVariables.day = 0;
        GlobalVariables.productsList = new HashMap<>();
        GlobalVariables.customersList = new HashMap<>();
        GlobalVariables.distributorsList = new HashMap<>();
        GlobalVariables.monthlyProfitBalance = new HashMap<>();
        GlobalVariables.randomNumbersManager = new RandomNumbersManager();

        // get movies first
        GlobalVariables.database = new DatabaseManager();
    }

    public void startSimulation() {
        TimeManagerThread timeManagerThread = new TimeManagerThread();
        timeManagerThread.start();

        VODitemsThread vodItemsThread = new VODitemsThread();
        vodItemsThread.start();
    }
}
