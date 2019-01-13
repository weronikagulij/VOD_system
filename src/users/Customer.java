package users;

import enums.Genres;
import product.Product;
import single_classes.Subscription;
import single_classes.VODitem;

import java.util.*;

public class Customer implements VODitem {
    private int id;
    private Date birthDate;
    private String email;
    private String cardNumber;
    private Subscription subscription;
    private HashMap<Integer,Product> purchasedProducts;
    private String favouriteGenre;

    public Customer(int id, Date birthDate, String email, String cardNumber, String favouriteGenre) {
        this.id = id;
        this.subscription = new Subscription(-1);
        this.purchasedProducts = new HashMap<>();

//        System.out.println(Genres.randomGenre().toString());

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

    @Override
    public void writeShort() {
        System.out.println("--- Id: " + id + " - " + email + " ---");
    }

    @Override
    public void writeAll() {
        System.out.printf("--- Customer nr " + id + " ---\n"
        + "Birth date: " + birthDate + "\n"
        + "E-mail: " + email + "\n"
        + "Card number: " + cardNumber + "\n"
        + "Subscription: " + subscription.getVersionName()
        + "\nPurchased products: ");

        for (Map.Entry<Integer, Product> p : purchasedProducts.entrySet()) {
            System.out.printf(p.getValue().getId() + " - " + p.getValue().getName() + "\n");
        }

        System.out.printf("\nFavourite genre: " + favouriteGenre + "\n");
    }

    public void clearSubscription() {
        this.subscription = null;
    }

    public void setSubscription(Subscription s) {
        this.subscription = s;
    }

    // getters
    public String getFavouriteGenre() {
        return this.favouriteGenre;
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
        return subscription.getVersionName();
    }

    public Subscription getSubscriptionItem() {
        return subscription;
    }

    public String getPurchasedProducts() {
        String products = "";
        for (Map.Entry<Integer, Product> p : purchasedProducts.entrySet()) {
            products = String.join(Integer.toString(p.getValue().getId()), ", ");
        }

        if(products.endsWith(", ")) products = products.substring(0, products.length() - 2);

        return products;
    }

    public HashMap< Integer, Product> getPurchasedProductsItems() {
        return purchasedProducts;
    }

    public void addPurchasedProduct(Integer k, Product p) {
        purchasedProducts.put(k, p);
    }
}
