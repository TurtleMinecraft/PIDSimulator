package simulator;

import java.awt.*;

public class Character extends Rectangle {

    private static final int WIDTH = 32;
    private static final int HEIGHT = 32;

    private static final int SOURCE_X = 0;
    private static final int SOURCE_Y = 320;

    private static final int MAX_SPEED = 120;
    private static final int MAX_ACCELERATION = 2;
    private static final int FRICTION = 3;

    private double kP;
    private double kI;
    private double kD;

    private static final int I_ZONE = 200;

    private static Character instance;

    private double errorSum;
    private double lastTimestamp;
    private double lastError;
    private double lastSpeed;
    private double error;
    private double dt;
    private double errorRate;

    public static Character getInstance() {
        if (instance == null) {
            instance = new Character(WIDTH, HEIGHT, SOURCE_X, SOURCE_Y);
        }
        return instance;
    }

    private Character(int width, int height, int sourceX, int sourceY) {
        super(sourceX, sourceY, width, height);
        errorSum = 0;
        lastTimestamp = System.currentTimeMillis();
        lastError = Setpoint.getInstance().x - this.x;
        lastSpeed = 0;
        kP = 0;
        kI = 0;
        kD = 0;
    }

    public void update() {
        error = Setpoint.getInstance().x - this.x;
        dt = System.currentTimeMillis() - lastTimestamp;
        errorRate = (error - lastError) / dt;
        if (Math.abs(error) < I_ZONE) errorSum += error;
        int moveValue = (int) (error * kP + errorSum * kI + errorRate * kD);
        moveValue = (int) normalizeSpeed(moveValue);
        if (Math.abs(moveValue - lastSpeed) > MAX_ACCELERATION) {
            if (lastSpeed > moveValue) moveValue = (int) (lastSpeed - MAX_ACCELERATION);
            if (lastSpeed < moveValue) moveValue = (int) (lastSpeed + MAX_ACCELERATION);
        }
        this.translate(moveValue, 0);
        lastError = error;
        lastTimestamp = System.currentTimeMillis();
        lastSpeed = normalizeNoFriction(moveValue);
    }

    private double normalizeSpeed(double speed) {
        if (speed > 0) speed -= FRICTION;
        if (speed < 0) speed += FRICTION;
        if (Math.abs(speed) > MAX_SPEED) speed = (int) (MAX_SPEED * Math.signum(speed));
        if (Math.abs(speed) < FRICTION) speed = 0;
        return speed;
    }

    private double normalizeNoFriction(double speed) {
        if (Math.abs(speed) > MAX_SPEED) speed = (int) (MAX_SPEED * Math.signum(speed));
        return speed;
    }

    public static void reset() {
        Character character = getInstance();
        character.errorSum = 0;
        character.lastTimestamp = System.currentTimeMillis();
        character.lastError = Setpoint.getInstance().x - character.x;
        character.lastSpeed = 0;
        character.setLocation(SOURCE_X, SOURCE_Y);
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

    public double getError() {
        return error;
    }

    public double getRate() {
        return errorRate;
    }

}
