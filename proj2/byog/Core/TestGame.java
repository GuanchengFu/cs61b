package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.junit.Test;
import static org.junit.Assert.*;
public class TestGame {

    TERenderer ter = new TERenderer();

    Game g = new Game();

    //SEED = 12345678

    TETile[][] world = g.getWorld();

    Game.Coordinate entry;

    int width = 4;
    int height = 6;

    /**Initialize the world.*/
    public void setUp() {
        ter.initialize(80, 30);
        for (int i = 0; i < 80; i ++) {
            for (int j = 0; j < 30; j ++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        buildRoom();
        ter.renderFrame(world);
    }

    /**Build a test room */
    public void buildRoom() {
        Game.Coordinate entry1 = new Game.Coordinate(76, 16);
        Game.Coordinate entry2 = new Game.Coordinate(76, 14);
        world[76][16] = Tileset.WALL;
        world[76][14] = Tileset.WALL;
        world[76][15] = Tileset.FLOOR;
        //entry = g.buildRightRoom(width, height, entry1, entry2);
    }

    /**Should test the following conditions:
     * 4 basic conditions
     * The corner condition*/
    public void TestFindEntry() {
        setUp();
        assertEquals(76, entry.x);
        assertEquals(18, entry.y);

        Game.Coordinate x = g. findEntry(3, entry, width, height);
        assertEquals(79, x.x);
        assertEquals(18, x.y);

        x = g.findEntry(0, entry, width, height);
        assertEquals(entry.x, x.x);
        assertEquals(entry.y, x.y);

        x = g.findEntry(5, entry, width, height);
        assertEquals(79, x.x);
        assertEquals(16, x.y);

        x = g.findEntry(8, entry, width, height);
        assertEquals(79, x.x);
        assertEquals(13, x.y);

        x = g.findEntry(12, entry, width, height);
        assertEquals(76, x.x);
        assertEquals(14, x.y);

        x = g.findEntry(21, entry, width, height);
        assertEquals(79, x.x);
        assertEquals(16, x.y);
    }

    //@Test
    public void TestGetDirection() {
        setUp();
        int test = g.getDirection(new Game.Coordinate(76, 18),
                new Game.Coordinate(78, 18), entry);
        assertEquals(1, test);

        test = g.getDirection(new Game.Coordinate(79, 16),
                new Game.Coordinate(79, 14), entry);
        assertEquals(4, test);

        test = g.getDirection(new Game.Coordinate(77, 13),
                new Game.Coordinate(77, 13), entry);
        assertEquals(2, test);

        test = g.getDirection(new Game.Coordinate(76, 15),
                new Game.Coordinate(76, 13), entry);
        assertEquals(3, test);
    }

    //@Test
    public void TestValidEntries() {
        setUp();
        assertTrue(g.checkValidExit(new Game.Coordinate(76, 17)));
        assertFalse(g.checkValidExit(new Game.Coordinate(76, 18)));
        assertFalse(g.checkValidExit(new Game.Coordinate(79, 16)));
        assertFalse(g.checkValidExit(new Game.Coordinate(76, 13)));
        assertTrue(g.checkValidExit(new Game.Coordinate(77, 13)));
    }

    @Test
    public void TestGetLeftSpace() {
        setUp();
        assertEquals(6, g.getLeftSpace(new Game.Coordinate(6, 0)));
        world[3][0] = Tileset.WALL;
        assertEquals(2, g.getLeftSpace(new Game.Coordinate(6, 0)));
        world[4][0] = Tileset.WALL;
        assertEquals(0, g.getLeftSpace(new Game.Coordinate(4, 0)));
        assertEquals(1, g.getLeftSpace(new Game.Coordinate(6, 0)));
    }

    public static void main(String[] args) {
        TestGame t = new TestGame();
        t.setUp();
    }
}
