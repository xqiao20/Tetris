package TetrisCommon;

public class Piece {
    private int x;
    private int y;
    private int[][] shape;
    public Piece(int x, int y, int[][] shape){
        this.x = x;
        this.y = y;
        this.shape = shape;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[][] getShape() {
        return shape;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void plusX(){
        this.x++;
    }

    //width -> column
    //height -> row
    public int getHeight(){
        return this.shape.length;
    }

    public int getWidth(){
        if(this.shape.length == 0)
            return 0;
        return this.shape[0].length;
    }
    public boolean canRotate(int[][] gameState){
        boolean rotatable = true;
        int m = shape.length;
        int n = shape[0].length;
        if(this.x + n <= gameState.length && this.y + m <= gameState[0].length){
            for(int i = 0; i < n; i++){
                for(int j = 0; j < m; j++){
                    if(gameState[this.x + i][this.y + j] > 0){
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

    public void rotate(){
        int m = shape.length;
        int n = shape[0].length;
        int[][] res = new int[n][m];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res[j][m - 1 - i] = shape[i][j];
            }
        }
        this.shape = res;
    }
}
