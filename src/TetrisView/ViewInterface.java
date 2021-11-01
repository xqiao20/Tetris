package TetrisView;

import TetrisModel.Player;

import java.awt.*;

public interface ViewInterface {
    void render(Graphics g, int[][] gameState, Player player);
}
