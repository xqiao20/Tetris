package TetrisView;

import TetrisCommon.*;
import TetrisCommon.Shape;
import TetrisController.Controller;
import TetrisModel.GameModel;

import java.awt.*;

public class GameView implements ViewInterface{
    private int width = StartView.WIDTH;
    private int height = StartView.HEIGHT;
    private int buttonWidth = StartView.WIDTHRECT;
    private int buttonHeight = StartView.HEIGHTRECT;

    private int cellSize = 25;
    private int lineSize = 5;
    private int arcSize = 25;

    private int buttonX = width/3 + 160;
    private int buttonYStart = 165;
    private int buttonGap = 65;


    public Rectangle[] buttons = {new Rectangle(buttonX, buttonYStart, buttonWidth, buttonHeight),
            new Rectangle(buttonX, buttonYStart + buttonGap, buttonWidth, buttonHeight),
            new Rectangle(buttonX, buttonYStart + 2*buttonGap, buttonWidth, buttonHeight),
            new Rectangle(buttonX, buttonYStart + 3*buttonGap, buttonWidth, buttonHeight)
    };

    private ImageLoader square;

    public Point mousePosition;


    private Color[] backgroundColor = {new Color(245,222,179), Color.black};
    private Color[] gridColor = {Color.white, new Color(30, 100, 100)};
    private Color[] scoreColor = {new Color(255, 238, 244), Color.darkGray};
    private Color[] guidelineColor = {new Color(10,25,250), Color.white};
    private Color[] gridLineColor = {new Color(255,228,225), Color.darkGray};

    private Color buttonColor = new Color(147,112,219);
    private Color stringColor = new Color(135, 206, 235);
    private Color nextStringColor = new Color(120, 100, 150);


    private int buttonStringX = width/3 + 200;
    private int buttonStringY = 200;


    private int scoreStringX = width / 3 + 170;
    private int scoreStringY = 480;
    private int scoreGap = 35;


    private int scoreX = width/3+150;
    private int scoreY = 150;
    private int scoreWidth = 220;
    private int scoreHeight = 275;

    private int guide_gap = 0;

    private GameModel gameModel;
    Font font = new Font("sansserif", Font.BOLD, 22);
    public GameView(GameModel gameModel){
        this.gameModel = gameModel;
        init();
    }
    public void init(){
        square = new ImageLoader(ImageLoader.squarePath);
        mousePosition = new Point(0, 0);
    }


    public void tick() {

    }


    //draw next piece
    public void drawNextPiece(Graphics g){
        Piece nextPiece = gameModel.nextPiece;
        Piece pieceAfterNext = gameModel.pieceAfterNext;
        int[][] gameState = gameModel.gameState;
        int gridWidth = gameState[0].length;
        g.setFont(font);
        g.setColor(nextStringColor);
        g.drawString("Next Pieces", (gridWidth*cellSize)+4*cellSize + lineSize, cellSize + lineSize*2);
        int dark = gameModel.darkMode ? 1: 0;
        g.setColor(gridColor[dark]);
        g.fillRect((gridWidth*cellSize)+4*cellSize, cellSize*2 - lineSize, 4*cellSize, 4*cellSize);
        g.fillRect((gridWidth*cellSize)+9*cellSize, cellSize*2 - lineSize, 4*cellSize, 4*cellSize);
        g.setColor(gridLineColor[dark]);
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                g.drawRect((i*cellSize) + (gridWidth*cellSize)+4*cellSize, j*cellSize + cellSize*2 - lineSize, cellSize, cellSize);
                g.drawRect((i*cellSize) + (gridWidth*cellSize)+9*cellSize, j*cellSize + cellSize*2 - lineSize, cellSize, cellSize);
            }
        }

        for(int i = 0; i < nextPiece.getHeight(); i++) {
            for(int j = 0; j < nextPiece.getWidth(); j++) {
                if(nextPiece.getShape()[i][j] != 0) {
                    g.drawImage(square.getSubImage(nextPiece.getShape()[i][j]), (j*cellSize) + (5+gridWidth)*cellSize, (i+2)*cellSize-lineSize, cellSize, cellSize, null);
                    g.setColor(Color.BLACK);
                    g.drawRoundRect((j*cellSize) + (5+gridWidth)*cellSize, (i+2)*cellSize - lineSize, cellSize, cellSize, 1, 1);
                }
            }
        }

        for(int i = 0; i < pieceAfterNext.getHeight(); i++) {
            for(int j = 0; j < pieceAfterNext.getWidth(); j++) {
                if(pieceAfterNext.getShape()[i][j] != 0) {
                    g.drawImage(square.getSubImage(pieceAfterNext.getShape()[i][j]), (j*cellSize) + (10+gridWidth)*cellSize, (i+2)*cellSize-lineSize, cellSize, cellSize, null);
                    g.setColor(Color.BLACK);
                    g.drawRoundRect((j*cellSize) + (10+gridWidth)*cellSize, (i+2)*cellSize-lineSize , cellSize, cellSize, 1, 1);
                }
            }
        }
        g.setColor(scoreColor[dark]);
        g.fillRoundRect(scoreX,scoreY, scoreWidth, scoreHeight, arcSize, arcSize);
    }
    //draw background
    private int squareX = 50;
    private int squareY = 20;
    private int squareWidth = 250;
    private int squareHeight = 550;

    public void drawBackground(Graphics g){
        int dark = gameModel.darkMode ? 1: 0;
        int gridHeight = gameModel.gameState.length;
        int gridWidth = gameModel.gameState[0].length;
        g.setColor(backgroundColor[dark]);
        g.fillRect(0, 0, width, height);
        g.setColor(gridColor[dark]);
        g.fillRect(squareX, squareY, squareWidth, squareHeight);
        g.setColor(gridLineColor[dark]);
        for(int i = 0; i < gridHeight; i++) {
            for(int j = 0; j < gridWidth; j++) {
                g.drawRect((j+2)*cellSize , (i+ 1)*cellSize - lineSize,  cellSize, cellSize);
            }
        }
    }


    //draw guide line
    public void drawGuideline(Graphics g){
        Piece currentPiece = gameModel.currentPiece;
        int dark = gameModel.darkMode ? 1 : 0;
        g.setColor(guidelineColor[dark]);
        int currentX = currentPiece.getX();
        guide_gap = gameModel.findGuideGap();
        for(int i = 0; i < currentPiece.getHeight(); i++) {
            for (int j = 0; j < currentPiece.getWidth(); j++) {
                if (currentPiece.getShape()[i][j] != 0) {
                    g.drawRoundRect((j + currentPiece.getY() + 2) * cellSize, (i + currentX + guide_gap + 1)  * cellSize - lineSize, cellSize, cellSize, 1, 1);
                }
            }
        }
    }

    //draw buttons
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

    //draw scores
    public void drawString(Graphics g) {
        Player player = gameModel.player;
        g.setFont(font);
        g.setColor(stringColor);
        g.drawString("New Game", buttonStringX, buttonStringY);
        g.drawString("AutoPilot", buttonStringX, buttonStringY + buttonGap);
        g.drawString("Show Guide", buttonStringX, buttonStringY + 2*buttonGap);
        g.drawString("Main Menu", buttonStringX, buttonStringY + 3*buttonGap);
        g.drawString("Level : " + player.getLevel(), scoreStringX, scoreStringY);
        g.drawString("Score : " + player.getScore(), scoreStringX, scoreStringY + scoreGap);
        g.drawString("Line : " + player.getLines(), scoreStringX, scoreStringY + 2*scoreGap);
        g.setColor(Color.white);
    }

    public void drawCurrentPiece(Graphics g){
        int[][] gameState = gameModel.gameState;
        int gridHeight = gameState.length;
        int gridWidth = gameState[0].length;
        for(int i = 0; i < gridHeight; i++) { //grid height
            for(int j = 0; j < gridWidth; j++) { //width
                g.drawImage(square.getSubImage(gameState[i][j]), j*cellSize + cellSize*2, i*cellSize+cellSize - lineSize, cellSize, cellSize, null);
                if(gameState[i][j] > 0) {
                    g.setColor(Color.BLACK);
                    g.drawRoundRect(j*cellSize + cellSize*2, i*cellSize+cellSize - lineSize, cellSize, cellSize, 1, 1);
                }
            }
        }
    }
    @Override

    public void render(Graphics g) {
        drawBackground(g);
        drawNextPiece(g);
        drawButton(g);
        drawString(g);
        drawCurrentPiece(g);
        if(gameModel.showGuide) {
            drawGuideline(g);
        }
    }

    @Override
    public void goBack() {

    }

}
