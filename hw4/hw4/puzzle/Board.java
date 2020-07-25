package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState{

    // This class should be immutable.

    private int[][] space;

    private int N;

    public Board(int[][] tiles) {
        N = tiles.length;
        space = new int[N][N];
        for (int i = 0; i < N; i ++) {
            for (int j = 0; j < N; j ++) {
                space[i][j] = tiles[i][j];
            }
        }
    }

    public int tileAt(int i, int j) {
        if (i < 0 || i >= N || j < 0 || j >= N)
            throw new IndexOutOfBoundsException("Invalid arguments in tileAt");
        return space[i][j];
    }
    public int size() {
        return N;
    }
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        final int BLANK = 0;
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }
    public int hamming() {
        int count = 0;
        for (int i = 0; i < N; i ++) {
            for (int j = 0; j < N; j ++) {
                if (i == N - 1 && j == N - 1) {
                    // Do nothing
                } else {
                    if (tileAt(i, j) != i * N + j + 1) {
                        count += 1;
                    }
                }
            }
        }
        return count;
    }


    public int manhattan() {
        int count = 0;
        for (int i = 0; i < N; i ++) {
            for (int j = 0; j < N; j ++) {
                count += calculateCorrectDis(tileAt(i, j), i, j);
            }
        }
        return count;
    }


    private int calculateCorrectDis(int value, int row, int col) {
        if (value == 0)
            return 0;
        int correctRow = (value - 1) / size();
        int correctCol = (value - 1) % size();
        return Math.abs(correctRow - row) + Math.abs(correctCol - col);
    }
    public int estimatedDistanceToGoal() {
        return manhattan();
    }
    public boolean equals(Object y) {
        if (this == y)
            return true;
        if (y == null)
            return false;
        if (getClass() != y.getClass())
            return false;
        Board b = (Board) y;
        if (this.size() == b.size()) {
            for (int i = 0; i < this.size(); i ++) {
                for (int j = 0; j < this.size(); j ++) {
                    if (this.tileAt(i,j) != b.tileAt(i, j))
                        return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }


    /** Returns the string representation of the board.
     * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    /*
    public static void main(String[] args) {
        int[][] test = new int[3][3];
        test[0][0] = 8;
        test[0][1] = 1;
        test[0][2] = 3;
        test[1][0] = 4;
        test[1][1] = 0;
        test[1][2] = 2;
        test[2][0] = 7;
        test[2][1] = 6;
        test[2][2] = 5;

        int[][] test2 = new int[3][3];
        test2[0][0] = 8;
        test2[0][1] = 1;
        test2[0][2] = 3;
        test2[1][0] = 4;
        test2[1][1] = 0;
        test2[1][2] = 2;
        test2[2][0] = 7;
        test2[2][1] = 5;
        test2[2][2] = 6;

        Board b = new Board(test);
        System.out.println(b);
        System.out.println(b.manhattan());
        System.out.println(b.hamming());
        Board c = new Board(test2);
        System.out.println(b.equals(c));

    }*/


}
