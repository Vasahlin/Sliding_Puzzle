import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    public GameWindow(boolean showCase) {
        GameBoard gb = new GameBoard(3,3);
        setLayout(new BorderLayout());
        if (showCase) {
            add(gb.createGameBoard(true), BorderLayout.CENTER);
        } else {
            add(gb.createGameBoard(false), BorderLayout.CENTER);
        }
        MoveCounter mc = new MoveCounter(this);
        gb.getGameLogic().setMoveCountListener(mc);
        add(mc.movePanel(), BorderLayout.NORTH);
        add(gb.createSouthPanel(), BorderLayout.SOUTH);
        pack();
        setTitle("Sliding Puzzle");
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            new GameWindow(false);
        } else {
            new GameWindow(true);
        }
    }
}