package simulator.control;

import simulator.Setpoint;

public class PIDController {

    private static final int I_ZONE = 200;

    private double kP;
    private double kI;
    private double kD;

    private double errorSum;
    private double lastTimestamp;
    private double lastError;
    private double lastSpeed;
    private double error;
    private double dt;
    private double errorRate;

    public PIDController(PIDSettings pidSettings) {
        this.kP = pidSettings.getP();
        this.kI = pidSettings.getI();
        this.kD = pidSettings.getD();
        errorSum = 0;
        lastTimestamp = System.currentTimeMillis();
        lastError = 0;
    }

    public PIDController(double kP, double kI, double kD) {
        this(new PIDSettings(kP, kI, kD));
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

    public void setPID(PIDSettings pidSettings) {
        setPID(pidSettings.getP(), pidSettings.getI(), pidSettings.getD());
    }

    public int calculate(double source, double setpoint) {
        error = setpoint - source;
        dt = System.currentTimeMillis() - lastTimestamp;
        errorRate = (error - lastError) / dt;
        if (Math.abs(error) < I_ZONE) errorSum += error;
        int moveValue = (int) (error * kP + errorSum * kI + errorRate * kD);
        lastError = error;
        lastTimestamp = System.currentTimeMillis();
        return moveValue;
    }

    public void reset() {
        errorSum = 0;
        lastTimestamp = System.currentTimeMillis();
        lastError = 0;
    }
}
