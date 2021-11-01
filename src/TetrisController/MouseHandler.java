package TetrisController;

import TetrisModel.BasicModel;
import TetrisView.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    public BasicModel model;
    public MouseHandler(BasicModel model){
        this.model = model;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (model.state){
            case STARTVIEW:
                if(model.startView.buttons[0].contains(e.getPoint())){
                    model.isRun = false;
                    model.switchState(STATE.GAME);
                }
                else if(model.startView.buttons[1].contains(e.getPoint())){
                    model.startView.isRun = false;
                    model.switchState(STATE.TOPSCORES);
                }
                else if(model.startView.buttons[2].contains(e.getPoint())){
                    model.darkMode = !model.darkMode;
                }
                else if(model.startView.buttons[3].contains(e.getPoint())){
                    model.isRun = false;
                    model.startView.visitSite();
                }
                break;
            case GAME:
                if(model.game.buttons[0].contains(e.getPoint())){
                    model.clearGame();
                }
                else if(model.game.buttons[1].contains(e.getPoint())){
                    model.game.showGuide = !model.game.showGuide;
                }
                else if(model.game.buttons[2].contains(e.getPoint())){
                    model.isRun = false;
                    model.switchState(STATE.STARTVIEW);
                }
                break;
            case TOPSCORES:
                if(model.topScores.button.contains(e.getPoint())){
                    model.switchState(STATE.STARTVIEW);
                }
                break;
            case ENDVIEW:
                if(model.endView.button.contains(e.getPoint())){
                    model.switchState(STATE.STARTVIEW);
                }
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
