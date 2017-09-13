/**
 * The data structure for a game of Connect 4.
 * 
 * Connect4Game is made of a 2d array of Connect4Slots row 6 by column 7
 */
public class Connect4Game
{
    public static Connect4Slot [][] grid = new Connect4Slot[6][7];
    private boolean redPlayedFirst;


    /**
     * Construct a new Connect 4 game with a 7x6 Grid.
     */
    public Connect4Game()
    {
        this.newBoard();
    }

    /**
     * Drops a token into a particular column so that it will fall to the bottom of the column.
     * If the column is already full, nothing will change.
     *
     * @param column The column into which to drop the token.
     * @param iAmRed The color of the current players' token.
     */
    public static void dropToken(int column, boolean iAmRed)
    {
        int emptyRow = getLowestEmptyIndex(column);   // Find the top empty slot in the column
        // If the column is full, lowestEmptySlot will be -1
        if (emptyRow > -1)  // if the column is not full
        {
            if (iAmRed) // If the current agent is the Red player...
            {
                grid[emptyRow][column].addRed(); // Place a red token into the empty slot
            }
            else // If the current agent is the Yellow player (not the Red player)...
            {
                grid[emptyRow][column].addYellow(); // Place a yellow token into the empty slot
            }
        }
    }

    /**
     * Returns the index of the top empty slot in a particular column.
     * @param column The column to check.
     * @return the index of the top empty slot in a particular column; -1 if the column is already full.
     */
    public static int getLowestEmptyIndex(int column) {
        for  (int row = 5; row >= 0; row--)
        {
            if (!grid[row][column].getIsFilled())
            {
                return row;
            }
        }
        return -1;
    }

    /**
     * Start a new game.
     */
    public void newBoard()
    {
        for (int row = 0; row<6; row++){
            for (int col = 0; col<7; col++){
                grid[row][col] = new Connect4Slot();
            }
        }
    }

    /**
     * Retrieve a matrix form of the board.
     * @return a character matrix of the game board.
     */
    public void setBoardMatrix()
    {
        for (int row = 0; row < 6; row++)
        {
            for(int col = 0; col < 7; col++)
            {
                if (grid[row][col].getIsFilled())
                {
                    if (grid[row][col].getIsRed())
                    {
                        Board.currentBoard[row][col] = 'R';
                        fourStars(row, col);
                    }
                    else
                    {
                        Board.currentBoard[row][col] = 'Y';
                        fourStars(row, col);
                    }
                }
                else
                {
                    Board.currentBoard[row][col] = 'B';
                }
            }
        }
    }

    public void highlightSlot (int row, int col){
        grid[row][col].highlight();
    }

    /**
     * Replaces the 4-in-a-row highlighted winning tokens with X
     * @param row the row index of the token
     * @param col the column index of the token
     */
    private void fourStars(int row, int col){
        if(grid[row][col].getIsHighlighted()){
            Board.currentBoard[row][col] = 'X';
        }
    }

    /**
     * Sets whether red played first. This is used to confirm the players are alternating correctly.
     * @param redPlayedFirst true if red played first in this game, false otherwise.
     */
    public void setRedPlayedFirst(boolean redPlayedFirst)
    {
        this.redPlayedFirst = redPlayedFirst;
    }

    /**
     * Displays the current board layout to the console
     */
    public void displayBoard(){
        for (int row = 0; row < 6; row++){
            System.out.print("|" + grid[row][0] + "|");
            for (int col = 1; col < 7; col++){
                System.out.print(grid[row][col] + "|");
            }
            System.out.println();
        }
        System.out.println("---------------");

        System.out.print("|" + 1 + "|");
        for (int columns = 2 ; columns <= 7; columns++){
            System.out.print(columns + "|");
        }
        System.out.println();
    }

    /**
     * Check if the game has been won.
     * @return 'R' if red won, 'Y' if yellow won, 'N' if the game has not been won.
     */
    public char gameWon()//TODO: Problems with B+R+R+R WINS
    {
        char [][] currentBoard = Board.currentBoard;
        for (int row = 0; row < 6; row++)
        {
            for(int col = 0; col < 7; col++)
            {
                if(currentBoard[row][col] != 'B')
                {
                    if (row + 3 < 6)
                    {
                        if(currentBoard[row][col] == currentBoard[row + 1][col] && currentBoard[row][col] == currentBoard[row + 2][col] && currentBoard[row][col] == currentBoard[row + 3][col])
                        {
                            highlightSlot(row, col);
                            highlightSlot(row + 1, col);
                            highlightSlot(row + 2, col);
                            highlightSlot(row + 3, col);
                            return currentBoard[row][col];
                        }
                    }
                    if (col + 3 < 6)
                    {
                        if (currentBoard[row][col] == currentBoard[row][col + 1] && currentBoard[row][col] == currentBoard[row][col + 2] && currentBoard[row][col] == currentBoard[row][col + 3])
                        {
                            highlightSlot(row, col);
                            highlightSlot(row, col + 1);
                            highlightSlot(row, col + 2);
                            highlightSlot(row, col + 3);
                            return currentBoard[row][col];
                        }
                    }
                    if (col + 3 < 6 && row + 3 < 6)
                    {
                        if(currentBoard[row][col] == currentBoard[row + 1][col + 1] && currentBoard[row][col] == currentBoard[row + 2][col + 2] && currentBoard[row][col] == currentBoard[row + 3][col + 3])
                        {
                            highlightSlot(row, col);
                            highlightSlot(row+ 1, col + 1);
                            highlightSlot(row + 2, col + 2);
                            highlightSlot(row + 3, col + 3);
                            return currentBoard[row][col];
                        }
                    }
                    if (col > 2 && row + 3 < 6)
                    {
                        if (currentBoard[row][col] == currentBoard[row + 1][col - 1] && currentBoard[row][col] == currentBoard[row + 2][col - 2] && currentBoard[row][col] == currentBoard[row + 3][col - 3])
                        {
                            highlightSlot(row, col);
                            highlightSlot(row+ 1, col - 1);
                            highlightSlot(row + 2, col - 2);
                            highlightSlot(row + 3, col - 3);
                            return currentBoard[row][col];
                        }
                    }
                }
            }
        }

        return 'N';
    }

}
