package single_classes;

import product.Product;
import threads.CustomerThread;
import threads.DistributorThread;
import users.Customer;
import users.Distributor;

import java.util.HashMap;

public class GlobalVariables {
    private HashMap<Integer, Product> productsList;
    private HashMap<Integer, Customer> customersList;
    private HashMap<Integer, Distributor> distributorsList;
    public int globalBalance;

    public RandomNumbersManager randomNumbersManager;
    public int month;
    public int day;
    public DatabaseManager database;

    public int productsCount;
    public int customersCount;
    public int distributorsCount;

    public static GlobalVariables instance = new GlobalVariables();

    private final Object distributorsMonitor = new Object();
    private final Object customersMonitor = new Object();
    private final Object productsMonitor = new Object();

    public GlobalVariables() {
        productsCount = 1;
        customersCount = 1;
        distributorsCount = 1;
        month = 0;
        day = 0;
        productsList = new HashMap<>();
        customersList = new HashMap<>();
        distributorsList = new HashMap<>();
        randomNumbersManager = new RandomNumbersManager();
        globalBalance = 0;

        // get movies first
        database = new DatabaseManager();
    }

    public synchronized int addProduct(int distributorId) {
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

    public void removeProduct(int id) {
        int distributorId = productsList.get(id).getDistributorId();
        if(distributorsList.containsKey(distributorId)) {
            distributorsList.get(distributorId).decreaseProducts();
        }

        productsList.remove(id);
    }

    public int addCustomer() {
        synchronized (customersMonitor) {
            CustomerThread c = new CustomerThread();
            Customer customer = new Customer(customersCount, null, "", "", "" );
            GlobalVariables.instance.customersList.put(customersCount, customer);
            c.start(customersCount, customer);
            customersCount++;
            return customersCount - 1;
        }
    }

    public void removeCustomer(int id) {
        synchronized (customersMonitor) {
            customersList.remove(id);
        }
    }

    public HashMap<Integer, Distributor> getDistributorsList() {
        synchronized (distributorsMonitor) {
            return distributorsList;
        }
    }

    public HashMap<Integer, Customer> getCustomersList() {
        synchronized (customersMonitor) {
            return customersList;
        }
    }

    public HashMap<Integer, Product> getProductsList() {
        return productsList;
    }

    // returns id of created product
    public int addProduct(Product p) {
        synchronized (productsMonitor) {
            p.setid(productsCount);
            productsList.put(productsCount, p);
            productsCount ++;
            return productsCount - 1;
        }
    }

    public int addDistributor() {
        synchronized (distributorsMonitor) {
            DistributorThread dThread = new DistributorThread();
            Distributor d = new Distributor(distributorsCount);
            GlobalVariables.instance.distributorsList.put(distributorsCount, d);
            dThread.start(distributorsCount, d);
            distributorsCount++;
            return distributorsCount - 1;
        }
    }

    public void removeDistributor(int id) {
        synchronized (distributorsMonitor) {
            distributorsList.remove(id);
        }
    }
}
