import javax.swing.*;

public class ShuffleButton implements ShuffleButtonListener{
    protected JButton shuffleButton = new JButton("Shuffle");
    private final GameBoard gameBoard;
    private final GameWindow gameWindow;

    public ShuffleButton(GameBoard gameBoard, GameWindow window) {
        this.gameBoard = gameBoard;
        this.gameWindow = window;
    }

    protected JButton shuffleButton(MoveCounter mc) {
        GameLogic logic = gameBoard.getGameLogic();
        shuffleButton.addActionListener(_ -> {
            if (gameBoard.getGameLogic().getGameState() == GameLogic.GameState.WON_GAME) {
                mc.resetMoveCount();
                shuffleButton.setText("Shuffle");
                logic.setGameState(GameLogic.GameState.ACTIVE);
                gameBoard.setColor();
                logic.setGameState(GameLogic.GameState.SHUFFLE);
                logic.notifyMoveCount();
            }
            do {
                gameBoard.newValues();
                logic.setGameState(GameLogic.GameState.SHUFFLE);
                logic.notifyMoveCount();
            } while (gameBoard.getGameLogic().isNotSolvable(gameBoard.getTiles()));
            for (Tile[] row : gameBoard.getTiles()) {
                for (Tile tile : row) {
                    tile.button().setEnabled(true);
                }
            }
            gameBoard.setEmptyTile();
            gameWindow.revalidate();
            gameWindow.repaint();
        });
        return shuffleButton;
    }

    @Override
    public void notifyButton() {
        if (gameBoard.getGameLogic().getGameState() == GameLogic.GameState.WON_GAME &&
            shuffleButton.getText().equals("Shuffle")) {
            shuffleButton.setText("New Game");
            gameWindow.revalidate();
            gameWindow.repaint();
        }
    }
}
