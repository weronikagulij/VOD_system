package single_classes;

import product.Product;
import users.Customer;
import users.Distributor;

import java.util.HashMap;

public class GlobalVariables {
    public static HashMap<Integer, Product> productsList;
    public static HashMap<Integer, Customer> customersList;
    public static HashMap<Integer, Distributor> distributorsList;
    public static HashMap<Integer, Float> monthlyProfitBalance;

    public static RandomNumbersManager randomNumbersManager;
    public static int month;
    public static int day;
    public static DatabaseManager database;

    public static int productsCount;
    public static int customersCount;
    public static int distributorsCount;
}
