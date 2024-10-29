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

    protected void lockGameState() {
        Tile[][] tiles = gameBoard.getTiles();
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                tiles[row][col].button().setEnabled(false);
            }
        }
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

    protected void createShowcase() {
        resetEmptyTile(gameBoard.getTiles());
        int index = 0;
        for (int row = 0; row < gameBoard.getTiles().length; row++) {
            for (int col = 0; col < gameBoard.getTiles()[row].length; col++) {
                String s = solvedPuzzleOrder.get(index++);
                gameBoard.getTiles()[row][col].button().setText(s);
            }
        }
    }

    protected void resetEmptyTile(Tile[][] tiles) {
        tiles[emptyTileRow][emptyTileCol].button().setEnabled(true);
        tiles[tiles.length-1][tiles[0].length-1].button().setEnabled(false);
        setEmptyTile(tiles.length-1, tiles[0].length-1);
    }
}
