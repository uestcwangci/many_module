package manual.design.observe.withoutJDK;

import java.util.LinkedList;
import java.util.List;

public class Server implements ServerObservable {
    private List<ClientObserver> observers = new LinkedList<>();
    private String msg;
    @Override
    public void setObserver(ClientObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(ClientObserver observer) {
        if (!observers.isEmpty()) {
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObservers() {
        for (ClientObserver observer : observers) {
            observer.update(msg);
        }
    }

    public void sendMsg(String msg) {
        this.msg = msg;
        System.out.println("服务端更新信息：" + msg);
        notifyObservers();
    }
}
