package manual.design.single;

public class LazyStaticSingleton {
    private LazyStaticSingleton() {
    }

    private static class InnerClass {
        final static LazyStaticSingleton INSTANCE = new LazyStaticSingleton();
    }

    public static LazyStaticSingleton getInstance() {
        return InnerClass.INSTANCE;
    }
}
