package simulator.control;

public class FeedForwardSettings {

    private double kS;
    private double kV;
    private double kA;

    public FeedForwardSettings(double kS, double kV, double kA) {
        this.kS = kS;
        this.kV = kV;
        this.kA = kA;
    }

    public double getS() {
        return kS;
    }

    public void setS(double kS) {
        this.kS = kS;
    }

    public double getV() {
        return kV;
    }

    public void setV(double kV) {
        this.kV = kV;
    }

    public double getA() {
        return kA;
    }

    public void setA(double kA) {
        this.kA = kA;
    }

    public void setFF(double kS, double kV, double kA) {
        setS(kS);
        setV(kV);
        setA(kA);
    }
}
