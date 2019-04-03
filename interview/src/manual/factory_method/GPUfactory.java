package manual.factory_method;

public class GPUfactory extends MySimpleFactory {

    @Override
    public PC getPC() {
        return new GPU();
    }

}
