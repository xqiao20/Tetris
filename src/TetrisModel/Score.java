package TetrisModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Score {
    private static String filePath = getDefaultDirectory() + "/project/";
    public static String TOPSCOREPATH = getDefaultDirectory() + "/project/TopScores.txt";
    public static int top = 5;

    public static void init(){
        makeDir(filePath);
        creatFile(TOPSCOREPATH);
    }

    private static void creatFile(String filePath) {
        File path = new File(filePath);
        if(!path.exists()){
            try{
                path.createNewFile();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            int[] scores = new int[top];
            writeFile(path, scores);
        }
    }

    private static void makeDir(String filePath) {
        File path = new File(filePath);
        if(!path.exists()){
            path.mkdir();
        }
    }

    private static String getDefaultDirectory() {
        String OS = System.getProperty("os.name").toUpperCase();
        if(OS.contains("WIN")){
            return System.getenv("APPDATA");
        }
        if(OS.contains("MAC")){
            return System.getProperty("user.home") + "/Library/Application Support";
        }
        return System.getProperty("user.home");
    }

    public static int[] readFile(String filePath){
        int[] topScores = new int[top];
        File file = new File(filePath);
        if(file.exists()){
            try{
                Scanner scanner = new Scanner(file);
                int line = 0;
                while(scanner.hasNextInt()){
                    topScores[line++] = scanner.nextInt();
                }
                scanner.close();
                return topScores;
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        else{
            creatFile(filePath);
        }
        return topScores;
    }

    public static void saveScores(int[] scores){
        deleteFile(filePath + "TopScores.txt");
        creatFile(filePath + "TopScores.txt");
        writeFile(new File(filePath + "TopScores.txt"), scores);
    }

    public static void deleteFile(String filePath){
        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }
    }

    public static void writeFile(File file, int[] scores){
        FileWriter writer;
        try{
            writer = new FileWriter(file);
            for(int i = 0; i < scores.length; i++){
                writer.write(scores[i] + "\n");
            }
            System.out.println();
            writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
