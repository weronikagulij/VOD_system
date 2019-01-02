package single_classes;

import java.util.Date;

public class Subscription {
    private String version;
    private int devices;
    private int resolutionX;
    private int resolutionY;
    private double profitability;
    private int price;
    private boolean kids;

    public Subscription(int id) {
        version = "none";
        kids = false;

        switch (id) {
            case 0: devices = 1;
                    resolutionX = 720;
                    resolutionY = 476;
                    price = 15;
                    version = "Basic";
                    break;
            case 1: devices = 2;
                    resolutionX = 1280;
                    resolutionY = 720;
                    price = 20;
                    version = "Family";
                    kids = true;
                    break;
            case 2: devices = 4;
                    resolutionX = 1920;
                    resolutionY = 1080;
                    price = 35;
                    version = "Premium";
                    break;
            default: {
                        devices = 0;
                        resolutionX = 0;
                        resolutionY = 0;
                        price = 0;
            }

        }

        countProfitability();
    }

    private void countProfitability() {
        profitability = (resolutionX * devices) / 10.0 / (double)price / (double)price;
    }

    public void extend() {}

    public void set_values_by_version() {}

    // getters

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

    public double getProfitability() {
        return profitability;
    }

    public int getPrice() { return price; }
}
