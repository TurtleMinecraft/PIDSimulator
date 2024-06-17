package simulator.control;

import simulator.Character;
import simulator.Window;
import simulator.textfields.BaseTextField;

import javax.swing.*;
import java.awt.*;

public class ControlType extends JComboBox<String> {

    public enum Types {

        POSITION(0), VELOCITY(1);

        public int index;

        Types(int index) {
            this.index = index;
        }
    }

    private static final int WIDTH = 140;
    private static final int HEIGHT = 65;

    private static final int X = Window.WINDOW_WIDTH - WIDTH - 30;
    private static final int Y = 60 + HEIGHT;
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
