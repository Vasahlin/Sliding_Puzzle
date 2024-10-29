import java.util.ArrayList;

public class GameLogic {
    private int emptyTileCol, emptyTileRow;
    private ArrayList<String> solvedPuzzleOrder;
    private final GameBoard gameBoard;

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
            if (isPuzzleSolved())
                lockGameState();
        }
    }

    private void lockGameState() {

    }

    protected ArrayList<String> getCurrentPuzzleOrder() {
        Tile[][] tiles = gameBoard.getTiles();
        ArrayList<String> currentPuzzleOrder = new ArrayList<>();
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                currentPuzzleOrder.add(tiles[row][col].button().getText());
            }
        }
        return currentPuzzleOrder;
    }

    private boolean isPuzzleSolved() {
        ArrayList<String> currentPuzzleOrder = getCurrentPuzzleOrder();
        if (currentPuzzleOrder.size() == solvedPuzzleOrder.size()) {
            for (int i = 0; i < solvedPuzzleOrder.size(); i++) {
                if (!solvedPuzzleOrder.get(i).equals(currentPuzzleOrder.get(i)))
                    break;
                if ((i == solvedPuzzleOrder.size() - 1) && currentPuzzleOrder.get(i).isEmpty())
                    return true;
            }
        }
        return false;
    }

    protected boolean isPuzzleSolved(ArrayList<String> currentPuzzleOrder) {
        if (currentPuzzleOrder.size() == solvedPuzzleOrder.size()) {
            for (int i = 0; i < solvedPuzzleOrder.size(); i++) {
                if (!solvedPuzzleOrder.get(i).equals(currentPuzzleOrder.get(i)))
                    break;
                if ((i == solvedPuzzleOrder.size() - 1) && currentPuzzleOrder.get(i).isEmpty())
                    return true;
            }
        }
        return false;
    }

    private boolean moveTile(int pressedRow, int pressedCol) {
        if (isValidMove(pressedRow, pressedCol)) {
            String s = gameBoard.getTiles()[pressedRow][pressedCol].button().getText();
            gameBoard.getTiles()[emptyTileRow][emptyTileCol].button().setText(s);
            gameBoard.getTiles()[emptyTileRow][emptyTileCol].button().setEnabled(true);
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
