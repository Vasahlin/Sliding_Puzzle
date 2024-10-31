import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController {
    private final GameModel model;
    private final GameView view;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        setupButtonListeners();
        setupShuffleListener();
    }

    private void setupButtonListeners() {
        JButton[][] buttons = view.getTileButtons();
        for (int row = 0; row < model.getAmountRows(); row++) {
            for (int col = 0; col < model.getAmountColumns(); col++) {
                buttons[row][col].addActionListener(new ButtonListener(row, col));
            }
        }
    }

    private void setupShuffleListener() {
        view.getShuffleButton().addActionListener(_ -> shufflePressed());
    }

    private void shufflePressed() {
        if (model.getGameState() == GameModel.GameState.WON_GAME) {
            model.setGameState(GameModel.GameState.ACTIVE);
            view.notifyNewGame();
            view.setGameState();
        }
        do {
            model.shuffleValues();
            model.findEmptyTile();
        } while (model.isNotSolvable(model.getTiles()));

        model.resetMoveCount();
        view.updateMoveCounter();
        view.updateBoard();
    }

    private class ButtonListener implements ActionListener {
        private final int row, column;

        private ButtonListener(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int tileValue = model.getTiles()[row][column].value;
            if (model.isValidMove(this.row, this.column)) {
                view.swapValues(this.row, this.column, tileValue);
                model.moveTile(this.row, this.column);
                model.incrementMoveCount();
                view.updateMoveCounter();
                if (model.getGameState() == GameModel.GameState.WON_GAME) {
                    view.setGameState();
                    view.notifyWon();
                }
            }

        }
    }
}