package manual.design.observe.withJDK;

import java.util.Observable;

public class Server extends Observable {

    public void setMsg(String msg) {
        setChanged();
        notifyObservers(msg);
    }
}
