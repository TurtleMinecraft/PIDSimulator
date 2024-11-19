package simulator;

import simulator.information.ErrorGraph;
import simulator.textfields.BaseTextField;

import javax.swing.*;
import java.awt.*;

public class RerunButton extends JButton {

    public static final int WIDTH = 100;
    public static final int HEIGHT = 45;

    private static final int X = Window.WINDOW_WIDTH / 2 - WIDTH / 2;
    private static final int Y = 200;

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
            ErrorGraph.reset();
        });
    }
}
