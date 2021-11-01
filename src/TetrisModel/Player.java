package TetrisModel;

public class Player {
    final static int GAP = 10;
    final static int HIGHESTLEVEL = 10;
    final static int[] scoreCoefficient = {0, 40, 100, 300, 1200};
    private int score;
    private int level;
    private int lines;

    public Player(int score, int level){
        this.score = score;
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public int getLines() {return lines;}
    public void clearLine(){
        lines++;
        if(lines % GAP == 0){
            if(level < HIGHESTLEVEL){
                level++;
            }
        }
    }

    public void updateScore(int completedRows){
        score += scoreCoefficient[completedRows]*(level + 1);
    }

    public void addScore(){
        score++;
    }

}
