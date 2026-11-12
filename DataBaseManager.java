// Makes a connection between Java and a database
import java.sql.Connection;
// Trying to establish the connection and find the right driver
import java.sql.DriverManager;
//Errors for SQL
import java.sql.SQLException;

import java.sql.Connection;

import javax.swing.JOptionPane;

import javax.swing.JPasswordField;

import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.PreparedStatement;

public class DataBaseManager {
    // Making connection to MySQL
    public static Connection connection(String databaseUrl, String username, String password) throws SQLException {

        //Make sure the database location is provided
        if (databaseUrl == null || databaseUrl.isBlank()) {
            throw new SQLException("Database URL cannot be blank");
        }
        // Connect using the provided details
        return DriverManager.getConnection(databaseUrl, username, password);

    }

    public static void main(String[] args) {

        // database connection input
        String url = JOptionPane.showInputDialog("Enter database url:");

        String username = JOptionPane.showInputDialog("Enter MySQL username:");

        JPasswordField passwordField = new JPasswordField();

        int choice = JOptionPane.showConfirmDialog(null, passwordField, "Enter MySQL password", JOptionPane.OK_CANCEL_OPTION);

        if (url == null || url.isBlank() || username == null || username.isBlank() || choice != JOptionPane.OK_OPTION)
        {
            return;
        }
        String password = new String(passwordField.getPassword());

        try (Connection conn = connection(url, username, password)) {

            JOptionPane.showMessageDialog(null, "Connected to MySQL.");



            ArrayList<VideoGame> games = loadGames(conn);

            System.out.println("Total games loaded: " + games.size());

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null,
                    "Connection failed: " + exception.getMessage());
        }

    } // End of main


    // Retrieve all games from MySQL
    public static ArrayList<VideoGame> loadGames(Connection conn) throws SQLException {

        ArrayList<VideoGame> loadedGames = new ArrayList<>();

        String sql = "SELECT * FROM games";

        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sql)) {

            while (results.next()) {

                VideoGame game = new VideoGame(
                        results.getInt("game_id"), results.getString("title"), results.getString("developer"),
                        results.getString("genre"), results.getInt("release_year"), results.getDouble("public_rating"),
                        results.getBoolean("completed")
                );

                loadedGames.add(game);
            }
        }

        return loadedGames;
    }

    // Adding game to database
    public static boolean addGame(Connection conn, VideoGame game) throws SQLException{

        String sql = "INSERT INTO games " + "(game_id, title, developer, genre, release_year, public_rating, completed)" +
                "Values (?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setInt(1, game.getGameID());
            statement.setString(2, game.getTitle());
            statement.setString(3, game.getDeveloper());
            statement.setString(4, game.getGenre());
            statement.setInt(5, game.getReleaseYear());
            statement.setDouble(6, game.getPublicRating());
            statement.setBoolean(7, game.getCompleted());

            return statement.executeUpdate() == 1;


        }

    }

    // Removing game to database
    public static boolean removeGame(Connection conn, int gameID) throws SQLException{
        String sql = "DELETE FROM games WHERE game_id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql) ){

            statement.setInt(1,gameID);

            return statement.executeUpdate() == 1;
        }
    }
   // Update game to database

    public static boolean updateGame(Connection conn, VideoGame game) throws SQLException{

        String sql = "UPDATE games SET title = ?, developer = ?, genre = ?, " + "release_year = ?, public_rating = ?, completed = ? " + "WHERE game_id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, game.getTitle());
            statement.setString(2, game.getDeveloper());
            statement.setString(3, game.getGenre());
            statement.setInt(4, game.getReleaseYear());
            statement.setDouble(5, game.getPublicRating());
            statement.setBoolean(6, game.getCompleted());
            statement.setInt(7, game.getGameID());

            return  statement.executeUpdate() == 1;
        }

    }
}
