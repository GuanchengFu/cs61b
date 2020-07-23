package lab11.graphs;

import java.util.Deque;
import java.util.LinkedList;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */


    private Maze maze;
    private int[] parents;
    private int[] result = new int[2];
    private boolean terminate = false;
    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        parents = new int[maze.V()];
        parents[maze.xyTo1D(1, 1)] = maze.xyTo1D(1, 1);
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        dfs(maze.xyTo1D(1, 1));
        setPath(result);
    }

    private void dfs(int v) {
        if (terminate)
            return;
        marked[v] = true;
        announce();

        for (int w: maze.adj(v)) {
            if (marked[w]) {
                if (parents[v] != w) {
                    //System.out.println(v);
                    //System.out.println(w);
                    result[0] = v;
                    result[1] = w;
                    terminate = true;
                }
            } else {
                parents[w] = v;
                dfs(w);
                if (terminate)
                    return;
            }
        }
    }

    private void setPath(int[] temp) {
        if (temp == null)
            return;
        //System.out.println(temp[0]);
        //System.out.println(temp[1]);
        int endPoint = temp[0];
        int startPoint = temp[1];
        while (endPoint != startPoint) {
            int temp1 = parents[endPoint];
            edgeTo[temp1] = endPoint;
            endPoint = parents[endPoint];
        }
        edgeTo[temp[0]] = temp[1];
        announce();
    }
}
