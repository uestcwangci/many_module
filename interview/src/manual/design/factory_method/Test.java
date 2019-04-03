package manual.design.factory_method;

public class Test {
    public static void main(String[] args) {
//        MySimpleFactory factory = new MySimpleFactory();
//        PC pc = factory.getProduce("cpu");
//        if (pc == null) {
//            return;
//        }
//        pc.progress();

        MySimpleFactory factory = new CPUfactory();
        PC pc = factory.getPC();
        if (pc == null) {
            return;
        }
        pc.progress();

    }
}
