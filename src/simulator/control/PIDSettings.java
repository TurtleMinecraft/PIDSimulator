package simulator.control;

public class PIDSettings {

    private double kP;
    private double kI;
    private double kD;

    public PIDSettings(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public double getP() {
        return kP;
    }

    public double getI() {
        return kI;
    }

    public double getD() {
        return kD;
    }

    public void setP(double kP) {
        this.kP = kP;
    }

    public void setI(double kI) {
        this.kI = kI;
    }

    public void setD(double kD) {
        this.kD = kD;
    }

    public void setPID(double kP, double kI, double kD) {
        setP(kP);
        setI(kI);
        setD(kD);
    }
}
