package threads;

import single_classes.GlobalVariables;
import single_classes.Utility;

import java.util.ArrayList;

public class VODitemsThread implements Runnable {
    private Thread t;

    public void run() {
        try {
            //        while(true) {
            // randomly create distributor thread
//            if (Math.random() > GlobalVariables.randomNumbersManager.chanceForDistributor(GlobalVariables.distributorsList.size())
//                && Utility.getMaxDistributors() >= GlobalVariables.distributorsList.size()) {
//                DistributorThread d = new DistributorThread();
//                d.start();
//            }

            // randomly create customer thread
            if ( Math.random() > GlobalVariables.randomNumbersManager.chanceForCustomer(GlobalVariables.productsList.size())
                && Utility.getMaxCustomers() >= GlobalVariables.customersList.size()) {
                CustomerThread c = new CustomerThread();
                c.start();
            }

            Thread.sleep(1000);
//        }
        } catch (InterruptedException e) {
            System.out.println("There was an error: " + e.getMessage());
        }
    }

    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
}
