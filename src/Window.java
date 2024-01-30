import textfields.BaseTextField;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class Window extends JPanel {

    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 792;

    private static final boolean IS_DOUBLE_BUFFERED = true;

    private final Character character;
    private final Setpoint setpoint;
    private final RerunButton rerunButton;
    private final BaseTextField kPField;
    private final BaseTextField kIField;
    private final BaseTextField kDField;
    private final Status status;

    private Window() {
        super(IS_DOUBLE_BUFFERED);
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setVisible(true);
        this.setLayout(null);
        character = Character.getInstance();
        setpoint = Setpoint.getInstance();
        rerunButton = RerunButton.getInstance();
        status = Status.getInstance();
        kPField = new BaseTextField("kP", 300, 30);
        kIField = new BaseTextField("kI", 600, 30);
        kDField = new BaseTextField("kD", 900, 30);
        this.add(rerunButton);
        this.add(kPField);
        this.add(kIField);
        this.add(kDField);
        this.add(status);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fill(character);
        g2.setColor(Color.RED);
        g2.fill(setpoint);
    }

    private void delay(double seconds) {
        long currentTime = System.currentTimeMillis();
        long targetTime = (long) (System.currentTimeMillis() + seconds * 1000);
        while (targetTime > currentTime) {
            currentTime = System.currentTimeMillis();
            repaint(character);
        }
    }

    private void update() {
        delay(0.02);
        character.update();
        character.setPID(kPField.getValue(), kIField.getValue(), kDField.getValue());
        status.update();
        repaint();
    }

    public static void initGame() {
        JFrame frame = new JFrame();
        Window window = new Window();
        frame.add(window);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        while (true) {
            window.update();
        }
    }
}
