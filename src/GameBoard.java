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
    private final Color defaultColor = new JPanel().getBackground();
    private final Color winColor = Color.GREEN;

    public GameBoard(int amountRows, int amountColumns) {
        this.amountRows = amountRows;
        this.amountColumns = amountColumns;
        tiles = new Tile[amountRows][amountColumns];
        gameLogic = new GameLogic(this);
    }

    public int getAmountRows() {
        return amountRows;
    }

    public int getAmountColumns() {
        return amountColumns;
    }

    public GameLogic getGameLogic() { //added for testing purposes
        return gameLogic;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    protected JPanel createGameBoard(boolean showCase) {
        JPanel panel = new JPanel(new GridLayout(amountRows, amountColumns));
        ArrayList<String> values;
        if (showCase) {
            values = ascendingValues();
            setSolvedOrder(ascendingValues());
            values.removeFirst();
            values.add("0");
        } else {
            values = createShuffledValues();
        }
        int index = 0;
        for (int row = 0; row < amountRows; row++) {
            for (int col = 0; col < amountColumns; col++) {
                String value = values.get(index++);
                tiles[row][col] = new Tile(new JButton(value));
                int tileWidth = 90;
                int tileHeight = 90;
                tiles[row][col].button().setPreferredSize(new Dimension(tileWidth, tileHeight));
                tiles[row][col].button().addActionListener(new ButtonListener(row, col));
                panel.add(tiles[row][col].button());
            }
        }
        while (gameLogic.isNotSolvable(tiles)) {
            newValues();
        }
        setEmptyTile();
        return panel;
    }

    protected void newValues() {
        ArrayList<String> values = createShuffledValues();
        int index = 0;
        for (int row = 0; row < amountRows; row++) {
            for (int col = 0; col < amountColumns; col++) {
                tiles[row][col].button().setText(values.get(index++));
            }
        }
    }

    protected ArrayList<String> ascendingValues() {
        ArrayList<String> values = new ArrayList<>();
        for (int i = 0; i < amountRows * amountColumns; i++) {
            values.add(String.valueOf(i));
        }
        return values;
    }

    protected ArrayList<String> createShuffledValues() {
        ArrayList<String> values = ascendingValues();
        setSolvedOrder(values);
        Collections.shuffle(values);
        return values;
    }

    private void setSolvedOrder(ArrayList<String> ascendingValues) {
        gameLogic.setSolvedOrder(new ArrayList<>(ascendingValues.subList(1, ascendingValues.size())));
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

    protected void setEmptyTile() {
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

    protected void updateTileColors() {
        Color color;
        GameLogic.GameState state = gameLogic.getGameState();
        if (state == GameLogic.GameState.WON_GAME) {
            color = winColor;
            for (Tile[] tile : tiles) {
                for (Tile value : tile) {
                    value.button().setEnabled(false);
                    value.button().setBackground(color);
                }
            }
        }
        else if (tiles[0][0].button().getBackground() == winColor &&
                 state == GameLogic.GameState.ACTIVE || state == GameLogic.GameState.SHUFFLE)  {
            color = defaultColor;
            for (Tile[] tile : tiles) {
                for (Tile value : tile) {
                    value.button().setEnabled(true);
                    value.button().setBackground(color);
                }
            }
        }
    }

    protected void lockGameState() {
        for (Tile[] tile : tiles) {
            for (Tile value : tile) {
                value.button().setEnabled(false);
            }
        }
    }
}
