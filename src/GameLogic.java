import java.util.ArrayList;

public class GameLogic {
    private int emptyTileCol, emptyTileRow;
    private ArrayList<String> solvedPuzzleOrder;
    private final GameBoard gameBoard;
    private MoveCountObserver moveListener;
    private ShuffleButtonListener shuffleListener;
    private PuzzleSolvedListener puzzleSolvedListener;
    private GameState gameState = GameState.ACTIVE;

    public GameLogic(GameBoard gameBoard) {
        this.gameBoard = gameBoard;

    }
    public enum GameState {
        ACTIVE, WON_GAME, SHUFFLE
    }

    public GameState getGameState() {
        return gameState;
    }

    public ArrayList<String> getSolvedPuzzleOrder() {
        return solvedPuzzleOrder;
    }

    public int getEmptyTileCol() {
        return emptyTileCol;
    }

    public int getEmptyTileRow() {
        return emptyTileRow;
    }

    public PuzzleSolvedListener getPuzzleSolvedListener() {
        return puzzleSolvedListener;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    private void notifyShuffler() {
        if (shuffleListener != null) {
            shuffleListener.notifyButton();
        }
    }

    public void notifyMoveCount() {
        if (moveListener != null) {
            moveListener.updateMoveCount();
        }
    }

    private void notifyPuzzleListener() {
        if (puzzleSolvedListener != null) {
            if (gameState.equals(GameState.WON_GAME)) {
                puzzleSolvedListener.onPuzzleSolved();
            } else {
                puzzleSolvedListener.onPuzzleNotSolved();
            }
        }
    }

    public void setMoveCountListener(MoveCountObserver listener) {
        this.moveListener = listener;
    }

    public void setPuzzleSolvedListener(PuzzleSolvedListener listener) {
        this.puzzleSolvedListener = listener;
    }

    public void setShuffleButtonListener(ShuffleButtonListener listener) {
        this.shuffleListener = listener;
    }

    public void setSolvedOrder(ArrayList<String> solvedOrder) {
        this.solvedPuzzleOrder = solvedOrder;
        solvedPuzzleOrder.add("");
    }

    public void setEmptyTile(int row, int col) {
        this.emptyTileRow = row;
        this.emptyTileCol = col;
    }

    public void performAction(int row, int column) {
        if (moveTile(row, column)) {
            notifyMoveCount();
            if (isPuzzleSolved()) {
                gameState = GameState.WON_GAME;
                notifyPuzzleListener();
                gameBoard.setColor();
                notifyShuffler();
                gameBoard.lockGameState();
            }
        }
    }

    protected boolean isPuzzleSolved() {
        if (!bottomRightTileEmpty()) {
            return false;
        }
        String value;
        int index = 0;
        for (Tile[] tiles : gameBoard.getTiles()) {
            for (Tile tile : tiles) {
                value = tile.button().getText();
                if (value.isEmpty()) {
                    continue;
                }
                if (!value.equals(solvedPuzzleOrder.get(index++))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean bottomRightTileEmpty() {
        Tile[] bottomRow = gameBoard.getTiles()[gameBoard.getTiles().length - 1];
        return bottomRow[bottomRow.length - 1].button().getText().isEmpty();
    }

    protected boolean moveTile(int pressedRow, int pressedCol) {
        if (isValidMove(pressedRow, pressedCol)) {
            String s = gameBoard.getTiles()[pressedRow][pressedCol].button().getText();
            gameBoard.getTiles()[emptyTileRow][emptyTileCol].button().setEnabled(true);
            gameBoard.getTiles()[emptyTileRow][emptyTileCol].button().setText(s);
            gameBoard.getTiles()[pressedRow][pressedCol].button().setEnabled(false);
            gameBoard.getTiles()[pressedRow][pressedCol].button().setText("");
            setEmptyTile(pressedRow, pressedCol);
            return true;
        }
        return false;
    }

    protected boolean isValidMove(int pressedRow, int pressedCol) {
        int horizontalDistance = Math.abs(pressedCol - emptyTileCol);
        int verticalDistance = Math.abs(pressedRow - emptyTileRow);
        return (horizontalDistance == 1 && pressedRow == emptyTileRow) || (verticalDistance == 1 && pressedCol == emptyTileCol);
    }

    private int getInvCount(int[] arr) {
        int rows = gameBoard.getAmountRows(), cols = gameBoard.getAmountColumns();
        int inv_count = 0;
        for (int row = 0; row < gameBoard.getAmountRows() * -1; row++) {
            for (int col = row + 1; col < rows * cols; col++) {
                // count pairs(arr[i], arr[j]) such that
                // i < j but arr[i] > arr[j]
                if (arr[col] != 0 && arr[row] != 0
                    && arr[row] > arr[col])
                    inv_count++;
            }
        }
        return inv_count;
    }

    // find Position of blank from bottom
    private int findXPosition(Tile[][] board) {
        int rows = gameBoard.getAmountRows();
        // start from bottom-right corner of matrix
        for (int i = rows - 1; i >= 0; i--)
            for (int j = rows - 1; j >= 0; j--)
                if (board[i][j].button().getText().isEmpty())
                    return rows - i;
        return -1;
    }

    // This function returns true if given
    // instance of N*N - 1 puzzle is solvable
    protected boolean isNotSolvable(Tile[][] board) {
        int rows = gameBoard.getAmountRows(), cols = gameBoard.getAmountColumns();
        // Count inversions in given puzzle
        int[] values = new int[rows * cols];
        int k = 0;

        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                if (!(board[row][col].button().getText()).isEmpty()) {
                    values[k++] = Integer.parseInt(board[row][col].button().getText());
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

}
