package TetrisAI;


import TetrisModel.GameModel;

import java.util.*;

// An interface to be implemented by everyone interested in "Hello" events


// Someone interested in "Hello" events
public class Responder implements AutoListener {

    GameModel gameModel;
    public static int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public Responder(GameModel gameModel){
        this.gameModel = gameModel;
    }
    @Override
    public void autoPlay() {
        int currY = gameModel.currentPiece.getY();
        if (gameModel.autoplay){
            int height= gameModel.gameState.length;
            int width = gameModel.gameState[0].length;
            int[][] currState = new int[height][width];
            for(int i = 0; i < height; i++){
                currState[i] = Arrays.copyOf(gameModel.gameState[i], width);
            }

            for(int i = 0; i < gameModel.currentPiece.getHeight(); i++){
                for(int j = 0; j < gameModel.currentPiece.getWidth(); j++){
                    currState[i+gameModel.currentPiece.getX()][j+gameModel.currentPiece.getY()] = 0;
                }
            }
            int bestPos = optimalCol(currState);
            if (bestPos > currY) {
                while (bestPos > currY) {
                    gameModel.moveRight();
                    bestPos--;
                }
            } else {
                while (bestPos < currY) {
                    gameModel.moveLeft();
                    bestPos++;
                }
            }
            for(int i = 0; i < gameModel.gameState.length; i++){
                if(gameModel.moveDown()){
                    gameModel.updateBlockPosition(true, true);
                }
            }
        }
    }

    public boolean canRotate(int[][] newPiece, int[][] currState){
        boolean rotatable = true;
        int m = newPiece.length;
        int n = newPiece[0].length;
        if(gameModel.currentPiece.getX() + n <= currState[0].length && gameModel.currentPiece.getY() + m <= currState.length){
            for(int i = 0; i < n; i++){
                for(int j = 0; j < m; j++){
                    if(currState[gameModel.currentPiece.getX() + i][gameModel.currentPiece.getY() + j] > 0){
                        rotatable = false;
                    }
                }
            }
        }
        else{
            rotatable = false;
        }
        return rotatable;
    }

    public int[][] rotatePiece(int[][] newPiece){
        int m = newPiece.length;
        int n = newPiece[0].length;
        int[][] res = new int[n][m];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res[j][m - 1 - i] = newPiece[i][j];
            }
        }
        return res;
    }

    public int optimalCol(int[][] currState){
        int result = 0;
        int rotateTime = 0;
        double bestCost = 1000;
        int[][] shape = gameModel.currentPiece.getShape();
        int r = shape.length;
        int c = shape[0].length;
        int[][] newPiece = new int[r][c];
        for(int i = 0; i < r; i++){
            newPiece[i] = Arrays.copyOf(shape[i], c);
        }

        for(int k = 0; k < 4; k++) {
            double cost = 1000;
            int localResult = 0;
            int cols = currState[0].length - newPiece[0].length;
            for (int i = 0; i <= cols; i++) {
                predict(currState, i, newPiece);
                double currCost = heuristic(currState);
                if (currCost < cost) {
                    cost = currCost;
                    localResult = i;
                }
                reset(currState);
            }
            if(bestCost > cost){
                bestCost = cost;
                rotateTime = k;
                result = localResult;
            }
            if (canRotate(newPiece, currState)) {
                newPiece = rotatePiece(newPiece);
            } else {
                break;
            }

        }
        int cnt = 0;
        while(rotateTime> 0){
            gameModel.rotatePiece();
            cnt ++;
            rotateTime--;
        }
        return result;
    }

    public void predict(int[][] currState, int y, int[][] newPieceShape){
        int minGap = findGuideGap(0, y, currState, newPieceShape);
        for(int i = 0; i < newPieceShape.length; i++){
            for (int j = 0; j < newPieceShape[0].length; j++){
                currState[gameModel.currentPiece.getX() + i][gameModel.currentPiece.getY() + j] = 0;
            }
        }
        for(int i = 0; i < newPieceShape.length; i++) {
            for (int j = 0; j < newPieceShape[0].length; j++) {
                if (newPieceShape[i][j] != 0) {
                    currState[minGap + i][y + j] = -1;
                }
            }
        }
    }

    private void reset(int[][] currState){
        for(int i = 0; i < currState.length; i++) {
            for (int j = 0; j < currState[0].length; j++) {
                if (currState[i][j]== -1) {
                    currState[i][j] = 0;
                }
            }
        }
    }

    private static int[] getHoles(int[][] currState){
        //0-> holes , 1-> sum of height, 2-> diff between height
        int[] result = new int[3];
        int[] heights = new int[currState[0].length];
        for (int j = 0; j < currState[0].length; j++) {
            boolean block = false;
            for(int i = 0; i < currState.length; i++) {
                if(!block && currState[i][j] != 0){
                    block = true;
                    heights[j] = currState.length - 1 - i ;
                    result[1] += heights[j];
                }
                if(block && currState[i][j] == 0){
                    result[0]++;
                }
            }
        }

        for(int i = 0; i < heights.length - 1; i++){
            result[2] += Math.abs(heights[i+1] - heights[i]);
        }

        return result;
    }

    private static int countLine(int[][] currState){
        int line = 0;
        for(int i = 0; i < currState.length; i++){
            int count = 0;
            for(int j = 0; j < currState[0].length; j++){
                if(currState[i][j] != 0){
                    count++;
                }
            }
            if(count == currState[0].length){
                line++;
            }
        }
        return line;
    }

    private static double heuristic(int[][] currState){
        int[] results = getHoles(currState);
        int lines = countLine(currState);
        double a = 0.47015812119690154;
        double b = -0.7643351415993872;
        double c = 0.3842050185079524;
        double d = 0.21709361147244394;
        return a*results[1] + b*lines + c*results[0] + d*results[2];
    }

    public static void printNestArray(int[][] states){
        for(int i = 0; i < states.length; i++){
            for(int j = 0; j < states[0].length; j++){
                System.out.print(states[i][j] + "; ");
            }
            System.out.println("/");
        }
    }

    private int findGuideGap(int x, int y, int[][] currState, int[][] shape){
        int minGap = currState.length;
        for(int j = y; j < y+shape[0].length; j++) {
            int row = 0;
            if(shape[shape.length - 1][j - y] == 0){
                row++;
                if(shape[shape.length - 2][j - y] == 0){
                    row++;
                }
            }

            for(int i = x +shape.length; i < gameModel.gameState.length; i++) {
                if(currState[i][j] != 0){
                    break;
                }
                else {
                    row++;
                }
            }
            minGap = Math.min(minGap, row);
        }
        return minGap;
    }
}