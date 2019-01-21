package product;

import single_classes.Promotion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LiveStreaming extends Product {
    private float price;
    private Promotion promotion;
    private Date startDate;

    public LiveStreaming(int distributorId, String imageLink, String name, String description, int prodDate,
                  String durationTime, String prodCountry, float rating, float price, Date startDate ) {
        super(distributorId, imageLink, name, description, prodDate, durationTime, prodCountry, rating);

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
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String reportDate = df.format(startDate);
        return reportDate;
    }

    public void setPromotion(int durationMonths, float discount) {
        promotion = new Promotion(durationMonths, discount);
    }
}
