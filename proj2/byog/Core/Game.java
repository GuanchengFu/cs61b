package byog.Core;

import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.awt.*;
import java.io.StreamCorruptedException;
import java.util.Random;

enum state{menu, enter_seed, world_created}
public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /* The max width and height of a room. */
    public static final int MAX_WIDTH = 8;
    public static final int MAX_HEIGHT = 8;

    private TETile[][] world = new TETile[WIDTH][HEIGHT];
    private String SEED = "";
    //For test purpose only, change this later.
    private static final Random RANDOM = new Random(12345678);
    state progState = state.menu;

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
        //displayMenu();
        //handleInput();
        //System.out.print(SEED);
        createWorld();

    }

    /**Creates the related interface for the user to input the Seed of the game.
     * Set the parameters and display the SEED input by the user to the background.*/
    public void enterSeedInterface() {
        StdDraw.clear(new Color(0, 0, 0));
        StdDraw.text(275, 300, "Enter Seed:");
        StdDraw.text(275, 275, SEED);
    }

    /**Handle all the conditions where the user input something.
     * Make decisions based on the condition of the parameter "progState".*/
    public void handleInput() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                if (progState == state.menu) {
                    char temp = Character.toLowerCase(StdDraw.nextKeyTyped());
                    switch(temp) {
                        case 'n': enterSeedInterface(); progState = state.enter_seed; break;
                        case 'l': System.out.println("Need to implement some method for store."); break;
                        case 'q': System.exit(0); break;
                        default: System.out.println("Not valid choice: " + temp);
                    }
                } else if (progState == state.enter_seed) {
                    char temp = StdDraw.nextKeyTyped();
                    if (Character.isDigit(temp)) {
                        SEED += temp;
                        enterSeedInterface();
                    } else if (temp == '\n'){
                        progState = state.world_created;
                        //Try to create the world!
                        createWorld();
                    } else {
                        System.out.println("Not valid input:" + temp);
                    }
                }
            }
        }
    }






    /**Create the world using the seed input by the user.
     * The world is connected by one room - oen hallway - one room,
     * which means that each room must connect with a hallway.*/
    public void createWorld() {
        ter.initialize(WIDTH, HEIGHT);
        initializeWorld(world);

        boolean preRoom = true;
        int number = 0;
        Coordinate[] next_entry;

        next_entry = createInitialRoom();
        next_entry = createHallWay(7, next_entry[0], next_entry[1], next_entry[2]);
        setTileset(Tileset.FLOWER, next_entry[0]);
        setTileset(Tileset.FLOWER, next_entry[1]);



        ter.renderFrame(world);

        //createRoom();
    }



    private void setTileset(TETile tile, Coordinate coor) {
        world[coor.x][coor.y] = tile;
    }

    /**Create a initial room at the right side of the map.
     * @return return the entry for next room.*/
    private Coordinate[] createInitialRoom() {
        Coordinate[] result = new Coordinate[3];
        Coordinate entry1 = new Coordinate(76, 16);
        Coordinate entry2 = new Coordinate(76, 14);
        world[76][16] = Tileset.WALL;
        world[76][14] = Tileset.WALL;
        world[76][15] = Tileset.FLOOR;
        Coordinate corner = buildRightRoom(4, 6, entry1, entry2);
        result[0] = entry1;
        result[1] = entry2;
        result[2] = corner;
        return result;
    }

    /**Initialize the world array with Nothing at first.*/
    private static void initializeWorld(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    /**This function will create a room based on the arguments given.
     * @param length: the number of walls in one line.
     * @param width: the number of walls in one column.
     * @param entry_fir: The coordinate of the first entry point.
     *                 The left point in up-down case
     *                 The up point in left-right case
     * @param entry_sec: The coordinate of the second entry point.
     * @param first: the upper-left corner of the previous area.
     * May change this so that it will automatically stop when corrupt with existed walls.
     * */
    public Coordinate[] createRoom(int length, int width, Coordinate entry_fir, Coordinate entry_sec, Coordinate first) {
        int direction = getDirection(entry_fir, entry_sec, first);
        Coordinate entry_for_next;   //The upper-left corner of the new area.

        Coordinate[] result = new Coordinate[3];
        //Write the case for the down direction.
        switch (direction) {
            case 1:
                entry_for_next = buildUpRoom(length, width, entry_fir, entry_sec);
                break;
            case 2:
                entry_for_next = buildDownRoom(length, width, entry_fir, entry_sec);
                break;
            case 3:
                entry_for_next = buildLeftRoom(length, width, entry_fir, entry_sec);
                break;
            case 4:
                entry_for_next = buildRightRoom(length, width, entry_fir, entry_sec);
                break;
            default:
                System.out.println("Something went wrong about the getDirection!");
                entry_for_next = null;
        }

        Coordinate exit = getExit(length, width, entry_for_next);
        setTileset(Tileset.FLOOR, exit);
        if (world[exit.x - 1][exit.y] == Tileset.WALL) {
            result[0] = new Coordinate(exit.x - 1, exit.y);
            result[1] = new Coordinate(exit.x + 1, exit.y);
        } else {
            result[0] = new Coordinate(exit.x, exit.y + 1);
            result[1] = new Coordinate(exit.x, exit.y - 1);
        }
        result[2] = entry_for_next;
        return result;
    }

    public Coordinate[] createHallWay(int length, Coordinate entry_fir, Coordinate entry_sec, Coordinate first) {
        int direction = getDirection(entry_fir, entry_sec, first);
        switch (direction) {
            case 1:
            case 2:
                return createRoom(3, length, entry_fir, entry_sec, first);
            case 3:
            case 4:
                return createRoom(length, 3, entry_fir, entry_sec, first);
            default:
                System.out.println("Something wrong occurs in the createHallWay switch branches!");
                return null;
        }
    }

    /**Randomly select one possible exit from all the available walls.
     * @param length The number of walls in one row.
     * @param width The number of walls in one column.
     * @param entry The up-left corner of the area.
     * @return exit: Return the entry which is used as the entrance for the next area.
     * Later change this function so that it can choose the appropriate entry so that it has
     * enough space to build the room.*/
    private Coordinate getExit(int length, int width, Coordinate entry) {
        int availableChoices = length * 2 + (width - 2) * 2 - 5;
        boolean valid = false;
        Coordinate exit = null;
        while (!valid) {
            int exitNumber = RandomUtils.uniform(RANDOM, availableChoices);
            System.out.print(exitNumber);
            exit = findEntry(exitNumber, entry, length, width);
            if (checkValidExit(exit)) {
                valid = true;
            }
        }
        return exit;
    }







    /**@return Return the Coordinate of the upper-left corner.
     * This coordinate can contain all the information needed for further calculation.*/
    public Coordinate buildDownRoom(int length, int width, Coordinate entry_fir, Coordinate entry_sec) {
        int randomLength = length - 3;
        if (randomLength == 0) {
            buildDownWall(width - 1, entry_fir);
            buildDownWall(width - 1, entry_sec);
            buildLeftWall(1, new Coordinate(entry_sec.x, entry_sec.y - width + 1));
            fillWithFloor(1, width - 2, new Coordinate(entry_fir.x + 1, entry_fir.y - 1));
            return entry_fir;
        } else {
            int leftOut = RandomUtils.uniform(RANDOM, randomLength);
            int rightOut = randomLength - leftOut;
            //System.out.println(rightOut);
            buildLeftWall(leftOut, entry_fir);
            buildLeftWall(rightOut, new Coordinate(entry_sec.x + rightOut + 1, entry_sec.y));
            buildDownWall(width - 1, new Coordinate(entry_fir.x - leftOut, entry_fir.y));
            buildDownWall(width - 1, new Coordinate(entry_sec.x + rightOut, entry_sec.y));
            buildLeftWall(length - 2, new Coordinate(entry_sec.x + rightOut, entry_sec.y - width + 1));
            fillWithFloor(length - 2, width - 2, new Coordinate(entry_fir.x - leftOut + 1, entry_fir.y - 1));
            return new Coordinate(entry_fir.x - leftOut, entry_fir.y);
        }
    }

    public Coordinate buildUpRoom(int length, int width, Coordinate entry_fir, Coordinate entry_sec) {
        int randomLength = length - 3;
        if (randomLength == 0) {
            buildDownWall(width - 1, new Coordinate(entry_fir.x, entry_fir.y + width));
            buildDownWall(width - 1, new Coordinate(entry_sec.x, entry_sec.y + width));
            buildLeftWall(1, new Coordinate(entry_sec.x, entry_sec.y + width - 1));
            fillWithFloor(1, width - 2, new Coordinate(entry_fir.x + 1, entry_fir.y + width - 2));
            return new Coordinate(entry_fir.x, entry_fir.y + width - 1);
        } else {
            int leftOut = RandomUtils.uniform(RANDOM, randomLength);
            int rightOut = randomLength - leftOut;
            //System.out.println(rightOut);
            buildLeftWall(leftOut, entry_fir);
            buildLeftWall(rightOut, new Coordinate(entry_sec.x + rightOut + 1, entry_sec.y));
            buildDownWall(width - 1, new Coordinate(entry_fir.x - leftOut, entry_fir.y + width));
            buildDownWall(width - 1, new Coordinate(entry_sec.x + rightOut, entry_sec.y + width));
            buildLeftWall(length - 2, new Coordinate(entry_sec.x + rightOut, entry_sec.y + width - 1));
            fillWithFloor(length - 2, width - 2, new Coordinate(entry_fir.x - leftOut + 1, entry_fir.y + width - 2));
            return new Coordinate(entry_fir.x - leftOut, entry_fir.y + width - 1);
        }
    }

    public Coordinate buildLeftRoom(int length, int width, Coordinate entry_fir, Coordinate entry_sec) {
        int randomLength = width - 3;
        if (randomLength == 0) {
            buildLeftWall(length - 1, entry_fir);
            buildLeftWall(length - 1, entry_sec);
            buildDownWall(1, new Coordinate(entry_fir.x - length + 1, entry_fir. y));
            fillWithFloor(length - 2, 1, new Coordinate(entry_fir.x - length + 2, entry_fir.y - 1));
            return new Coordinate(entry_fir.x - length + 1, entry_fir.y);
        } else {
            int upOut = RandomUtils.uniform(RANDOM, randomLength);
            int downOut = randomLength - upOut;
            //System.out.println(rightOut);
            buildDownWall(upOut, new Coordinate(entry_fir.x, entry_fir.y + upOut + 1));
            buildDownWall(downOut, entry_sec);
            buildLeftWall(length - 1, new Coordinate(entry_fir.x, entry_fir.y + upOut));
            buildLeftWall(length - 1, new Coordinate(entry_sec.x, entry_sec.y - downOut));
            buildDownWall(width - 2, new Coordinate(entry_fir.x - length + 1, entry_fir.y + upOut));
            fillWithFloor(length - 2, width - 2, new Coordinate(entry_fir.x - length + 2, entry_fir.y + upOut - 1));
            return new Coordinate(entry_fir.x - length + 1, entry_fir.y + upOut);
        }
    }

    public Coordinate buildRightRoom(int length, int width, Coordinate entry_fir, Coordinate entry_sec) {
        int randomLength = width - 3;
        if (randomLength == 0) {
            buildLeftWall(length - 1, new Coordinate(entry_fir.x + length, entry_fir.y));
            buildLeftWall(length - 1, new Coordinate(entry_sec.x + length, entry_sec.y));
            buildDownWall(1, new Coordinate(entry_fir.x + length - 1, entry_fir.y));
            fillWithFloor(length - 2, 1, new Coordinate(entry_fir.x + 1, entry_fir.y - 1));
            return new Coordinate(entry_fir.x, entry_fir.y);
        } else {
            int upOut = RandomUtils.uniform(RANDOM, randomLength);
            int downOut = randomLength - upOut;
            //System.out.println(rightOut);
            buildDownWall(upOut, new Coordinate(entry_fir.x, entry_fir.y + upOut + 1));
            buildDownWall(downOut, entry_sec);
            buildLeftWall(length - 1, new Coordinate(entry_fir.x + length, entry_fir.y + upOut));
            buildLeftWall(length - 1, new Coordinate(entry_sec.x + length, entry_sec.y - downOut));
            buildDownWall(width - 2, new Coordinate(entry_fir.x + length - 1, entry_fir.y + upOut));
            fillWithFloor(length - 2, width - 2, new Coordinate(entry_fir.x + 1, entry_fir.y + upOut - 1));
            return new Coordinate(entry_fir.x , entry_fir.y + upOut);
        }
    }


    /**Build the left wall from entry to entry + length, entry not included.*/
    private void buildLeftWall(int length, Coordinate entry) {
        int workX = entry.x;
        int workY = entry.y;
        for (int i = 1; i <= length; i ++) {
            world[workX - i][workY] = Tileset.WALL;
        }
    }

    /**Build the down wall from entry to entry + length, entry not included.*/
    private void buildDownWall(int length, Coordinate entry) {
        int workX = entry.x;
        int workY = entry.y;
        for (int i = 1; i <= length; i ++) {
            world[workX][workY - i] = Tileset.WALL;
        }
    }

    /**Fill an area (width * height) will Tileset.FLOOR.*/
    private void fillWithFloor(int width, int height, Coordinate entry) {
        int x = entry.x;
        int y = entry.y;
        for (int row = 0; row < height; row ++) {
            for (int column = 0; column < width; column ++) {
                world[x + column][y - row] = Tileset.FLOOR;
            }
        }
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






    //The following functions have been tested.


    /**Find the entry whose distance between entry is number.
     * @param entry Entry is the up-left corner of the room area.
     *              entry should not be changed during the execution.
     * @param number number is [0, number]*/
    public Coordinate findEntry(int number, Coordinate entry, int width, int height) {
        Coordinate result = new Coordinate(entry.x, entry.y);
        int xValue = entry.x;
        int yValue = entry.y;
        int xLength = 0;
        int yLength = 0;
        while (number > 0) {
            if (xLength < width - 1 && yLength == 0) {
                xValue += 1;
                xLength += 1;
            } else if (xLength == width - 1 && yLength != height - 1) {
                yValue -= 1;
                yLength += 1;
            } else if (yLength == height - 1 && xLength != 0) {
                xValue -= 1;
                xLength -= 1;
            } else {
                yValue += 1;
                yLength -= 1;
            }
            number --;
        }

        return new Coordinate(xValue, yValue);
    }

    /**Get the direction of the room for the next entry.
     * @param first The upper left corner of the current area.
     * @return 1: The up direction.
     * @return 2: The down direction.
     * @return 3: The left direction.
     * @return 4: The right direction.
     * Throw exception and return 0 if non of the above conditions are not fulfilled.*/
    public int getDirection(Coordinate entry_fir, Coordinate entry_sec, Coordinate first) {
        if (entry_fir.y == entry_sec.y) {
            if (entry_fir.y == first.y) {
                return 1;
            } else {
                return 2;
            }
        }
        if (entry_fir.x == entry_fir.x) {
            if (entry_fir.x == first.x) {
                return 3;
            } else {
                return 4;
            }
        }
        return 0;
    }


    /**Check whether an entry can be used to connect with other entries.
     * should check the corner condition.*/
    public boolean checkValidExit(Coordinate entry) {
        int x = entry.x;
        int y = entry.y;
        if (x == 0 || x == WIDTH - 1 || y == 0 || y == HEIGHT - 1) {
            return false;
        } else {
            boolean validFlag1 = (world[x - 1][y] == Tileset.FLOOR) || (world[x + 1][y] == Tileset.FLOOR) || (world[x][y + 1] == Tileset.FLOOR) || (world[x][y - 1] == Tileset.FLOOR);
            boolean validFlag2 =  (world[x - 1][y] == Tileset.NOTHING) || (world[x + 1][y] == Tileset.NOTHING) || (world[x][y + 1] == Tileset.NOTHING) || (world[x][y - 1] == Tileset.NOTHING);
            return validFlag1 && validFlag2;
        }
    }


    /**This class is only used for record the coordinate of the tiles.*/
    public static class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**For test purpose only!*/
    public TETile[][] getWorld() {
        return world;
    }
}
