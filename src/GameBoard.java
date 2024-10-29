import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class GameBoard {

    private final int amountRows, amountColumns;
    private final Tile[][] tiles;
    private final GameLogic gameLogic;

    public GameBoard(int amountRows, int amountColumns) {
        this.amountRows = amountRows;
        this.amountColumns = amountColumns;
        tiles = new Tile[amountRows][amountColumns];
        gameLogic = new GameLogic(this);
    }

    public GameLogic getGameLogic() { //added for testing purposes
        return gameLogic;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public JPanel createGameBoard() {
        JPanel panel = new JPanel(new GridLayout(amountRows, amountColumns));
        ArrayList<String> shuffledValues = createShuffledValues();
        int index = 0;
        for (int row = 0; row < amountRows; row++) {
            for (int col = 0; col < amountColumns; col++) {
                String value = shuffledValues.get(index++);
                tiles[row][col] = new Tile(new JButton(value));
                tiles[row][col].button().addActionListener(new ButtonListener(row, col));
                panel.add(tiles[row][col].button());
            }
        }
        setEmptyTile();
        return panel;
    }

    private ArrayList<String> createShuffledValues() {
        ArrayList<String> shuffledValues = new ArrayList<>();
        for (int i = 0; i < amountRows * amountColumns; i++) {
            shuffledValues.add(String.valueOf(i));
        }
        gameLogic.setSolvedOrder(new ArrayList<>(shuffledValues.subList(1, shuffledValues.size())));
        Collections.shuffle(shuffledValues);
        return shuffledValues;
    }

    private class ButtonListener implements ActionListener {
        private final int row, column;

        private ButtonListener(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            gameLogic.performAction(this.row, this.column);
        }
    }

    private void setEmptyTile() {
        for (int row = 0; row < amountRows; row++) {
            for (int col = 0; col < amountColumns; col++) {
                if (tiles[row][col].button().getText().equals("0")) {
                    tiles[row][col].button().setEnabled(false);
                    tiles[row][col].button().setText("");
                    gameLogic.setEmptyTile(row, col);
                }
            }
        }
    }
}
