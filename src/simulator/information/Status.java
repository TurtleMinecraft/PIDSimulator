package simulator.information;

import simulator.Character;
import simulator.Setpoint;
import simulator.control.ControlType;

import javax.swing.*;
import java.awt.*;

public class Status extends JPanel {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 640;
    private static final int X = 800;
    private static final int Y = 480;
    private static final int FONT_SIZE = 20;
    private BaseInfoField characterError;
    private BaseInfoField errorRate;
    private BaseInfoField commandEnded;

    private static Status instance;

    public static Status getInstance() {
        if (instance == null) {
            instance = new Status();
        }
        return instance;
    }

    private Status() {
        characterError = new BaseInfoField(0, 0, 400, 50,
                "Error: " + Character.getInstance().getError());
        errorRate = new BaseInfoField(0, 50, 400, 50,
                "Error rate: " + Character.getInstance().getPIDController().getErrorRate());
        commandEnded = new BaseInfoField(0, 100, 400, 50, "Command ended: " +
                Character.getInstance().commandEnded());
        this.add(characterError);
        this.add(errorRate);
        this.add(commandEnded);
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
    }
}
