package TetrisView;

import TetrisController.*;
import TetrisModel.Shape;
import TetrisModel.Piece;
import TetrisModel.Player;

import java.awt.*;

public class Game implements ViewInterface {
    private int width = StartView.WIDTH;
    private int height = StartView.HEIGHT;
    private int buttonWidth = StartView.WIDTHRECT;
    private int buttonHeight = StartView.HEIGHTRECT;

    private Thread tickThread;
    private int cellSize = 25;
    private int lineSize = 5;
    private int arcSize = 25;


    private int buttonX = width/3 + 160;
    private int buttonY = 55;
    private int buttonYStart = 165;
    private int minGap = 15;

    public Rectangle[] buttons = {new Rectangle(buttonX, buttonYStart, buttonWidth, buttonHeight),
            new Rectangle(buttonX, buttonYStart + buttonY, buttonWidth, buttonHeight),
            new Rectangle(buttonX, buttonYStart + 2*buttonY, buttonWidth, buttonHeight)};

    public Piece currentPiece;
    private Piece nextPiece;
    private Piece pieceAfterNext;
    private ImageLoader square;

    public boolean isRun;
    public Point mousePosition;

    public boolean showGuide;
    public boolean darkMode;
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
    private int buttonGap = 55;

    private int scoreStringX = width / 3 + 170;
    private int scoreStringY = 360;
    private int scoreGap = 25;


    private int scoreX = width/3+150;
    private int scoreY = 150;
    private int scoreWidth = 220;
    private int scoreHeight = 275;


    private int speed = 1100;
    private int timeGap = 100;
    private int initialX = 4;
    private int initialY = 0;

    Font font = new Font("sansserif", Font.BOLD, 22);


    public Game(){

    }

    public void drawNextPiece(Graphics g, int[][] gameState){
        int girdHeight = gameState[0].length;
        int gridWidth = gameState.length;
        g.setFont(font);
        g.setColor(nextStringColor);
        g.drawString("Next Pieces", (gridWidth*cellSize)+4*cellSize + lineSize, cellSize + lineSize*2);
        int dark = darkMode ? 1: 0;
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

        for(int i = 0; i < nextPiece.getWidth(); i++) {
            for(int j = 0; j < nextPiece.getHeight(); j++) {
                if(nextPiece.getShape()[i][j] != 0) {
                    g.drawImage(square.getSubImage(nextPiece.getShape()[i][j]), (i*cellSize) + (5+gridWidth)*cellSize, (j+2)*cellSize-lineSize, cellSize, cellSize, null);
                    g.setColor(Color.BLACK);
                    g.drawRoundRect((i*cellSize) + (5+gridWidth)*cellSize, (j+2)*cellSize - lineSize, cellSize, cellSize, 1, 1);
                }
            }
        }

        for(int i = 0; i < pieceAfterNext.getWidth(); i++) {
            for(int j = 0; j < pieceAfterNext.getHeight(); j++) {
                if(pieceAfterNext.getShape()[i][j] != 0) {
                    g.drawImage(square.getSubImage(pieceAfterNext.getShape()[i][j]), (i*cellSize) + (10+gridWidth)*cellSize, (j+2)*cellSize-lineSize, cellSize, cellSize, null);
                    g.setColor(Color.BLACK);
                    g.drawRoundRect((i*cellSize) + (10+gridWidth)*cellSize, (j+2)*cellSize-lineSize , cellSize, cellSize, 1, 1);
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
    private int squareHeight = 400;

    public void drawBackground(Graphics g, int gridWidth, int gridHeight){
        int dark = darkMode ? 1: 0;
        g.setColor(backgroundColor[dark]);
        g.fillRect(0, 0, width, height);
        g.setColor(gridColor[dark]);
        g.fillRect(squareX, squareY, squareWidth, squareHeight);
        g.setColor(gridLineColor[dark]);
        for(int i = 0; i < gridWidth; i++) {
            for(int j = 0; j < gridHeight; j++) {
                g.drawRect((i+ 2)*cellSize , (j+1)*cellSize - lineSize, cellSize, cellSize);
            }
        }
    }


    //draw guide line
    public void drawGuideline(Graphics g, int[][] gameState){
        int dark = darkMode ? 1 : 0;
        g.setColor(guidelineColor[dark]);
        int currentY = currentPiece.getY();
        findGuideGap(gameState);
        for(int i = 0; i < currentPiece.getWidth(); i++) {
            for (int j = 0; j < currentPiece.getHeight(); j++) {
                if (currentPiece.getShape()[i][j] != 0) {
                    g.drawRoundRect((i + currentPiece.getX() + 2) * cellSize, (j + currentY + minGap + 1)  * cellSize - lineSize, cellSize, cellSize, 1, 1);
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
        g.setColor(buttonColor); //border
        for(int i = 0; i < buttons.length; i++) {
            g.drawRoundRect(buttons[i].x, buttons[i].y, buttons[i].width, buttons[i].height, arcSize, arcSize);
        }
    }

    //draw scores
    public void drawString(Graphics g, Player player) {
        g.setFont(font);
        g.setColor(stringColor);
        g.drawString("New Game", buttonStringX, buttonStringY);
        g.drawString("Show Guide", buttonStringX, buttonStringY + buttonGap);
        g.drawString("Main Menu", buttonStringX, buttonStringY + 2*buttonGap);
        g.drawString("Level : " + player.getLevel(), scoreStringX, scoreStringY);
        g.drawString("Score : " + player.getScore(), scoreStringX, scoreStringY + scoreGap);
        g.drawString("Line : " + player.getLines(), scoreStringX, scoreStringY + 2*scoreGap);
        g.setColor(Color.white);
    }

    public void drawCurrentPiece(Graphics g, int[][] gameState){
        for(int i = 0; i < gameState.length; i++) { //grid height
            for(int j = 0; j < gameState[0].length; j++) { //width
                g.drawImage(square.getSubImage(gameState[i][j]), i*cellSize + cellSize*2, j*cellSize+cellSize - lineSize, cellSize, cellSize, null);
                if(gameState[i][j] > 0) {
                    g.setColor(Color.BLACK);
                    g.drawRoundRect(i*cellSize + cellSize*2, j*cellSize+cellSize - lineSize, cellSize, cellSize, 1, 1);
                }
           }
        }
    }

    @Override
    public void render(Graphics g, int[][] gameState, Player player) {
        drawBackground(g, gameState.length, gameState[0].length);
        drawNextPiece(g, gameState);
        drawButton(g);
        drawString(g, player);
        drawCurrentPiece(g, gameState);
        if(showGuide) {
            drawGuideline(g, gameState);
        }
    }

    private void findGuideGap(int[][] gameState){

        minGap = gameState.length - 1;
        for(int j = currentPiece.getX(); j < currentPiece.getX()+currentPiece.getShape().length; j++) {
            int row = 0;
            if(currentPiece.getShape()[j - currentPiece.getX()][currentPiece.getShape()[0].length - 1] == 0){
                row++;
                if(currentPiece.getShape()[j - currentPiece.getX()][ currentPiece.getShape()[0].length - 2] == 0){
                    row++;
                }
            }
            for(int i = currentPiece.getY()+currentPiece.getShape()[0].length; i < gameState[0].length; i++) {
                if(gameState[j][i] != 0){
                    break;
                }
                else {
                    row++;
                }
            }
            minGap = Math.min(minGap, row);
        }
    }
}
