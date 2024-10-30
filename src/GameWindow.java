import listeners.PuzzleSolvedListener;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame implements PuzzleSolvedListener {
    protected JLabel winMessage = new JLabel();
    protected GameBoard gameBoard;

    public GameWindow(boolean showCase) {
        gameBoard = new GameBoard(3,3);
        GameLogic logic = gameBoard.getGameLogic();
        setLayout(new BorderLayout());

        MoveCounter moveCounter = new MoveCounter(this, logic);
        logic.setMoveCountListener(moveCounter);
        logic.setPuzzleSolvedListener(this);

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

    private JPanel features(GameBoard gb, MoveCounter moveCounter) {
        JPanel features = new JPanel(new BorderLayout());

        JPanel winPanel = new JPanel(new GridLayout(1,3));
        winPanel.add(Box.createRigidArea(new Dimension(20,20)),0);
        winPanel.add(winMessage, 1);
        winPanel.add(Box.createRigidArea(new Dimension(20,20)),2);
        features.add(winPanel, BorderLayout.NORTH);

        ShuffleButton sb = new ShuffleButton(gb,this, moveCounter,
                gameBoard.getGameLogic().getPuzzleSolvedListener());
        gb.getGameLogic().setShuffleButtonListener(sb);
        features.add(sb.shuffleButton(), BorderLayout.CENTER);

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

    @Override
    public void onPuzzleSolved() {
        winMessage.setText("*** You win! ***");
        revalidate();
        repaint();
    }

    @Override
    public void onPuzzleNotSolved() {
        winMessage.setText("");
        revalidate();
        repaint();
    }
}