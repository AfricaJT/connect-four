/**
 * Created by Jesse T on 2017/07/04.
 */
public class Winner69 extends Winner{

    public static Winner [][] matrix = new Winner[6][7];
    private char [] [] matrixBoard;
    private char agentColor, blank = 'B';

    /**
     * Constructor that saves the current board and updates the winning Percentage for each grid block
     * @param iAmRed true if the player is red
     */
    public Winner69 (boolean iAmRed) {
        super();
        agentColor = setAgentColor(iAmRed);
        matrixBoard = Board.currentBoard;
        this.setMatrix();
        this.setFullMatrix();
        this.setMatrixWinPercent();
    }

    /**
     * Sets the agents Color based on if it is red or not
     * @param iAmRed true if it is red
     * @return char 'R' if it is red, 'Y' if it is not
     */
    public char setAgentColor(boolean iAmRed){
        if (iAmRed){
            return 'R';
        }
        else{
            return 'Y';
        }
    }

    /**
     * After each move, it checks for changes in each grid position to see if valid possible wins are still valid
     */
    public void updateMatrix(){
        matrixBoard = Board.currentBoard;

        for (int row = 5; row >= 0 ; row--){
            for (int col = 0; col < 7 ; col++){
                if (matrix[row][col].getDiagLeftWin()){//Is a DiagLeftWin token
                    if (opposToken(row, col)){//Position is taken by opposition's token
                        matrix[row][col].setDiagLeftWin(false);
                    }
                }
                if (matrix[row][col].getDiagRightWin()){//Is a DiagRightWin token
                    if (opposToken(row, col)){//Position is taken by opposition's token
                        matrix[row][col].setDiagRightWin(false);
                    }
                }
                if (matrix[row][col].getRightWin()){//Is a RightWin token
                    if (opposToken(row, col)){//Position is taken by opposition's token
                        matrix[row][col].setRightWin(false);
                    }
                }
                if (matrix[row][col].getUpWin()){//Is a DiagUpWin token
                    if (opposToken(row, col)){//Position is taken by opposition's token
                        matrix[row][col].setUpWin(false);
                    }
                }
            }
        }
        setMatrixWinPercent();
    }

    /**
     * Conditional Statement - returning true if the slot is filled by the opponents token
     * @param row the int row position of the token
     * @param col the int column position of the token
     * @return boolean true if the slot is filled
     */
    private boolean opposToken(int row, int col){
        return matrixBoard[row][col] != agentColor && matrixBoard[row][col] != blank;
    }

    /**
     * Checks each row and col of the 7x6 grid for any possible winning arrangements:
     * upWin - 4 in a row Vertically Upwards
     * rightWin - 4 in a row Horizontally Across to the right
     * diagRightWin - 4 in a row Diagonally Across to the right
     * diagLeftWin - 4 in a row Diagonally Across to the left
     * If a Winning arrangement is possible then the corresponding above variable is set to true
     */
    public void setFullMatrix() {

        for (int row = 5 ; row >= 0 ; row--){//Loops through rows starting at the bottom of the 7x6 grid
            for (int col = 0 ; col < 7 ; col++){//Loops through columns starting at the far left of the 7x6 grid
                if (matrixBoard[row][col] == blank){//Check slot to match opponents color

                    if (row >= 3 && row <= 5 && col >= 3 && col <= 6){//Only Rows & Columns possible of a DiagLeftWin
                        matrix[row][col].setDiagLeftWin(true);
                    }
                    if (row >= 3 && row <= 5 && col >= 0 && col <= 3){//Only Rows & Columns possible of a DiagRightWin
                        matrix[row][col].setDiagRightWin(true);
                    }
                    if (col >= 0 && col <= 3){//Only Columns possible of a RightWin
                        matrix[row][col].setRightWin(true);
                    }
                    if (row == 3 || row == 4 || row == 5){//Only Rows possible of an UpWin
                        matrix[row][col].setUpWin(true);
                    }
                }
            }
        }

    }

    /**
     * Creates a matrix of Winner Objects
     */
    public void setMatrix(){
        for (int row = 0; row<6; row++){
            for (int col = 0; col<7; col++){
                matrix[row][col] = new Winner();//Initialises a Winner matrix
            }
        }
    }

    /**
     * Mutator for each Winner Object in the matrix array calculating the winPercent
     */
    public void setMatrixWinPercent(){
        for (int row = 5 ; row >= 0 ; row--){
            for (int col = 0 ; col < 7 ; col++){
                matrix[row][col].setWinPercent();
            }
        }
    }

    /**
     * Debugging for the changes in winPercentages with a detailed display grid
     */
    public void displayWinPercent(){
        for (int row = 0; row < 6; row++){
            System.out.print("|" + matrix[row][0].toString() + "|");
            for (int col = 1; col < 7; col++){
                System.out.print(matrix[row][col].toString() + "|");
            }
            System.out.print(" " + (row+1) );
            System.out.println();
        }
        System.out.println("-----------------------------");

        System.out.print("|" + 1 + "|");
        for (int columns = 2 ; columns <= 7; columns++){
            System.out.print(columns + "|");
        }
        System.out.println();
    }
}
