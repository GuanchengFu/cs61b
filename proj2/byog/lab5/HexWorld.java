package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 * A big hexagon will consist of small three dimensional hexagons.
 */
public class HexWorld {

    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 123;
    private static final Random RANDOM = new Random(SEED);

    /**Add a hexagon with length specified in length variable at position (x, y)
     * The hexagon will span from the upper-left corner to the bottom-right corner.
     * @param tile the tile used in the hexagon
     * position(0, 0) is the bottom left corner.*/
    private static void addHexagon(int length, int positionX, int positionY, TETile[][] world, TETile tile){
        int l = length;
        for (int i = 0; i < length; i ++) {
            drawLine(l, positionX, positionY, world, tile);
            l += 2;
            positionX -= 1;
            positionY -= 1;
        }
        l -= 2;
        positionX += 1;
        for (int i = 0; i < length; i ++) {
            drawLine(l, positionX, positionY, world, tile);
            l -= 2;
            positionX += 1;
            positionY -= 1;
        }
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.GRASS;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.WATER;
            case 3: return Tileset.MOUNTAIN;
            case 4: return Tileset.SAND;
            default: return Tileset.TREE;
        }
    }

    /**Draw a line with length width at position (x, y) and tile tile.*/
    private static void drawLine(int width, int x, int y, TETile[][] world, TETile tile) {
        for (int i = 0; i < width; i += 1) {
            world[x + i][y] = tile;
        }
    }


    private static void drawMulHex(int size, int x, int y,TETile[][] world, int number) {
        for (int i = 0; i < number; i ++) {
            addHexagon(size, x, y, world, randomTile());
            int[] temp = findNextLeft(size, x, y);
            x = temp[0];
            y = temp[1];
        }
    }

    /**Draw a hexagon with length "length at position x, y"*/
    public static void drawHexagon(int size, int positionX, int positionY, TETile[][] world) {
        int number = 0;
        int[] temp;
        for (int i = 0; i < 5; i ++) {
            switch (i) {
                case 0: number = 3; break;
                case 1: number = 4; break;
                case 2: number = 5; break;
                case 3: number = 4; break;
                case 4: number = 3; break;
                default: System.out.println("Wrong!");
            }
            drawMulHex(size, positionX, positionY, world, number);
            switch (i) {
                case 0: temp = findNextRight(size, positionX, positionY); break;
                case 1: temp = findNextRight(size, positionX, positionY); break;
                default: temp = findNextBelow(size, positionX, positionY);
            }
            positionX = temp[0];
            positionY = temp[1];
        }
    }


    public static void initializeWorld(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    /**Find the next begin position (x', y') stored in the array.*/
    private static int[] findNextLeft(int size, int x, int y) {
        int[] result = new int[2];
        result[0] = x - size - size + 1;
        result[1] = y - size;
        return result;
    }

    private static int[] findNextRight(int size, int x, int y) {
        int[] result = new int[2];
        result[0] = x + 2 * size - 1;
        result[1] = y - size;
        return result;
    }

    private static int[] findNextBelow(int size, int x, int y) {
        int[] result = new int[2];
        result[0] = x;
        result[1] = y - 2 * size;
        return result;
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);


        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeWorld(world);

        drawHexagon(4, 20, 49, world);
        ter.renderFrame(world);
    }
}
