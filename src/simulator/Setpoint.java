package simulator;

import java.awt.*;

public class Setpoint extends Rectangle {

    private static final int WIDTH = 32;
    private static final int HEIGHT = 32;

    private static final int SOURCE_X = 1000;
    private static final int SOURCE_Y = 320;

    private static Setpoint instance;

    public static Setpoint getInstance() {
        if (instance == null) {
            instance = new Setpoint(WIDTH, HEIGHT, SOURCE_X, SOURCE_Y);
        }
        return instance;
    }

    private Setpoint(int width, int height, int sourceX, int sourceY) {
        super(sourceX, sourceY, width, height);
    }
}
