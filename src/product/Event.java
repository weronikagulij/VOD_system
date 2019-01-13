package product;

import java.util.Date;

public class Event extends Product {
    private float price; // entry price
    private String specialGuests;
    private Date startDate;

    public Event(int id, int distributorId, String imageLink, String name, String description,
                 String durationTime, String prodCountry, float rating, float price, String specialGuests, Date startDate) {
        super(id, distributorId, imageLink, name, description, 0, durationTime, prodCountry, rating);

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
        return "1.1.1"; // to do
    }

    @Override
    public String getSpecialGuests() {
        return specialGuests;
    }
}
