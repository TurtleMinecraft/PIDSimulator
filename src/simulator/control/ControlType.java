package simulator.control;

import simulator.Character;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlType extends JComboBox<String> {

    private static final int WIDTH = 140;
    private static final int HEIGHT = 65;

    private static final int X = 1100;
    private static final int Y = 65;
    private static final int FONT_SIZE = 20;

    private static final String[] OPTIONS = new String[]{"Position", "Velocity"};

    private static ControlType instance;

    public static ControlType getInstance() {
        if (instance == null) {
            instance = new ControlType();
        }
        return instance;
    }

    private ControlType() {
        super(OPTIONS);
        this.setBounds(X, Y, WIDTH, HEIGHT);
        this.setBackground(Color.GRAY);
        this.setFont(new Font(Font.MONOSPACED, Font.PLAIN, FONT_SIZE));
        addActionListener(e -> Character.reset());
    }
}
