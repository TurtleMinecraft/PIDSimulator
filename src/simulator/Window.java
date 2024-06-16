package simulator;

import simulator.control.ControlType;
import simulator.information.Status;
import simulator.textfields.BaseTextField;

import javax.swing.*;
import java.awt.*;

public class Window extends JPanel {

    private static final int MILLISECONDS_IN_SECOND = 1000;
    public static final double PERIODIC_FRAME = 0.02;

    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 792;
    private static final boolean IS_DOUBLE_BUFFERED = true;

    private static final Point P_FIELD_LOCATION = new Point(300, 30);
    private static final Point I_FIELD_LOCATION = new Point(600, 30);
    private static final Point D_FIELD_LOCATION = new Point(900, 30);
    private static final Point I_ZONE_FIELD_LOCATION = new Point(30, 30);
    private static final Point TOLERANCE_FIELD_LOCATION = new Point(450, 65);
    private static final Point WAIT_TIME_FIELD_LOCATION = new Point(750, 65);
    private static final Point S_FIELD_LOCATION = new Point(300, 100);
    private static final Point V_FIELD_LOCATION = new Point(600, 100);
    private static final Point A_FIELD_LOCATION = new Point(900, 100);
    private static final Point SETPOINT_FIELD_LOCATION = new Point(30, 722);

    private final Character character;
    private final Setpoint setpoint;
    private final RerunButton rerunButton;
    private final BaseTextField kPField;
    private final BaseTextField kIField;
    private final BaseTextField kDField;
    private final BaseTextField iZoneField;
    private final BaseTextField toleranceField;
    private final BaseTextField waitTimeField;
    private final BaseTextField kSField;
    private final BaseTextField kVField;
    private final BaseTextField kAField;
    private final BaseTextField setpointField;
    private final ControlType controlType;
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
        kPField = new BaseTextField("kP", P_FIELD_LOCATION);
        kIField = new BaseTextField("kI", I_FIELD_LOCATION);
        kDField = new BaseTextField("kD", D_FIELD_LOCATION);
        iZoneField = new BaseTextField("i zone", I_ZONE_FIELD_LOCATION);
        toleranceField = new BaseTextField("tolerance", TOLERANCE_FIELD_LOCATION);
        waitTimeField = new BaseTextField("wait time", WAIT_TIME_FIELD_LOCATION);
        kSField = new BaseTextField("kS", S_FIELD_LOCATION);
        kVField = new BaseTextField("kV", V_FIELD_LOCATION);
        kAField = new BaseTextField("kA", A_FIELD_LOCATION);
        setpointField = new BaseTextField("setpoint", SETPOINT_FIELD_LOCATION);
        controlType = ControlType.getInstance();
        configureTextFields();
        this.add(rerunButton);
        this.add(controlType);
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fill(character);
        if (ControlType.getInstance().getSelectedIndex() == 1) {
            g2.drawString("Velocity: " + character.getLastSpeed(), character.x, character.y);
        }
        g2.setColor(Color.RED);
        g2.fill(setpoint);
    }

    private void delay(double seconds) {
        long currentTime = System.currentTimeMillis();
        long targetTime = (long) (System.currentTimeMillis() + seconds * MILLISECONDS_IN_SECOND);
        while (targetTime > currentTime) {
            currentTime = System.currentTimeMillis();
            repaint(character);
        }
    }

    private void update() {
        delay(PERIODIC_FRAME);
        configurePIDF();
        character.update();
        status.update();
        repaint();
    }

    private void configurePIDF() {
        setpoint.setSetpoint((int) setpointField.getValue());
        character.setPID(kPField.getValue(), kIField.getValue(), kDField.getValue(), toleranceField.getValue(),
                waitTimeField.getValue());
        character.setIZone((int) iZoneField.getValue());
        character.setFF(kSField.getValue(), kVField.getValue(), kAField.getValue());
    }

    private void configureTextFields() {
        this.add(kPField);
        this.add(kIField);
        this.add(kDField);
        this.add(toleranceField);
        this.add(waitTimeField);
        this.add(iZoneField);
        this.add(kSField);
        this.add(kVField);
        this.add(kAField);
        this.add(status);
        this.add(setpointField);
    }
}
