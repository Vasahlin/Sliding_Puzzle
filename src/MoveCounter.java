import javax.swing.*;
import java.awt.*;

public class MoveCounter implements MoveCountListener {
    JLabel label;
    private int moveCount = 0;
    private final GameWindow gameWindow;

    public MoveCounter(GameWindow window) {
        this.gameWindow = window;
        label = new JLabel();
    }

    public JPanel movePanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3));
        panel.add(Box.createRigidArea(new Dimension(30, 30)), 0);
        label.setText(String.format("Moves: %d", moveCount));
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(30, 30)), 2);
        return panel;
    }

    @Override
    public void moveCountUpdated() {
        label.setText(String.format("Moves: %d", ++moveCount));
        gameWindow.revalidate();
        gameWindow.repaint();
    }
}
