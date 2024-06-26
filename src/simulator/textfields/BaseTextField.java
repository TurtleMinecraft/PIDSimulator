package simulator.textfields;

import simulator.Window;

import javax.swing.*;
import java.awt.*;

public class BaseTextField extends JTextField {

    public static final int FIELD_WIDTH = Window.WINDOW_WIDTH / 15;
    public static final int FIELD_HEIGHT = Window.WINDOW_HEIGHT / 20;

    private static final int FONT_SIZE = 40;

    public BaseTextField(int x, int y, String initialText) {
        super(initialText);
        this.setBounds(x, y, FIELD_WIDTH, FIELD_HEIGHT);
        this.setBackground(Color.GRAY);
        this.setFont(new Font(Font.MONOSPACED, Font.PLAIN, FONT_SIZE));
    }

    public BaseTextField(Point position, String initialText) {
        this(position.x, position.y, initialText);
    }

    public double getValue() {
        try {
            return Double.parseDouble(this.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
