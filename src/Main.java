import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        GameModel model;
        if (args.length == 0) {
            model = new GameModel(4,4,false);
        } else {
            model = new GameModel(4,4,true);
        }
        GameView view = new GameView(
                model.getTiles(), model.getEmptyTileRow(), model.getEmptyTileCol(), model.getMoveCount());
        new GameController(model, view);

        JFrame frame = new JFrame("Sliding Puzzle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
