import java.util.ArrayList;

public class GameLogic {
    private int emptyTileCol, emptyTileRow;
    private ArrayList<String> solvedPuzzleOrder;
    private final GameBoard gameBoard;
    private MoveCountListener moveListener;

    public GameLogic(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
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

    public void setMoveCountListener(MoveCountListener listener) {
        this.moveListener = listener;
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
            if (moveListener != null) {
                moveListener.moveCountUpdated();
            }
            if (isPuzzleSolved())
                lockGameState();
        }
    }

    protected void lockGameState() {
        Tile[][] tiles = gameBoard.getTiles();
        for (Tile[] tile : tiles) {
            for (Tile value : tile) {
                value.button().setEnabled(false);
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
        Tile[] bottomRow = gameBoard.getTiles()[gameBoard.getTiles().length -1];
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
}
