package single_classes;

public class RandomNumbersManager {
    private double defaultMinimumChance;
    private double distributorsChanceFactor;
    private double productsChanceFactor;
    private double customersChanceFactor;

    public RandomNumbersManager() {
        defaultMinimumChance = 0.005;
        distributorsChanceFactor = 1.1;
        productsChanceFactor = 1.25;
        customersChanceFactor = 1.01;
    }

    public double chanceForProduct(int distributorsProductCount) {
        double chance = defaultMinimumChance;
        if (distributorsProductCount < 200) chance = 1 / Math.pow(1.25, distributorsProductCount);

        return (1.0 - chance);
    }

    public double chanceForCustomer(int productsCount) {
        // chance for customer depends on movies count
        double chance = defaultMinimumChance;
        if (productsCount < 200) chance = 1 / Math.pow(1.01, productsCount);

        return 0.0;
    }

    public double chanceForDistributor(int distributorsCount) {
        // the more distributors is, tle less chance to get new one
        double chance = defaultMinimumChance;
        if (distributorsCount < 200) chance = 1 / Math.pow(1.1, distributorsCount);

        return (1.0 - chance);
    }

    public double getDefaultMinimumChance() {
        return defaultMinimumChance;
    }
}
