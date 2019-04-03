package manual.simple_factory;

public class Test {
    public static void main(String[] args) {
//        MySimpleFactory factory = new MySimpleFactory();
//        PC pc = factory.getProduce("cpu");
//        if (pc == null) {
//            return;
//        }
//        pc.progress();

        MySimpleFactory factory = new MySimpleFactory();
        PC pc = factory.getProduce(CPU.class);
        if (pc == null) {
            return;
        }
        pc.progress();

    }
}
