package threads;

import single_classes.GlobalVariables;
import single_classes.Utility;

import java.util.ArrayList;

public class VODitemsThread implements Runnable {
    private Thread t;

    public void run() {
        try {
            while(true) {
            // randomly create customer thread
            if ( Math.random() > GlobalVariables.instance.randomNumbersManager.chanceForCustomer()
                && Utility.getMaxCustomers() >= GlobalVariables.instance.getCustomersList().size()) {
                GlobalVariables.instance.addCustomer();
            }

            Thread.sleep(Utility.getDayTime() * 2 );
        }
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
