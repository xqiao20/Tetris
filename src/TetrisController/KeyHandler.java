package TetrisController;

import TetrisCommon.STATE;
import TetrisModel.GameModel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GameModel gameModel;

    public KeyHandler(GameModel gameModel){
        this.gameModel = gameModel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(Controller.state == STATE.GAMEVIEW && !gameModel.isLost && !gameModel.autoplay){
            if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
                gameModel.rotatePiece();
            }
            else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A){
                gameModel.moveLeft();
            }
            else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
                gameModel.moveRight();
            }
            else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
                gameModel.updateBlockPosition(true, true);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
