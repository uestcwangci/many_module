package manual.design.observe.withoutJDK;

public class Test {
    public static void main(String[] args) {
        Server server = new Server();
        Client c1 = new Client("c1");
        Client c2 = new Client("c2");
        Client c3 = new Client("c3");
        Client c4 = new Client("c4");
        Client c5 = new Client("c5");
        server.setObserver(c1);
        server.setObserver(c2);
        server.setObserver(c3);
        server.setObserver(c4);
        server.setObserver(c5);
        server.removeObserver(c2);

        server.sendMsg("hello");

    }
}
