import jdk.jshell.execution.Util;
import product.Product;
import single_classes.*;
import users.Customer;
import users.Distributor;

import java.lang.reflect.Array;
import java.util.*;

public class VODmanager {
    private HashMap<Integer, Product> productsList;
    private HashMap<Integer, Customer> customersList;
    private HashMap<Integer, Distributor> distributorsList;
    private ArrayList<Float> monthlyProfitBalance;
    private int month; // adding 0.2 with every action
    private Integer productsCount;
    private int customersCount;
    private int distributorsCount;
    private DatabaseManager database;
    private RandomNumbersManager randomNumbersManager;

    private ArrayList<String> productsWithPromotion;
    private ArrayList<String> productsToBuy;

    VODmanager() {
        productsCount = 0;
        customersCount = 0;
        distributorsCount = 0;
        month = 0;
        productsList = new HashMap<>();
        customersList = new HashMap<>();
        distributorsList = new HashMap<>();
        monthlyProfitBalance = new ArrayList<>();
        randomNumbersManager = new RandomNumbersManager();

        productsToBuy  = new ArrayList<>();
        productsToBuy.add("Event");
        productsToBuy.add("Movie");
        productsToBuy.add("LiveStreaming");

        productsWithPromotion = new ArrayList<>();
        productsWithPromotion.add("Movie");
        productsWithPromotion.add("LiveStreaming");

        // get movies first
        database = new DatabaseManager();
    }

    public void startSimulation() {
        timeManager();

        // create distributors and their threads
//        if(Math.random() > randomNumbersManager.chanceForDistributor(distributorsList.size()))
        System.out.println("chance for distributor: " + randomNumbersManager.chanceForDistributor(distributorsList.size()));
//            createDistributor();

        for (Map.Entry<Integer, Product> pair: productsList.entrySet()) {
            pair.getValue().writeShort();
        }

        // create users and their threads
        System.out.println("chance for customer: " + randomNumbersManager.chanceForCustomer(productsList.size()));
//        createCustomer();
//        if(Math.random() > chance)
//        showMenu();

        // randomly set promotion

        // randomly remove product
        if(Math.random() > 1.0 - randomNumbersManager.getDefaultMinimumChance()) {
             productsList.remove(randomKey(new ArrayList<Integer>(productsList.keySet())));
        }

        // randomly remove distributor
        if(Math.random() > 1.0 - randomNumbersManager.getDefaultMinimumChance()) {
            distributorsList.remove(randomKey(new ArrayList<Integer>(distributorsList.keySet())));
        }

        // randomly remove customer
        if(Math.random() > 1.0 - randomNumbersManager.getDefaultMinimumChance()) {
            customersList.remove(randomKey(new ArrayList<Integer>(customersList.keySet())));
        }
    }

    private Integer randomKey(ArrayList<Integer> keyList) {
        Random random = new Random();
        List<Integer> keys = keyList;
        Integer randomKey = keys.get(random.nextInt(keys.size()));
        return randomKey;
    }

    private void createCustomer() {
        int currentId = customersCount;
        Customer c = new Customer(currentId, null, "", "", "" );
        customersList.put(currentId, c);
        customersCount++;

        for(int i = 0; i < 10; i++) {
            randomCustomerBehaviour(c);
        }

    }

    private void randomCustomerBehaviour(Customer c) {
        double rand = Math.random();
        // if customer does not have subscription
        // subscription can be bought or changed with 20% probability
        if( rand > 0.8 ) {
            buySubscription(c);
        } else if (c.getSubscription() == null || c.getSubscription().getVersionName().equals("")  || c.getSubscription().getVersionName().equals("none")) {
            // 40% chance to watch a purchased movie
            if(c.getPurchasedProducts() != null && rand > 0.6 && c.getPurchasedProducts().size() > 0) {
                watchMovie(c, c.getPurchasedProducts());
            }

            // chance to buy a movie
            else if (productsList.size() > 0) {
                buyMovie(c);
            }

        } else {
            // resign - 5% chance
            if(rand > 0.95) {
                System.out.println("clear subs\n");
                c.clearSubscription();
            }

            // watch a movie - 80% chance
            else if( rand > 0.2) {
                watchMovie(c, null);
            }
        }
    }

    private void watchMovie(Customer c, HashMap<Integer, Product> availableMovies) {
        HashMap<Integer, Product> productsToWatch = new HashMap<>();
        if(availableMovies == null) productsToWatch = productsList;
        else productsToWatch = availableMovies;

        for (Map.Entry<Integer, Product> p: productsToWatch.entrySet()) {
            float score = p.getValue().getRating();

            // the higher rating is, the more probability to buy the movie
            // another point for favourite genre
            if (p.getValue().getGenres().toLowerCase().contains(c.getFavouriteGenre().toLowerCase())) {
                score += 1.0;
            }

            // chance to watch a product cannot be to big to not watch the same all the time
            double chance = (1.0 - (score/30.0));

            // watch a product
            if(Math.random() > chance) {
                // increase viewership
                p.getValue().viewership.set(month, p.getValue().viewership.get(month) + 1);
                System.out.println("User with id " + c.getId() + " is watching " + p.getValue().getName()
                        + " viewership of this movie: " + p.getValue().viewership.get(month) + "\n");
                break;
            }
        }
    }

    private void buyMovie(Customer c) {
        // buy only events, livestreaming and movie
        for (Map.Entry<Integer, Product> p: productsList.entrySet()) {
            // check if user arleady have this movie
            // and if product is available to buy
            if(!c.getPurchasedProducts().containsKey(p.getKey()) && productsToBuy.contains(p.getValue().getClassName())) {
                float score = p.getValue().getRating();

                // the higher rating is, the more probability to buy the movie
                // another point for favourite genre
                if (p.getValue().getGenres().toLowerCase().contains(c.getFavouriteGenre().toLowerCase())) {
                    score += 1.0;
                }

                // buy a product
                if(Math.random() > (1.0 - (score/10.0))) {
                    System.out.println("User with id " + c.getId() + " buys: " + p.getValue().getName());
                    // to do: Promotions
                    c.addPurchasedProduct(p.getKey(), p.getValue());
                    monthlyProfitBalance.set(month, (monthlyProfitBalance.get(month) + p.getValue().getPrice()) );
                    break;
                }
            }
        }
    }

    private void buySubscription(Customer c) {
        double randSub = Math.random();
        Subscription worst = new Subscription(0);
        Subscription good = new Subscription(1);
        Subscription best = new Subscription(2);

        if( randSub > (1.0 - worst.getProfitability() ) ) {
            c.setSubscription(worst);
        }  else if (randSub > (1.0 - best.getProfitability())) {
            c.setSubscription(best);
        }  else {
            c.setSubscription(good);
        }
        System.out.println("User with id " + c.getId() + " buys subs " + c.getSubscription().getVersionName() + "\n");
    }

    private void createDistributor() {
        Distributor d = new Distributor(distributorsCount);
        int currentId = distributorsCount;
        distributorsList.put(currentId, d);
        distributorsCount ++;

        for(int i = 0; i < 10; i++) {
            double chance = 0.005;
            if (distributorsCount < 200) chance = 1 / Math.pow(1.25, d.getProductsCount());

            if(Math.random() > 1.0 - chance && Utility.getMaxProducts() <= productsList.size()) {
                if (database.getNextMovie(productsList, productsCount, currentId)) {
                    // to do: dodaj 0 tyle razy ile jest miesiecy 0...current month
                    productsList.get(productsCount).viewership.add(0);
                    distributorsList.get(currentId).increaseProducts();
                    productsCount++;
                }
            }
        }

//        try {
//            while (true) {
//                if(database.getNextMovie(productsList, productsCount, currentId) == true) {
//                    distributorsList.get(currentId).increaseSalary();
//                }
//
//                System.out.println("Current salary with distributor " + currentId + " equals " +
//                        distributorsList.get(currentId).getSalary());
//                Thread.sleep(5000);
//            }
//        } catch (InterruptedException e) {
//            System.out.println("there was a problem with threads.\n");
//        }

    }

    private void showMenu() {
        System.out.println("Welcome in WerFlix! How should I start the simulation? \n " +
                "1 - Start simulation from the beginning. \n " +
                "2 - Start simulation from the saved point. \n" +
                "3 - Quit.\n");

        for (Map.Entry<Integer, Product> p: productsList.entrySet()) {
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
                for (Map.Entry<Integer, Distributor> p: distributorsList.entrySet()) {
                    p.getValue().writeShort();
                }
            else if (id == 2)
                for (Map.Entry<Integer, Customer> p: customersList.entrySet()) {
                    p.getValue().writeShort();
                }
            else
                for (Map.Entry<Integer, Product> p: productsList.entrySet()) {
                    p.getValue().writeShort();
                }
        }
    }

    private void timeManager() {
        monthlyProfitBalance.add((float)0); // starting for the first month
        for (Map.Entry<Integer, Product> pair: productsList.entrySet()) {
            pair.getValue().viewership.add(0);
        }

        // to do: if pelny miesiac
        for (Map.Entry<Integer, Product> pair: productsList.entrySet()) {
            // create promotion with 10% chance
            if(productsToBuy.contains(pair.getValue().getClassName())) {
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

                // to do
//                System.out.println("Produkt " + pair.getValue().getName() + " podsiada promocje: " + pair.getValue().getPromotion().getDiscount() +
//                        " \nCena przed promocja: " + pair.getValue().getPrice() + " \nCena po promocji: " +
//                        Float.toString(pair.getValue().getPrice() - pair.getValue().getPromotion().getDiscount() * pair.getValue().getPrice()));
            }
        }
    }
    private void showViewership() {}
    private void manageProducts() {}
    private void manageCustomers() {}
    private void manageDistributors() {}
    private void checkProfitBalance() {}
    private void quit() {}
}
