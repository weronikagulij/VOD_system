package single_classes;

import product.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Utility {
    private static int max_products = 200;
    private static int day_time = 2000;

    public static int getDayTime() {
        return day_time;
    }

    public static int inputNumber(int max) {
        int n;
        while ((n = checkIfNumber(max)) == 0) {
            System.out.println("Value is wrong!\n");
        }
        return n;
    }

    private static int checkIfNumber(int max) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String s = br.readLine();

            Scanner sc = new Scanner(s.trim());
            if(!sc.hasNextInt(10)) return 0;
            sc.nextInt(10);
            if (sc.hasNext()) return 0;

            int i = Integer.parseInt(s);
            if(i > 0 && i <= max) return i;

        } catch (IOException e) {
            System.out.println("Sorry, there was an error: " + e.getMessage());
        }

        return 0;
    }

    public static int getMaxProducts() {
        return max_products;
    }

    public static Integer randomKey(ArrayList <Integer> keyList) {
        Random random = new Random();
        List<Integer> keys = keyList;
        Integer randomKey = keys.get(random.nextInt(keys.size()));
        return randomKey;
    }

    public static ArrayList<String> getProductsToBuy() {
        ArrayList<String> productsToBuy = new ArrayList<>();
        productsToBuy.add("Event");
        productsToBuy.add("Movie");
        productsToBuy.add("LiveStreaming");

        return productsToBuy;
    }

    public static ArrayList<String> getProductsWithPromotion() {
        ArrayList<String> productsWithPromotion = new ArrayList<>();
        productsWithPromotion.add("Movie");
        productsWithPromotion.add("LiveStreaming");
        return productsWithPromotion;
    }

    public static void addMonthToProductViewership(Product p) {
        // increase viewership
        if(p.viewership.size() <= GlobalVariables.month ) p.viewership.add(0);
    }

    public static void addMonthToProfitBalance() {
        if(!GlobalVariables.monthlyProfitBalance.containsKey(GlobalVariables.month))
            GlobalVariables.monthlyProfitBalance.put(GlobalVariables.month, (float)0);
    }
}
