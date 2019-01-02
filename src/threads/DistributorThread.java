package threads;

import single_classes.GlobalVariables;
import single_classes.RandomNumbersManager;
import single_classes.Utility;
import users.Distributor;

import java.util.ArrayList;
import java.util.HashMap;

public class DistributorThread implements Runnable {
    private Thread t;

    public void run() {
        try {
            Distributor d = new Distributor(GlobalVariables.distributorsCount);
            int currentId = GlobalVariables.distributorsCount;
            GlobalVariables.distributorsList.put(currentId, d);
            GlobalVariables.distributorsCount ++;

            while (true) {
//          create product
                if (Math.random() > GlobalVariables.randomNumbersManager.chanceForProduct(d.getProductsCount()) && Utility.getMaxProducts() >= GlobalVariables.productsList.size()) {
                    if (GlobalVariables.database.getNextMovie(currentId)) {
                        // to do: dodaj 0 tyle razy ile jest miesiecy 0...current month
                        System.out.println("tworzy: " + GlobalVariables.productsList.get(currentId).getName());
                        for (int j = 0; j < GlobalVariables.month; j++) {
                            GlobalVariables.productsList.get(GlobalVariables.productsCount).viewership.add(0);
                        }
                        GlobalVariables.distributorsList.get(currentId).increaseProducts();
                        GlobalVariables.productsCount++;
                    }
                }

                Thread.sleep(Utility.getDayTime() * 10);
            }

        } catch (InterruptedException e) {
            System.out.println("There was an error: " + e.getMessage());
        }
    }

//    private void createDistributor() throws InterruptedException {
//        Distributor d = new Distributor(GlobalVariables.distributorsCount);
//        int currentId = GlobalVariables.distributorsCount;
//        GlobalVariables.distributorsList.put(currentId, d);
//        GlobalVariables.distributorsCount ++;
//
////        while (true) {
////         create product
//            if (Math.random() > GlobalVariables.randomNumbersManager.chanceForProduct(d.getProductsCount()) && Utility.getMaxProducts() >= GlobalVariables.productsList.size()) {
//                if (GlobalVariables.database.getNextMovie(currentId)) {
//                    // to do: dodaj 0 tyle razy ile jest miesiecy 0...current month
//                    for (int j = 0; j < GlobalVariables.month; j++) {
//                        GlobalVariables.productsList.get(GlobalVariables.productsCount).viewership.add(0);
//                    }
//                    GlobalVariables.distributorsList.get(currentId).increaseProducts();
//                    GlobalVariables.productsCount++;
//                }
//            }
////        }
//
//        Thread.sleep(1000);
//
//    }

    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
}
