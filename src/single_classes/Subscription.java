package single_classes;

import java.util.Date;

public class Subscription {
    private String version;
    private int devices;
    private int resolutionX;
    private int resolutionY;
    private double profitability;
    private double price;
    private boolean kids;
    private int id;

    public Subscription(int id) {
        version = "none";
        this.id = id;
        switch (id) {
            case 0: devices = SubscriptionTypes.getDevices(0);
                    resolutionX = SubscriptionTypes.getResolutionX(0);
                    resolutionY = SubscriptionTypes.getResolutionY(0);
                    price = SubscriptionTypes.getPrice(0);
                    version = SubscriptionTypes.getName(0);
                    kids = SubscriptionTypes.getKids(0);
                    break;
            case 1: devices = SubscriptionTypes.getDevices(1);
                    resolutionX = SubscriptionTypes.getResolutionX(1);
                    resolutionY = SubscriptionTypes.getResolutionY(1);
                    price = SubscriptionTypes.getPrice(1);
                    version = SubscriptionTypes.getName(1);
                    kids = SubscriptionTypes.getKids(1);
                    break;
            case 2: devices = SubscriptionTypes.getDevices(2);
                    resolutionX = SubscriptionTypes.getResolutionX(2);
                    resolutionY = SubscriptionTypes.getResolutionY(2);
                    price = SubscriptionTypes.getPrice(2);
                    version = SubscriptionTypes.getName(2);
                    kids = SubscriptionTypes.getKids(2);
                    break;
            default: {
                        devices = 0;
                        resolutionX = 0;
                        resolutionY = 0;
                        price = 0;
            }

        }

//        countProfitability();
    }

//    private void countProfitability() {
//        profitability = (resolutionX * devices) / 10.0 / (double)price / (double)price;
//    }

//    public void extend() {}
//
//    public void set_values_by_version() {}

    // getters

    public int getId() {
        return id;
    }

    public boolean getKids() {
        return kids;
    }

    public String getVersionName() {
        return version;
    }

    public int getDevices() {
        return devices;
    }

    public int getResolutionX() {
        return resolutionX;
    }

    public int getResolutionY() {
        return resolutionY;
    }

//    public double getProfitability() {
//        return profitability;
//    }

    public double getPrice() { return price; }
}
