package TetrisModel;
import TetrisController.Controller;
import TetrisView.StartView;
import javax.swing.JFrame;

public class Play {
    static JFrame window = new JFrame();
    public static void main(String[] args) {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new Controller());
        window.pack();
        window.setSize(StartView.WIDTH, StartView.HEIGHT);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setTitle("Tetris in CS5004");
        window.setVisible(true);
    }
}
