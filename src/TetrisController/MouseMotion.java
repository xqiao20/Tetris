package TetrisController;

import TetrisModel.BasicModel;
import TetrisView.EndView;
import TetrisView.Game;
import TetrisView.HighScores;
import TetrisView.StartView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotion implements MouseMotionListener {
    BasicModel model;
    public MouseMotion(BasicModel model){
        this.model = model;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (Controller.state){
            case STARTVIEW:
                model.startView.mousePosition.x = e.getX();
                model.startView.mousePosition.y = e.getY();
                break;
            case GAME:
                model.game.mousePosition.x = e.getX();
                model.game.mousePosition.y = e.getY();
                break;
            case TOPSCORES:
                model.topScores.mousePosition.x = e.getX();
                model.topScores.mousePosition.y = e.getY();
                break;
            case ENDVIEW:
                model.endView.mousePosition.x = e.getX();
                model.endView.mousePosition.y = e.getY();
        }
    }
}
