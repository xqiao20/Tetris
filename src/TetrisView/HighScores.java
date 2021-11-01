package TetrisView;

import TetrisController.Controller;
import TetrisModel.Player;
import TetrisModel.Score;

import java.awt.*;

public class HighScores implements ViewInterface {
    public Rectangle button;
    public Point mousePosition;
    public int top = 5;
    public int[] scores = new int[top];

    private int width = StartView.WIDTH;
    private int height = StartView.HEIGHT;
    private int widthRect = StartView.WIDTHRECT;
    private int heightRect = StartView.HEIGHTRECT;
    private int buttonX = 100;
    private int buttonY = 50;
    private int arcSize = 25;
    private int stringY = 100;
    private int scoresX = 50;
    private int scoresY = 150;
    private int backY = 32;
    private int gap = 40;
    public boolean darkMode;
    private Color[] backgroundColor = {new Color(245,222,179), Color.black};
    private Color[] squareColor = {new Color(120, 160, 120), new Color(30, 100, 100)};
    private Color buttonStringColor = new Color(1, 128, 128);


    public void init(){
        mousePosition = new Point(0, 0);
        button = new Rectangle((width - widthRect)/2, height - heightRect * 2, widthRect, heightRect);
        scores = Score.readFile(Score.TOPSCOREPATH);
    }


    @Override
    public void render(Graphics g, int[][] gameState, Player player){
        int dark = darkMode ? 1: 0;
        g.setColor(backgroundColor[dark]);
        g.fillRect(0, 0, width, height);

        g.setColor(squareColor[dark]);
        g.fillRoundRect(buttonX, buttonY, width/3*2, height/3*2, arcSize, arcSize);

        g.setColor(Color.white);
        g.drawString("Scores", width/2 - g.getFontMetrics().stringWidth("Scores")/2, stringY);

        for(int i = 0; i < scores.length; i++){
            g.drawString("" + scores[i], width/2 - scoresX, scoresY + i*gap);
        }

        if(button.contains(mousePosition)){
            g.fillRoundRect(button.x, button.y, button.width, button.height, arcSize, arcSize);
        }
        g.setColor(buttonStringColor);
        g.drawString("Back", button.x + button.x/2 - g.getFontMetrics().stringWidth("Back")/2, button.y + backY);

        g.setColor(Color.pink);
        g.drawRoundRect(button.x, button.y, button.width, button.height, arcSize, arcSize);
    }


}
