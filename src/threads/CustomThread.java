package threads;

import single_classes.VODuser;

public class CustomThread<Element extends VODuser> {
    int id;
    Element user;

    public void initThread(int id, Element user) {
        this.id = id;
        this.user = user;
    }
}
