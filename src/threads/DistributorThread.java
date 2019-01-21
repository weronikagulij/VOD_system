package threads;

import single_classes.GlobalVariables;
import single_classes.Utility;
import users.Distributor;

public class DistributorThread extends CustomThread<Distributor> implements Runnable {
    public void run() {
        try {
            while (true) {
//          create product
                if (Math.random() > GlobalVariables.instance.randomNumbersManager.chanceForProduct(user.getProductsCount())
                        && Utility.getMaxProducts() >= GlobalVariables.instance.getProductsList().size()) {
                    int productId = GlobalVariables.instance.addProduct(id);
                    if(productId != 0)
                    System.out.println("dsystybutor " + id + " creates: " + GlobalVariables.instance.getProductsList().get(productId).getName());
                }

                Thread.sleep(Utility.getDayTime() * 10);
            }

        } catch (InterruptedException e) {
            System.out.println("There was an error: " + e.getMessage());
        }
    }

    public void start(int id, Distributor d) {
        super.initThread(id, d);
        if (d.getT() == null) {
            Thread thread = new Thread(this);
            thread.setDaemon(true);
            d.setT(thread);
            d.getT().start();
        }
    }
}
