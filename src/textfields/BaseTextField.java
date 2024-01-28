package textfields;

import javax.swing.*;
import java.awt.*;

public class BaseTextField extends JTextField {

    private static final int FIELD_WIDTH = 140;
    private static final int FIELD_HEIGHT = 65;

    private static final int FONT_SIZE = 40;

    public BaseTextField(String initialText, int x, int y) {
        super(initialText);
        this.setBounds(x, y, FIELD_WIDTH, FIELD_HEIGHT);
        this.setBackground(Color.GRAY);
        this.setFont(new Font(Font.MONOSPACED, Font.PLAIN, FONT_SIZE));
    }

    public double getValue() {
        try {
            return Double.parseDouble(this.getText());
        } catch(NumberFormatException e) {
            return 0;
        }
    }
}
