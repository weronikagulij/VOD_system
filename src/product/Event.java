package product;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event extends Product {
    private float price; // entry price
    private String specialGuests;
    private Date startDate;

    public Event(int distributorId, String imageLink, String name, String description,
                 String durationTime, String prodCountry, float rating, float price, String specialGuests, Date startDate) {
        super(distributorId, imageLink, name, description, 0, durationTime, prodCountry, rating);

        this.startDate = startDate;
        this.price = price;
        this.specialGuests = specialGuests;
    }

    public void randomize_fields() {}

    // getters
    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public String getStartDate() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String reportDate = df.format(startDate);
        return reportDate;
    }

    @Override
    public String getSpecialGuests() {
        return specialGuests;
    }
}
