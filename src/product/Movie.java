package product;

import single_classes.Promotion;

import java.util.Date;

public class Movie extends Product {
    private String actors;
//    private String[] trailerLinks;
    private float price;
    private Promotion promotion;
    private String genres;

    public Movie(int id, int distributorId, String imageLink, String name, String description, int prodDate,
          String durationTime, String prodCountry, float rating, String actors, float price, String genres) {
        super(id, distributorId, imageLink, name, description, prodDate, durationTime, prodCountry, rating);

        this.genres = genres;
        this.actors = actors;
        this.price = price;
        this.promotion = null;
    }

    public void randomize_fields() {}

    // getters
    public String getActors() {
        return actors;
    }

//    public String[] getTrailerLinks() {
//        return trailerLinks;
//    }

    public float getPrice() {
        return price;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(int durationMonths, float discount) {
        promotion = new Promotion(durationMonths, discount);
    }

    public String getGenres() { return genres; }
}
