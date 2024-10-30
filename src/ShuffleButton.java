import listeners.PuzzleSolvedListener;
import listeners.ShuffleButtonListener;

import javax.swing.*;

public class ShuffleButton implements ShuffleButtonListener {
    private final String shuffle = "Shuffle";
    protected JButton shuffleButton = new JButton(shuffle);
    private final GameBoard gameBoard;
    private final GameWindow gameWindow;
    private final MoveCounter moveCounter;
    private final PuzzleSolvedListener puzzleListener;

    public ShuffleButton(GameBoard gameBoard, GameWindow window, MoveCounter mc, PuzzleSolvedListener pl) {
        this.gameBoard = gameBoard;
        this.gameWindow = window;
        this.moveCounter = mc;
        puzzleListener = pl;
    }

    protected JButton shuffleButton() {
        shuffleButton.addActionListener(_ -> buttonPressed());
        return shuffleButton;
    }

    public void buttonPressed() {
        GameLogic logic = gameBoard.getGameLogic();
        if (logic.getGameState().equals(GameLogic.GameState.WON_GAME)) {
            moveCounter.resetMoveCount();
            shuffleButton.setText(shuffle);
            logic.setGameState(GameLogic.GameState.SHUFFLE);
            gameBoard.setColor();
            puzzleListener.onPuzzleNotSolved();
        }
        do {
            gameBoard.newValues();
            logic.setGameState(GameLogic.GameState.SHUFFLE);

        } while (gameBoard.getGameLogic().isNotSolvable(gameBoard.getTiles()));

        for (Tile[] row : gameBoard.getTiles()) {
            for (Tile tile : row) {
                tile.button().setEnabled(true);
            }
        }
        logic.notifyMoveCount();
        gameBoard.setEmptyTile();
        gameWindow.revalidate();
        gameWindow.repaint();
    }

    @Override
    public void notifyButton() {
        if (gameBoard.getGameLogic().getGameState() == GameLogic.GameState.WON_GAME &&
            shuffleButton.getText().equals(shuffle)) {
            shuffleButton.setText("New Game");
        }
    }
}
