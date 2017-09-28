/******************************************************************************
 * Name: Wurood Mandawi 
 * Project URL: http://www.cs.princeton.edu/courses/archive/fall16/cos226/assignments/8puzzle.html
 * The 8-puzzle problem is a puzzle invented and popularized by Noyes Palmer Chapman in the 1870s. 
 * It is played on a 3-by-3 grid with 8 square tiles labeled 1 through 8 and a blank square. 
 * Your goal is to rearrange the tiles so that they are in order, 
 * using as few moves as possible. You are permitted to slide tiles horizontally or 
 * vertically into the blank square. The following shows a sequence of legal moves from 
 * an initial board (left) to the goal board (right).
 * 
 * Write the class Solver that uses the A* algorithm to solve puzzle instances. 
 * This will include creating a MinPQ. You can choose one of two options to determine order:
 * Make it implement the Comparable<SearchNode> interface so that you can use it with a MinPQ.
 * The compareTo() method should compare search nodes based on their Hamming or Manhattan priorities.
 * Create a Comparator<SearchNode> for each priority function and initialize the MinPQ with it.
 
 ******************************************************************************/
 import java.util.Stack;
//import java.util.Comparator;
public class Solver {
    private MinPQ<SearchNode> MinPQ;
    private SearchNode goal;

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private SearchNode previous;
        //private int priority = -1;
        private SearchNode(Board bo, int move, SearchNode prev)
        {
            board = bo;
            moves = move;
            previous = prev;
        }

        public Board board() 
        {
            return board;
        }

        public int moves()
        {
            return moves;
        }

        public SearchNode previous()
        {
            return previous;
        }

        //         private int prio() 
        //         {
        //             if (priority == -1)
        //                 priority = moves + board.hamming();
        //             return priority;
        //         }
        //manhattan()
        //hamming()

        public int compareTo(SearchNode that)
        {
            //smaller
            if ((board.manhattan() + moves) < (that.board.manhattan() + that.moves))
                return -1;
            //greater
            else if ((board.manhattan() + moves) > (that.board.manhattan() + that.moves))
                return +1;

            else return 0;
        }
    }

    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if (!initial.isSolvable())
            throw new IllegalArgumentException("the initial board is not solvable!");

        if (initial == null)
            throw new NullPointerException("the initial board is null");

        MinPQ = new MinPQ<SearchNode>();
        //initial
        SearchNode initi = new SearchNode(initial, 0, null);
        MinPQ.insert(initi);

        while (goal == null)
        {
            //found node
            SearchNode current = MinPQ.delMin();
            //is it goal
            if (current.board().isGoal())
            {
                goal = current;
                return;
            }  
            //else get nighbors
            Iterable<Board> neighbors = current.board.neighbors();
            //put them in MinPQ
            for (Board bo : neighbors)
            {
                SearchNode node = new SearchNode(bo, current.moves + 1, current);
                if (current.previous() != null) 
                {
                    if (!node.board().equals(current.previous().board()))
                        MinPQ.insert(node);
                }
                else MinPQ.insert(node);    
            }
        }
    }

    public int moves()                     // min number of moves to solve initial board
    {
        return goal.moves;
    }

    public Iterable<Board> solution()      // sequence of boards in a shortest solution
    {
        Stack<Board> Sboards = new Stack<Board>();
        SearchNode current = goal;
        Board goalB = current.board();
        Sboards.push(goalB);

        while (current.previous() != null)
        {
            Board temp = current.previous().board();
            Sboards.push(temp);
            current = current.previous();
        }
        return Sboards;
    }

    public static void main(String[] args) // solve a slider puzzle (given below) 
    {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Stopwatch s = new Stopwatch();
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);
        StdOut.println(s.elapsedTime());
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
}