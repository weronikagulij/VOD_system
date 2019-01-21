import threads.TimeManagerThread;
import threads.VODitemsThread;
import views.MainView;
import single_classes.*;

public class VODmanager {

    public void startSimulation() {

        for(int i = 0; i < 2; i ++) {
            GlobalVariables.instance.addDistributor();
        }

        VODitemsThread vodItemsThread = new VODitemsThread();
        vodItemsThread.start();

        TimeManagerThread timeManagerThread = new TimeManagerThread();
        timeManagerThread.start();

        MainView view = new MainView();
        view.run();
    }
}
