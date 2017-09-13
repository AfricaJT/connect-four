/**
 * Created by Jesse T on 2017/07/17.
 */
public class Board {
    public static char [] [] currentBoard = new char [6][7];

    public Board (){

    }

    /**
     * Check if the board is full.
     * @return true if the board is full, false otherwise.
     */
    public static boolean boardFull()
    {
        for (int row = 0; row < 6; row++)
        {
            for (int col = 0; col < 7; col++)
            {
                if (currentBoard[row][col] == 'B')
                {
                    return false;
                }
            }
        }
        return true;
    }
}
