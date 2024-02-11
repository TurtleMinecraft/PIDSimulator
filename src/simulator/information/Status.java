package simulator.information;

import simulator.Character;
import simulator.Setpoint;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Status extends JPanel {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 640;
    private static final int X = 800;
    private static final int Y = 480;
    private static final int FONT_SIZE = 20;
    private BaseInfoField characterError;
    private BaseInfoField errorRate;

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
        this.add(characterError);
        this.add(errorRate);
        this.setLayout(null);
        this.setVisible(true);
        this.setBounds(X, Y, WIDTH, HEIGHT);
        this.setBackground(Color.GRAY);
    }

    public void update() {
        characterError.setText("Error: " + (Setpoint.getInstance().x - Character.getInstance().x));
        errorRate.setText("Error rate: " + (Character.getInstance().getPIDController().getErrorRate()));
    }
}
