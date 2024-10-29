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
            String s = gameBoard.getTiles()[pressedRow][pressedCol].button().getText();
            gameBoard.getTiles()[emptyTileRow][emptyTileCol].button().setText(s);
            gameBoard.getTiles()[emptyTileRow][emptyTileCol].button().setEnabled(true);
            gameBoard.getTiles()[pressedRow][pressedCol].button().setEnabled(false);
            gameBoard.getTiles()[pressedRow][pressedCol].button().setText("");
            emptyTileRow = pressedRow;
            emptyTileCol = pressedCol;
            return true;
        }
        return false;
    }

    private boolean isValidMove(int pressedRow, int pressedCol) {
        int horizontalDistance = Math.abs(pressedRow - emptyTileRow);
        int verticalDistance = Math.abs(pressedCol - emptyTileCol);
        return (horizontalDistance == 1 && pressedRow == emptyTileRow) || (verticalDistance == 1 && pressedCol == emptyTileCol);
    }

}
