import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void test() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        String message = "";
        while (true) {
            double random = StdRandom.uniform();
            int temp = StdRandom.uniform(100);
            if (ads.size() == 0) {
                if (random < 0.5) {
                    sad.addFirst(temp);
                    ads.addFirst(temp);
                    message += "addFirst(" + temp + ")" + "\n";
                } else {
                    sad.addLast(temp);
                    ads.addLast(temp);
                    message += "addLast(" + temp + ")" + "\n";
                }
            } else {
                if (random < 0.2) {
                    message += "removeFirst()\n";
                    assertEquals(message.trim(), sad.removeFirst(), ads.removeFirst());
                } else if (random >= 0.2 && random < 0.4) {
                    message += "removeLast()\n";
                    assertEquals(message.trim(), sad.removeLast(), ads.removeLast());
                } else if (random >= 0.4 && random < 0.7) {
                    sad.addFirst(temp);
                    ads.addFirst(temp);
                    message += "addFirst(" + temp + ")" + "\n";
                } else {
                    sad.addLast(temp);
                    ads.addLast(temp);
                    message += "addLast(" + temp + ")" + "\n";
                }
            }
        }
    }

}
