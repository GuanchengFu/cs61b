package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.junit.Test;
import static org.junit.Assert.*;
public class TestGame {

    TERenderer ter = new TERenderer();
    TETile[][] world = new TETile[80][30];


    /**Initialize the world.*/
    public void setUp() {
        ter.initialize(80, 30);
        for (int i = 0; i < 80; i ++) {
            for (int j = 0; j < 30; j ++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }


    /**Should test the following conditions:
     * 4 basic conditions
     * The corner condition*/
    @Test
    public void TestGetDirection() {

    }

}
