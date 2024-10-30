import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class GameLogicTest {
    GameBoard gb = new GameBoard(3, 3);


    @Test
    void isValidMoveTest() {

        int emptyCol = gb.getGameLogic().getEmptyTileCol();
        int emptyRow = gb.getGameLogic().getEmptyTileRow();

        int moveRow = emptyRow + 1;

        int moveCol2 = emptyCol - 1;

        int falseMoveCol = emptyCol + 2;
        int falseMoveRow = emptyRow + 2;
        int falseMoveCol2 = emptyCol - 2;
        int falseMoveRow2 = emptyRow - 2;

        assertTrue(gb.getGameLogic().isValidMove(moveRow, emptyCol));
        assertTrue(gb.getGameLogic().isValidMove(emptyRow, moveCol2));
        assertFalse(gb.getGameLogic().isValidMove(moveRow, falseMoveCol));
        assertFalse(gb.getGameLogic().isValidMove(falseMoveRow, emptyCol));
        assertFalse(gb.getGameLogic().isValidMove(falseMoveRow2, moveCol2));
        assertFalse(gb.getGameLogic().isValidMove(moveRow, falseMoveCol2));
    }

    @Test
    void createShuffledValuesTest() {
        List<String> shuffledOrder = gb.createShuffledValues();
        List<String> correctOrder = gb.getGameLogic().getSolvedPuzzleOrder();

        //dålig assertion..
        assertFalse((Objects.equals(correctOrder.getFirst(),
                shuffledOrder.getFirst())) && (Objects.equals(correctOrder.getLast(), shuffledOrder.getLast())));
        assertEquals(correctOrder.size(), shuffledOrder.size());
    }

    @Test
    void isPuzzleSolvedTest() {
        JPanel p = gb.createGameBoard(true);
        assertTrue(gb.getGameLogic().isPuzzleSolved());
    }

    @Test
    void moveTileTest() {
        JPanel p = gb.createGameBoard(false);

        int emptyCol = gb.getGameLogic().getEmptyTileCol();
        int emptyRow = gb.getGameLogic().getEmptyTileRow();

        int falseMoveCol = switch (emptyCol) {
            case 0 -> 2;
            default -> 0;
        };
        int moveRow = switch (emptyRow) {
            case 0, 2 -> 1;
            default -> 0;
        };

        String s = gb.getTiles()[moveRow][emptyCol].button().getText();
        String s2 = gb.getTiles()[emptyRow][emptyCol].button().getText();

        assertNotEquals(s, s2);
        assertFalse(gb.getGameLogic().moveTile(moveRow, falseMoveCol));
        assertTrue(gb.getGameLogic().moveTile(moveRow, emptyCol));
        s = gb.getTiles()[moveRow][emptyCol].button().getText();
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
        JPanel p = gb.createGameBoard(false);

        gb.getTiles()[0][0].button().setEnabled(true);
        assertTrue(gb.getTiles()[0][0].button().isEnabled());
        gb.getGameLogic().lockGameState();
        assertFalse(gb.getTiles()[0][0].button().isEnabled());
    }


}