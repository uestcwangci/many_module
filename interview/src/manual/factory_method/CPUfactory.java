package manual.factory_method;

public class CPUfactory extends MySimpleFactory {

    @Override
    public PC getPC() {
        return new CPU();
    }

}
