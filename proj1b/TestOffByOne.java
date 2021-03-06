import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
   public void testOffByOne(){
       assertTrue(offByOne.equalChars('a', 'b'));
       assertTrue(offByOne.equalChars('d', 'c'));
       assertTrue(offByOne.equalChars('&', '%'));
       assertTrue(offByOne.equalChars('%', '&'));
       assertFalse(offByOne.equalChars('a', '&'));
       assertFalse(offByOne.equalChars('!', 'd'));
       assertFalse(offByOne.equalChars('d', 'a'));
       assertTrue(offByOne.equalChars('A', 'B'));
       assertTrue(offByOne.equalChars('B', 'A'));
       assertFalse(offByOne.equalChars('a', 'B'));
       assertFalse(offByOne.equalChars('A', 'b'));
   }
}

