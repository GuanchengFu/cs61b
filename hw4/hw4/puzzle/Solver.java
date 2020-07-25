package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class Solver {

    private int move = 0;

    private Deque<WorldState> sequence = new LinkedList<>();

    private Map<WorldState, Integer> cache = new HashMap<>();

    private class SearchNode{
        WorldState state;
        int moves;
        SearchNode prev;

        public SearchNode(WorldState cur, int move, SearchNode pre) {
            state = cur;
            moves = move;
            prev = pre;
        }


        public int getPriority() {
            if (!cache.containsKey(state))
                cache.put(state, state.estimatedDistanceToGoal());
            return moves + cache.get(state);
        }
    }

    private class PriorityComparator implements Comparator<SearchNode> {

        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            if (o1.getPriority() < o2.getPriority())
                return -1;
            if (o1.getPriority() > o2.getPriority())
                return 1;
            return 0;
        }
    }

    /**Optimization 1: Never add its previous node into the priority queue.
     * Optimization 2: Use a cache to store the calculated estimated distance.*/
    public Solver(WorldState initial) {
        MinPQ<SearchNode> priorityQ = new MinPQ<>(new PriorityComparator());
        SearchNode begin = new SearchNode(initial, 0, null);
        priorityQ.insert(begin);
        while (!priorityQ.isEmpty()) {
            SearchNode temp = priorityQ.delMin();
            if (temp.state.isGoal()) {
                move = temp.moves;
                // Need to set the arrayList by using the prev node.
                while (!temp.equals(begin)) {
                    sequence.addFirst(temp.state);
                    temp = temp.prev;
                }
                sequence.addFirst(begin.state);
                return;
            } else {
                for (WorldState s: temp.state.neighbors()) {
                    if (temp.prev == null) {
                        priorityQ.insert(new SearchNode(s, temp.moves + 1, temp));
                    } else {
                        if (!s.equals(temp.prev.state)) {
                            priorityQ.insert(new SearchNode(s, temp.moves + 1, temp));
                        }
                    }
                }
            }

        }
    }

    public int moves() {
        return move;
    }

    public Iterable<WorldState> solution() {
        return sequence;
    }
}
