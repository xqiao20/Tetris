package TetrisController;

import TetrisController.Controller;
import TetrisModel.GameModel;
import TetrisView.EndView;
import TetrisView.GameView;
import TetrisView.HighScores;
import TetrisView.StartView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotion implements MouseMotionListener {
    StartView startView;
    GameView gameView;
    HighScores topScores;
    EndView endView;
    GameModel gameModel;

    public MouseMotion(StartView startView, GameView gameView, HighScores topScores, EndView endView, GameModel gameModel){
        this.startView = startView;
        this.gameView = gameView;
        this.topScores = topScores;
        this.endView = endView;
        this.gameModel = gameModel;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (Controller.state){
            case STARTVIEW:
                startView.mousePosition.x = e.getX();
                startView.mousePosition.y = e.getY();
                break;
            case GAMEVIEW:
                gameView.mousePosition.x = e.getX();
                gameView.mousePosition.y = e.getY();
                break;
            case TOPSCORES:
                topScores.mousePosition.x = e.getX();
                topScores.mousePosition.y = e.getY();
                break;
            case ENDVIEW:
                endView.mousePosition.x = e.getX();
                endView.mousePosition.y = e.getY();

        }
    }
}
