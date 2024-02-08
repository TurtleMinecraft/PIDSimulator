package simulator.control;

public class PIDSettings {

    private double kP;
    private double kI;
    private double kD;
    private double tolerance;
    private double waitTime;

    public PIDSettings(double kP, double kI, double kD, double tolerance, double waitTime) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.tolerance = tolerance;
        this.waitTime = waitTime;
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

    public double getTolerance() {
        return this.tolerance;
    }

    public double getWaitTime() {
        return this.waitTime;
    }

    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    public void setWaitTime(double waitTime) {
        this.waitTime = waitTime;
    }

    public void setPID(double kP, double kI, double kD) {
        setP(kP);
        setI(kI);
        setD(kD);
    }
}
