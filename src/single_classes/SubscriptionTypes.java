package single_classes;

public class SubscriptionTypes {
    private static int[] devices = {1, 2, 4};
    private static int[] resolutionX = {720, 1280, 1920};
    private static int[] resolutionY = {476, 720, 1080};
    private static double[] price = {15, 20, 35};
    private static boolean[] kids = {false, true, false};
    private static String[] name = {"Basic", "Family", "Premium"};
    private static double profitability;

    public static void updateBasic(int newDevices, double newPrice) {
        price[0] = newPrice;
        devices[0] = newDevices;
    }

    public static void updateFamily(int newDevices, double newPrice) {
        price[1] = newPrice;
        devices[1] = newDevices;
    }

    public static void updatePremium(int newDevices, double newPrice) {
        price[2] = newPrice;
        devices[2] = newDevices;
    }

    public static int getDevices(int i) {
        return devices[i];
    }

    public static int getResolutionX(int i) {
        return resolutionX[i];
    }

    public static int getResolutionY(int i) {
        return resolutionY[i];
    }

    public static double getPrice(int i) {
        return price[i];
    }

    public static boolean getKids(int i) {
        return kids[i];
    }

    public static String getName(int i) {
        return name[i];
    }

    public static double getProfitability(int i) {
        profitability = (resolutionX[i] * devices[i]) / 10.0 / (double)price[i] / (double)price[i];
        return profitability;
    }
}
