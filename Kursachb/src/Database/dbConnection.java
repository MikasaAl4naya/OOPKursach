package Database;
import java.sql.*;


public class dbConnection {

    protected String dbHost = "localhost";
    protected String dbport = "3306";
    protected String dbUser = "root";
    protected String dbPassword = "moe.imya8223";
    protected String dbName = "Oleg";
    String connectionString = "jdbc:mysql://" + dbHost + ":" + dbport + "/" + dbName;
    listOfPlayers listOfPlayers = new listOfPlayers();
    Connection dbConnection;
    public static Music music;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPassword);
        System.out.println("Подключение к БД прошло успешно");
        return dbConnection;
    }

    public void playerInsert(Player player) throws SQLException, ClassNotFoundException {
        String insert = "Insert into " + Const.PLAYERS_TABLE + "(" + Const.Name + "," + Const.Score + "," + Const.Difficult + "," + Const.Style + ")" + "Values(?,?,?,?)";

        PreparedStatement prst = getDbConnection().prepareStatement(insert);
        prst.setString(1, player.getName());
        prst.setInt(2, player.getScore());
        prst.setString(3, player.getDifficult());
        prst.setString(4, player.getStyle());
        prst.executeUpdate();


    }

    public void selectPlayers() throws SQLException {
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPassword);
        Statement statement = dbConnection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM playersTable");

        while (resultSet.next()) {
            int score = resultSet.getInt("Score");
            String Name = resultSet.getString("Name");
            String Difficult = resultSet.getString("Difficult");
            String Style = resultSet.getString("Style");
            Player player = new Player(score, Name, Difficult, Style);
            listOfPlayers.add(player);
        }
    }

    public void selectMusic(String style) throws SQLException {
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPassword);
        Statement statement = dbConnection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM music");
        MusicList musicList = new MusicList();
        switch (style) {
            case"AllStyles" :
                {
                while (resultSet.next()) {
                     music = music(resultSet);
                    if(music.getStyle() == null)
                    {
                        musicList.add(music);
                    }
                    else{
                        musicList.addTPlay(music);
                    }
                }
                break;
            }


            case "Rock": {
                resultSet = statement.executeQuery("SELECT * FROM music WHERE style='Rock'");
                while (resultSet.next()) {
                     music = music(resultSet);
                    if(music.getStyle() == null)
                    {
                        musicList.add(music);
                    }
                    else{
                        musicList.addTPlay(music);
                    }
                }
                break;
            }
            case "Pop": {
                resultSet = statement.executeQuery("SELECT * FROM music WHERE style='Pop'");
                while (resultSet.next()) {
                     music = music(resultSet);
                    if(music.getStyle() == null)
                    {
                        musicList.add(music);
                    }
                    else{
                        musicList.addTPlay(music);
                    }
                }
                break;

            }
            case "Hiphop": {
                resultSet = statement.executeQuery("SELECT * FROM music WHERE style='Hiphop'");
                while (resultSet.next()) {
                     music = music(resultSet);
                    if(music.getStyle() == null)
                    {
                        musicList.add(music);
                    }
                    else{
                        musicList.addTPlay(music);
                    }
                }
                break;
            }

            case "OST": {
                resultSet = statement.executeQuery("Select* from music where style=' OST' or  style = 'OST'");
                while (resultSet.next()) {
                     music = music(resultSet);
                    if(music.getStyle() == null)
                    {
                        musicList.add(music);
                    }
                    else{
                        musicList.addTPlay(music);
                    }
                }
                break;
            }


        }

    }
private Music music (ResultSet resultSet) throws SQLException {
    String Title = resultSet.getString("Title");
    String Musician = resultSet.getString("Musician");
    String path = resultSet.getString("Path");
    String Style = resultSet.getString("Style");
    return new Music(Title, Musician, Style, path);
}


}


