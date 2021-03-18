package Objects;

import java.io.Serializable;

public class HighScore implements Serializable {
    String name;
    String score;

    public HighScore(String name, String score){
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }
}
