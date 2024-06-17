package simulator;

import simulator.control.*;

import java.awt.*;

public class Character extends Rectangle {

    private static final int MILLISECONDS_IN_SECOND = 1000;
    private static final int WIDTH = 32;
    private static final int HEIGHT = 32;

    private static final int POSITION_SOURCE_X = 0;
    private static final int POSITION_SOURCE_Y = 320;

    private static final int VELOCITY_SOURCE_X = 640;
    private static final int VELOCITY_SOURCE_Y = 320;

    private static final int MAX_SPEED = 120;
    private static final int MAX_ACCELERATION = 2;
    private static final int FRICTION = 3;

    private final PIDSettings pidSettings;
    private final PIDController pidController;
    private final FeedForwardSettings feedForwardSettings;
    private final FeedForwardController feedForwardController;

    private double lastTimeNotOnTarget;
    private double lastSpeed;
    private boolean commandFinished;

    private static Character instance;

    public static Character getInstance() {
        if (instance == null) {
            instance = new Character(WIDTH, HEIGHT, POSITION_SOURCE_X, POSITION_SOURCE_Y);
        }
        return instance;
    }

    private Character(int width, int height, int sourceX, int sourceY) {
        super(sourceX, sourceY, width, height);
        lastSpeed = 0;
        pidSettings = new PIDSettings(0, 0, 0, 0, 0);
        feedForwardSettings = new FeedForwardSettings(0, 0, 0);
        pidController = new PIDController(pidSettings);
        feedForwardController = new FeedForwardController(feedForwardSettings);
        lastTimeNotOnTarget = System.currentTimeMillis();
    }

    public static void reset() {
        Character character = getInstance();
        character.lastSpeed = 0;
        if (ControlType.getInstance().getSelectedIndex() == ControlType.Types.POSITION.index) {
            character.setLocation(POSITION_SOURCE_X, POSITION_SOURCE_Y);
        } else {
            if (ControlType.getInstance().getSelectedIndex() == ControlType.Types.VELOCITY.index) {
                character.setLocation(VELOCITY_SOURCE_X, VELOCITY_SOURCE_Y);
            }
        }
        character.commandFinished = false;
        character.lastTimeNotOnTarget = System.currentTimeMillis();
        character.pidController.reset();
        character.feedForwardController.reset();
    }

    public void update() {
        if (!commandFinished) {
            if (ControlType.getInstance().getSelectedIndex() == ControlType.Types.POSITION.index) {
                runPosition();
            } else if (ControlType.getInstance().getSelectedIndex() == ControlType.Types.VELOCITY.index) {
                runVelocity();
            }
        }
        if (!pidController.isOnTarget()) {
            lastTimeNotOnTarget = System.currentTimeMillis();
        }
    }

    public void setPID(double kP, double kI, double kD, double tolerance, double waitTime) {
        pidController.setPID(kP, kI, kD);
        pidController.setTolerance(tolerance);
        pidSettings.setWaitTime(waitTime);
    }

    public void setIZone(int iZone) {
        pidController.setIZone(iZone);
    }

    public PIDController getPIDController() {
        return pidController;
    }

    public double getError() {
        if (ControlType.getInstance().getSelectedIndex() == ControlType.Types.POSITION.index) {
            return Setpoint.getInstance().x - this.x;
        } else {
            if (ControlType.getInstance().getSelectedIndex() == ControlType.Types.VELOCITY.index) {
                return Setpoint.getInstance().x - lastSpeed;
            } else {
                return 0;
            }
        }
    }

    public void setFF(double kS, double kV, double kA) {
        feedForwardController.setGains(kS, kV, kA);
    }

    public double getLastSpeed() {
        return lastSpeed;
    }

    public boolean commandEnded() {
        return commandFinished;
    }

    private void runPosition() {
        commandFinished = (System.currentTimeMillis() - lastTimeNotOnTarget >=
                pidSettings.getWaitTime() * MILLISECONDS_IN_SECOND && pidController.isOnTarget());
        int moveValue = pidController.calculate(this.x, Setpoint.getInstance().x) +
                feedForwardController.calculate(this.x, Setpoint.getInstance().x);
        moveValue = (int) normalizeSpeed(moveValue);
        if (Math.abs(moveValue - lastSpeed) > MAX_ACCELERATION) {
            if (lastSpeed > moveValue) moveValue = (int) (lastSpeed - MAX_ACCELERATION);
            if (lastSpeed < moveValue) moveValue = (int) (lastSpeed + MAX_ACCELERATION);
        }
        this.translate(moveValue, 0);
        lastSpeed = moveValue;
    }

    private void runVelocity() {
        int moveValue = pidController.calculate(lastSpeed, Setpoint.getInstance().x) +
                feedForwardController.calculate(lastSpeed, Setpoint.getInstance().x);
        moveValue = (int) normalizeSpeed(moveValue);
        if (Math.abs(moveValue - lastSpeed) > MAX_ACCELERATION) {
            if (lastSpeed > moveValue) moveValue = (int) (lastSpeed - MAX_ACCELERATION);
            if (lastSpeed < moveValue) moveValue = (int) (lastSpeed + MAX_ACCELERATION);
        }
        lastSpeed = moveValue;
    }

    private double normalizeSpeed(double speed) {
        if (speed != 0) {
            if (speed > 0) speed -= FRICTION;
            if (speed < 0) speed += FRICTION;
            if (Math.abs(speed) > MAX_SPEED) speed = (int) (MAX_SPEED * Math.signum(speed));
        } else if (Math.abs(speed) < FRICTION) speed = 0;
        return speed;
    }
}
