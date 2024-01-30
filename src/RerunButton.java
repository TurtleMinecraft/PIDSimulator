import javax.swing.*;
import java.awt.*;

public class RerunButton extends JButton {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 45;

    private static final int X = 620;
    private static final int Y = 700;

    private static RerunButton instance;

    public static RerunButton getInstance() {
        if (instance == null) {
            instance = new RerunButton();
        }
        return instance;
    }

    private RerunButton() {
        super("Reset");
        this.setLayout(null);
        this.setBackground(Color.GREEN);
        this.setVisible(true);
        this.setBounds(X, Y, WIDTH, HEIGHT);
        this.addActionListener(e -> {
            Character.reset();
        });
    }
}
