import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

class VideoGameTest {

    @Test
    void testUpdateTitle(){
        VideoGame testGame = new VideoGame(1,"Zelda Skyward Sword","Shigeru Miyamoto","Adventure", 2011, 8.5, true);

        testGame.setTitle("Zelda Twilight Princess");
        assertEquals("Zelda Twilight Princess", testGame.getTitle());

    }
    @Test
    void testAddGame(){
        ArrayList <VideoGame> testGames = new ArrayList<>();
        VideoGame newGame = new VideoGame(1,"Zelda Skyward Sword","Shigeru Miyamoto","Adventure", 2011, 8.5, true);
        boolean added = Main.addGame(testGames,newGame);
        Assertions.assertTrue(added);
        Assertions.assertEquals(1,testGames.size());

    }
    @Test
    void  testRemoveGame(){
        ArrayList <VideoGame> testGames = new ArrayList<>();
        VideoGame testGame = new VideoGame(1,"Zelda Skyward Sword","Shigeru Miyamoto","Adventure", 2011, 8.5, true);
        testGames.add(testGame);
        boolean removed = Main.removeGame(testGames, 1);
        Assertions.assertTrue(removed);
        Assertions.assertTrue(testGames.isEmpty());


    }
   @Test
    void testAverageRating() {
       ArrayList <VideoGame> testGames = new ArrayList<>();
       testGames.add(new VideoGame(1,"Zelda Skyward Sword","Shigeru Miyamoto","Adventure", 2011, 8.0, true));
       testGames.add(new VideoGame(2,"God of war","Santa Monica Studio","Action", 2018, 9.0, true));
       testGames.add(new VideoGame(3,"Eldin Ring","FromSoftware","RPG", 2022, 10.0, true));
       double actualAverage = Main.calculateAverageRating(testGames);
       Assertions.assertEquals (9.0, actualAverage , 0.0001);

   }
   @Test
    void testOpenFile() {
       File testFile = new File("src/games.txt");
       Assertions.assertDoesNotThrow(() -> {
           try (Scanner fileScanner = new Scanner(testFile)) {
               Assertions.assertTrue(fileScanner.hasNextLine());

           }
       });
   }



}
