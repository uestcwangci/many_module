package manual.design.observe.withJDK;

public class Test {
    public static void main(String[] args) {
        Server server = new Server();
        Client c1 = new Client("c1");
        Client c2 = new Client("c2");
        Client c3 = new Client("c3");
        Client c4 = new Client("c4");
        Client c5 = new Client("c5");
        server.addObserver(c1);
        server.addObserver(c2);
        server.addObserver(c3);
        server.addObserver(c4);
        server.addObserver(c5);
        server.deleteObserver(c3);

        server.setMsg("world");
    }
}
