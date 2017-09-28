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
 * 
 ******************************************************************************/
import java.util.Stack;
public class Board {
    private int size; //N tiles
    private int[][] boards; //board
    public Board(int[][] tiles)            // construct a board from an N-by-N array of tiles
    // (where tiles[i][j] = tile at row i, column j)
    {
        this.size = tiles.length; //the size of th tiles (N)
        boards = new int[size][size]; 
        //tiles[size][size] = this.tiles[size][size];

        for (int row = 0; row < size; row++)
        {
            for (int col = 0; col < size; col++)
            {
                boards[row][col] = tiles[row][col];
            }
        }
    }

    public int tileAt(int i, int j)        // return tile at row i, column j (or 0 if blank)
    {
        if (i >= 0 && i <= (size - 1) && j >= 0 && j <= (size - 1))
        {
            if (boards[i][j] == 0)
                return 0;

            else 
                return boards[i][j];
        }
        else throw new IndexOutOfBoundsException("i or j are not between 0 and size - 1");
    }

    public int size()                      // board size N
    {
        return size;
    }

    public int hamming()                   // number of tiles out of place
    {
        int outPlace = 0;
        for (int row = 0; row < size; row++)
        {
            for (int col = 0; col < size; col++) {
                if (boards[row][col] != (size * row) + 1 + col && boards[row][col] != 0)
                    outPlace++;
            }
        }
        return outPlace;
    }

    //     private int goalhere(int row, int col) 
    //     {
    //         if( row == size - 1 && col == size - 1) return 0;
    //         else return size * row + 1 + col;
    //     }

    public int manhattan()                 // sum of Manhattan distances between tiles and goal
    {
        int sum = 0;
        for (int row = 0; row < size; row++)
        {
            for (int col = 0; col < size; col++)
            {
                if (boards[row][col] != 0) //&& boards[row][col] != goalhere(row, col))
                {
                    int rowPosition =(int) Math.floor((boards[row][col] - 1) / size); //the goal position in the row
                    int colPosition = (boards[row][col] - 1) % size; // the goal col
                    int movei = Math.abs(row - rowPosition);
                    int movej = Math.abs(col - colPosition);
                    int move = movei + movej;
                    sum = sum + move;
                }
            }
        }
        return sum;
    }

    public boolean isGoal()                // is this board the goal board?
    {
        if (hamming() != 0)
            return false;
        else return true;
    }

    public boolean isSolvable()            // is this board solvable?
    {
        int inv = 0;
        //for odd board size
        if (size % 2 == 1)
        {
            for (int i = 0; i < size * size; i++)
            {
                for (int j = i; j < size * size; j++)
                {
                    if (boards[(int)Math.floor(j/size)][j % size] != 0 &&
                    boards[(int)Math.floor(j/size)][j % size] < 
                    boards[(int)Math.floor(i/size)][i%size])  
                        inv++;

                }
            }
            //is odd 
            return (inv % 2 != 1);
        }
        //for even board size
        else {
            //num of inv and the row of the blank tile then add them if we move the
            //blank left or right the inv wont change so the sum wont change or -1 
            //and the inv will change by -3, -1, 1 or 3 and so the total sum will change 
            //by an even amount. The goal board has a sum of 3 then if we keep changing by even 
            //we will never reach it; unsolvable.
            int blankrow = -1;
            for (int i = 0; i < size * size; i++)
            {
                for (int j = i; j < size * size; j++)
                {
                    if (boards[(int)Math.floor(i/size)][i % size] == 0)
                        blankrow = (int)Math.floor(i/size);
                    if (boards[(int)Math.floor(j/size)][j % size] != 0 &&
                    boards[(int)Math.floor(j/size)][j % size] <
                    boards[(int)Math.floor(i/size)][i % size])  
                        inv++;
                }
            }
            int sum = inv + blankrow;
            //if sum is odd
            return (sum % 2 != 0);
        }
    }

    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) 
            return true;
        if ((y == null) || (y.getClass() != this.getClass()))
            return false;

        Board that = (Board) y;
        if (this.size != that.size)
            return false;
        else {
            for (int row = 0; row < size; row++) 
            {
                for (int col = 0; col < size; col++)
                {
                    if (this.boards[row][col] != that.boards[row][col])
                        return false;
                }
            }
        }
        return true;
    }

    //     private boolean nighbor(int row, int col, int otherR, int otherC)
    //     {
    //         if (otherR < 0 || otherC < 0 || otherR > size - 1 || otherC > size - 1) 
    //             return false;
    //         int tile = boards[row][col];
    //         boards[row][col] = boards[otherR][otherC];
    //         boards[otherR][otherC] = tile;
    //         return true;
    //     }

    public Iterable<Board> neighbors()     // all neighboring boards
    {
        // find 0, the empty square
        //boolean zero = false;
        //the row 0 is in
        int row0 = -1;
        //the col 0 is in
        int col0 = -1;
        //go through all the tiles
        for (int row = 0; row < size; row++)
        {
            for (int col = 0; col < size; col++) 
            {
                //if you find 0 then save its row and col and break
                if (boards[row][col] == 0)
                {
                    row0 = row;
                    col0 = col;
                    //zero = true;
                }
            }
        }
        //add to a stack
        Stack<Board> neighbors =new Stack<Board>();

        //left
        if (col0 != 0)
        {
            int[][] grid = new int[size][size];
            for (int row = 0; row < size; row++)
            {
                for (int col = 0; col < size; col++){
                    grid[row][col] = boards[row][col];
                }
            }
            grid[row0][col0] = grid[row0][col0 - 1];
            grid[row0][col0 - 1] = 0;
            Board nighb = new Board(grid);
            neighbors.push(nighb);
        }

        //up
        if (row0 != 0)
        {
            int[][] grid1 = new int[size][size];
            for (int row = 0; row < size; row++)
            {
                for (int col = 0; col < size; col++){
                    grid1[row][col] = boards[row][col];
                }
            }
            grid1[row0][col0] = grid1[row0 - 1][col0];
            grid1[row0 - 1][col0] = 0;
            Board nighb1 = new Board(grid1);
            neighbors.push(nighb1);
        }

        //right
        if (col0 != size - 1)
        {
            int[][] grid2 = new int[size][size];
            for (int row = 0; row < size; row++)
            {
                for (int col = 0; col < size; col++){
                    grid2[row][col] = boards[row][col];
                }
            }
            grid2[row0][col0] = grid2[row0][col0 + 1];
            grid2[row0][col0 + 1] = 0;
            Board nighb2 = new Board(grid2);
            neighbors.push(nighb2);
        }

        //down
        if (row0 != size - 1)
        {
            int[][] grid3 = new int[size][size];
            for (int row = 0; row < size; row++)
            {
                for (int col = 0; col < size; col++){
                    grid3[row][col] = boards[row][col];
                }
            }
            grid3[row0][col0] = grid3[row0 + 1][col0];
            grid3[row0 + 1][col0] = 0;
            Board nighb3 = new Board(grid3);
            neighbors.push(nighb3);
        }
        //Board item = new Board (boards);
        //the left nighbor
        //         if (item.nighbor(row0, col0, row0, col0 - 1) == true)
        //             neighbors.push(item);
        //         //up    
        //         if (item.nighbor(row0, col0, row0 - 1, col0) == true)
        //             neighbors.push(item);
        //         //right
        //         if (item.nighbor(row0, col0, row0, col0 + 1) == true)
        //             neighbors.push(item);
        //         //down
        //         if (item.nighbor(row0, col0, row0 + 1, col0) == true)
        //             neighbors.push(item);
        return neighbors; 
    }

    public String toString()               // string representation of this board (in the output format specified below)

    {
        StringBuilder s = new StringBuilder();
        s.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) // unit testing (not graded)
    {   
        //         // create initial board from file
        //         In in = new In(args[0]);
        //         int N = in.readInt();
        //         int[][] boards = new int[N][N];
        //         for (int i = 0; i < N; i++)
        //             for (int j = 0; j < N; j++)
        //                 boards[i][j] = in.readInt();
        //         Board initial = new Board(boards);
    }
}
