import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {
    private JButton[][] tileButtons;
    private JPanel buttonPanel;
    private JPanel moveCounter;
    private JPanel features;
    private JButton shuffleButton;
    private JButton exitButton;
    private JLabel winMessage;
    private JLabel moves;
    private final GameModel model;
    private final Color defaultColor = new JButton().getBackground();

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

    public JButton getExitButton() {
        return exitButton;
    }

    protected void updateMoveCounter() {
        moves.setText(String.format("Moves: %d", model.getMoveCount()));
    }

    protected void notifyWon() {
        winMessage.setText("*** You win! ***");
        shuffleButton.setText("New game");
    }

    protected void notifyNewGame() {
        winMessage.setText("");
        shuffleButton.setText("Shuffle");
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
        int buttonHeight = 100, buttonWidth = 100;
        for (int row = 0; row < amountRows; row++) {
            for (int col = 0; col < amountCols; col++) {
                value = tiles[row][col].value;
                tileButtons[row][col] = new JButton(value == 0 ? "" : String.valueOf(value));
                tileButtons[row][col].setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                buttonPanel.add(tileButtons[row][col]);
            }
        }
        tileButtons[model.getEmptyTileRow()][model.getEmptyTileCol()].setEnabled(false);
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

        //Win panel
        JPanel winPanel = new JPanel(new GridLayout(1,3));
        winMessage = new JLabel("");
        winPanel.add(Box.createRigidArea(new Dimension(20,20)),0);
        winPanel.add(winMessage, 1);
        winPanel.add(Box.createRigidArea(new Dimension(20,20)),2);
        features.add(winPanel, BorderLayout.NORTH);

        //Shuffle button
        shuffleButton = new JButton("shuffle");
        features.add(shuffleButton, BorderLayout.CENTER);

        //Exit button
        exitButton = new JButton("Exit");
        features.add(exitButton, BorderLayout.SOUTH);
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
                    tileButtons[row][col].setEnabled(false);
                } else {
                    tileButtons[row][col].setText(String.valueOf(value));
                    tileButtons[row][col].setEnabled(true);
                }
            }
        }
        repaint();
        revalidate();
    }

    protected void setGameState() {
        int amountRows = model.getAmountRows(), amountCols = model.getAmountColumns();
        boolean isEnabled;
        Color color;
        if (model.getGameState() == GameModel.GameState.WON_GAME) {
            isEnabled = false;
            color = Color.green;
        } else {
            isEnabled = true;
            color = defaultColor;
        }
        for (int row = 0; row < amountRows; row++) {
            for (int col = 0; col < amountCols; col++) {
                tileButtons[row][col].setEnabled(isEnabled);
                tileButtons[row][col].setBackground(color);
            }
        }
    }
}
