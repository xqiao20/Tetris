package TetrisController;

import TetrisModel.BasicModel;
import TetrisView.STATE;
import TetrisView.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    BasicModel model;

    public KeyHandler(BasicModel model){
        this.model = model;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(Controller.state == STATE.GAME && !model.isLost){
            if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
                model.rotatePiece();
            }
            else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A){
                model.moveLeft();
            }
            else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
                model.moveRight();
            }
            else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
                model.updateBlockPosition(true, true);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
