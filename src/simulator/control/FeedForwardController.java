package simulator.control;

import simulator.Window;

public class FeedForwardController {

    private double kS;
    private double kV;
    private double kA;
    private double previousTarget;

    public FeedForwardController(double kS, double kV, double kA) {
        this.kS = kS;
        this.kV = kV;
        this.kA = kA;
        this.previousTarget = 0;
    }

    public FeedForwardController(FeedForwardSettings feedForwardSettings) {
        this(feedForwardSettings.getS(), feedForwardSettings.getV(), feedForwardSettings.getA());
    }

    public void setGains(double kS, double kV, double kA) {
        this.kS = kS;
        this.kV = kV;
        this.kA = kA;
    }

    public void setGains(FeedForwardSettings feedForwardSettings) {
        setGains(feedForwardSettings.getS(), feedForwardSettings.getV(), feedForwardSettings.getA());
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


    public void reset() {
        this.previousTarget = 0;
    }

    public int calculate(double source, double setpoint) {
        double error = setpoint - source;
        double targetDerivative = (setpoint - previousTarget) / Window.PERIODIC_FRAME;
        previousTarget = setpoint;
        return (int) (kS * Math.signum(error) + kV * setpoint + kA * targetDerivative);
    }
}
