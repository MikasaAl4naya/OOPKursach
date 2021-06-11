package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class listOfPlayers {
    public static ObservableList<Player> players = FXCollections.observableArrayList();
    public static void clear()
    {
        players.clear();
    }

    public void add(Player player)
    {
        players.add(player);
    }
    public String returnName()
    {
        Player player = players.get(players.size()-1);
        String result = player.getName();
        return result;
    }

}
