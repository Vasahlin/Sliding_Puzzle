import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        GameModel model = new GameModel(2,2,true);
        GameView view = new GameView(model);
        GameController controller = new GameController(model, view);

        JFrame frame = new JFrame("Puzzle Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.pack();
        frame.setVisible(true);
    }
}
