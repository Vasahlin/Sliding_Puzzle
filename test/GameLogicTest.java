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

        //dålig assertion..
        assertFalse((correctOrder.getFirst() == shuffledOrder.getFirst()) && (correctOrder.getLast() == shuffledOrder.getLast()));
        assertEquals(correctOrder.size(), shuffledOrder.size());
    }

    @Test
    void isPuzzleSolvedTest() {
        JPanel p = gb.createGameBoard();
        assertFalse(gb.getGameLogic().isPuzzleSolved(new ArrayList<>(Arrays.asList("5", "6"))));
        assertFalse(gb.getGameLogic().isPuzzleSolved(new ArrayList<>(Arrays.asList("a", "2"))));
        assertTrue(gb.getGameLogic().isPuzzleSolved(gb.getGameLogic().getSolvedPuzzleOrder()));
    }

    @Test
    void moveTileTest() {
        JPanel p = gb.createGameBoard();

        int emptyCol = gb.getGameLogic().getEmptyTileCol();
        int emptyRow = gb.getGameLogic().getEmptyTileRow();

        int moveCol = emptyCol;
        int falseMoveCol = 0;
        switch (emptyCol) {
            case 0: falseMoveCol = 2; break;
            case 1: falseMoveCol = 0; break;
            case 2: falseMoveCol = 0; break;
        }
        int moveRow = 0;
        switch (emptyRow) {
            case 0: moveRow = 1; break;
            case 1: moveRow = 0; break;
            case 2: moveRow = 1; break;
        }

        String s = gb.getTiles()[moveRow][moveCol].button().getText();
        String s2 = gb.getTiles()[emptyRow][emptyCol].button().getText();

        assertNotEquals(s, s2);
        assertFalse(gb.getGameLogic().moveTile(moveRow, falseMoveCol));
        assertTrue(gb.getGameLogic().moveTile(moveRow, moveCol));
        s = gb.getTiles()[moveRow][moveCol].button().getText();
        assertEquals(s, s2);
        s2 = gb.getTiles()[emptyRow][emptyCol].button().getText();
        assertNotEquals(s, s2);
        int newEmptyRow = gb.getGameLogic().getEmptyTileRow();
        int newEmptyCol = gb.getGameLogic().getEmptyTileCol();
        assertNotEquals(newEmptyRow, emptyRow);
        assertEquals(newEmptyCol, emptyCol);
    }

    @Test
    void lockGameStateTest() {
        JPanel p = gb.createGameBoard();

        gb.getTiles()[0][0].button().setEnabled(true);
        assertTrue(gb.getTiles()[0][0].button().isEnabled());
        gb.getGameLogic().lockGameState();
        assertFalse(gb.getTiles()[0][0].button().isEnabled());
    }

  
}