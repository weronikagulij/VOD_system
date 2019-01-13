package threads;

import single_classes.GlobalVariables;
import single_classes.RandomNumbersManager;
import single_classes.Utility;
import users.Distributor;

import java.util.ArrayList;
import java.util.HashMap;

public class DistributorThread implements Runnable {
    private Thread t;
    int id = 0;

    public void run() {
        try {
            Distributor d = new Distributor(id);
            GlobalVariables.distributorsList.put(id, d);
//            int currentId = GlobalVariables.distributorsList.size();
//            GlobalVariables.distributorsList.put(currentId, d);

            while (true) {
//          create product
                if (Math.random() > GlobalVariables.randomNumbersManager.chanceForProduct(d.getProductsCount()) && Utility.getMaxProducts() >= GlobalVariables.productsList.size()) {
//                    if (GlobalVariables.database.getNextMovie(currentId)) {

//                        for (int j = 0; j < GlobalVariables.month; j++) {
//                            GlobalVariables.productsList.get(GlobalVariables.productsList.size()).viewership.add(0);
//                        }
//                        GlobalVariables.distributorsList.get(currentId).increaseProducts();
//                    }
                    int productId = GlobalVariables.addProduct(id);
                    if(productId != 0)
                    System.out.println("dsystybutor " + id + " tworzy: " + GlobalVariables.productsList.get(productId).getName());
                }

                Thread.sleep(Utility.getDayTime() * 10000);
            }

        } catch (InterruptedException e) {
            System.out.println("There was an error: " + e.getMessage());
        }
    }

    public void start(int id) {
        this.id = id;
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
}
