import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);

        Deque b = palindrome.wordToDeque("I have an egg");
        String actual2 = "";
        for (int i = 0; i < "I have an egg".length(); i++) {
            actual2 += b.removeFirst();
        }
        assertEquals("I have an egg", actual2);
    }

    @Test
    public void testIsPalindrome(){
        assertFalse(palindrome.isPalindrome("cat"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("A"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertFalse(palindrome.isPalindrome("Noon"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertFalse(palindrome.isPalindrome("“aaaaab”"));

        //Test by using the rule OffByOne
        OffByOne obo = new OffByOne();
        assertTrue(palindrome.isPalindrome("flake", obo));
        assertTrue(palindrome.isPalindrome("flame", obo));
        assertTrue(palindrome.isPalindrome("dlame", obo));
        assertTrue(palindrome.isPalindrome("flbme", obo));
        assertFalse(palindrome.isPalindrome("flane", obo));
        assertFalse(palindrome.isPalindrome("Flake", obo));
    }

    /**
    @Test
    public void testGetReverse(){
        String input = "I have an egg";
        String actual = palindrome.getReverse(input);
        String expected = "gge na evah I";
        assertEquals(expected, actual);
    }*/
}
