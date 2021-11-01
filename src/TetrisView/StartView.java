package TetrisView;

import TetrisController.*;
import TetrisModel.Player;
import TetrisModel.Shape;
import TetrisModel.Piece;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

public class StartView extends JPanel implements ViewInterface {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 500;

    public static final int WIDTHRECT = 200;
    public static final int HEIGHTRECT = 50;

    private int squareX = 100;
    private int squareY = 60;

    private int logoX = 400;
    private int logoY = 400;
    private int logoWidth = 130;
    private int logoHeight = 80;

    private int tetrisWidth = 160;
    private int tetrisHeight = 105;
    private int tetrisX  = (WIDTH - tetrisWidth)/2;
    private int tetrisY =  20;

    private int pieceStartY = -50;
    private int startY = 140;
    private int gap = 60;
    public boolean darkMode;

    Font font = new Font("sansserif", Font.BOLD, 22);
    Font smallFont = new Font("sansserif", Font.PLAIN, 16);


    public Rectangle[] buttons = {new Rectangle((WIDTH - WIDTHRECT)/2, startY, WIDTHRECT, HEIGHTRECT),
            new Rectangle((WIDTH - WIDTHRECT)/2, startY + gap, WIDTHRECT, HEIGHTRECT),
            new Rectangle((WIDTH - WIDTHRECT)/2, startY + 2*gap, WIDTHRECT, HEIGHTRECT),
            new Rectangle((WIDTH - WIDTHRECT)/2, startY + 3*gap, WIDTHRECT, HEIGHTRECT)};
    private Piece[] pieces;
    public boolean isRun;
    public Point mousePosition;
    private ImageLoader square;

    public Thread thread;
    private Random random;

    private int spawnCount = 0;
    private int paddingWidth = 20;
    private int speed = 300;
    private int arcSize = 25;
    private int pieceNumber = 20;
    private int cellSize = 25;
    private Color[] backgroundColor = {new Color(245,222,179), Color.darkGray};
    private Color[] squareColor = {new Color(173,216,230, 180),new Color(30, 100, 100, 180) };
    private Color buttonColor = new Color(33,178,170);
    private Color stringColor = new Color(31, 144, 255);


    public StartView(){
        mousePosition = new Point(0, 0);
        square = new ImageLoader(ImageLoader.squarePath);
        init();
    }


    public int center(Graphics g, String text){
        return WIDTH/2 - g.getFontMetrics().stringWidth(text)/2;
    }

    public void init() {
        random = new Random();
        isRun = true;
        pieces = new Piece[pieceNumber];
        thread = new Thread(new Clock());
        thread.start();
    }

    //TODO
    public void tick() {
        int chance;
        chance = random.nextInt(3);
        if(chance == 0) {
            spawnCount %= pieceNumber;
            pieces[spawnCount] = new Piece(random.nextInt(pieceNumber + 1)*cellSize, pieceStartY, Shape.generator());
            spawnCount++;
        }
        for(int i = 0; i < pieces.length; i++) {
            if (pieces[i] != null) {
                pieces[i].setY(pieces[i].getY() + cellSize);
            }
        }
    }

    public void drawButton(Graphics g){
        g.setColor(Color.white);
        for(int i = 0; i < buttons.length; i++) {
            if(buttons[i].contains(mousePosition)) {
                g.fillRoundRect(buttons[i].x, buttons[i].y, buttons[i].width, buttons[i].height, arcSize, arcSize);
            }
        }
        g.setColor(buttonColor);
        for(int i = 0; i < buttons.length; i++) {
            g.drawRoundRect(buttons[i].x, buttons[i].y, buttons[i].width, buttons[i].height, arcSize, arcSize);
        }
    }

    public void drawString(Graphics g) {
        g.setColor(stringColor);
        g.setFont(font);
        g.drawString("New Game", center(g, "New Game"), buttons[0].y + paddingWidth*5/3);
        g.drawString("High Score", center(g, "High Score"), buttons[1].y + paddingWidth*5/3);
        g.drawString("Themes", center(g, "Themes" ), buttons[2].y + paddingWidth*5/3);
        g.drawString("Github", center(g, "Github"), buttons[3].y + paddingWidth*5/3);
    }

    public void drawBackground(Graphics g){
        int dark = darkMode ? 1: 0;
        g.setColor(backgroundColor[dark]);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        int xPos;
        int yPos;
        for(int i = 0; i < pieces.length; i++) {
            if(pieces[i] != null) {
                for(int j = 0; j < pieces[i].getShape().length; j++) {
                    for(int k = 0; k < pieces[i].getShape()[j].length; k++) {
                        if(pieces[i].getShape()[j][k] != 0) {
                            xPos = pieces[i].getX()+k*cellSize;
                            yPos = pieces[i].getY()+j*cellSize;
                            g.drawImage(square.getSubImage(Shape.getColor(pieces[i].getShape())), xPos, yPos, cellSize, cellSize, null);
                        }
                    }
                }
            }
        }
        g.setColor(squareColor[dark]);
        g.fillRoundRect(squareX, squareY, WIDTH/3*2, HEIGHT/7*5, arcSize, arcSize);
        g.drawImage(new ImageLoader(ImageLoader.tetrisPath).getImage(), tetrisX, tetrisY, tetrisWidth, tetrisHeight, null);
        g.setFont(smallFont);
        g.setColor(Color.orange);
        g.drawImage(new ImageLoader(ImageLoader.logoPath).getImage(), logoX, logoY, logoWidth, logoHeight, null);
        g.setColor(Color.white);
        g.setFont(font);
    }
    @Override
    public void render(Graphics g, int[][] gameState, Player player) {
        drawBackground(g);
        drawButton(g);
        drawString(g);

    }

    public void visitSite(){
        try{
            Desktop desktop = Desktop.getDesktop();
            URI url = new URI("https://github.ccs.neu.edu/Xiaoqing/CS5004");
            desktop.browse(url);
        }
        catch (IOException | URISyntaxException e){
            e.printStackTrace();
        }
    }

    private class Clock implements Runnable {
        @Override
        public void run() {
            while(isRun){
                tick();
                try{
                    Thread.sleep(speed);
                }
                catch(InterruptedException e){
                    break;
                }
            }
        }
    }
}
