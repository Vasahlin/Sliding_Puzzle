import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    public GameWindow() {
        GameBoard gb = new GameBoard(3,3);
        setLayout(new BorderLayout());
        add(gb.createGameBoard(), BorderLayout.CENTER);
        //gb.getGameLogic().createShowcase();
        pack();
        setTitle("Sliding Puzzle");
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new GameWindow();
    }
}