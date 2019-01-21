package single_classes;

public class RandomNumbersManager {
    private double defaultMinimumChance;

    public RandomNumbersManager() {
        defaultMinimumChance = 0.000000001;
    }

    public double chanceForProduct(int distributorsProductCount) {
        double chance = defaultMinimumChance;
        if (distributorsProductCount < 200) chance = 1 / Math.pow(1.25, distributorsProductCount);

        return (1.0 - chance);
    }

    public double chanceForCustomer() {
        // chance for customer depends on movies count
        int productsCount = GlobalVariables.instance.getProductsList().size();
        double chance = 0.9;
        if (productsCount < 200) chance = 1 / Math.pow(1.08, productsCount);
        if(chance < 0.7) chance = 0.9;

        return chance;
    }

    public double chanceForDistributor() {
        // the more distributors is, tle less chance to get new one
        int distributorsCount = GlobalVariables.instance.getDistributorsList().size();
        double chance = defaultMinimumChance;
        if (distributorsCount < 200) chance = 1 / Math.pow(1.1, distributorsCount);

        return (1.0 - chance);
    }

    public double getDefaultMinimumChance() {
        return defaultMinimumChance;
    }
}
