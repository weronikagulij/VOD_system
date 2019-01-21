package users;

import enums.Genres;
import product.Product;
import single_classes.Subscription;
import single_classes.VODuser;

import java.util.*;

public class Customer implements VODuser {
    private int id;
    private Date birthDate;
    private String email;
    private String cardNumber;
    private Subscription subscription;
    private HashMap<Integer,Product> purchasedProducts;
    private String favouriteGenre;
    private Thread t;

    private Object subscriptionMonitor = new Object();
    private Object purchasedProductsMonitor = new Object();

    public Customer(int id, Date birthDate, String email, String cardNumber, String favouriteGenre) {
        this.id = id;
        this.subscription = new Subscription(-1);
        this.purchasedProducts = new HashMap<>();

        if (birthDate == null) {
            randomizeFields();
        } else  {
            this.birthDate = birthDate;
            this.email = email;
            this.cardNumber = cardNumber;
            this.favouriteGenre = favouriteGenre;
        }
    }

    private void randomizeFields() {
        birthDate = new Date(System.currentTimeMillis() + (int)(Math.random() * 100000000));
        favouriteGenre = Genres.randomGenre().toString();
        cardNumber = Integer.toString((int)(Math.random() * 10)) +  Integer.toString((int)(Math.random() * 10))
                +  Integer.toString((int)(Math.random() * 10)) +  Integer.toString((int)(Math.random() * 10));

        // randomize email
        Random random = new Random();
        char[] word = new char[random.nextInt(6)+3];
        for(int j = 0; j < word.length; j++) {
            word[j] = (char)('a' + random.nextInt(26));
        }
        email = new String(word);

        if(Math.random() > 0.5) email += "@wp.pl";
        else email += "@gmail.com";
    }

    public void clearSubscription() {
        synchronized (subscriptionMonitor) {
            this.subscription = null;
        }
    }

    public void setSubscription(Subscription s) {
        synchronized (subscriptionMonitor) {
            this.subscription = s;
        }
    }

    public void setT(Thread t) {
        this.t = t;
    }

    // getters
    public String getFavouriteGenre() {
        return this.favouriteGenre;
    }

    public Thread getT() {
        return t;
    }

    public int getId() {
        return id;
    }

    public String getBirthDate() {
        return "20. 20. 120";
    }

    public String getEmail() {
        return email;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getSubscription() {
        synchronized (subscriptionMonitor) {
            if(subscription == null) return "none";
            else return subscription.getVersionName();
        }
    }

    public Subscription getSubscriptionItem() {
        synchronized (subscriptionMonitor) {
            return subscription;
        }
    }

    public String getPurchasedProducts() {
        synchronized (purchasedProductsMonitor) {
            String products = "";
            for (Map.Entry<Integer, Product> p : purchasedProducts.entrySet()) {
                products = products + Integer.toString(p.getValue().getId()) + ", ";
            }

            if (products.endsWith(", ")) products = products.substring(0, products.length() - 2);
            return products;
        }
    }

    public HashMap< Integer, Product> getPurchasedProductsItems() {
        synchronized (purchasedProductsMonitor) {
            return purchasedProducts;
        }
    }

    public void addPurchasedProduct(Integer k, Product p) {
        synchronized (purchasedProductsMonitor) {
            purchasedProducts.put(k, p);
        }
    }
}
