package single_classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Utility {
    private static int max_products = 200;

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
}
