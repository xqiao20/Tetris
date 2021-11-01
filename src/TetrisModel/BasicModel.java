package TetrisModel;

import TetrisController.Controller;
import TetrisView.*;

public class BasicModel {
    private int gridWidth = 10;
    private int gridHeight = 16;
    public Piece currentPiece;
    private Piece nextPiece;
    private Piece pieceAfterNext;
    public boolean isCreated;
    public boolean isLost;
    private int initialX = 4;
    private int initialY = 0;
    public boolean isRun;
    public Player player;
    public STATE state;
    public StartView startView;
    public Game game;
    public HighScores topScores;
    public EndView endView;
    public boolean darkMode;

    public int[][] gameState = new int[gridWidth][gridHeight];

    public BasicModel(){
        startView = new StartView();
        game = new Game();
        topScores = new HighScores();
        endView = new EndView();
        currentPiece = new Piece(initialX, initialY, Shape.generator());
        nextPiece = new Piece(initialX, initialY, Shape.generator());
        pieceAfterNext = new Piece(initialX, initialY, Shape.generator());
        state = STATE.STARTVIEW;
    }

    private void removeCurrentPiece(){
        if(currentPiece.getY() >= 0) {
            for(int i = 0; i < currentPiece.getWidth(); i++) {
                for(int j = 0; j < currentPiece.getHeight(); j++) {
                    if(currentPiece.getShape()[i][j] != 0 && currentPiece.getY()+j < gridHeight){
                        gameState[currentPiece.getX()+i][currentPiece.getY()+j] = 0;
                    }
                }
            }
        }
    }

    private void updateCurrentPiece(){
        for(int i = 0; i < currentPiece.getHeight(); i++) {
            for(int j = 0; j < currentPiece.getWidth(); j++) {
                if(currentPiece.getShape()[j][i] != 0) { //
                    gameState[currentPiece.getX()+j][currentPiece.getY()+i] = currentPiece.getShape()[j][i];
                }
            }
        }
    }

    public void rotatePiece(){
        removeCurrentPiece();
        currentPiece.rotate(gameState);
        isCreated = true;
        updateCurrentPiece();
    }


    public void moveLeft(){
        boolean canMove = true;
        if(currentPiece.getX() > 0) {
            for(int i = 0; i < currentPiece.getHeight(); i++) {
                if((gameState[currentPiece.getX() - 1][currentPiece.getY()+i] > 0 && currentPiece.getShape()[0][i] > 0) ||(gameState[currentPiece.getX()][currentPiece.getY() + i] > 0 && currentPiece.getShape()[0][i] == 0)) {
                    canMove = false;
                }
            }
            if(canMove) {
                removeCurrentPiece();
                currentPiece.setX(currentPiece.getX()-1);
                updateCurrentPiece();
                isCreated = true;
            }
        }
    }

    public void moveRight(){
        boolean canMove = true;
        if(currentPiece.getX()+currentPiece.getWidth() < gridWidth) {
            for(int i = 0; i < currentPiece.getHeight(); i++) {
                int newX = currentPiece.getX()+currentPiece.getWidth();
                if((gameState[newX][currentPiece.getY()+i] > 0 && currentPiece.getShape()[currentPiece.getWidth()-1][i] > 0) || (gameState[newX - 1][currentPiece.getY()+ i] > 0 && currentPiece.getShape()[currentPiece.getWidth()-1][i] == 0)) {
                    canMove = false;
                }
            }
            if(canMove) {
                removeCurrentPiece();
                currentPiece.setX(currentPiece.getX()+1);
                updateCurrentPiece();
                isCreated = true;
            }
        }

    }

    public void moveFullLine(int currentHeight){
        for(int i = currentHeight; i > 1; i--) { //start from the bottom and work way up
            for(int j = 0; j < gridWidth; j++) {
                gameState[j][i] = gameState[j][i-1];
                //get the previous columns numbers and replace them.
            }
        }
    }
    public boolean gameOver(){
        if(gameState[initialX][initialY] > 0 || gameState[initialX + 1][initialY] > 0) {
            return true;
        }
        return false;
    }

    public void checkRowCompletion(){
        int rowCompletion = 0;

        for(int i = 0; i < gridHeight; i++) {
            int count = 0;
            for(int j = 0; j < gridWidth; j++) {
                if(gameState[j][i] > 0) {
                    count++;
                }
            }
            if(count == gridWidth) {//completed one row
                for(int j = 0; j < gridWidth; j++) {
                    gameState[j][i] = 0;
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
        if(currentPiece.getY() + currentPiece.getHeight() < gridHeight) {
            for (int i = 0; i < currentPiece.getWidth(); i++) {
                if (down) {
                    if (currentPiece.getShape()[i][currentPiece.getHeight() - 1] > 0) {
                        if (gameState[currentPiece.getX() + i][currentPiece.getY() + currentPiece.getHeight()] > 0) {
                            down = false;
                        }
                    } else if (currentPiece.getShape()[i][currentPiece.getHeight() - 2] > 0) {
                        if (gameState[currentPiece.getX() + i][currentPiece.getY() + currentPiece.getHeight() - 1] > 0) {
                            down = false;
                        }
                    } else if (currentPiece.getShape()[i][currentPiece.getHeight() - 3] > 0) {
                        if (gameState[currentPiece.getX() + i][currentPiece.getY() + currentPiece.getHeight() - 2] > 0) {
                            down = false;
                        }
                    } else if (currentPiece.getShape()[i][currentPiece.getHeight() - 4] > 0) {
                        if (gameState[currentPiece.getX() + i][currentPiece.getY() + currentPiece.getHeight() - 3] > 0) {
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
                    currentPiece.plusY();
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

    public void clearGame(){
        player = new Player(0, 0);
        isLost = false;
        isCreated = false;
        nextPiece = new Piece(initialX, initialY, Shape.generator());
        for(int i = 0; i < gridHeight; i++){
            for(int j = 0; j < gridWidth; j++){
                gameState[j][i] = 0;
            }
        }
    }

    public void stateCheck() {
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

    public void switchState(STATE gameState){
        state = gameState;
        switch (state){
            case STARTVIEW:
                startView.init();
                break;
            case GAME:
                start();
                break;
            case TOPSCORES:
                topScores.init();
                break;
            case ENDVIEW:
                endView.checkScore();

        }
    }
    private Thread tickThread;
    public void start(){
        clearGame();
        tickThread = new Thread(new BasicModel.Clock());
        tickThread.start();
        isRun = true;
    }

    private int speed = 1100;
    private int timeGap = 100;

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
