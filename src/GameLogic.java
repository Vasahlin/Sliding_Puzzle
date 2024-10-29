import java.util.ArrayList;

public class GameLogic {
    private int emptyTileCol, emptyTileRow;
    private ArrayList<String> solvedPuzzleOrder;
    private final GameBoard gameBoard;

    public GameLogic(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void setSolvedOrder(ArrayList<String> solvedOrder) {
        this.solvedPuzzleOrder = solvedOrder;
    }

    public void setEmptyTile(int row, int col) {
        this.emptyTileRow = row;
        this.emptyTileCol = col;
    }

    public void performAction(int row, int column) {
        if (moveTile(row, column)) {

        }
    }

    private boolean moveTile(int pressedRow, int pressedCol) {
        if (isValidMove(pressedRow, pressedCol)) {
            //ändra

        }
        return true;
    }

    private boolean isValidMove(int pressedRow, int pressedCol) {
        int horizontalDistance = Math.abs(pressedRow - emptyTileRow);
        int verticalDistance = Math.abs(pressedCol - emptyTileCol);
        return (horizontalDistance == 1 && pressedRow == emptyTileRow) || (verticalDistance == 1 && pressedCol == emptyTileCol);
    }

}
