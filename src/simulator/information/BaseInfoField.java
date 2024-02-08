package simulator.information;

import simulator.Character;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class BaseInfoField extends JTextField {

    private static final int FONT_SIZE = 20;

    public BaseInfoField(int x, int y, int width, int height, String text) {
        this.setText(text);
        this.setBounds(x, y, width, height);
        this.setEditable(false);
        this.setFont(new Font(Font.MONOSPACED, Font.PLAIN, FONT_SIZE));
        this.setLayout(null);
        this.setBackground(Color.GRAY);
        this.setBorder(new LineBorder(Color.GRAY));
    }
}
