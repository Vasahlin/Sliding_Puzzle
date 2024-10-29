import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class GameLogicTest {
    GameBoard gb = new GameBoard(3,3);

    @Test
    void getCurrentPuzzleOrderTest() {
        int emptyCol = gb.getGameLogic().getEmptyTileCol();
        int emptyRow = gb.getGameLogic().getEmptyTileRow();

        int moveCol = emptyCol;
        int moveRow = emptyRow + 1;

        int moveCol2 = emptyCol -1;
        int moveRow2 = emptyRow;

        int falseMoveCol = emptyCol + 2;
        int falseMoveRow = emptyRow + 2;
        int falseMoveCol2 = emptyCol - 2;
        int falseMoveRow2 = emptyRow - 2;

        assertTrue(gb.getGameLogic().isValidMove(moveRow, moveCol));
        assertTrue(gb.getGameLogic().isValidMove(moveRow2, moveCol2));
        assertFalse(gb.getGameLogic().isValidMove(moveRow, falseMoveCol));
        assertFalse(gb.getGameLogic().isValidMove(falseMoveRow, moveCol));
        assertFalse(gb.getGameLogic().isValidMove(falseMoveRow2, moveCol2));
        assertFalse(gb.getGameLogic().isValidMove(moveRow, falseMoveCol2));
    }

  
}