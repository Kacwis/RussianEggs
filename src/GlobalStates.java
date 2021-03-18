import Objects.HighScore;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;

public class GlobalStates {

    public static Color backgroundColor = Color.color(0.624,0.659,0.584);

    public static Image leftWood;
    public static Image rightWood;
    public static Image groundCrackedEgg;
    public static Image rightChicken;
    public static Image leftChicken;
    public static int timeOfEggRolling = 3000;
    public int pointsCounter = 0;
    public int counterOfCrackedEggs = 0;
    public ArrayList<HighScore>listOfHighScores = new ArrayList<>();

    public static final GlobalStates INSTANCE = new GlobalStates();

    private GlobalStates(){
        addAllImages();
        //loadingHighScores();
    }

    private void addAllImages(){
        try{
            leftWood =  new Image(new FileInputStream("Graphics/LeftWood.png"));
            rightWood = new Image(new FileInputStream("Graphics/RightWood.png"));
            groundCrackedEgg = new Image(new FileInputStream("Graphics/GroundCrackedEgg.png"));
            rightChicken = new Image(new FileInputStream("Graphics/Chicken.png"));
            leftChicken = new Image(new FileInputStream("Graphics/ChickenRight.png"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

   public  void savingHighScores(){
        try {
          File highScores = new File("src/HighScores.txt");
            PrintWriter writing = new PrintWriter(highScores);
            for(HighScore highScore : GlobalStates.INSTANCE.listOfHighScores){
                writing.println(highScore.getName() + ";" + highScore.getScore());
            }
            writing.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

     public void loadingHighScores(){
        listOfHighScores.clear();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/HighScores.txt"));
            String currentLine = "";
            String[] spllitedLine;
            String name;
            String score;
            while (currentLine != null){
                currentLine = reader.readLine();
                if(currentLine == null)
                    continue;
                spllitedLine = currentLine.split(";");
                name = spllitedLine[0];
                score = spllitedLine[1];
                GlobalStates.INSTANCE.listOfHighScores.add(new HighScore(name,score));
            }
            reader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setTimeOfEggRolling(int amount){
        timeOfEggRolling = amount;
    }



}
