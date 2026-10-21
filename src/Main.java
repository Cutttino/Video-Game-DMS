
import java.awt.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {

System.out.println("Welcome to the Video Game DMS!");
System.out.println("\n == VIDEO GAME DMS ==");
System.out.println("1. Load games from file");
System.out.println("2. Add game");
System.out.println("3. Remove game");
System.out.println("4. Update Game ");
System.out.println("5. Display Game");
System.out.println("6. Calculate Average Rating");
System.out.println("7. Exit Program");

Scanner scanner = new Scanner(System.in);

ArrayList<VideoGame> games =  new ArrayList<>();

while (true) {
    System.out.println("Enter menu inputs (1 - 7) " );
    try {
        int menu_input = scanner.nextInt();
        if (menu_input == 7){
            System.out.println("Exiting program..");
            break;
        } else if (menu_input < 1 || menu_input > 7) {
             System.out.println("Invalid enter options 1-7");
        }

        switch (menu_input) {
            case 1:
                System.out.println("Loading games from file");
                System.out.println("Enter file path:");

                scanner.nextLine();
                String filePath = scanner.nextLine();

                try (Scanner fileScanner = new Scanner(new File(filePath))) {

                    int loadedCount = 0;

                    while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine();

                        if (line.isBlank()) {
                            continue;
                        }

                        try {
                            // Separate each game's seven fields
                            String[] data = line.split(",", -1);

                            if (data.length != 7) {
                                System.out.println("Skipping invalid record.");
                                continue;
                            }

                            int id = Integer.parseInt(data[0].trim());
                            String title = data[1].trim();
                            String developer = data[2].trim();
                            String genre = data[3].trim();
                            int year = Integer.parseInt(data[4].trim());
                            double rating = Double.parseDouble(data[5].trim());
                            String status = data[6].trim();

                            // Validate all fields
                            if (id <= 0 || title.isBlank() ||
                                    developer.isBlank() || genre.isBlank() ||
                                    year < 1958 || year > 2026 ||
                                    !Double.isFinite(rating) ||
                                    rating < 0 || rating > 10 ||
                                    (!status.equalsIgnoreCase("true") &&
                                            !status.equalsIgnoreCase("false"))) {

                                System.out.println("Skipping invalid game.");
                                continue;
                            }

                            // Prevent duplicate Game IDs
                            boolean duplicate = false;

                            for (VideoGame game : games) {
                                if (game.getGameID() == id) {
                                    duplicate = true;
                                    break;
                                }
                            }

                            if (duplicate) {
                                System.out.println("Duplicate Game ID: " + id);
                                continue;
                            }

                            boolean completed = Boolean.parseBoolean(status);

                            VideoGame loadedGame = new VideoGame(
                                    id, title, developer, genre,
                                    year, rating, completed
                            );

                            games.add(loadedGame);
                            loadedCount++;

                        } catch (NumberFormatException e) {
                            System.out.println("Skipping invalid record.");
                        }
                    }

                    System.out.println("Successfully loaded " + loadedCount + " games.");

                } catch (FileNotFoundException e) {
                    System.out.println("File not found. Check your file path.");
                } catch (SecurityException e) {
                    System.out.println("Permission denied when opening file.");
                }


                break;
            case 2:
                System.out.println("Add game selected");
                int gameID_input = 0;
                while (true) {
                    try {
                        System.out.println("Enter Game Id: ");
                        gameID_input = scanner.nextInt();

                        boolean duplicateID = false;

                        for (VideoGame game : games) {
                            if (game.getGameID() == gameID_input) {
                                duplicateID = true;
                            }
                        }


                        if (gameID_input <= 0) {
                            System.out.println("Invalid Game Id");
                        } else if (duplicateID) {
                            System.out.println("Game Id already exists. Try again");

                        } else {
                            System.out.println("Valid Game Id");
                            break;
                        }

                    } catch (InputMismatchException e) {
                        System.out.println("Error that was a invalid input try again.");

                        scanner.next();
                    }


                }

                scanner.nextLine();

                System.out.println("Enter game title: ");
                String title_input = scanner.nextLine();
                while (title_input.isBlank()) {
                    System.out.println("Invalid Title try again.");
                    title_input = scanner.nextLine();
                }


                System.out.println("Enter developer name: ");
                String developer_input = scanner.nextLine();
                while (developer_input.isBlank()) {
                    System.out.println("Invalid developer name");
                    developer_input = scanner.nextLine();
                }

                System.out.println("Enter genre: ");
                String genre_input = scanner.nextLine();
                while (genre_input.isBlank()) {
                    System.out.println("Invalid genre name");
                    genre_input = scanner.nextLine();
                }

                int releaseYear_input = 0;

                while (true) {
                 try{
                     System.out.println("Enter release year: ");
                     releaseYear_input = scanner.nextInt();
                     if (releaseYear_input < 1958 || releaseYear_input > 2026) {
                         System.out.println("Invalid, enter years (1958 - 2026)");
                     }else {
                         System.out.println("Year is valid");
                         break;
                     }
                 } catch (InputMismatchException e){
                     System.out.println("Invalid input ");
                     scanner.next();
                 }

                }
                double public_rating_input = 0.0;
                while(true) {
                    try {
                        System.out.println("Enter public rating: ");
                        public_rating_input = scanner.nextDouble();
                        if (public_rating_input < 0.0 || public_rating_input > 10.0 || !Double.isFinite(public_rating_input)) {
                            System.out.println("Invalid enter public rating anywhere through 0.0 - 10.0.");
                        } else {
                            System.out.println("Public rating is valid");
                            break;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input");
                        scanner.next();
                    }

                }
                scanner.nextLine();

                    System.out.println("Have you completed the game? (yes/no): ");
                    String completed_input = scanner.nextLine();
                    while(!completed_input.equalsIgnoreCase("yes") && !completed_input.equalsIgnoreCase("no")){
                        System.out.println("Enter yes or no.");
                        completed_input = scanner.nextLine();
                    }

                    boolean completed_status = completed_input.equalsIgnoreCase("yes");


                    VideoGame newGame = new VideoGame(gameID_input,title_input,developer_input,genre_input,releaseYear_input,public_rating_input,completed_status );
                    games.add(newGame);

                 break;
            case 3:
                System.out.println("Remove game selected");
                System.out.println("Enter Game ID to remove: ");
                int removeId_input = scanner.nextInt();
                boolean removed = games.removeIf(game -> game.getGameID() == removeId_input);

                if (removed){
                    System.out.println("Game removed successfully");
                }else {
                    System.out.println("Game ID is not found");
                }

                break;
            case 4:
                System.out.println("Update game selected");
                System.out.println("Enter Game ID to update: ");
                int updateID_input = scanner.nextInt();

                VideoGame gameToUpdate = null;

                for(VideoGame game: games){
                    if(game.getGameID() == updateID_input){
                        gameToUpdate = game;
                        break;
                    }


                    }
                if (gameToUpdate == null){
                    System.out.println("Game ID not found");

                    break;

                }
                System.out.println("What would you like to update?");
                System.out.println("1. Game ID");
                System.out.println("2. Title");
                System.out.println("3. Developer");
                System.out.println("4. Genre");
                System.out.println("5. Release Year");
                System.out.println("6. Public Rating");
                System.out.println("7. Completion Status");

                int updateChoice = scanner.nextInt();

                switch (updateChoice) {
                    case 1:
                        System.out.println("Update Game ID selected");
                        System.out.println("Enter new Game ID: ");
                        int newGameID = scanner.nextInt();

                        if (newGameID <= 0) {
                            System.out.println("Invalid Game ID. Must be greater than 0.");
                        }
                        boolean duplicateNewID = false;

                        for (VideoGame game : games) {
                            if (game.getGameID() == newGameID && game != gameToUpdate) {
                                duplicateNewID = true;
                                break;
                            }

                        }
                        if (duplicateNewID){
                            System.out.println("Game ID already exists ");
                        } else if (newGameID > 0){
                            gameToUpdate.setGameID(newGameID);
                            System.out.println("Game Id updated successfully.");
                        }

                        break;

                    case 2:
                        scanner.nextLine();
                        System.out.println("Enter new game title: ");
                        String newTitle = scanner.nextLine();

                        if(newTitle.isBlank()){
                            System.out.println("Invalid title");
                        } else {
                            gameToUpdate.setTitle(newTitle);
                            System.out.println("Title updated successfully");
                        }
                        break;
                    case 3:
                        scanner.nextLine();
                        System.out.println("Enter new developer:");
                        String newDeveloper = scanner.nextLine();


                        if(newDeveloper.isBlank()){
                            System.out.println("Invalid developer");
                        }else {
                            gameToUpdate.setDeveloper(newDeveloper);
                            System.out.println("Developer updated successfully");
                        }
                        break;

                    case 4:
                        scanner.nextLine();
                        System.out.println("Enter new genre: ");
                        String newGenre = scanner.nextLine();


                        if (newGenre.isBlank()){
                            System.out.println("Invalid genre.");
                        } else{
                            gameToUpdate.setGenre(newGenre);
                            System.out.println("Genre updated successfully.");
                        }

                        break;
                    case 5:
                        System.out.println("Enter new release year");
                        int newYear = scanner.nextInt();

                        if (newYear < 1958 || newYear > 2026){
                            System.out.println("Invalid release year");
                        }else {
                            gameToUpdate.setReleaseYear(newYear);
                            System.out.println("Release your updated successfully");
                        }
                        break;

                    case 6:
                        System.out.println("Enter new pu blic rating:");
                        double newRating = scanner.nextDouble();

                        if (!Double.isFinite(newRating) || newRating < 0 || newRating > 10){
                            System.out.println("Invalid rating");
                        } else {
                            gameToUpdate.setPublicRating(newRating);
                            System.out.println("Rating updated successfully. ");
                        }
                        break;

                    case 7:
                        scanner.nextLine();
                        System.out.println("Have you completed the game? (yes/no): ");
                        String answer = scanner.nextLine();

                        if (answer.equalsIgnoreCase("yes")){
                            gameToUpdate.setCompleted(true);
                            System.out.println("Completion status updated.");
                        } else if (answer.equalsIgnoreCase("no")){
                            gameToUpdate.setCompleted(false);
                            System.out.println("Completion status updated.");
                        }else {
                            System.out.println("Invalid input Enter yes or no.");
                        }
                       break;

                    default:
                        System.out.println("Invalid update option.");

                }






                break;
            case 5:
                System.out.println("Display game selected");
                for (VideoGame game: games){
                    System.out.println("Game-Id: " + game.getGameID());
                    System.out.println("Title: " + game.getTitle());
                    System.out.println("Developer: " + game.getDeveloper());
                    System.out.println("Genre: " + game.getGenre());
                    System.out.println("Release Year: " +game.getReleaseYear());
                    System.out.println("Public Rating: " +game.getPublicRating());
                    System.out.println("Completion status: " + game.getCompleted());
                    System.out.println("..................");
                }

                break;
            case 6:
                System.out.println("Calculate average selected");
                double totalRating = 0.0;
                if (games.isEmpty()){
                    System.out.println("No games available to calculate a average.");
                    break;
                }

                for (VideoGame game: games) {
                    totalRating += game.getPublicRating();


                }
                double averageRating = totalRating / games.size();
                System.out.println("Average Game Rating: " + averageRating);

                break;


        }

    } catch (InputMismatchException e) {
      System.out.println("Error that was a invalid option, please try again.");

      scanner.next();
    }


}

    }


}
