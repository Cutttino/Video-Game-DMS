
/**
 * Represents specific video game data that can be entered.
 */
public class VideoGame {

    private int gameID;
    private String title;
    private String developer;
    private String genre;
    private int releaseYear;
    private double publicRating;
    private boolean completed;

    /**
     * Creates a new VideoGame Object with the input given for each one.
     *
     * @param gameID is there so each game has a unique ID and has no duplicates
     * @param title is there so each video game has a title
     * @param developer is there so each game has a developer
     * @param genre is there so each game has a genre that can be entered
     * @param releaseYear is there so each game has a release year
     * @param  publicRating is there so each game has a public rating
     * @param  completed is there so each game can have a complete or incomplete option
     */
    public VideoGame(int gameID, String title, String developer, String genre, int releaseYear, double publicRating, boolean completed) {
        this.gameID = gameID;
        this.title = title;
        this.developer = developer;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.publicRating = publicRating;
        this.completed = completed;


    }
    /**
     * @return  the game id
     */

    public int getGameID() {
        return gameID;
    }
    /**
     * @return  the game title
     */
    public String getTitle() {
        return title;
    }
    /**
     * @return  the games developer
     */
    public String getDeveloper() {
        return developer;
    }
    /**
     * @return  the game genre
     */
    public String getGenre() {
        return genre;
    }
    /**
     * @return  the game release year
     */
    public int getReleaseYear() {
        return releaseYear;
    }
    /**
     * @return  the game public rating
     */
    public double getPublicRating() {
        return publicRating;
    }
    /**
     * @return  the game completion status
     */
    public boolean getCompleted() {
        return completed;
    }
    /**
     * Updates the game's ID if the new ID is positive.
     * Leaves the current ID unchanged if the new ID is zero or negative.
     *
     * @param gameID the new ID for the game
     */
    public void setGameID(int gameID) {
        if (gameID > 0) {
            this.gameID = gameID;
        } else {
            System.out.println("Invalid input");
        }

    }
    /**
     * Updates the title if the input is not empty
     * Leaves the current title unchanged if the new title is empty or blank.
     *
     * @param title the new title for the game
     */
    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            System.out.println("Empty string input");
        } else {
            this.title = title;
        }

    }
    /**
     * Updates the game's developer if the new developer is not blank or empty
     * Leaves the current developer if the new developer is blank or empty
     *
     * @param developer the new developer for the game
     */

    public void setDeveloper(String developer) {
        if (developer == null || developer.isBlank()) {
            System.out.println("Empty String input");
        } else {
            this.developer = developer;
        }
    }
    /**
     * Updates the game's genre if the genre is not empty or blank
     * Leaves the current genre unchanged if the new genre is blank or empty.
     *
     * @param genre the new genre for the game
     */
    public void setGenre(String genre) {
        if (genre == null || genre.isBlank()) {
            System.out.println("Empty String input");
        } else {
            this.genre = genre;
        }

    }
    /**
     * Updates the game's release year if the new release year is between or on the year 1958 - 2026
     * Leaves the current releaseYear unchanged if the new releaseYear is less than 1958 or greater than 2026.
     *
     * @param releaseYear the new releaseYear for the game
     */
    public void setReleaseYear(int releaseYear) {
        if (releaseYear < 1958 || releaseYear > 2026){
            System.out.println("Enter year between or on 1958 - 2026 (current year)");
        } else {
            this.releaseYear = releaseYear;
        }
    }
    /**
     * Updates the game's publicRating if the new publicRating is on or between 0.0 and 10.0.
     * Leaves the current publicRating unchanged if the new publicRating is not between or on 0.0 and 10.0.
     *
     * @param publicRating the new publicRating for the game
     */
    public void setPublicRating(double publicRating) {
        if (publicRating < 0.0 || publicRating > 10.0 || !Double.isFinite(publicRating)){
            System.out.print("Enter rating between 0.0 & 10.0");
        } else {
            this.publicRating = publicRating;
        }

    }
    /**
     * Updates the game's completion status
     *
     * @param completed the new completion status for the game
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

