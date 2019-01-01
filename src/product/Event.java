package product;

import java.util.Date;

public class Event extends Product {
    private float entryPrice;
    private String specialGuests;
    private Date eventDate;

    public Event(int id, int distributorId, String imageLink, String name, String description,
          String durationTime, String prodCountry, float rating, float entryPrice, String specialGuests, Date eventDate) {
        super(id, distributorId, imageLink, name, description, 0, durationTime, prodCountry, rating);

        this.eventDate = eventDate;
        this.entryPrice = entryPrice;
        this.specialGuests = specialGuests;
    }

    public void randomize_fields() {}

    // getters
    public float getPrice() {
        return entryPrice;
    }

    public String getSpecialGuests() {
        return specialGuests;
    }
}
