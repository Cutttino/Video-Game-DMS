public class VideoGame {

    private int gameID;
    private String title;
    private String developer;
    private String genre;
    private int releaseYear;
    private double publicRating;
    private boolean completed;


    public VideoGame(int gameID, String title, String developer, String genre, int releaseYear, double publicRating, boolean completed) {
        this.gameID = gameID;
        this.title = title;
        this.developer = developer;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.publicRating = publicRating;
        this.completed = completed;


    }

    public int getGameID() {
        return gameID;
    }

    public String getTitle() {
        return title;
    }

    public String getDeveloper() {
        return developer;
    }

    public String getGenre() {
        return genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public double getPublicRating() {
        return publicRating;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setGameID(int gameID) {
        if (gameID > 0) {
            this.gameID = gameID;
        } else {
            System.out.println("Invalid input");
        }

    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            System.out.println("Empty string input");
        } else {
            this.title = title;
        }

    }

    public void setDeveloper(String developer) {
        if (developer == null || developer.isBlank()) {
            System.out.println("Empty String input");
        } else {
            this.developer = developer;
        }
    }

    public void setGenre(String genre) {
        if (genre == null || genre.isBlank()) {
            System.out.println("Empty String input");
        } else {
            this.genre = genre;
        }

    }

    public void setReleaseYear(int releaseYear) {
        if (releaseYear < 1958 || releaseYear > 2026){
            System.out.println("Enter year between or on 1958 - 2026 (current year)");
        } else {
            this.releaseYear = releaseYear;
        }
    }

    public void setPublicRating(double publicRating) {
        if (publicRating < 0.0 || publicRating > 10.0 || !Double.isFinite(publicRating)){
            System.out.print("Enter rating between 0.0 & 10.0");
        } else {
            this.publicRating = publicRating;
        }

    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

