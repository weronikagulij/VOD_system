package single_classes;

import product.Product;
import threads.DistributorThread;
import users.Customer;
import users.Distributor;

import java.util.HashMap;
import java.util.Map;

public class GlobalVariables {
    public static HashMap<Integer, Product> productsList;
    public static HashMap<Integer, Customer> customersList;
    public static HashMap<Integer, Distributor> distributorsList;
    public static int globalBalance;

    public static RandomNumbersManager randomNumbersManager;
    public static int month;
    public static int day;
    public static DatabaseManager database;

    public static int productsCount;
    public static int customersCount;
    public static int distributorsCount;

    public static GlobalVariables instance = new GlobalVariables();

    public static synchronized int addProduct(int distributorId) {
        int productId = 0;
        if(!distributorsList.containsKey(distributorId)) return productId;

        productId = database.getNextMovie(distributorId);
        if (productId == 0) return 0;

        for (int j = 0; j < month; j++) {
            productsList.get(productId).viewership.add(0);
        }

        distributorsList.get(distributorId).increaseProducts();
        return productId;
    }

    public static void removeProduct(int id) {
        int distributorId = productsList.get(id).getDistributorId();
        if(distributorsList.containsKey(distributorId)) {
            distributorsList.get(distributorId).decreaseProducts();
        }

        productsList.remove(id);
    }

    public static synchronized int addCustomer() {
        customersList.put(customersCount, new Customer(customersCount, null, "", "", ""));
        customersCount++;
        return customersCount - 1;
    }

    public static void removeCustomer(int id) {
        customersList.remove(id);
    }

    public static synchronized int addDistributor() {
        DistributorThread d = new DistributorThread();
        d.start(distributorsCount);
        distributorsCount++;
        return distributorsCount - 1;
    }

    public static void removeDistributor(int id) {
        distributorsList.remove(id);
    }
}
