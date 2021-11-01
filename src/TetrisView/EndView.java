package TetrisView;

import TetrisController.Controller;
import TetrisModel.Player;
import TetrisModel.Score;

import java.awt.*;

public class EndView implements ViewInterface {
    public Point mousePosition;
    private int width = StartView.WIDTH;
    private int height = StartView.HEIGHT;
    private int stringY = 175;
    private int scoreY = 200;
    private int arcSize = 25;
    private int buttonWidth = 200;
    private int buttonHeight = 50;
    private int buttonX = 200;
    private int buttonY = 300;
    public Rectangle button = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
    public boolean darkMode;
    private Color[] backgroundColor = {new Color(245,222,179), Color.black};
    private Color stringColor = new Color(1,138,138) ;
    private Color buttonStringColor = new Color(188,143,143);


    public EndView(){
        mousePosition = new Point(0, 0);
    }


    @Override
    public void render(Graphics g, int[][] gameState, Player player) {
        int dark = darkMode ? 1: 0;
        g.setColor(backgroundColor[dark]);
        g.fillRect(0, 0, width, height);

        g.setColor(stringColor);
        g.drawString("You Lost the Game", width/2 - g.getFontMetrics().stringWidth("You Lost the Game")/2 , stringY);
        g.drawString("Score: " + Controller.currentScore, width/2 - g.getFontMetrics().stringWidth("Score: " + Controller.currentScore)/2, scoreY);

        g.setColor(Color.white);
        if(button.contains(mousePosition)){
            g.fillRoundRect(button.x, button.y, button.width, button.height, arcSize, arcSize);
        }

        g.setColor(Color.pink);
        g.drawRoundRect(button.x, button.y, button.width, button.height, arcSize, arcSize);

        g.setColor(buttonStringColor);
        g.drawString("Main Menu", width/2 - g.getFontMetrics().stringWidth("Main Menu")/2, button.y + buttonHeight*2/3);
    }


    public void checkScore(){
        int[] score = Score.readFile(Score.TOPSCOREPATH);
        for(int i = 0; i < score.length; i++){
            if(score[i] < Controller.currentScore){
                for(int j = score.length - 1; j > i; j--){
                    score[j] = score[j-1];
                }
                score[i] = Controller.currentScore;
                Score.saveScores(score);
                break;
            }
        }
    }
}
