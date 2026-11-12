import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.sql.Connection;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.sql.SQLException;


public class VideoGameGUI extends JFrame {

    private ArrayList<VideoGame> games = new ArrayList<>();

    private Connection conn;

  public VideoGameGUI() {
   // Setting the title of the Window
   this.setTitle("Video Game DMS");
   // Setting the size
   this.setSize(800,600);
   // Setting the location
   this.setLocationRelativeTo(null);
   // Closing the window
   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

   // SQL database connection details
      String url = JOptionPane.showInputDialog("Enter database URL: ");
      String username = JOptionPane.showInputDialog("Enter MySQL username:");
      JPasswordField passwordField = new JPasswordField();

      int choice = JOptionPane.showConfirmDialog(null,passwordField, "Enter MySQL password",JOptionPane.OK_CANCEL_OPTION);

      if (url == null || url.isBlank() || username == null || username.isBlank() || choice!= JOptionPane.OK_OPTION) {
          return;
      }

      String password = new String(passwordField.getPassword());

      try {
          // Connect the GUI to SQL
          conn = DataBaseManager.connection(url, username, password);

          games = DataBaseManager.loadGames(conn);

          JOptionPane.showMessageDialog(null,"Connection Loaded " + games.size() + " games." );

      } catch (SQLException exception){
          JOptionPane.showMessageDialog(null, "Database connection failed: " + exception.getMessage());
          return;
      }

   // JPanel Object
   JPanel mainPanel = new JPanel();
   // Adding JPanel to Window
   this.add(mainPanel);

   //JButton
   JButton button = new JButton("Add Game");
   // Adding JButton to Panel - Window
   mainPanel.add(button);
   // Remove game button
      JButton removeButton = new JButton("Remove Game: ");
      // Adding Remove button to main panel
      mainPanel.add(removeButton);
      // Display game button
      JButton displayButton = new JButton("Display Game: ");
      // Adding Display button to main panel
      mainPanel.add(displayButton);
     // Update Game button
      JButton updateButton = new JButton("Update Game: ");
      // Adding Update button to main panel
      mainPanel.add(updateButton);
      //Load File Button
      JButton fileButton =  new JButton("Load the database");
      mainPanel.add(fileButton);
      //Rating Button
      JButton averageButton = new JButton("Average Rating");
      mainPanel.add(averageButton);
      //Exit Button
      JButton exitButton = new JButton("Exit");
      mainPanel.add(exitButton);

   // Button action listener
   button.addActionListener(e -> {
    System.out.println("Add game button clicked.");
    String gameTitle = JOptionPane.showInputDialog("Enter game title: ");
    if (gameTitle == null || gameTitle.trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Invalid Input");
            return;
    }
    try {
        // GameID Input
        String gameId = JOptionPane.showInputDialog("Enter game id: ");
        int id = Integer.parseInt(gameId);

        if (id <= 0) {
            JOptionPane.showMessageDialog(null, "Only positive numbers no negative numbers or the number zero is allowed");
            return;
        }
        for (VideoGame game : games) {
            if (game.getGameID() == id) {
                JOptionPane.showMessageDialog(null, "This id already exists ");
                return;
            }


        }
        JOptionPane.showMessageDialog(null, "Valid Game Id");
        // Developer Input
        String game_Dev = JOptionPane.showInputDialog("Enter developer name: ");
        if (game_Dev == null || game_Dev.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Invalid Input");
            return;
        }
        String[] genres = {"Action", "Adventure", "RPG", "Horror", "Puzzle", "Sports", "Simulation", "Other"};
        String selected = (String) JOptionPane.showInputDialog(
                null,
                "Select a genre: ",
                "Game Genre",
                JOptionPane.QUESTION_MESSAGE,
                null,
                genres,
                genres[0]

        );

        if (selected == null) {
            JOptionPane.showMessageDialog(null, "Option cancelled");
            return;
        }

        // Release year input
        int year = 0;
        try {
            String release_Year = JOptionPane.showInputDialog("Enter release year 1958 - 2026: ");
            year = Integer.parseInt(release_Year);
            if (year < 1958 || year > 2026) {
                JOptionPane.showMessageDialog(null, "Invalid Input, Enter number from ranges 1958 - 2026");
                return;
            }
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, "Invalid Input");
            return;
        }
        // Public Rating Input
        double rating = 0;
        try {
            String public_Rating = JOptionPane.showInputDialog("Enter public rating 0.0 - 10.0: ");
            if (public_Rating == null) {
                JOptionPane.showMessageDialog(null, "Input cancelled.");
                return;
            }
            rating = Double.parseDouble(public_Rating);
            if (rating < 0.0 || rating > 10.0) {
                JOptionPane.showMessageDialog(null, "Invalid input, enter rating ranging from 0.0 - 10.0");
                return;
            }


        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, "Invalid Input");
            return;
        }
        // Completion Input
        String[] completed_Options = {"Completed", "Not Completed"};
        String options = (String) JOptionPane.showInputDialog(
                null,
                "Select completion status: ",
                "Completion status",
                JOptionPane.QUESTION_MESSAGE,
                null,
                completed_Options,
                completed_Options[0]
        );
        if (options == null) {
            JOptionPane.showMessageDialog(null, "Input cancelled");
            return;
        }
        boolean completed = options.equals("Completed");

        VideoGame newGame = new VideoGame(id, gameTitle, game_Dev, selected, year, rating, completed);
        try {
            if (DataBaseManager.addGame(conn, newGame)) {

                games = DataBaseManager.loadGames(conn);

                JOptionPane.showMessageDialog(null,
                        "Game added successfully to MySQL database.");
            }

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null,
                    "Could not add game: " + exception.getMessage());
        }
    }catch (NumberFormatException exception){
        JOptionPane.showMessageDialog(null, "Invalid Game ID.");
    }
           });
   // Remove game button
   removeButton.addActionListener(e -> {
       try {
           String removeId = JOptionPane.showInputDialog("Enter the Game ID to remove: ");
           int remove_ID = Integer.parseInt(removeId);

           try {
               if (DataBaseManager.removeGame(conn, remove_ID)) {
                   games = DataBaseManager.loadGames(conn);
                   JOptionPane.showMessageDialog(null,"Game removed successfully from MySQL.");
               }else {
                   JOptionPane.showMessageDialog(null,"Game ID cannot be found");
               }
           } catch (SQLException exception){
              JOptionPane.showMessageDialog(null,"Game could not be removed: " + exception.getMessage());
           }

       } catch (NumberFormatException exception){
           JOptionPane.showMessageDialog(null,"Invalid Game ID");
       }
           }

           );
   // Display game button


      displayButton.addActionListener(e -> {
          String allGames = "";

          if (games.isEmpty()){
              JOptionPane.showMessageDialog(null, "No games available");
              return;
          }

          for (VideoGame game: games){
          String game_Info = "ID: " + game.getGameID()+ " | Title: " + game.getTitle()
                  + " | Developer: " + game.getDeveloper()
                  + " | Genre: " + game.getGenre()
                  + " | Release Year: " + game.getReleaseYear()
                  + " | Rating: " + game.getPublicRating()
                  + " | Completed: " + game.getCompleted();
          allGames += game_Info + ("\n");
          }
          JOptionPane.showMessageDialog(null, allGames);


      });
      // Update game button
      updateButton.addActionListener(e -> {
          JOptionPane.showMessageDialog(null,"Update Game button clicked.");


          try{
              boolean gameFound = false;

              String updateID = JOptionPane.showInputDialog("Enter the Game ID to update: ");
              int update_Id = Integer.parseInt(updateID);

              for (VideoGame game: games){
                  if (update_Id == game.getGameID()) {
                      gameFound = true;
                      String[] updateOptions = {
                              "Title",
                              "Developer",
                              "Genre",
                              "Release Year",
                              "Public Rating",
                              "Completion Status"
                      };

                      String selectedField = (String) JOptionPane.showInputDialog(
                              null,
                              "What would you like to update",
                              "Update game",
                              JOptionPane.QUESTION_MESSAGE,
                              null,
                              updateOptions,
                              updateOptions[0]
                      );
                      if (selectedField == null) {
                          JOptionPane.showMessageDialog(null, "Update cancelled.");
                          return;
                      }
                      if (selectedField.equals("Title")) {
                          String newTitle = JOptionPane.showInputDialog("Enter new game title: ");

                          if (newTitle == null || newTitle.trim().isEmpty()) {
                              JOptionPane.showMessageDialog(null, "Invalid title.");
                              return;
                          }
                          game.setTitle(newTitle);
                      }

                      if (selectedField.equals("Developer")) {
                          String newDeveloper = JOptionPane.showInputDialog("Enter new developer : ");

                          if (newDeveloper == null || newDeveloper.trim().isEmpty()) {
                              JOptionPane.showMessageDialog(null, "Invalid title.");
                              return;
                          }
                          game.setDeveloper(newDeveloper);
                      }

                      if (selectedField.equals("Genre")) {


                          String[] genreOptions = {
                                  "Action", "Adventure", "RPG", "Horror",
                                  "Puzzle", "Sports", "Simulation", "Other"
                          };

                          String newGenre = (String) JOptionPane.showInputDialog(
                                  null,
                                  "Select new genre:",
                                  "Update genre",
                                  JOptionPane.QUESTION_MESSAGE,
                                  null,
                                  genreOptions,
                                  genreOptions[0]

                          );
                          if (newGenre == null) {
                              return;
                          }
                          game.setGenre(newGenre);
                      }
                      if (selectedField.equals("Release Year")) {
                          String yearInput = JOptionPane.showInputDialog("Enter new release year: ");
                          if (yearInput == null) {
                              return;
                          }
                          int newYear = Integer.parseInt(yearInput);

                          if (newYear < 1958 || newYear > 2026) {
                              JOptionPane.showMessageDialog(null, "Invalid year. Enter 1958 - 2026");
                              return;
                          }
                          game.setReleaseYear(newYear);
                      }

                      if (selectedField.equals("Public Rating")) {
                          String ratingInput = JOptionPane.showInputDialog("Enter new rating (0.0 - 10.0)");

                          if (ratingInput == null) {
                              return;
                          }
                          double newRating = Double.parseDouble(ratingInput);

                          if (!Double.isFinite(newRating) || newRating < 0.0 || newRating > 10.0) {
                              JOptionPane.showMessageDialog(null, "Invalid rating. Enter 0.0 - 10.0.");
                              return;
                          }
                          game.setPublicRating(newRating);
                      }


                      if (selectedField.equals("Completion Status")) {
                          String[] completedOptions = {
                                  "Completed", "Not Completed"
                          };
                          String newStatus = (String) JOptionPane.showInputDialog(null, "Select completion status", "Update completion status", JOptionPane.QUESTION_MESSAGE, null, completedOptions, completedOptions[0]);

                          if (newStatus == null) {
                              return;
                          }
                          boolean completed = newStatus.equals("Completed");
                          game.setCompleted(completed);
                      }



                   try {
                       if (DataBaseManager.updateGame(conn,game)){
                           games = DataBaseManager.loadGames(conn);

                           JOptionPane.showMessageDialog(null,"Updated game successfully");
                       } else {
                           JOptionPane.showMessageDialog(null,"Game ID cannot be found ");
                       }

                    }catch (SQLException exception){
                       JOptionPane.showMessageDialog(null,"Could not update game" + exception.getMessage());
                   }
                   break;
                  }


              }
              if (!gameFound){
                  JOptionPane.showMessageDialog(null, "Game ID not found");
              }

          } catch (NumberFormatException exception) {
              JOptionPane.showMessageDialog(null, "Invalid Game Id");
          }

      });
// file button
      fileButton.addActionListener(e -> {
          try {
              games = DataBaseManager.loadGames(conn);

              JOptionPane.showMessageDialog(null,
                      "Loaded " + games.size() + " games from MySQL.");

          } catch (SQLException exception) {
              JOptionPane.showMessageDialog(null,
                      "Could not load games: " + exception.getMessage());
          }
      });

      averageButton.addActionListener(e -> {
                  try {
                      games = DataBaseManager.loadGames(conn);

                      double average = Main.calculateAverageRating(games);

                      JOptionPane.showMessageDialog(null, "Average Game Rating: " + String.format("%.2f", average));
                  } catch (SQLException exception) {
                      JOptionPane.showMessageDialog(null, "Could not calculate average: " + exception.getMessage());
                  }
              });
      // Display window
      this.setVisible(true);
  }
public static void main(String[] args) {

VideoGameGUI videoGameGUI = new VideoGameGUI();
    }

 }