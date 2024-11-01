import java.util.ArrayList;
import java.util.Collections;

public class GameModel {
    private final int amountRows, amountColumns;
    private final Tile[][] tiles;
    private int emptyTileCol, emptyTileRow;
    private ArrayList<Integer> solvedPuzzleOrder;
    private GameState gameState = GameState.ACTIVE;
    private int moveCount = 0;

    public GameModel(int amountColumns, int amountRows, boolean showCase) {
        this.amountColumns = amountColumns;
        this.amountRows = amountRows;
        tiles = new Tile[amountRows][amountColumns];
        initializeTiles(showCase);
    }

    public enum GameState {
        ACTIVE, WON_GAME
    }

    public int getEmptyTileCol() {
        return emptyTileCol;
    }

    public int getEmptyTileRow() {
        return emptyTileRow;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public int getAmountColumns() {
        return amountColumns;
    }

    public int getAmountRows() {
        return amountRows;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    protected void initializeTiles(boolean showCase) {
        ArrayList<Integer> values;
        if (showCase) {
            values = ascendingValues();
            setSolvedOrder(ascendingValues());
            values.removeFirst();
            values.add(0);
        } else {
            values = createShuffledValues();
        }
        int index = 0;
        for (int row = 0; row < amountRows; row++) {
            for (int col = 0; col < amountColumns; col++) {
                tiles[row][col] = new Tile(values.get(index++), row, col);
            }
        }
        while (isNotSolvable(tiles)) {
            shuffleValues();
        }
        findEmptyTile();
    }

    protected void incrementMoveCount() {
        moveCount++;
    }

    protected void resetMoveCount() {
        moveCount = 0;
    }

    protected void moveTile(int pressedRow, int pressedCol) {
        tiles[emptyTileRow][emptyTileCol].value = tiles[pressedRow][pressedCol].value;
        tiles[pressedRow][pressedCol].value = 0;
        setEmptyTile(pressedRow, pressedCol);
        if (isPuzzleSolved()) {
            gameState = GameState.WON_GAME;
        }
    }

    protected boolean isValidMove(int pressedRow, int pressedCol) {
        int horizontalDistance = Math.abs(pressedCol - emptyTileCol);
        int verticalDistance = Math.abs(pressedRow - emptyTileRow);
        return (horizontalDistance == 1 && pressedRow == emptyTileRow) ||
               (verticalDistance == 1 && pressedCol == emptyTileCol);
    }

    protected boolean isPuzzleSolved() {
        if (!bottomRightTileEmpty()) {
            return false;
        }
        int value;
        int index = 0;
        for (Tile[] t : tiles) {
            for (Tile tile : t) {
                value = tile.value;
                if (value == 0) {
                    continue;
                }
                if (!(value == solvedPuzzleOrder.get(index++))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean bottomRightTileEmpty() {
        Tile[] bottomRow = tiles[tiles.length - 1];
        return bottomRow[bottomRow.length - 1].value == 0;
    }

    protected void setEmptyTile(int row, int col) {
        emptyTileRow = row;
        emptyTileCol = col;
    }

    protected void findEmptyTile() {
        for (int row = 0; row < amountRows; row++) {
            for (int col = 0; col < amountColumns; col++) {
                if (tiles[row][col].value == 0) {
                    emptyTileRow = row;
                    emptyTileCol = col;
                }
            }
        }
    }

    protected void shuffleValues() {
        ArrayList<Integer> values = createShuffledValues();
        int index = 0;
        for (int row = 0; row < amountRows; row++) {
            for (int col = 0; col < amountColumns; col++) {
                tiles[row][col].value = values.get(index++);
            }
        }
    }

    protected ArrayList<Integer> ascendingValues() {
        ArrayList<Integer> values = new ArrayList<>();
        for (int i = 0; i < amountRows * amountColumns; i++) {
            values.add(i);
        }
        return values;
    }

    protected ArrayList<Integer> createShuffledValues() {
        ArrayList<Integer> values = ascendingValues();
        setSolvedOrder(values);
        Collections.shuffle(values);
        return values;
    }

    private void setSolvedOrder(ArrayList<Integer> ascendingValues) {
        solvedPuzzleOrder = new ArrayList<>(ascendingValues.subList(1, ascendingValues.size()));
    }

    // find Position of blank from bottom
    private int findXPosition(Tile[][] board) {
        int rows = amountRows;
        // start from bottom-right corner of matrix
        for (int i = rows - 1; i >= 0; i--)
            for (int j = rows - 1; j >= 0; j--)
                if (board[i][j].value == 0)
                    return rows - i;
        return -1;
    }

    // This function returns true if given
    // instance of N*N - 1 puzzle is solvable
    protected boolean isNotSolvable(Tile[][] board) {
        int rows = amountRows, cols = amountColumns;
        // Count inversions in given puzzle
        int[] values = new int[rows * cols];
        int k = 0;

        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                if (!(board[row][col].value == 0)) {
                    values[k++] = board[row][col].value;
                } else {
                    values[k++] = 0;
                }

        int invCount = getInvCount(values);

        // If grid is odd, return true if inversion
        // count is even.
        if (rows % 2 == 1)
            return invCount % 2 != 0;
        else // grid is even
        {
            int pos = findXPosition(board);
            if (pos % 2 == 1)
                return invCount % 2 != 0;
            else
                return invCount % 2 != 1;
        }
    }
    
    private int getInvCount(int[] arr) {
        int inv_count = 0;
        for (int row = 0; row < amountRows * -1; row++) {
            for (int col = row + 1; col < amountColumns * amountColumns; col++) {
                // count pairs(arr[i], arr[j]) such that
                // i < j but arr[i] > arr[j]
                if (arr[col] != 0 && arr[row] != 0
                    && arr[row] > arr[col])
                    inv_count++;
            }
        }
        return inv_count;
    }
}
