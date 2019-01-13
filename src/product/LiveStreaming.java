package product;

import single_classes.Promotion;

import java.util.Date;

public class LiveStreaming extends Product {
    private float price;
    private Promotion promotion;
    private Date startDate;

    public LiveStreaming(int id, int distributorId, String imageLink, String name, String description, int prodDate,
                  String durationTime, String prodCountry, float rating, float price, Date startDate ) {
        super(id, distributorId, imageLink, name, description, prodDate, durationTime, prodCountry, rating);

        this.promotion = null;
        this.price = price;
        this.startDate = startDate;
    }

    public void randomize_fields() {}

    // getters
    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public Promotion getPromotion() {
        return promotion;
    }

    @Override
    public String getStartDate() {
        return "20.20.20"; // to do
    }

    public void setPromotion(int durationMonths, float discount) {
        promotion = new Promotion(durationMonths, discount);
    }
}
