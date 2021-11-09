package TetrisController;

import TetrisModel.GameModel;
import TetrisView.EndView;
import TetrisView.GameView;
import TetrisView.HighScores;
import TetrisView.StartView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    StartView startView;
    GameView gameView;
    HighScores topScores;
    EndView endView;
    GameModel gameModel;

    public MouseHandler(StartView startView, GameView gameView, HighScores topScores, EndView endView, GameModel gameModel){
        this.startView = startView;
        this.gameView = gameView;
        this.topScores = topScores;
        this.endView = endView;
        this.gameModel = gameModel;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (Controller.state){
            case STARTVIEW:
                if(startView.buttons[0].contains(e.getPoint())){
                    startView.isRun = false;
                    startView.playGame();
                }
                else if(startView.buttons[1].contains(e.getPoint())){
                    startView.isRun = false;
                    startView.goBack();
                }
                else if(startView.buttons[2].contains(e.getPoint())){
                    startView.darkMode = !startView.darkMode;
                    gameModel.darkMode = !gameModel.darkMode;
                }
                else if(startView.buttons[3].contains(e.getPoint())){
                    startView.isRun = false;
                    startView.visitSite();
                }
                break;
            case GAMEVIEW:
                if(gameView.buttons[0].contains(e.getPoint())){
                    gameModel.clearGame();
                }
                else if(gameView.buttons[1].contains(e.getPoint())){
                    gameModel.autoplay = !gameModel.autoplay;
                }
                else if(gameView.buttons[2].contains(e.getPoint())){
                    gameModel.showGuide = !gameModel.showGuide;
                }
                else if(gameView.buttons[3].contains(e.getPoint())){
                    gameModel.isRun = false;
                    gameModel.goBack();
                }
                break;
            case TOPSCORES:
                if(topScores.button.contains(e.getPoint())){
                    topScores.goBack();
                }
                break;
            case ENDVIEW:
                if(endView.button.contains(e.getPoint())){
                    endView.goBack();
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
