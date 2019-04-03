package manual.builder;

public class Test {
    public static void main(String[] args) {
        Pc pc = new Pc.PcBuilder().setCpu("微星").setGpu("1080TI").setPower("1010W").build();
        System.out.println(pc);

    }
}
