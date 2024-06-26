package simulator.information;

import simulator.Character;
import simulator.Setpoint;
import simulator.Window;
import simulator.control.ControlType;

import javax.swing.*;
import java.awt.*;

public class Status extends JPanel {

    public static final int WIDTH = Window.WINDOW_WIDTH;
    public static final int HEIGHT = Window.WINDOW_HEIGHT / 2;
    public static final int INFO_TEXT_WIDTH = WIDTH;
    public static final int INFO_TEXT_HEIGHT = HEIGHT / 15;

    private static final int X = 0;
    private static final int Y = Window.WINDOW_HEIGHT - HEIGHT;
    private static final int FONT_SIZE = 20;

    private static final Point ERROR_LOCATION = new Point(0, 0);
    private static final Point ERROR_RATE_LOCATION = new Point(0, INFO_TEXT_HEIGHT);
    private static final Point COMMAND_ENDED_LOCATION = new Point(0, INFO_TEXT_HEIGHT * 2);

    private BaseInfoField characterError;
    private BaseInfoField errorRate;
    private BaseInfoField commandEnded;
    private ErrorGraph errorGraph;

    private static Status instance;

    public static Status getInstance() {
        if (instance == null) {
            instance = new Status();
        }
        return instance;
    }

    private Status() {
        characterError = new BaseInfoField(ERROR_LOCATION, INFO_TEXT_WIDTH, INFO_TEXT_HEIGHT,
                "Error: " + Character.getInstance().getError());
        errorRate = new BaseInfoField(ERROR_RATE_LOCATION, INFO_TEXT_WIDTH, INFO_TEXT_HEIGHT,
                "Error rate: " + Character.getInstance().getPIDController().getErrorRate());
        commandEnded = new BaseInfoField(COMMAND_ENDED_LOCATION, INFO_TEXT_WIDTH, INFO_TEXT_HEIGHT, "Command ended: " +
                Character.getInstance().commandEnded());
        errorGraph = ErrorGraph.getInstance();
        this.add(characterError);
        this.add(errorRate);
        this.add(commandEnded);
        this.add(errorGraph);
        this.setLayout(null);
        this.setVisible(true);
        this.setBounds(X, Y, WIDTH, HEIGHT);
        this.setBackground(Color.GRAY);
    }

    public void update() {
        if (ControlType.getInstance().getSelectedIndex() == ControlType.Types.POSITION.index) {
            characterError.setText("Error: " + (Setpoint.getInstance().x - Character.getInstance().x));
            errorRate.setText("Error rate: " + Character.getInstance().getPIDController().getErrorRate());
            commandEnded.setText("Command ended: " + Character.getInstance().commandEnded());
        } else {
            if (ControlType.getInstance().getSelectedIndex() == ControlType.Types.VELOCITY.index) {
                characterError.setText("Error: " + (Setpoint.getInstance().x - Character.getInstance().getLastSpeed()));
                errorRate.setText("Error rate: " + Character.getInstance().getPIDController().getErrorRate());
                commandEnded.setText("Command ended: " + Character.getInstance().commandEnded());
            }
        }
        errorGraph.addPoint((int) Character.getInstance().getError());
    }
}
