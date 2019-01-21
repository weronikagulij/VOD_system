package threads;

import product.Product;
import single_classes.GlobalVariables;
import single_classes.Utility;
import users.Customer;
import users.Distributor;
import views.MainView;

import java.util.HashMap;
import java.util.Map;

public class TimeManagerThread implements Runnable {
    private Thread t;
    private int monthsOfFailure;

    public TimeManagerThread() {
        monthsOfFailure = 0;
    }

    public void run() {
        try {
        while (true) {
            Thread.sleep(Utility.getDayTime());

            if (GlobalVariables.instance.day < 30) {
                GlobalVariables.instance.day++;
            } else {
                GlobalVariables.instance.month++;
                GlobalVariables.instance.day = 0;

                checkProfitBalance();
                viewershipForAllProducts();
                checkPromotions();
            }

        }
        } catch (InterruptedException e) {
            System.out.println("There was an error: " + e.getMessage());
        }
    }

    private void checkProfitBalance() {
        double income = 0;

        // if monthly profit balance to this month was not created
//        Utility.addMonthToProfitBalance();

        // get income from subsciptions
        HashMap<Integer, Customer> customersList = (HashMap<Integer, Customer>) GlobalVariables.instance.getCustomersList().clone();

        for (Map.Entry<Integer, Customer> pair: customersList.entrySet()) {
            if(pair.getValue().getSubscriptionItem() != null) {
                income += (double) pair.getValue().getSubscriptionItem().getPrice();
            }
        }

        // give money to disytibutors

        HashMap<Integer, Distributor> distributorsList = (HashMap<Integer, Distributor>) GlobalVariables.instance.getDistributorsList().clone();

        for (Map.Entry<Integer, Distributor> pair: distributorsList.entrySet()) {
            income -= (double) pair.getValue().getSalary();
        }

//        GlobalVariables.monthlyProfitBalance.put(GlobalVariables.month, (float)(GlobalVariables.monthlyProfitBalance.get(GlobalVariables.month) + income));
//    GlobalVariables.monthlyBalance += income;
        GlobalVariables.instance.globalBalance += income;

        if(income < 0) monthsOfFailure ++;
        else monthsOfFailure = 0;

        if(monthsOfFailure >= 3) quit();

//        System.out.println("profit balance: " + GlobalVariables.monthlyProfitBalance.get(GlobalVariables.month));
    }

    private void viewershipForAllProducts() {
        HashMap<Integer, Product> productsList = (HashMap<Integer, Product>) GlobalVariables.instance.getProductsList().clone();
        for (Map.Entry<Integer, Product> pair: productsList.entrySet()) {
            Utility.addMonthToProductViewership(pair.getValue());
        }
    }

    private void checkPromotions() {
        HashMap<Integer, Product> productsList = (HashMap<Integer, Product>) GlobalVariables.instance.getProductsList().clone();
        for (Map.Entry<Integer, Product> pair: productsList.entrySet()) {
            // create promotion with 10% chance
            if(Utility.getProductsWithPromotion().contains(pair.getValue().getClassName())) {
                // if product does not have a promotion
                if (pair.getValue().getPromotion() == null || pair.getValue().getPromotion().getDiscount() == 0) {
                    if (Math.random() > 0.9) {
                        int promotion = (int)(Math.random() * 50 + 5);
                        int durationMonths = (int)(Math.random() * 2 + 1);
                        pair.getValue().setPromotion(durationMonths, ((float)promotion / 100) );
                    }
                } else {
                    if(pair.getValue().getPromotion().decreaseDuration() == 0)
                        pair.getValue().getPromotion().reset();
                }
            }
        }
    }

    private void quit() {
        System.out.println("przegrales symulacje. ");
    }

    public void start() {
        if (t == null) {
            t = new Thread(this);
//            t.setDaemon(true);
            t.start();
        }
    }
}
