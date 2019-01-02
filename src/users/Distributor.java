package users;

import single_classes.VODitem;

public class Distributor implements VODitem {
    private int id;
    private int salary;
    private int productsCount;

    public Distributor(int id) {
        this.productsCount = 0;
        this.id = id;
        this.salary = 5;
    }

    public void randomBehavior() {}

    public void increaseProducts() {
        productsCount ++;
        salary += 10;
    }

    // getters
    public int getId() {
        return id;
    }

    public int getSalary() {
        return salary;
    }

    public int getProductsCount() {
        return productsCount;
    }

    public void writeShort() {
        System.out.println("Id: " + id + " did " + productsCount + " products");
    }

    public void writeAll() {
        System.out.printf("--- Distributor nr " + id + " ---\n"
        + "Salary: " + salary + "\n"
        + "Number of created products: " + productsCount + "\n");
    }
}
