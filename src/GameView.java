import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {
    private JButton[][] tileButtons;
    private JPanel buttonPanel;
    private JPanel moveCounter;
    private JPanel features;
    private JButton shuffleButton;
    private JLabel winMessage;
    private JLabel moves;
    private GameModel model;
    private Color defaultColor = new JButton().getBackground();

    public GameView(GameModel model) {
        this.model = model;
        initializeTiles(model.getTiles());
        initializeMoveCounter();
        initializeFeatures();

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.CENTER);
        add(moveCounter, BorderLayout.NORTH);
        add(features, BorderLayout.SOUTH);
    }

    public JButton[][] getTileButtons() {
        return tileButtons;
    }

    public JButton getShuffleButton() {
        return shuffleButton;
    }

    protected void swapValues(int pressedRow, int pressedCol, int value) {
        int emptyRow = model.getEmptyTileRow(), emptyCol = model.getEmptyTileCol();
        tileButtons[pressedRow][pressedCol].setEnabled(false);
        tileButtons[pressedRow][pressedCol].setText("");
        tileButtons[emptyRow][emptyCol].setText(String.valueOf(value));
        tileButtons[emptyRow][emptyCol].setEnabled(true);
        revalidate();
        repaint();
    }

    private void initializeTiles(Tile[][] tiles) {
        int amountRows = model.getAmountRows(), amountCols = model.getAmountColumns();
        buttonPanel = new JPanel(new GridLayout(amountRows, amountCols));
        tileButtons = new JButton[amountRows][amountCols];
        int value;
        int buttonHeight = 90, buttonWidth = 90;
        for (int row = 0; row < amountRows; row++) {
            for (int col = 0; col < amountCols; col++) {
                value = tiles[row][col].value;
                tileButtons[row][col] = new JButton(value == 0 ? "" : String.valueOf(value));
                tileButtons[row][col].setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                buttonPanel.add(tileButtons[row][col]);
            }
        }
    }

    private void initializeMoveCounter() {
        moves = new JLabel(String.format("Moves: %d", model.getMoveCount()));
        moveCounter = new JPanel(new GridLayout(1, 3));
        moveCounter.add(moves, 0);
        moveCounter.add(Box.createRigidArea(new Dimension(30, 30)), 1);
        moveCounter.add(Box.createRigidArea(new Dimension(30, 30)), 2);
    }

    private void initializeFeatures() {
        features = new JPanel(new BorderLayout());
        shuffleButton = new JButton("shuffle");
        features.add(shuffleButton, BorderLayout.CENTER);
    }

    protected void updateMoveCounter() {
        moves.setText(String.format("Moves: %d", model.getMoveCount()));
    }

    protected void updateBoard() {
        int amountRows = model.getAmountRows(), amountCols = model.getAmountColumns();
        Tile[][] tiles = model.getTiles();
        int value;
        for (int row = 0; row < amountRows; row++) {
            for (int col = 0; col < amountCols; col++) {
                value = tiles[row][col].value;
                if (value == 0) {
                    tileButtons[row][col].setText("");
                } else {
                    tileButtons[row][col].setText(String.valueOf(value));
                }
            }
        }
        repaint();
        revalidate();
    }


    protected void setGameState() {
        int amountRows = model.getAmountRows(), amountCols = model.getAmountColumns();
        boolean enabled;
        Color color;
        if (model.getGameState() == GameModel.GameState.WON_GAME) {
            enabled = false;
            color = Color.green;
        } else {
            enabled = true;
            color = defaultColor;
        }
        for (int row = 0; row < amountRows; row++) {
            for (int col = 0; col < amountCols; col++) {
                tileButtons[row][col].setEnabled(enabled);
                tileButtons[row][col].setBackground(color);
            }
        }
    }
}
