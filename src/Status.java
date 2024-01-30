import javax.swing.*;
import java.awt.*;

public class Status extends JPanel {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 640;
    private static final int X = 800;
    private static final int Y = 480;
    private static final int FONT_SIZE = 20;
    private final JTextField characterError;

    private static Status instance;

    public static Status getInstance() {
        if (instance == null) {
            instance = new Status();
        }
        return instance;
    }

    private Status() {
        characterError = new JTextField("Error: " + Character.getInstance().getError());
        characterError.setBounds(1, 1, 200, 100);
        characterError.setEnabled(false);
        characterError.setFont(new Font(Font.MONOSPACED, Font.PLAIN, FONT_SIZE));
        characterError.setLayout(null);
        this.add(characterError);
        this.setLayout(null);
        this.setVisible(true);
        this.setBounds(X, Y, WIDTH, HEIGHT);
        this.setBackground(Color.GRAY);
    }

    public void update() {
        characterError.setText("Error: " + Character.getInstance().getError());
    }
}
