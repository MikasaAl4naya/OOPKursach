package Database;

import java.util.Random;

public class Player {
    public int score= 0;
    public String Name = "Игрок 1";
    private String difficult = "Easy";

    public String getDifficult() {
        return difficult;
    }

    public void setDifficult(String difficult) {
        this.difficult = difficult;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    private String style="Rock";

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    public Player(int score, String Name,String difficult, String style){
        this.score=score;
        this.Name= Name;
        this.difficult = difficult;
        this.style = style;
    }


}
