package threads;

import product.Product;
import single_classes.GlobalVariables;
import single_classes.Subscription;
import single_classes.SubscriptionTypes;
import single_classes.Utility;
import users.Customer;

import java.util.HashMap;
import java.util.Map;

public class CustomerThread extends CustomThread<Customer> implements Runnable {
    public void run() {
        try {
            while (true) {
                randomCustomerBehaviour();
                Thread.sleep(Utility.getDayTime() * 2);
            }
        } catch (InterruptedException e) {
            System.out.println("There was an error: " + e.getMessage());
        }
    }

    private void randomCustomerBehaviour() {
        double rand = Math.random();
        // if customer does not have subscription
        if (user.getSubscriptionItem() == null || user.getSubscriptionItem().getVersionName().equals("")  || user.getSubscriptionItem().getVersionName().equals("none")) {

            // subscription can be bought with 20% probability
            if( rand > 0.6 ) {
                buySubscription(user);
            }

            // 40% chance to watch a purchased movie
            if(user.getPurchasedProductsItems() != null && rand > 0.6 && user.getPurchasedProductsItems().size() > 0) {
                watchMovie(user.getPurchasedProductsItems());
            }

            // chance to buy a movie
            else if (GlobalVariables.instance.getProductsList().size() > 0) {
                buyMovie(user);
            }

        } else {
            // resign - 5% chance
            if(rand > 0.95) {
                clearSubscription();
            }

            // watch a movie - 80% chance
            else if( rand > 0.2) {
                watchMovie(null);
            }
        }
    }

    private void watchMovie(HashMap<Integer, Product> availableMovies) {
        HashMap<Integer, Product> productsToWatch;
        if(availableMovies == null) productsToWatch = (HashMap<Integer, Product>) GlobalVariables.instance.getProductsList().clone();
        else productsToWatch = availableMovies;

        for (Map.Entry<Integer, Product> p: productsToWatch.entrySet()) {
            float score = p.getValue().getRating();

            // the higher rating is, the more probability to buy the movie
            // another point for favourite genre
            if (p.getValue().getGenres().toLowerCase().contains(user.getFavouriteGenre().toLowerCase())) {
                score += 1.0;
            }

            // chance to watch a product cannot be to big to not watch the same all the time
            double chance = (1.0 - (score/30.0));

            // watch a product
            if(Math.random() > chance) {
                Utility.addMonthToProductViewership(p.getValue());

                p.getValue().viewership.set(GlobalVariables.instance.month, p.getValue().viewership.get(GlobalVariables.instance.month) + 1);
                System.out.println("User with id " + user.getId() + " is watching " + p.getValue().getName()
                        + " viewership of this movie: " + p.getValue().viewership.get(GlobalVariables.instance.month) + "\n");
                break;
            }
        }
    }

    private void buyMovie(Customer c) {
        // buy only events, livestreaming and movie
        HashMap<Integer, Product> productsList = (HashMap<Integer, Product>) GlobalVariables.instance.getProductsList().clone();

        for (Map.Entry<Integer, Product> p: productsList.entrySet()) {
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
                    c.addPurchasedProduct(p.getKey(), p.getValue());

                    if(productHasPromotion(p.getValue())) {
                        GlobalVariables.instance.globalBalance += (p.getValue().getPrice() - p.getValue().getPromotion().getDiscount() * p.getValue().getPrice());
                    } else
                        GlobalVariables.instance.globalBalance += p.getValue().getPrice();
                    break;
                }
            }
        }
    }

    private void clearSubscription() {
        double randSub = Math.random();
        if( randSub > (SubscriptionTypes.getProfitability(user.getSubscriptionItem().getId()) / 10 ) ) {
            user.clearSubscription();
            System.out.println("clear subs\n");
        }

    }

    private void buySubscription(Customer c) {
        double randSub = Math.random();
        if( randSub > (1.0 - SubscriptionTypes.getProfitability(0) ) ) {
            c.setSubscription(new Subscription(0));
        }  else if (randSub > (1.0 - SubscriptionTypes.getProfitability(2))) {
            c.setSubscription(new Subscription(2));
        }  else if (randSub > (1.0 - SubscriptionTypes.getProfitability(1))) {
            c.setSubscription(new Subscription(1));
        }
        if(c.getSubscriptionItem() != null)
        System.out.println("User with id " + c.getId() + " buys subs " + c.getSubscriptionItem().getVersionName() + "\n");
    }

    private boolean productHasPromotion(Product p) {
        if(!Utility.getProductsWithPromotion().contains(p.getClassName())) return false;
        if (p.getPromotion() == null) return false;
        if (p.getPromotion().getDiscount() == 0) return false;
        return true;
    }

    public void start(int id, Customer c) {
        super.initThread(id, c);
        if (c.getT() == null) {
            Thread thread = new Thread(this);
            thread.setDaemon(true);
            c.setT(thread);
            c.getT().start();
        }
    }
}
