import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        GameModel model;
        if (args.length == 0) {
            model = new GameModel(3,3,false);
        } else {
            model = new GameModel(3,3,true);
        }

        GameView view = new GameView(model);
        new GameController(model, view);

        JFrame frame = new JFrame("Sliding Puzzle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
