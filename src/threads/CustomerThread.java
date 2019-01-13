package threads;

import product.Product;
import single_classes.GlobalVariables;
import single_classes.Subscription;
import single_classes.SubscriptionTypes;
import single_classes.Utility;
import users.Customer;

import java.util.HashMap;
import java.util.Map;

public class CustomerThread implements Runnable {
    Thread t;

    public void run() {
        try {
            int currentId = GlobalVariables.customersList.size();
            Customer c = new Customer(currentId, null, "", "", "" );
            GlobalVariables.customersList.put(currentId, c);

            while (true) {
                randomCustomerBehaviour(c);
                Thread.sleep(Utility.getDayTime() / 2);
            }

        } catch (InterruptedException e) {
            System.out.println("There was an error: " + e.getMessage());
        }
    }

    private void randomCustomerBehaviour(Customer c) {
        double rand = Math.random();
        // if customer does not have subscription
        // subscription can be bought or changed with 20% probability
        if( rand > 0.8 ) {
            buySubscription(c);
        } else if (c.getSubscriptionItem() == null || c.getSubscriptionItem().getVersionName().equals("")  || c.getSubscriptionItem().getVersionName().equals("none")) {
            // 40% chance to watch a purchased movie
            if(c.getPurchasedProductsItems() != null && rand > 0.6 && c.getPurchasedProductsItems().size() > 0) {
                watchMovie(c, c.getPurchasedProductsItems());
            }

            // chance to buy a movie
            else if (GlobalVariables.productsList.size() > 0) {
                buyMovie(c);
            }

        } else {
            // resign - 5% chance
            if(rand > 0.95) {
                clearSubscription(c);
            }

            // watch a movie - 80% chance
            else if( rand > 0.2) {
                watchMovie(c, null);
            }
        }
    }

    private void watchMovie(Customer c, HashMap<Integer, Product> availableMovies) {
        HashMap<Integer, Product> productsToWatch = new HashMap<>();
        if(availableMovies == null) productsToWatch = GlobalVariables.productsList;
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
                Utility.addMonthToProductViewership(p.getValue());

                p.getValue().viewership.set(GlobalVariables.month, p.getValue().viewership.get(GlobalVariables.month) + 1);
                System.out.println("User with id " + c.getId() + " is watching " + p.getValue().getName()
                        + " viewership of this movie: " + p.getValue().viewership.get(GlobalVariables.month) + "\n");
                break;
            }
        }
    }

    private void buyMovie(Customer c) {
        // buy only events, livestreaming and movie
        for (Map.Entry<Integer, Product> p: GlobalVariables.productsList.entrySet()) {
            // check if user arleady have this movie
            // and if product is available to buy
            if(!c.getPurchasedProductsItems().containsKey(p.getKey()) && Utility.getProductsToBuy().contains(p.getValue().getClassName())) {
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

//                    Utility.addMonthToProfitBalance();

                    if(productHasPromotion(p.getValue())) {
                        GlobalVariables.globalBalance += (p.getValue().getPrice() - p.getValue().getPromotion().getDiscount() * p.getValue().getPrice());
                    } else
                        GlobalVariables.globalBalance += p.getValue().getPrice();
                    break;
                }
            }
        }
    }

    private void clearSubscription(Customer c) {
        double randSub = Math.random();
        if( randSub > (SubscriptionTypes.getProfitability(c.getSubscriptionItem().getId()) / 4 ) ) {
            c.clearSubscription();
            System.out.println("clear subs\n");
        }

    }

    private void buySubscription(Customer c) {
        double randSub = Math.random();
        Subscription worst = new Subscription(0);
        Subscription good = new Subscription(1);
        Subscription best = new Subscription(2);

        if( randSub > (1.0 - SubscriptionTypes.getProfitability(0) ) ) {
            c.setSubscription(new Subscription(0));
        }  else if (randSub > (1.0 - SubscriptionTypes.getProfitability(2))) {
            c.setSubscription(new Subscription(2));
        }  else {
            c.setSubscription(new Subscription(1));
        }
        System.out.println("User with id " + c.getId() + " buys subs " + c.getSubscriptionItem().getVersionName() + "\n");
    }

    private boolean productHasPromotion(Product p) {
        if(!Utility.getProductsWithPromotion().contains(p.getClassName())) return false;
        if (p.getPromotion() == null) return false;
        if (p.getPromotion().getDiscount() == 0) return false;
        return true;
    }

    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
}
