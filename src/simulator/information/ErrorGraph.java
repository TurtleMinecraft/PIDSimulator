package simulator.information;

import simulator.Setpoint;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ErrorGraph extends JPanel {

    public static final int WIDTH = Status.WIDTH;
    public static final int HEIGHT = 390;
    private static final int X = 0;
    private static final int Y = 150;

    private static final int POINT_WIDTH = 2;
    private static final int POINT_HEIGHT = 2;
    private static final int DISTANCE_BETWEEN_POINTS = 2;

    private final ArrayList<Integer> errors;
    private int currentErrorLocation;
    private double proportion;

    private static ErrorGraph instance;

    public static ErrorGraph getInstance() {
        if (instance == null) {
            instance = new ErrorGraph();
        }
        return instance;
    }

    private ErrorGraph() {
        this.setLayout(null);
        this.setVisible(true);
        this.setBounds(X, Y, WIDTH, HEIGHT);
        this.setBackground(Color.WHITE);
        errors = new ArrayList<>();
        currentErrorLocation = 0;
        proportion = 0;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);
        ArrayList<Rectangle> rectangles = createRectangles();
        rectangles.forEach(g2::fill);
        g2.setColor(Color.BLACK);
        g2.fill(new Rectangle(0, HEIGHT / 2, WIDTH, POINT_HEIGHT));
    }

    public static void reset() {
        ErrorGraph errorGraph = getInstance();
        errorGraph.errors.clear();
        errorGraph.currentErrorLocation = 0;
        errorGraph.setProportions(Setpoint.getInstance().x);
    }

    public void addPoint(int error) {
        errors.add(error);
    }

    public void setProportions(int setpoint) {
        if (setpoint == 0) proportion = 0;
        else proportion = (double) HEIGHT / setpoint;
    }

    private ArrayList<Rectangle> createRectangles() {
        ArrayList<Rectangle> rectangles = new ArrayList<>();
        currentErrorLocation = 0;
        errors.forEach(error -> {
            rectangles.add(new Rectangle(currentErrorLocation, (int) (error * proportion) / 2 + HEIGHT / 2,
                    POINT_WIDTH, POINT_HEIGHT));
            currentErrorLocation += DISTANCE_BETWEEN_POINTS;
        });
        return rectangles;
    }
}
