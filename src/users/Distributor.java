package users;

import single_classes.VODuser;

public class Distributor implements VODuser {
    private int id;
    private int salary;
    private int productsCount;
    private Thread t;

    public Distributor(int id) {
        this.productsCount = 0;
        this.id = id;
        this.salary = 20;
    }

    public void increaseProducts() {
        productsCount ++;
        salary += 15;
    }

    public void decreaseProducts() {
        productsCount --;
        salary -= 15;
    }

    public void setT(Thread t) {
        this.t = t;
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

    public Thread getT() {
        return t;
    }
}
