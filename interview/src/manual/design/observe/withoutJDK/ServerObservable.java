package manual.design.observe.withoutJDK;

public interface ServerObservable {
    void setObserver(ClientObserver observer);

    void removeObserver(ClientObserver observer);

    void notifyObservers();
}
