package simulator.information;

import simulator.Character;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Status extends JPanel {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 640;
    private static final int X = 800;
    private static final int Y = 480;
    private static final int FONT_SIZE = 20;
    private JTextField characterError;
    private JTextField errorRate;

    private static Status instance;

    public static Status getInstance() {
        if (instance == null) {
            instance = new Status();
        }
        return instance;
    }

    private Status() {
        configureCharacterError();
        configureErrorRate();
        this.add(characterError);
        this.add(errorRate);
        this.setLayout(null);
        this.setVisible(true);
        this.setBounds(X, Y, WIDTH, HEIGHT);
        this.setBackground(Color.GRAY);
    }

    public void configureCharacterError() {
        characterError = new JTextField("Error: " + Character.getInstance().getError());
        characterError.setSize(200, 50);
        characterError.setEditable(false);
        characterError.setFont(new Font(Font.MONOSPACED, Font.PLAIN, FONT_SIZE));
        characterError.setLayout(null);
        characterError.setBackground(Color.GRAY);
        characterError.setBorder(new LineBorder(Color.GRAY));
    }

    public void configureErrorRate() {
        errorRate = new JTextField("Error rate: " + Character.getInstance().getRate());
        errorRate.setBounds(0, 50, 400, 50);
        errorRate.setEditable(false);
        errorRate.setFont(new Font(Font.MONOSPACED, Font.PLAIN, FONT_SIZE));
        errorRate.setLayout(null);
        errorRate.setBackground(Color.GRAY);
        errorRate.setBorder(new LineBorder(Color.GRAY));
    }

    public void update() {
        characterError.setText("Error: " + Character.getInstance().getError());
        errorRate.setText("Error rate: " + Character.getInstance().getRate());
    }
}
