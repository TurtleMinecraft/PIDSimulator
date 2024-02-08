package simulator;

import simulator.information.Status;
import simulator.textfields.BaseTextField;

import javax.swing.*;
import java.awt.*;

public class Window extends JPanel {

    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 792;

    private static final double PERIODIC_FRAME = 0.02;

    private static final boolean IS_DOUBLE_BUFFERED = true;

    private final Character character;
    private final Setpoint setpoint;
    private final RerunButton rerunButton;
    private final BaseTextField kPField;
    private final BaseTextField kIField;
    private final BaseTextField kDField;
    private final BaseTextField toleranceField;
    private final BaseTextField waitTimeField;
    private final BaseTextField kSField;
    private final BaseTextField kVField;
    private final BaseTextField kAField;
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
        toleranceField = new BaseTextField("tolerance", 450, 65);
        waitTimeField = new BaseTextField("wait time", 750, 65);
        kSField = new BaseTextField("kS", 300, 100);
        kVField = new BaseTextField("kV", 600, 100);
        kAField = new BaseTextField("kA", 900, 100);
        configureTextFields();
        this.add(rerunButton);
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
        delay(PERIODIC_FRAME);
        character.setPID(kPField.getValue(), kIField.getValue(), kDField.getValue(), toleranceField.getValue(),
                waitTimeField.getValue());
        character.setFF(kSField.getValue(), kVField.getValue(), kAField.getValue());
        character.update();
        status.update();
        repaint();
    }

    private void configureTextFields() {
        this.add(kPField);
        this.add(kIField);
        this.add(kDField);
        this.add(toleranceField);
        this.add(waitTimeField);
        this.add(kSField);
        this.add(kVField);
        this.add(kAField);
        this.add(status);
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
