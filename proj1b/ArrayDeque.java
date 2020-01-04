public class ArrayDeque<T> implements  Deque<T>{
    private int nextFirst;
    private int nextLast;
    private int size;
    private T[] items;

    public ArrayDeque(){
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }
    // Add remove size
    // get use iteration.
    /**Adds an item of Type T to the front of the deque
     * Take constant time*/
    @Override
    public void addFirst(T item){
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst, items.length);
        size += 1;
        resize();
    }

    /**Adds an item of Type T to the back of the deque
     * Take constant time*/
    @Override
    public void addLast(T item){
        items[nextLast] = item;
        nextLast = plusOne(nextLast, items.length);
        size += 1;
        resize();
    }
    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    /**Take constant time*/
    @Override
    public int size(){
        return size;
    }

    /**Prints the items in the deque from first to last, separated by a space.*/
    @Override
    public void printDeque(){
        int beginIndex = plusOne(nextFirst, items.length);
        String result = "";
        for (int i = 0; i < size; i ++){
            result += items[beginIndex];
            result += " ";
            beginIndex = plusOne(beginIndex, items.length);
        }
        System.out.println(result.strip());
    }

    /**Removes and returns the item at the front of the deque.  Return null if no such
     * element exists.
     * Take constant time.*/
    @Override
    public T removeFirst(){
        if (size == 0)
            return null;
        else{
            T temp = items[plusOne(nextFirst, items.length)];
            items[plusOne(nextFirst, items.length)] = null;
            size -= 1;
            //resize needed.
            nextFirst = plusOne(nextFirst, items.length);
            resize();
            return temp;
        }
    }
    /**Removes and returns the item at the back of the deque.  Return null if no such
     * element exists.
     * Take constant time.*/
    @Override
    public T removeLast(){
        if (size == 0)
            return null;
        else{
            T temp = items[minusOne(nextLast, items.length)];
            items[minusOne(nextLast, items.length)] = null;
            size -= 1;
            nextLast = minusOne(nextLast, items.length);
            resize();
            return temp;
        }
    }

    /**Gets the item at the given index, where 0 is the front , 1 is the next item.
     * return null if no such element exists.
     * take constant time.*/
    @Override
    public T get(int index){
        if (index <= size - 1){
            int temp = plusOne(nextFirst, items.length);
            return items[(temp + index) % items.length];
        }else
            return null;
    }

    /**Return the position for the nextFirst pointer based on the current pointer position.
     * This method will not examine whether the size is valid or not.*/
    private int minusOne(int index, int length){
        if (index == 0){
            return length - 1;
        }
        return index - 1;
    }

    /**Return the position for the nextLast pointer based on the current pointer position.
     * This method will not examine whether the size is valid or not.*/
    private int plusOne(int index, int length){
        if (index == length - 1){
            return 0;
        }
        return index + 1;
    }

    private boolean full(){
        return size == items.length;
    }

    /**There exists arrays with zero elements, which should be paid special attention.*/
    private void resize(){
        T[] temp = null;
        double load_rate = ((double) size )/ items.length;
        if (full()){
            temp = (T[]) new Object[2 * items.length];
        }
        if (load_rate <= 0.25)
            temp = (T[]) new Object[items.length / 2];
        if (temp == null){
            return;
        }
        int index = 0;
        int next_original = minusOne(nextLast, items.length);
        int next = 0;
        while(index < size){
            temp[next] = items[next_original];
            next_original = minusOne(next_original, items.length);
            index += 1;
            next = minusOne(next, temp.length);
        }
        items = temp;
        nextFirst = next;
        if (items.length == 1)
            nextLast = 0;
        else
            nextLast = 1;
    }
    /**When the entire deque is full, the nextFirst will be 1 less than the nextLast.
     * The first element is in the location items[addFirst + 1]*/
}
