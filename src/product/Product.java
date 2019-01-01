package product;

import single_classes.Promotion;
import single_classes.VODitem;

import java.util.ArrayList;
import java.util.Date;

public abstract class Product extends VODitem {
    private int id;
    private int distributorId;
    private String imageLink;
    private String name;
    private String description;
    private int prodYear;
    private String durationTime; // in minutes
    private String prodCountry;
    private float rating;
    public ArrayList<Integer> viewership;

    public Product(int id, int distributorId, String imageLink, String name, String description, int prodDate,
                   String durationTime, String prodCountry, float rating) {

            this.id = id;
            this.distributorId = distributorId;
            this.imageLink = imageLink;
            this.name = name;
            this.description = description;
            this.prodYear = prodDate;
            this.durationTime = durationTime;
            this.prodCountry = prodCountry;
            this.rating = rating;
            this.viewership = new ArrayList<>();
    }

    public void watch() {}

    public void writeAll() {
        System.out.printf("--- " + id + ": " + name + " ---\nProduct type: " + this.getClass().getSimpleName()
        + "\nDistributor's id: " + distributorId
        + "\nImage: " + imageLink
        + "\nDescription: " + description
        + "\nProduction date: " + prodYear
        + "\nDuration time in minutes: " + durationTime
        + "\nProduction country: " + prodCountry
        + "\nRating on IMDb: " + rating + "\n");
    }

    public void writeShort() {
        System.out.printf(id + ": " + name + " distributor ID - " + distributorId + "\n");
    }

    public void randomize_fields() {}

    // getters
    public int getId() {
        return id;
    }

    public int getDistributorId() {
        return distributorId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public String getProdCountry() {
        return prodCountry;
    }

    public float getRating() {
        return rating;
    }

    public ArrayList<Integer> getViewership() {
        return viewership;
    }

    public String getGenres() { return ""; }

    public String getClassName() {
        return this.getClass().getSimpleName();
    }

    public Promotion getPromotion() { return null; }

    public void setPromotion(int durationMonths, float discount) {}

    public float getPrice() {
        return 0;
    }
}
