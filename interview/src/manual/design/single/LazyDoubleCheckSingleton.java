package manual.design.single;

public class LazyDoubleCheckSingleton {
    private static volatile  LazyDoubleCheckSingleton INSTANCE = null;

    private LazyDoubleCheckSingleton() {
    }

    public static LazyDoubleCheckSingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (LazyDoubleCheckSingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LazyDoubleCheckSingleton();
                    /**
                     * 1、给内存分配对象
                     * 2、初始化对象
                     * 3、把INSTANCE指向这个对象
                     * 这3个步骤可能会发生指令重排序，执行顺序为1/3/2，加入volatile关键字禁止重排序
                     */
                }
            }
        }
        return INSTANCE;
    }
}
