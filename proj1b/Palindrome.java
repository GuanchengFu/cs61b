public class Palindrome{

    /**Return a Deque which contains the given word in the same order.
     * input: "persiflage"
     * return: A Deque contains "p", "e" ... "g", "e"*/

    public Deque<Character> wordToDeque(String word){
        //Initialize a LinkedListDeque, and then add every elements into it?
        LinkedListDeque<Character> lld = new LinkedListDeque<>();
        for (int i =0; i < word.length(); i ++){
            lld.addLast(word.charAt(i));
        }
        return lld;
    }

    public boolean isPalindrome(String word){
        if (word.length() == 0 || word.length() == 1)
            return true;
        String reverse = getReverse(word);
        return word.equals(reverse);
    }

    /**Return the reverse version of the given string word.
     * This function will only be invoked if the word length is greater or equal to 2.*/
    public String getReverse(String word){
        String temp = "";
        Deque d = wordToDeque(word);
        while(!d.isEmpty()){
            temp += d.removeLast();
        }
        return temp;
    }

    /**Return whether or not the word fulfils the requirements mentioned in the cc.*/
    public boolean isPalindrome(String word, CharacterComparator cc){
        if (word.length() == 0 || word.length() == 1)
            return true;
        Deque<Character> d = wordToDeque(word);
        for (int i = 0; i < word.length() / 2; i ++){
            if (!cc.equalChars(d.removeFirst(), d.removeLast()))
                return false;
        }
        return true;
    }
}