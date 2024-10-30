import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    protected JLabel winMessage = new JLabel();
    protected GameBoard gameBoard;

    public GameWindow(boolean showCase) {
        gameBoard = new GameBoard(3,3);
        setLayout(new BorderLayout());

        MoveCounter moveCounter = new MoveCounter(this, gameBoard.getGameLogic());
        gameBoard.getGameLogic().setMoveCountListener(moveCounter);
        add(moveCounter.movePanel(), BorderLayout.NORTH);

        if (showCase) {
            add(gameBoard.createGameBoard(true), BorderLayout.CENTER);
        } else {
            add(gameBoard.createGameBoard(false), BorderLayout.CENTER);
        }

        add(features(gameBoard, moveCounter), BorderLayout.SOUTH);

        pack();
        setTitle("Sliding Puzzle");
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JPanel features(GameBoard gb, MoveCounter mc) {
        JPanel features = new JPanel(new BorderLayout());
        features.add(winMessage, BorderLayout.NORTH);

        ShuffleButton sb = new ShuffleButton(gb,this);
        gb.getGameLogic().setShuffleButtonListener(sb);
        features.add(sb.shuffleButton(mc), BorderLayout.CENTER);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(_ -> System.exit(0));
        features.add(exitButton, BorderLayout.SOUTH);
        return features;
    }


    public static void main(String[] args) {
        if (args.length == 0) {
            new GameWindow(false);
        } else {
            new GameWindow(true);
        }
    }
}