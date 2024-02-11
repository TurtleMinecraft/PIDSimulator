package simulator.control;

public class PIDController {

    private static final int DEFAULT_I_ZONE = 200;

    private double kP;
    private double kI;
    private double kD;
    private double iZone;
    private double tolerance;
    private double waitTime;

    private double errorSum;
    private double lastTimestamp;
    private double lastError;
    private double lastSpeed;
    private double error;
    private double dt;
    private double errorRate;
    private boolean onTarget;

    public PIDController(PIDSettings pidSettings) {
        this.kP = pidSettings.getP();
        this.kI = pidSettings.getI();
        this.kD = pidSettings.getD();
        this.iZone = DEFAULT_I_ZONE;
        this.tolerance = pidSettings.getTolerance();
        this.waitTime = pidSettings.getWaitTime();
        errorSum = 0;
        lastTimestamp = System.currentTimeMillis();
        lastError = 0;
        errorRate = 0;
    }

    public PIDController(double kP, double kI, double kD, double tolerance, double waitTime) {
        this(new PIDSettings(kP, kI, kD, tolerance, waitTime));
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

    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    public void setWaitTime(double waitTime) {
        this.waitTime = waitTime;
    }

    public boolean isOnTarget() {
        return onTarget;
    }

    public void setPID(PIDSettings pidSettings) {
        setPID(pidSettings.getP(), pidSettings.getI(), pidSettings.getD());
    }

    public void setIZone(int iZone) {
        this.iZone = iZone;
    }

    public int calculate(double source, double setpoint) {
        error = setpoint - source;
        onTarget = (Math.abs(error) <= tolerance);
        dt = System.currentTimeMillis() - lastTimestamp;
        errorRate = (error - lastError) / dt;
        if (Math.abs(error) < iZone) errorSum += error;
        int moveValue = (int) (error * kP + errorSum * kI + errorRate * kD);
        lastError = error;
        lastTimestamp = System.currentTimeMillis();
        return moveValue;
    }

    public void reset() {
        errorSum = 0;
        lastTimestamp = System.currentTimeMillis();
        lastError = 0;
        onTarget = false;
    }

    public double getErrorRate() {
        return errorRate;
    }
}
