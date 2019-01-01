package single_classes;

import java.util.Date;

public class Promotion {

    private float discount;
    private int durationMonths;

    public Promotion(int durationMonths, float discount) {
        this.durationMonths = durationMonths;
        this.discount = discount;
    }

    public void reset() {
        this.durationMonths = 0;
        this.discount = 0;
    }

    public int decreaseDuration() {
        if (durationMonths > 0) durationMonths --;
        if (durationMonths == 0) discount = 0;

        return durationMonths;
    }

    public float getDiscount() {

        return discount;
    }
}
