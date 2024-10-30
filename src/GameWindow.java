import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    protected JLabel winMessage = new JLabel();
    GameBoard gb;

    public GameWindow(boolean showCase) {
        gb = new GameBoard(3,3);
        setLayout(new BorderLayout());
        if (showCase) {
            add(gb.createGameBoard(true), BorderLayout.CENTER);
        } else {
            add(gb.createGameBoard(false), BorderLayout.CENTER);
        }
        MoveCounter mc = new MoveCounter(this, gb.getGameLogic());
        gb.getGameLogic().setMoveCountListener(mc);
        add(mc.movePanel(), BorderLayout.NORTH);

        JPanel southPanel = new JPanel(new BorderLayout());
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(_ -> System.exit(0));
        southPanel.add(winMessage, BorderLayout.NORTH);
        ShuffleButton sb = new ShuffleButton(gb,this);
        gb.getGameLogic().setShuffleButtonListener(sb);
        southPanel.add(sb.shuffleButton(mc), BorderLayout.CENTER);
        southPanel.add(exitButton, BorderLayout.SOUTH);
        add(southPanel, BorderLayout.SOUTH);

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