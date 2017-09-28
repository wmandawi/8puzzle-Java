/******************************************************************************
 *  Name: Wurood Mandawi      
 *  NetID:     
 *  Precept:    
 *
 *  Partner Name:       
 *  Partner NetID:      
 *  Partner Precept:    
 *
 *  Hours to complete assignment (optional): 20hrs
 *
 ******************************************************************************/


/******************************************************************************
 *  Explain briefly how you implemented the board data type.
 *****************************************************************************/
For board I implemented hamming() checking the number of tiles that are out of
space and that is y running throw the whole board and checking if I see a tile
that is not in the right spot that is what should be in this spot by
using this equation : (size * row + col + 1) the 1 is for the blankspot.
and if so increnment the outPlace

for manhattan() I go through the board and I calculate what is th right row and 
col for every tile and how much i need to move them 
then I add the number of moves in col and the row and increment sum = move
 
/******************************************************************************
 *  Explain briefly how you represented a search node
 *  (board + number of moves + previous search node).
 *****************************************************************************/
I have private Board board and int moves ad SearxhNode previous
and in the constructor I intialize them.
Then I have a public method for each of them that just return the nodes.
Then I have another method called compareTo that takes a searchNode that
and in that method I compare the manhattan() or the hammig() + moves of 
this to that and return -1 if less +1 if greated and 0 if equal.

/******************************************************************************
 *  Explain briefly how you detected unsolvable puzzles. What is the
 *  order of growth of the runtime of your isSolvable() method?
 *****************************************************************************/
I have two if statements one for odd board size and one for even
in the odd I check if the number of inv is odd and if it is then its a solvable puzzle.
Then I check if the board is even, I add the sum of number of inversions to the row the 
0 (the blank) is in and if that sum is even then it is an unsolvable puzzle.
The order of growth of the run time is ~N^4.

/******************************************************************************
 *  For each of the following instances, give the minimal number of 
 *  moves to reach the goal state. Also give the amount of time
 *  it takes the A* heuristic with the Hamming and Manhattan
 *  priority functions to find the solution. If it can't find the
 *  solution in a reasonable amount of time (say, 5 minutes) or memory,
 *  indicate that instead. Note that you may be able to solve
 *  puzzlexx.txt even if you can't solve puzzleyy.txt even if xx > yy.
 *****************************************************************************/


                  number of          seconds
     instance       moves      Hamming     Manhattan
   ------------  ----------   ----------   ----------
   puzzle28.txt 	28	3.99		0.14
   puzzle30.txt 	30	8.25		0.27		
   puzzle32.txt 	32	OutOfMemory	6.38
   puzzle34.txt 	34	OutOfMemory	1.28
   puzzle36.txt 	36	OutOfMemory	23.78
   puzzle38.txt 	38	OutOfMemory	29.18
   puzzle40.txt 	40	OutOfMemory	5.41
   puzzle42.txt 	42	OutOfMemory	OutOfMemory



/******************************************************************************
 *  If you wanted to solve random 4-by-4 or 5-by-5 puzzles, which
 *  would you prefer:  a faster computer (say, 2x as fast), more memory
 *  (say 2x as much), a better priority queue (say, 2x as fast),
 *  or a better priority function (say, one on the order of improvement
 *  from Hamming to Manhattan)? Why?
 *****************************************************************************/
A better priority function. Because after tasting Hamming and Manhattan 
I found that there is a big difference between the two. Manhattan is
much more faster.





/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/
N/A


/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/
N/A




/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/
It used alot of memory and I were not able to test for puzzlexx.txt > puzzle07.txt
Then I went back and editted some methods to reduce the memory used
and it worked!


/******************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 *****************************************************************************/
N/A






/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 *****************************************************************************/
-