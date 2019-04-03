package manual.factory_method;

import java.lang.reflect.InvocationTargetException;

public abstract class MySimpleFactory {
    public abstract PC getPC();

//    public PC getProduce(String name) {
//        if ("CPU".equalsIgnoreCase(name)) {
//            return new CPU();
//        } else if ("GPU".equalsIgnoreCase(name)) {
//            return new GPU();
//        } else {
//            return null;
//        }
//    }
//
//    public PC getProduce(Class c) {
//        PC pc = null;
//        try {
//            pc = (PC) Class.forName(c.getName()).getDeclaredConstructor().newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return pc;
//    }

}

