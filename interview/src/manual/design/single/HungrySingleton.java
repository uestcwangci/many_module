package manual.design.single;

public class HungrySingleton {
    private final static HungrySingleton INSTANCE = new HungrySingleton();
    // final修饰在类加载时就完成赋值

    private HungrySingleton() {
    }

    public static HungrySingleton getInstance() {
        return INSTANCE;
    }
}
