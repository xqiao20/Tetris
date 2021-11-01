package TetrisModel;

import java.util.Random;

public class Shape {
    private final static int[][][] TETROMINOS = {
            {{0}},

            {{1,1,1,1},
                    },
            {{2,2}, {2,2}},

            {{0,3,0}, {3,3,3}},

            {{4,0,0}, {4,4,4}},

            {{0,0,5}, {5,5,5}},

            {{0,6,6}, {6,6,0}},

            {{7,7,0}, {0,7,7}}};

    public static int[][] generator(){
        Random rand = new Random();
        int id = rand.nextInt(TETROMINOS.length - 1) + 1;
        return TETROMINOS[id];
    }

    public static int getColor(int[][] shape) {
        for(int i = 0; i < shape[0].length; i++) {
            if(shape[0][i] > 0) {
                return shape[0][i];
            }
        }
        return 0;
    }
}
