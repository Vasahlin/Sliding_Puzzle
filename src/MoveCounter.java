import listeners.MoveCountObserver;

import javax.swing.*;
import java.awt.*;

public class MoveCounter implements MoveCountObserver {
    JLabel label;
    private int moveCount = 0;
    private final GameWindow gameWindow;
    private final GameLogic gameLogic;

    public MoveCounter(GameWindow window, GameLogic logic) {
        this.gameWindow = window;
        this.gameLogic = logic;
        label = new JLabel();
    }

    public JPanel movePanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3));
        label.setText(String.format("Moves: %d", moveCount));
        panel.add(label, 0);
        panel.add(Box.createRigidArea(new Dimension(30, 30)), 1);
        panel.add(Box.createRigidArea(new Dimension(30, 30)), 2);
        return panel;
    }

    @Override
    public void updateMoveCount() {
        if (gameLogic.getGameState() == GameLogic.GameState.WON_GAME) {
            moveCount = -1;
        }
        if (gameLogic.getGameState() == GameLogic.GameState.SHUFFLE) {
            moveCount = -1;
            gameLogic.setGameState(GameLogic.GameState.ACTIVE);
        }
        label.setText(String.format("Moves: %d", ++moveCount));
        gameWindow.revalidate();
        gameWindow.repaint();
    }

    public void resetMoveCount() {
        this.moveCount = 0;
        gameWindow.revalidate();
        gameWindow.repaint();
    }
}
