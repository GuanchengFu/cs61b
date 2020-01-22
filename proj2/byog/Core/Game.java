package byog.Core;

import edu.princeton.cs.introcs.StdDraw;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.awt.*;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    //enum state{menu, enter_seed}
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     * There always only one StdDraw object, the methods are used to adjust its arguments other than
     * creating a new object.
     * Things need to be done:
     * 1. Display the menu  -- done
     *  --
     * 2. Waiting for the input from user -- partly done
     * 3. Do the related stuff
     */
    public void playWithKeyboard() {
        //
        boolean end = false;
        displayMenu();
        while (!end) {
            if (StdDraw.hasNextKeyTyped()) {
                switch(Character.toLowerCase(StdDraw.nextKeyTyped())) {
                    case 'n': enterSeed(); break;
                    case 'l': System.out.println("Need to implement some method for store."); break;
                    case 'q': System.exit(0); break;
                    default: System.out.println("Not valid choice.");
                }
            }
        }
    }

    /**Creates the related interface for the user to input the Seed of the game.*/
    public void enterSeed() {
        StdDraw.clear(new Color(0, 0, 0));
        StdDraw.text(275, 300, "Enter Seed:");
        boolean end = false;
        while (!end) {

        }
        createWorld();
    }

    /**Create the world using the seed input by the user.*/
    public void createWorld() {

    }

    /**Print out the original menu where the user can choose from:
     * 1.New Game: Start a new game and input the seed to generate the map.
     * 2.Load Game:
     * 3.Quit: Quit the Game.
     * The operations are done by using keyboards.*/
    public void displayMenu() {
        StdDraw.setCanvasSize(550, 600);
        StdDraw.setXscale(0, 550);
        StdDraw.setYscale(0, 600);
        StdDraw.clear(new Color(0, 0, 0));
        StdDraw.setPenColor(new Color(255, 255, 255));


        Font font = new Font("Monaco", Font.BOLD, 26);
        StdDraw.setFont(font);


        StdDraw.text(275, 500, "CS61B: Trash Game");
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 14));
        StdDraw.textLeft(275, 475, "--Created By: Guancheng Fu");

        StdDraw.text(275, 350, "New Game (N)");
        StdDraw.text(275, 300, "Load Game (L)");
        StdDraw.text(275, 250, "Quit (Q)");
        StdDraw.show();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        TETile[][] finalWorldFrame = null;
        return finalWorldFrame;
    }
}
