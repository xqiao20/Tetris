package TetrisController;

import TetrisModel.BasicModel;
import TetrisView.STATE;
import TetrisModel.Score;
import TetrisView.EndView;
import TetrisView.Game;
import TetrisView.HighScores;
import TetrisView.StartView;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Controller extends JPanel implements Runnable {
    public int width = StartView.WIDTH;
    public int height = StartView.HEIGHT;

    private BufferedImage image;
    private Graphics2D g;

    private boolean isRun = true;
    private Thread thread;

    private static StartView startView;
    private static Game game;
    private static HighScores topScores;
    private static EndView endView;
    public BasicModel model;

    public static int currentScore = 0;
    private static int fontSize = 20;
    private int waitTime = 150;

    Font largeFont = new Font("TimesRoman", Font.PLAIN, fontSize);

    public static STATE state = STATE.STARTVIEW;

    public Controller() {
        super();
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus(true);
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        startView = new StartView();
        game = new Game();
        topScores = new HighScores();
        endView = new EndView();
        model = new BasicModel();

        this.addMouseListener(new MouseHandler(model));
        this.addMouseMotionListener(new MouseMotion(model));
        this.addKeyListener(new KeyHandler(model));
    }

    public void addNotify(){
        super.addNotify();
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
    }


    @Override
    public void run() {
        new Controller();
        while(isRun){
            render();
            try{
                Thread.sleep(waitTime);
                tick();
            }catch(Exception e){

            }
        }
    }

    public void tick(){
        if(state == STATE.GAME){
            model.stateCheck();
        }
    }

    public void updateScore(){
        if(model.gameOver()){
            currentScore = model.player.getScore();
            model.switchState(STATE.ENDVIEW);
        }
    }
    public void render(){
        switch(state){
            case STARTVIEW:
                startView.render(g, model.gameState, model.player);
                break;
            case GAME:
                game.render(g, model.gameState, model.player);
                break;
            case TOPSCORES:
                topScores.render(g, model.gameState, model.player);
                break;
            case ENDVIEW:
                endView.render(g, model.gameState, model.player);
        }

        Graphics anotherG = getGraphics();
        anotherG.drawImage(image, 0, 0, null);
        anotherG.dispose();
    }


    private int speed = 1100;
    private int timeGap = 100;


}
