package lab11.graphs;

import java.util.Deque;
import java.util.LinkedList;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        // Add more variables here!
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        marked[s] = true;
        announce();
        Deque<Integer> stack = new LinkedList<Integer>();
        stack.addLast(s);
        while (!stack.isEmpty()) {
            int temp = stack.pollFirst();
            if (temp == t)
                return;
            for (int v: maze.adj(temp)) {
                if (!marked[v]) {
                    marked[v] = true;
                    edgeTo[v] = temp;
                    distTo[v] = distTo[temp] + 1;
                    announce();
                    if (v == t)
                        return;
                    stack.addLast(v);
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

