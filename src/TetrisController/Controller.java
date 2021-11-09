package TetrisController;

import TetrisAI.AutoListener;
import TetrisAI.Responder;
import TetrisCommon.*;
import TetrisModel.GameModel;
import TetrisView.EndView;
import TetrisView.GameView;
import TetrisView.HighScores;
import TetrisView.StartView;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Controller extends JPanel implements Runnable {
    public int width = StartView.WIDTH;
    public int height = StartView.HEIGHT;

    private BufferedImage image;
    private Graphics2D g;

    private boolean isRun = true;
    private Thread thread;

    private static StartView startView;
    private static GameView gameView;
    private static HighScores topScores;
    private static EndView endView;
    private static GameModel gameModel;
    public static int currentScore = 0;
    private static int fontSize = 20;
    private int waitTime = 450;
    private List<AutoListener> listeners = new ArrayList<AutoListener>();

    Font largeFont = new Font("TimesRoman", Font.PLAIN, fontSize);

    public static STATE state = STATE.STARTVIEW;

    public Controller() {
        super();
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus(true);
    }

    public void addNotify(){
        super.addNotify();
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
    }

    public void init(){
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        this.gameModel = new GameModel();
        startView = new StartView();
        gameView = new GameView(gameModel);
        topScores = new HighScores();
        endView = new EndView();
        startView.init();
        Score.init();

        this.addMouseListener(new MouseHandler(startView, gameView, topScores, endView, gameModel));
        this.addMouseMotionListener(new MouseMotion(startView, gameView, topScores, endView, gameModel));
        this.addKeyListener(new KeyHandler(gameModel));
        System.out.println("add new ...");
        listeners.add(new Responder(gameModel));
    }

    @Override
    public void run() {
        init();
        int i = 0;
        while(isRun){
            render();
            try{
                Thread.sleep(waitTime);
                tick();
                for (AutoListener hl : listeners)
                    hl.autoPlay();
            }catch(Exception e){

            }
        }
    }

    public void tick(){
        if(state == STATE.GAMEVIEW){
            gameModel.tick();
        }
    }

    public void render(){
        switch(state){
            case STARTVIEW:
                startView.render(g);
                break;
            case GAMEVIEW:
                gameView.render(g);
                break;
            case TOPSCORES:
                topScores.render(g);
                break;
            case ENDVIEW:
                endView.render(g);
        }

        Graphics anotherG = getGraphics();
        anotherG.drawImage(image, 0, 0, null);
        anotherG.dispose();
    }

    public static void switchState(STATE gameState){
        state = gameState;
        switch (state){
            case STARTVIEW:
                startView.init();
                break;
            case GAMEVIEW:
                gameModel.start();
                break;
            case TOPSCORES:
                topScores.init();
                break;
            case ENDVIEW:
                endView.init();

        }
    }
}
