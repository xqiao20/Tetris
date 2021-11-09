package TetrisModel;

import TetrisCommon.*;
import TetrisCommon.Shape;
import TetrisController.Controller;
import TetrisView.StartView;

import java.awt.*;

public class GameModel{
    private Thread tickThread;

    public Piece currentPiece;
    public Piece nextPiece;
    public Piece pieceAfterNext;
    private ImageLoader square;

    public boolean isRun;
    public Point mousePosition;
    public static int gridWidth = 10;
    public static int gridHeight = 22;
    public boolean isCreated;
    public boolean isLost;
    public boolean showGuide;
    public boolean darkMode;
    public boolean autoplay = false;

    private int speed = 500;
    private int timeGap = 100;
    private int initialX = 0;
    private int initialY = 4;
    public Player player;
    public int[][] gameState = new int[gridHeight][gridWidth];

    public GameModel(){
        init();
    }

    public void init() {
        player = new Player(0, 0);
        square = new ImageLoader(ImageLoader.squarePath);
        for(int i = 0; i < gameState.length; i++) {
            for(int j = 0; j < gameState[0].length; j++) {
                gameState[i][j] = 0;
            }
        }
        nextPiece  = new Piece(initialX, initialY, Shape.generator());
        pieceAfterNext = new Piece(initialX, initialY, Shape.generator());
    }

    public void start(){
        clearGame();
        tickThread = new Thread(new TetrisModel.GameModel.Clock());
        tickThread.start();
        isRun = true;
    }

    public void clearGame(){
        player = new Player(0, 0);
        isLost = false;
        isCreated = false;
        nextPiece = new Piece(initialX, initialY, Shape.generator());
        for(int i = 0; i < gridHeight; i++){
            for(int j = 0; j < gridWidth; j++){
                gameState[i][j] = 0;
            }
        }
    }

    private void removeCurrentPiece(){
        if(currentPiece.getY() >= -1) {
            for(int i = 0; i < currentPiece.getHeight(); i++) {
                for(int j = 0; j < currentPiece.getWidth(); j++) {
                    if(currentPiece.getShape()[i][j] != 0 && currentPiece.getX()+i < gridHeight){
                        gameState[currentPiece.getX()+i][currentPiece.getY()+j] = 0;
                    }
                }
            }
        }
    }

    private void updateCurrentPiece(){
        for(int i = 0; i < currentPiece.getHeight(); i++) {//loop through height
            for(int j = 0; j < currentPiece.getWidth(); j++) { //loop through width
                if(currentPiece.getShape()[i][j] != 0) { //
                    gameState[currentPiece.getX()+i][currentPiece.getY()+j] = currentPiece.getShape()[i][j];
                }
            }
        }
    }



    public void rotatePiece(){
        removeCurrentPiece();
        if(currentPiece.canRotate(gameState)) {
            currentPiece.rotate();
            isCreated = true;
            updateCurrentPiece();
        }
    }

    public boolean canMoveLeft(){
        boolean left = true;
        if(currentPiece.getY() > 0) {
            for (int i = 0; i < currentPiece.getHeight(); i++) {
                if (left) {
                    if (currentPiece.getShape()[i][0] > 0) {
                        if (gameState[currentPiece.getX() + i][currentPiece.getY() - 1] > 0) {
                            left = false;
                        }
                    } else if (currentPiece.getShape()[i][1] > 0) {
                        if (gameState[currentPiece.getX() + i][currentPiece.getY()] > 0) {
                            left = false;
                        }
                    } else if (currentPiece.getShape()[i][2] > 0) {
                        if (gameState[currentPiece.getX() + i][currentPiece.getY() + 1] > 0) {
                            left = false;
                        }
                    } else if (currentPiece.getShape()[i][3] > 0) {
                        if (gameState[currentPiece.getX() + i][currentPiece.getY() + 2]> 0) {
                            left = false;
                        }
                    } else {
                        System.out.println("Apparently this block(" + currentPiece + ") has a whole row of empty 0.. Error in updateBlockPos");
                    }
                }
            }
        } else {
            left = false;
        }
        return left;
    }

    public boolean canMoveRight(){
        boolean right = true;
        if(currentPiece.getY() + currentPiece.getWidth() < gridWidth) {
            for (int i = 0; i < currentPiece.getHeight(); i++) {
                if (right) {
                    if (currentPiece.getShape()[i][currentPiece.getWidth() - 1] > 0) {
                        if (gameState[currentPiece.getX() + i][currentPiece.getY() + currentPiece.getWidth()] > 0) {
                            right = false;
                        }
                    } else if (currentPiece.getShape()[i][currentPiece.getWidth() - 2] > 0) {
                        if (gameState[currentPiece.getX() + i][currentPiece.getY() + currentPiece.getWidth() - 1] > 0) {
                            right = false;
                        }
                    } else if (currentPiece.getShape()[i][currentPiece.getWidth() - 3] > 0) {
                        if (gameState[currentPiece.getX() + i][currentPiece.getY() + currentPiece.getWidth() - 2] > 0) {
                            right = false;
                        }
                    } else if (currentPiece.getShape()[i][currentPiece.getWidth() - 4] > 0) {
                        if (gameState[currentPiece.getX() + i][currentPiece.getY() + currentPiece.getWidth() - 3]> 0) {
                            right = false;
                        }
                    } else {
                        System.out.println("Apparently this block(" + currentPiece + ") has a whole row of empty 0.. Error in updateBlockPos");
                    }
                }
            }
        } else {
            right = false;
        }
        return right;
    }


    public void moveLeft(){
        boolean canMove = canMoveLeft();
        if(canMove) {
            removeCurrentPiece();
            currentPiece.setY(currentPiece.getY()-1);
            updateCurrentPiece();
            isCreated = true;
        }
    }

    public void moveRight(){
        boolean canMove = canMoveRight();
        if(canMove) {
            removeCurrentPiece();
            currentPiece.setY(currentPiece.getY()+1);
            updateCurrentPiece();
            isCreated = true;
        }
    }

    public void moveFullLine(int currentHeight){
        for(int i = currentHeight; i > 1; i--) { //start from the bottom and work way up
            for(int j = 0; j < gridWidth; j++) {
                gameState[i][j] = gameState[i-1][j];
                //get the previous columns numbers and replace them.
            }
        }
    }
    public void gameOver(){
        if(gameState[initialX][initialY] > 0 || gameState[initialX + 1][initialY] > 0) {
            isLost = true;
            isRun = false;
            Controller.currentScore = player.getScore();
            Controller.switchState(STATE.ENDVIEW);
        }
    }

    public void checkRowCompletion(){
        int rowCompletion = 0;
        for(int i = 0; i < gridHeight; i++) {
            int count = 0;
            for(int j = 0; j < gridWidth; j++) {
                if(gameState[i][j] > 0) {
                    count++;
                }
            }
            if(count == gridWidth) {//completed one row
                for(int j = 0; j < gridWidth; j++) {
                    gameState[i][j] = 0;
                }
                player.clearLine();
                moveFullLine(i);
                rowCompletion++;
            }
        }
        if(rowCompletion > 0) {
            player.updateScore(rowCompletion);
        }
    }
    public boolean moveDown(){
        boolean down = true;
        if(currentPiece.getX() + currentPiece.getHeight() < gridHeight) {
            for (int i = 0; i < currentPiece.getWidth(); i++) {
                if (down) {
                    if (currentPiece.getShape()[currentPiece.getHeight() - 1][i] > 0) {
                        if (gameState[currentPiece.getX() + currentPiece.getHeight() ][currentPiece.getY() + i] > 0) {
                            down = false;
                        }
                    } else if (currentPiece.getShape()[currentPiece.getHeight() - 2][i] > 0) {
                        if (gameState[currentPiece.getX() + currentPiece.getHeight() -1][currentPiece.getY() + i] > 0) {
                            down = false;
                        }
                    } else if (currentPiece.getShape()[currentPiece.getHeight() - 3][i] > 0) {
                        if (gameState[currentPiece.getX() + currentPiece.getHeight() - 2][currentPiece.getY() + i] > 0) {
                            down = false;
                        }
                    } else if (currentPiece.getShape()[currentPiece.getHeight() - 4][i] > 0) {
                        if (gameState[currentPiece.getX() + currentPiece.getHeight() - 3][currentPiece.getY() + i]> 0) {
                            down = false;
                        }
                    } else {
                        System.out.println("Apparently this block(" + currentPiece + ") has a whole row of empty 0.. Error in updateBlockPos");
                    }
                }
            }
        }
        else{
            down = false;
        }
        return down;
    }

    public void updateBlockPosition(boolean update, boolean extraPoint) {
        if(currentPiece != null){
            if(moveDown()) {
                removeCurrentPiece();
                if(update) {
                    currentPiece.plusX();
                }
                updateCurrentPiece();
                if(extraPoint) {
                    player.addScore();
                }
            }
            else{
                isCreated = false;
            }
        }
        else {
            isCreated = false;
        }
    }



    public void tick() {
        if (!isCreated) {
            checkRowCompletion();
            gameOver();
            if(!isLost) {
                currentPiece = nextPiece;
                nextPiece = pieceAfterNext;
                pieceAfterNext = new Piece(initialX, initialY, Shape.generator());
                updateCurrentPiece();
                isCreated = true;
            }
        }else {
            if(!isLost) {
                updateBlockPosition(false, false);
            }
        }
    }

    public int findGuideGap(){
        int guide_gap = gridHeight - 1;
        for(int j = currentPiece.getY(); j < currentPiece.getY()+currentPiece.getShape()[0].length; j++) {
            int row = 0;
            if(currentPiece.getShape()[currentPiece.getShape().length - 1][j - currentPiece.getY()] == 0){
                row++;
                if(currentPiece.getShape()[currentPiece.getShape().length - 2][j - currentPiece.getY()] == 0){
                    row++;
                }
            }
            for(int i = currentPiece.getX()+currentPiece.getShape().length; i < gameState.length; i++) {
                if(gameState[i][j] != 0){
                    break;
                }
                else {
                    row++;
                }
            }
            guide_gap = Math.min(guide_gap, row);
        }
        return guide_gap;
    }


    public void goBack() {
        isRun = false;
        Controller.switchState(STATE.STARTVIEW);
    }

    private class Clock implements Runnable {
        @Override
        public void run() {
            while(isRun){
                try{
                    Thread.sleep(speed - player.getLevel()*timeGap);
                    if(!isLost){
                        updateBlockPosition(true, false);
                    }
                }
                catch(InterruptedException e){
                    break;
                }
            }
        }
    }
}

