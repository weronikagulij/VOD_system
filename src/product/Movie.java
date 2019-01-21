package product;

import single_classes.Promotion;

public class Movie extends Product {
    private String actors;
    private float price;
    private Promotion promotion;
    private String genres;

    public Movie(int distributorId, String imageLink, String name, String description, int prodDate,
          String durationTime, String prodCountry, float rating, String actors, float price, String genres) {
        super(distributorId, imageLink, name, description, prodDate, durationTime, prodCountry, rating);

        this.genres = genres;
        this.actors = actors;
        this.price = price;
        this.promotion = null;
    }

    public void randomize_fields() {}

    // getters
    @Override
    public String getActors() {
        return actors;
    }

    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(int durationMonths, float discount) {
        promotion = new Promotion(durationMonths, discount);
    }

    @Override
    public String getGenres() { return genres; }
}
