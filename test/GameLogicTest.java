import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class GameLogicTest {
    GameBoard gb = new GameBoard(3,3);


    @Test
    void isValidMoveTest() {

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

    @Test
    void createShuffledValuesTest() {
        List<String> shuffledOrder = gb.createShuffledValues();
        List<String> correctOrder = gb.getGameLogic().getSolvedPuzzleOrder();

        assertNotEquals(correctOrder.getFirst(), shuffledOrder.getFirst());
        assertNotEquals(correctOrder.getLast(), shuffledOrder.getLast());
        assertEquals(correctOrder.size() + 1, shuffledOrder.size());
    }

    @Test
    void isPuzzleSolvedTest() {
        JPanel p = gb.createGameBoard();
        assertFalse(gb.getGameLogic().isPuzzleSolved(new ArrayList<>(Arrays.asList("5", "6"))));
        assertFalse(gb.getGameLogic().isPuzzleSolved(new ArrayList<>(Arrays.asList("a", "2"))));
        assertTrue(gb.getGameLogic().isPuzzleSolved(gb.getGameLogic().getSolvedPuzzleOrder()));
    }

  
}