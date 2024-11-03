import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {
    private JButton[][] tileButtons;
    private JPanel buttonPanel;
    private JButton shuffleButton;
    private JButton exitButton;
    private JPanel moveCounter;
    private JPanel features;
    private JLabel winMessage;
    private JLabel moveCount;

    public GameView(Tile[][] tiles, int emptyTileRow, int emptyTileCol, int moveCount) {
        initializeTiles(tiles, emptyTileRow, emptyTileCol);
        initializeMoveCounter(moveCount);
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

    protected void updateMoveCounter(int moveCount) {
        this.moveCount.setText(String.format("Moves: %d", moveCount));
    }

    protected void displayWin() {
        winMessage.setText("*** You win! ***");
        shuffleButton.setText("New game");
    }

    protected void displayNewGame() {
        winMessage.setText("");
        shuffleButton.setText("Shuffle");
    }

    protected void swapValues(int pressedRow, int pressedCol, int value, int emptyRow, int emptyCol) {
        tileButtons[pressedRow][pressedCol].setEnabled(false);
        tileButtons[pressedRow][pressedCol].setText("");
        tileButtons[emptyRow][emptyCol].setText(String.valueOf(value));
        tileButtons[emptyRow][emptyCol].setEnabled(true);
        revalidate();
        repaint();
    }

    private void initializeTiles(Tile[][] tiles, int emptyTileRow, int emptyTileCol) {
        int amountRows = tiles.length, amountCols = tiles[0].length;
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
        tileButtons[emptyTileRow][emptyTileCol].setEnabled(false);
    }

    private void initializeMoveCounter(int moveCount) {
        this.moveCount = new JLabel(String.format("Moves: %d", moveCount));
        moveCounter = new JPanel(new GridLayout(1, 3));
        moveCounter.add(this.moveCount, 0);
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

    protected void updateBoard(Tile[][] tiles) {
        int amountRows = tiles.length, amountCols = tiles[0].length;
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

    protected void setGameState(int amountRows, int amountCols, GameModel.GameState state) {
        boolean isEnabled;
        Color color;
        if (state == GameModel.GameState.WON_GAME) {
            isEnabled = false;
            color = Color.green;
        } else {
            isEnabled = true;
            color = new JButton().getBackground();
        }
        for (int row = 0; row < amountRows; row++) {
            for (int col = 0; col < amountCols; col++) {
                tileButtons[row][col].setEnabled(isEnabled);
                tileButtons[row][col].setBackground(color);
            }
        }
    }
}
