package manual.producer.with_wait_notify;

public class Data {
    private int data;

    public Data(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Data{" +
                "data=" + data +
                '}';
    }
}
