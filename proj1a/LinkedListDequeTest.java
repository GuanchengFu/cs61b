/** Performs some basic linked list tests. */
public class LinkedListDequeTest {
	
	/* Utility method for printing out empty checks. */
	public static boolean checkEmpty(boolean expected, boolean actual) {
		if (expected != actual) {
			System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	/* Utility method for printing out empty checks. */
	public static boolean checkSize(int expected, int actual) {
		if (expected != actual) {
			System.out.println("size() returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	/* Utility method for printing out element checks. */
	public static boolean checkEqu(String expected, String actual){
		if (expected == null)
			return actual == null;
		if (!expected.equals(actual)) {
			System.out.println("item returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	/* Prints a nice message based on whether a test passed. 
	 * The \n means newline. */
	public static void printTestStatus(boolean passed) {
		if (passed) {
			System.out.println("Test passed!\n");
		} else {
			System.out.println("Test failed!\n");
		}
	}

	/** Adds a few things to the list, checking isEmpty() and size() are correct, 
	  * finally printing the results. 
	  *
	  * && is the "and" operation. */
	public static void addIsEmptySizeTest() {
		System.out.println("Running add/isEmpty/Size test.");


		LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

		boolean passed = checkEmpty(true, lld1.isEmpty());

		lld1.addFirst("front");
		
		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
		passed = checkSize(1, lld1.size()) && passed;
		passed = checkEmpty(false, lld1.isEmpty()) && passed;

		lld1.addLast("middle");
		passed = checkSize(2, lld1.size()) && passed;

		lld1.addLast("back");
		passed = checkSize(3, lld1.size()) && passed;

		System.out.println("Printing out deque: ");
		lld1.printDeque();

		printTestStatus(passed);

	}

	/** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
	public static void addRemoveTest() {

		System.out.println("Running add/remove test.");


		LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		// should be empty 
		boolean passed = checkEmpty(true, lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty 
		passed = checkEmpty(false, lld1.isEmpty()) && passed;

		lld1.removeFirst();
		// should be empty 
		passed = checkEmpty(true, lld1.isEmpty()) && passed;

		printTestStatus(passed);

	}

	/** Test whether the deque works properly when there are no elements inside the deque.*/
	public static void noElementTest(){

		System.out.println("Running test for deque with no elements.");

		LinkedListDeque<String> lld = new LinkedListDeque<>();

		boolean passed = checkEmpty(true, lld.isEmpty());

		passed = checkEqu(null, lld.removeFirst()) && passed;

		passed = checkEqu(null, lld.removeFirst()) && passed;

		passed = checkEqu(null, lld.get(0)) && passed;

		lld.addLast("Test");

		passed = checkEqu("Test", lld.removeFirst()) && passed;

		passed = checkEqu(null, lld.removeFirst()) && passed;

		passed = checkEqu(null, lld.removeFirst()) && passed;

		passed = checkEqu(null, lld.get(0)) && passed;

		lld.addFirst("Test again");

		passed = checkEqu("Test again", lld.removeLast()) && passed;

		passed = checkEqu(null, lld.removeFirst()) && passed;

		passed = checkEqu(null, lld.removeFirst()) && passed;

		passed = checkEqu(null, lld.get(0)) && passed;

		printTestStatus(passed);
	}

	public static void oneElementTest(){
		LinkedListDeque<String> lld = new LinkedListDeque<>();

		System.out.println("Running test for deque with one element.");
		lld.addFirst("One");

		lld.addLast("Two");

		boolean passed = checkEqu("Two", lld.getRecursive(1));

		passed = checkEqu("Two", lld.get(1)) && passed;

		lld.addFirst("Zero");

		passed = checkEqu("Zero", lld.getRecursive(0)) && passed;

		passed = checkEqu("One", lld.getRecursive(1))&& passed;

		lld.printDeque();
		printTestStatus(passed);
	}

	public static void addFirstTest(){
		System.out.println("Running add First test.");

		LinkedListDeque<String> lld= new LinkedListDeque<>();

		lld.addFirst("Four");

		lld.addFirst("Three");

		lld.addFirst("Two");

		lld.addFirst("One");

		boolean passed = checkEqu("One", lld.removeFirst());

		passed = checkEqu("Four", lld.removeLast())  && passed;

		passed = checkEqu("Two", lld.get(0)) && passed;

		passed = checkEqu("Three", lld.getRecursive(1)) && passed;

		printTestStatus(passed);
	}

	public static void addLastTest(){
		System.out.println("Running add Last test.");

		LinkedListDeque<String> lld = new LinkedListDeque<>();

		lld.addLast("Four");

		lld.addFirst("Zero");

		lld.addLast("Five");

		lld.addFirst("One");

		boolean passed = checkEqu("Five", lld.getRecursive(3));

		lld.removeLast();

		passed = checkEqu("One", lld.removeFirst()) && passed;

		passed = checkEqu("Zero", lld.get(0)) && passed;

		passed = checkEqu("Four", lld.get(1)) && passed;

		printTestStatus(passed);
	}

	public static void main(String[] args) {
		System.out.println("Running tests.\n");
		addIsEmptySizeTest();
		addRemoveTest();
		noElementTest();
		oneElementTest();
		addFirstTest();
		addLastTest();
	}
} 