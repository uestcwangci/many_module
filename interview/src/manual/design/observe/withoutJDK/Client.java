package manual.design.observe.withoutJDK;

public class Client implements ClientObserver {
    private String name;
    private String msg;

    public Client(String name) {
        this.name = name;
    }

    @Override
    public void update(String string) {
        this.msg = string;
        System.out.println(this.name + "收到:" + msg);
    }
}
