package manual.builder;

public class Pc {
    private String cpu;
    private String gpu;
    private String fan;
    private String power;

    public Pc(PcBuilder pcBuilder) {
        this.cpu = pcBuilder.cpu;
        this.gpu = pcBuilder.gpu;
        this.fan = pcBuilder.fan;
        this.power = pcBuilder.power;
    }


    public static class PcBuilder {
        private String cpu = "Inter";
        private String gpu;
        private String fan = "2风扇";
        private String power;

        public PcBuilder setCpu(String cpu) {
            this.cpu = cpu;
            return this;
        }
        public PcBuilder setGpu(String gpu) {
            this.gpu = gpu;
            return this;
        }
        public PcBuilder setFan(String fan) {
            this.fan = fan;
            return this;
        }
        public PcBuilder setPower(String power) {
            this.power = power;
            return this;
        }
        public Pc build() {
            return new Pc(this);
        }


    }



    @Override
    public String toString() {
        return "Pc{" +
                "cpu='" + cpu + '\'' +
                ", gpu='" + gpu + '\'' +
                ", fan='" + fan + '\'' +
                ", power='" + power + '\'' +
                '}';
    }
}
