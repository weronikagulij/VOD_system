import jdk.jshell.execution.Util;
import product.Product;
import single_classes.*;
import threads.CustomerThread;
import threads.DistributorThread;
import threads.TimeManagerThread;
import threads.VODitemsThread;
import users.Customer;
import users.Distributor;

import java.lang.reflect.Array;
import java.util.*;

public class VODmanager {
    private int monthsOfFailure;

//    private ArrayList<String> productsWithPromotion;

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

//        productsToBuy  = new ArrayList<>();
//        productsToBuy.add("Event");
//        productsToBuy.add("Movie");
//        productsToBuy.add("LiveStreaming");

//        productsWithPromotion = new ArrayList<>();
//        productsWithPromotion.add("Movie");
//        productsWithPromotion.add("LiveStreaming");

        // get movies first
        GlobalVariables.database = new DatabaseManager();
    }

    public void startSimulation() {
//        try {
            TimeManagerThread timeManagerThread = new TimeManagerThread();
            timeManagerThread.start();

            VODitemsThread vodItemsThread = new VODitemsThread();
            vodItemsThread.start();

//            DistributorThread distributorThread = new DistributorThread();
//            distributorThread.start();
//
//            Thread.sleep(6000);

//            for (Map.Entry<Integer, Product> pair: GlobalVariables.productsList.entrySet()) {
//                pair.getValue().writeShort();
//            }

//            CustomerThread customerThread = new CustomerThread();
//            customerThread.start();
//
//            productsThread();

//        } catch (InterruptedException e) {
//            System.out.println("Sorry, there was an error " + e.getMessage());
//        }

    }

//    private void distributorsThread() throws InterruptedException {
////        while(true) {
//            // create distributors and their threads
//            if (Math.random() > randomNumbersManager.chanceForDistributor(distributorsList.size()))
//                createDistributor();
//
//            // randomly remove distributor
//            if (Math.random() > 1.0 - randomNumbersManager.getDefaultMinimumChance()) {
//                distributorsList.remove(randomKey(new ArrayList<Integer>(distributorsList.keySet())));
//            }
//
//            Thread.sleep(1000);
////        }
//    }

//    private void customersThread() throws InterruptedException {
////        while (true) {
//            // create users and their threads
//            System.out.println("chance for customer: " + Double.toString(GlobalVariables.randomNumbersManager.chanceForCustomer(GlobalVariables.productsList.size())));
//            createCustomer();
//
//            // randomly remove customer
//            if (Math.random() > 1.0 - GlobalVariables.randomNumbersManager.getDefaultMinimumChance()) {
//                GlobalVariables.customersList.remove(Utility.randomKey(new ArrayList<Integer>(GlobalVariables.customersList.keySet())));
//            }
//
//            Thread.sleep(100);
////        }
//    }

//    private void productsThread() throws InterruptedException {
////        while(true) {
//            // randomly remove product
//            if (Math.random() > 1.0 - GlobalVariables.randomNumbersManager.getDefaultMinimumChance()) {
//                GlobalVariables.productsList.remove(Utility.randomKey(new ArrayList<Integer>(GlobalVariables.productsList.keySet())));
//            }
//
//            Thread.sleep(1000);
////        }
//    }

//    private Integer randomKey(ArrayList<Integer> keyList) {
//        Random random = new Random();
//        List<Integer> keys = keyList;
//        Integer randomKey = keys.get(random.nextInt(keys.size()));
//        return randomKey;
//    }

//    private void createCustomer() throws InterruptedException {
//        int currentId = GlobalVariables.customersCount;
//        Customer c = new Customer(currentId, null, "", "", "" );
//        GlobalVariables.customersList.put(currentId, c);
//        GlobalVariables.customersCount++;
//
////        while (true) {
//            randomCustomerBehaviour(c);
//            Thread.sleep(100);
////        }
//
//    }

//    private void randomCustomerBehaviour(Customer c) {
//        double rand = Math.random();
//        // if customer does not have subscription
//        // subscription can be bought or changed with 20% probability
//        if( rand > 0.8 ) {
//            buySubscription(c);
//        } else if (c.getSubscription() == null || c.getSubscription().getVersionName().equals("")  || c.getSubscription().getVersionName().equals("none")) {
//            // 40% chance to watch a purchased movie
//            if(c.getPurchasedProducts() != null && rand > 0.6 && c.getPurchasedProducts().size() > 0) {
//                watchMovie(c, c.getPurchasedProducts());
//            }
//
//            // chance to buy a movie
//            else if (GlobalVariables.productsList.size() > 0) {
//                buyMovie(c);
//            }
//
//        } else {
//            // resign - 5% chance
//            if(rand > 0.95) {
//                System.out.println("clear subs\n");
//                c.clearSubscription();
//            }
//
//            // watch a movie - 80% chance
//            else if( rand > 0.2) {
//                watchMovie(c, null);
//            }
//        }
//    }

    private void addMonthToProductViewership(Product p) {
        // increase viewership
        if(p.viewership.size() <= GlobalVariables.month ) p.viewership.add(0);
    }

    private void addMonthToProfitBalance() {
        if(!GlobalVariables.monthlyProfitBalance.containsKey(GlobalVariables.month))
            GlobalVariables.monthlyProfitBalance.put(GlobalVariables.month, (float)0);
    }

//    private void watchMovie(Customer c, HashMap<Integer, Product> availableMovies) {
//        HashMap<Integer, Product> productsToWatch = new HashMap<>();
//        if(availableMovies == null) productsToWatch = GlobalVariables.productsList;
//        else productsToWatch = availableMovies;
//
//        for (Map.Entry<Integer, Product> p: productsToWatch.entrySet()) {
//            float score = p.getValue().getRating();
//
//            // the higher rating is, the more probability to buy the movie
//            // another point for favourite genre
//            if (p.getValue().getGenres().toLowerCase().contains(c.getFavouriteGenre().toLowerCase())) {
//                score += 1.0;
//            }
//
//            // chance to watch a product cannot be to big to not watch the same all the time
//            double chance = (1.0 - (score/30.0));
//
//            // watch a product
//            if(Math.random() > chance) {
//                addMonthToProductViewership(p.getValue());
//
//                p.getValue().viewership.set(GlobalVariables.month, p.getValue().viewership.get(GlobalVariables.month) + 1);
//                System.out.println("User with id " + c.getId() + " is watching " + p.getValue().getName()
//                        + " viewership of this movie: " + p.getValue().viewership.get(GlobalVariables.month) + "\n");
//                break;
//            }
//        }
//    }
//
//    private void buyMovie(Customer c) {
//        // buy only events, livestreaming and movie
//        for (Map.Entry<Integer, Product> p: GlobalVariables.productsList.entrySet()) {
//            // check if user arleady have this movie
//            // and if product is available to buy
//            if(!c.getPurchasedProducts().containsKey(p.getKey()) && productsToBuy.contains(p.getValue().getClassName())) {
//                float score = p.getValue().getRating();
//
//                // the higher rating is, the more probability to buy the movie
//                // another point for favourite genre
//                if (p.getValue().getGenres().toLowerCase().contains(c.getFavouriteGenre().toLowerCase())) {
//                    score += 1.0;
//                }
//
//                // buy a product
//                if(Math.random() > (1.0 - (score/10.0))) {
//                    System.out.println("User with id " + c.getId() + " buys: " + p.getValue().getName());
//                    // to do: Promotions
//                    c.addPurchasedProduct(p.getKey(), p.getValue());
//
//                    addMonthToProfitBalance();
//
//                    if(productHasPromotion(p.getValue())) {
//                        GlobalVariables.monthlyProfitBalance.put(GlobalVariables.month, GlobalVariables.monthlyProfitBalance.get(GlobalVariables.month) +
//                                (p.getValue().getPrice() - p.getValue().getPromotion().getDiscount() * p.getValue().getPrice()));
//                    } else
//                        GlobalVariables.monthlyProfitBalance.put(GlobalVariables.month, (GlobalVariables.monthlyProfitBalance.get(GlobalVariables.month) + p.getValue().getPrice()) );
//                    break;
//                }
//            }
//        }
//    }
//
//    private void buySubscription(Customer c) {
//        double randSub = Math.random();
//        Subscription worst = new Subscription(0);
//        Subscription good = new Subscription(1);
//        Subscription best = new Subscription(2);
//
//        if( randSub > (1.0 - worst.getProfitability() ) ) {
//            c.setSubscription(worst);
//        }  else if (randSub > (1.0 - best.getProfitability())) {
//            c.setSubscription(best);
//        }  else {
//            c.setSubscription(good);
//        }
//        System.out.println("User with id " + c.getId() + " buys subs " + c.getSubscription().getVersionName() + "\n");
//    }

//    private void createDistributor() throws InterruptedException {
//        Distributor d = new Distributor(distributorsCount);
//        int currentId = distributorsCount;
//        distributorsList.put(currentId, d);
//        distributorsCount ++;
//
////        while (true) {
//            // create product
//            if(Math.random() > randomNumbersManager.chanceForProduct(d.getProductsCount()) && Utility.getMaxProducts() >= productsList.size()) {
//                if (database.getNextMovie(productsList, productsCount, currentId)) {
//                    // to do: dodaj 0 tyle razy ile jest miesiecy 0...current month
//                    for(int j = 0; j < month; j++ ) {
//                        productsList.get(productsCount).viewership.add(0);
//                    }
//                    distributorsList.get(currentId).increaseProducts();
//                    productsCount++;
//                }
//            }
//
//            Thread.sleep(1000);
////        }
//
//    }

    private void showMenu() {
        System.out.println("Welcome in WerFlix! How should I start the simulation? \n " +
                "1 - Start simulation from the beginning. \n " +
                "2 - Start simulation from the saved point. \n" +
                "3 - Quit.\n");

        for (Map.Entry<Integer, Product> p: GlobalVariables.productsList.entrySet()) {
            p.getValue().writeShort();
        }
        int nr = Utility.inputNumber(3);

        if(nr == 1) {
            System.out.println("Starting from the beginning...\n");
            menuPart2();
        }

        if(nr == 2) {
            System.out.println("Starting from the saved point...\n");
            menuPart2();
        }

        if(nr == 3) {
            quit();
        }

    }
    private void menuPart2() {
        System.out.println("What to manage? \n " +
                "1 - Distributors\n" +
                "2 - Customers\n" +
                "3 - Products\n" +
                "4 - Quit.\n");

        int nr = Utility.inputNumber(4);

        if(nr == 1) {
            commonMenu(1);
        } else if( nr == 2) {
            commonMenu(2);
        } else if( nr == 3 ) {
            commonMenu(3);
        } else {
            quit();
        }
    }

    private void commonMenu(int id) {
        String name;
        switch(id) {
            case 1: name = "Distributors";
                    break;
            case 2: name = "Customers";
                    break;
            case 3: name = "Products";
                    break;
            default: name = "";
                    break;
        }

        System.out.printf("Manage " + name.toLowerCase() + "\n"
        + "1 - Short information about all \n" +
                "2 - Long information by id\n" +
                "3 - Delete element\n" +
                "4 - Add element\n" +
                "5 - Return\n" +
                "5 - Quit.");

        HashMap<Integer, VODitem> item = new HashMap<>();

        int nr = Utility.inputNumber(5);

        if (nr == 1) {
            if(id == 1)
                for (Map.Entry<Integer, Distributor> p: GlobalVariables.distributorsList.entrySet()) {
                    p.getValue().writeShort();
                }
            else if (id == 2)
                for (Map.Entry<Integer, Customer> p: GlobalVariables.customersList.entrySet()) {
                    p.getValue().writeShort();
                }
            else
                for (Map.Entry<Integer, Product> p: GlobalVariables.productsList.entrySet()) {
                    p.getValue().writeShort();
                }
        }
    }

//    private boolean productHasPromotion(Product p) {
//        if(!productsWithPromotion.contains(p.getClassName())) return false;
//        if (p.getPromotion() == null) return false;
//        if (p.getPromotion().getDiscount() == 0) return false;
//        return true;
//    }

//    private void viewershipForAllProducts() {
//        for (Map.Entry<Integer, Product> pair: GlobalVariables.productsList.entrySet()) {
//            addMonthToProductViewership(pair.getValue());
//        }
//    }

//    private void checkPromotions() {
//        for (Map.Entry<Integer, Product> pair: GlobalVariables.productsList.entrySet()) {
//            // create promotion with 10% chance
//            if(Utility.getProductsWithPromotion().contains(pair.getValue().getClassName())) {
//                // if product does not have a promotion
//                if (pair.getValue().getPromotion() == null || pair.getValue().getPromotion().getDiscount() == 0) {
//                    if (Math.random() > 0.9) {
//                        int promotion = (int)(Math.random() * 50 + 5);
//                        int durationMonths = (int)(Math.random() * 2 + 1);
//                        pair.getValue().setPromotion(durationMonths, ((float)promotion / 100) );
//                    }
//                } else {
//                    if(pair.getValue().getPromotion().decreaseDuration() == 0)
//                        pair.getValue().getPromotion().reset();
//                }
//            }
//        }
//    }

//    private void timeManager() throws InterruptedException {
////        while (true) {
//            if (GlobalVariables.day < 30) {
//                GlobalVariables.day++;
//            } else {
//                GlobalVariables.month++;
//                GlobalVariables.day = 0;
//
//                checkProfitBalance();
//                viewershipForAllProducts();
//                checkPromotions();
//            }
//
//            Thread.sleep(1000);
////        }
//    }

    private void showViewership() {}

//    private void checkProfitBalance() {
//        double income = 0;
//
//        // if monthly profit balance to this month was not created
//        addMonthToProfitBalance();
//
//        for (Map.Entry<Integer, Customer> pair: GlobalVariables.customersList.entrySet()) {
//            if(pair.getValue().getSubscription() != null) {
//                income += (double) pair.getValue().getSubscription().getPrice();
//            }
//        }
//
//        for (Map.Entry<Integer, Distributor> pair: GlobalVariables.distributorsList.entrySet()) {
//            income -= (double) pair.getValue().getSalary();
//        }
//
//        GlobalVariables.monthlyProfitBalance.put(GlobalVariables.month, (float)(GlobalVariables.monthlyProfitBalance.get(GlobalVariables.month) + income));
//
//        if(income < 0) monthsOfFailure ++;
//        else monthsOfFailure = 0;
//
//        if(monthsOfFailure >= 3) quit();
//
//        System.out.println("profit balance: " + GlobalVariables.monthlyProfitBalance.get(GlobalVariables.month));
//    }

    private void quit() {
        System.out.println("przegrales");
    }
}
