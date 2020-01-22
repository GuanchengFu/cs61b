public class LinkedListDeque<T> implements Deque<T> {
    /**A Deque implementation based on the Linked list design.
     * One special condition is when size = 0.
     * At this time, the list is a null pointer, which cannot be used.*/

    private DLList list;
    private int size;

    public LinkedListDeque() {
        this.size = 0;
    }

    /** Adds an item of type T to the front of the deque.*/
    @Override
    public void addFirst(T item) {
        if (this.size == 0) {
            list = new DLList(item);
        }else{
            list.addFirst(item);
        }
        size += 1;
    }

    /** Adds an item of type T to the back of the deque.*/
    @Override
    public void addLast(T item){
        if (this.size == 0){
            list = new DLList(item);
        }else{
            list.addLast(item);
        }
        size += 1;
    }

    public T getRecursive(int index){
        if (size == 0){
            return null;
        }else{
            return list.getRecursive(index, list.sentinel.next);
        }
    }

    /** Returns true if deque is empty, false otherwise.*/
    @Override
    public boolean isEmpty(){
        if (this.size == 0){
            return true;
        }
        return false;
    }

    /** Returns the number of items in the deque.*/
    @Override
    public int size(){
        return this.size;
    }

    /** Prints the items in the deque from first to last, separated by a space.*/
    public void printDeque(){
        list.printSelf();
    }

    /** Removes and returns the item at the front of the deque. If no such item exists, returns null.*/
    @Override
    public T removeFirst(){
        if (size == 0){
            return null;
        }
        size -= 1;
        return list.removeFirst();
    }
    /** Removes and returns the item at the back of the deque. If no such item exists, returns null.*/
    @Override
    public T removeLast(){
        if (size == 0){
            return null;
        }
        size -= 1;
        return list.removeLast();
    }

    @Override
    public T get(int index){
        if (size == 0){
            return null;
        }else{
            return list.get(index);
        }
    }
    private class DLList{

        private Node sentinel;
        private int size;

        /** Generate a list containing element x.*/
        public DLList(T x){
            size = 0;
            sentinel = new Node(x, null, null);
            sentinel.next = sentinel;
            sentinel.previous = sentinel;
            addLast(x);
        }

        public void addLast(T x){
            size += 1;
            Node last_ref = sentinel.previous;
            last_ref.next = new Node(x, last_ref, sentinel);
            sentinel.previous = last_ref.next;
        }

        /** This function should be invoked as getRecursive(index, sentinel.next)*/
        public T getRecursive(int index, Node node){
            if (node == sentinel)
                return null;
            if (index == 0){
                return node.first;
            }else{
                return getRecursive(index - 1, node.next);
            }
        }

        /** 0 is the front, 1 is the next item.  Return null
         * if no such item exists.
         * index: index is integer and index >= 0*/
        public T get(int index){
            if(index + 1<= size && index >= 0){
                Node pointer = sentinel.next;
                while(index > 0){
                    pointer = pointer.next;
                    index -= 1;
                }
                return pointer.first;
            }
            return null;
        }

        /** Return and removes the last item of the DLList*/
        public T removeLast(){
            if (size == 0){
                return null;
            }else{
                Node last_item = sentinel.previous;
                sentinel.previous.previous.next = null;
                sentinel.previous = sentinel.previous.previous;
                size -= 1;
                return last_item.first;
            }
        }

        public T removeFirst(){
            if (size == 0){
                return null;
            }else if(size == 1){
                return removeLast();
            }else{
                Node remove_item = sentinel.next;
                sentinel.next = remove_item.next;
                sentinel.next.previous = sentinel;
                size -= 1;
                return remove_item.first;
            }
        }

        public void addFirst(T first){
            if (size == 0){
                addLast(first);
            }else{
                Node addNode = new Node(first, sentinel, sentinel.next);
                sentinel.next.previous = addNode;
                sentinel.next = addNode;
                size += 1;
            }
        }

        public void printSelf(){
            int index = size;
            Node pointer = sentinel.next;
            String result ="";
            while (index > 0){
                result += pointer.first;
                result += " ";
                index -= 1;
                pointer = pointer.next;
            }
            System.out.println(result.strip());
        }


        private class Node{
            private T first;
            private Node next;
            private Node previous;

            public Node(T f,Node p, Node n){
                this.first = f;
                this.next = n;
                this.previous = p;
            }
        }
    }
}
