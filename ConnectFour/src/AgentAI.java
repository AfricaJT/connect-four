/**
 * Created by Jesse T on 2017/07/04.
 */
import java.util.Random;

public class AgentAI extends Agent
{
    private Random r;
    private String name, blackValidMoves = "";
    public static Winner69 winner69;
    private boolean [][] blacklist = new boolean [6][7];
    private boolean [][] validlist = new boolean [6][7];
    private final char EMPTY = 'B';

    /**
     * Constructs a new agent, giving it the game, telling it whether it is Red or Yellow, A Name and
     * a matrix of possible winning moves
     *
     * @param game The game the agent will be playing.
     * @param iAmRed True if the agent is Red, False if the agent is Yellow.
     */
    public AgentAI(Connect4Game game, boolean iAmRed, String name)
    {
        super(game, iAmRed);
        r = new Random();
        this.name = name;
        this.blacklist = new boolean [6][7];
        winner69 = new Winner69(iAmRed);
    }

    /**
     * The move method is run every time it is this agent's turn in the game. You may assume that
     * when move() is called, the game has at least one open slot for a token, and the game has not
     * already been won.
     *
     * By the end of the move method, the agent should have placed one token into the game at some
     * point.
     *
     */
    public void move()
    {
        int col;
        int theyCanWin = theyCanWin();

        if (theyCanWin > -1){
            col = theyCanWin;
        }
        else {
            int iCanWin = iCanWin();
            if (iCanWin > -1){
                col = iCanWin;
            }
            else {
                int calculatedMove = calculatedMove();
                if(calculatedMove > -1){
                    col = calculatedMove;
                }
                else {
                    col = validRandomMove();
                }
            }
        }
        Connect4Game.dropToken(col, iAmRed);//place token in column - user color
        System.out.println();
    }

    /**
     * Creates a list of the 7 valid moves, if a column has no valid moves it is set to false
     */
    private void validMoves7 (){
        for (int col = 0 ; col < 7 ; col++){
            int row = Connect4Game.getLowestEmptyIndex(col);
            if (row != -1){
                validlist[row][col] = true;
            }
        }
    }

    /**
     * A calculated move by the AgentAI based on possibility of a winning move of each grid block
     * @return int column the best possible column, -1 if none
     */
    private int calculatedMove(){
        winner69.updateMatrix();
        validMoves7();//Check all valid moves out of 7
        int highestWinRate = -1;
        int bestCol = -1;
        for (int row = 5; row >= 0 ; row--){
            for (int col = 0 ; col < 7 ; col++){
                if (validlist[row][col] && (!blacklist[row][col])){//The valid move is not blacklisted
                    if (Winner69.matrix[row][col].getWinPercent() >= highestWinRate){//Probability of Winning Moves is better
                        bestCol = col;
                    }
                }
            }
        }
        if (highestWinRate == -1){//There are no Winning Moves left
            return -1;
        }
        else {
            return bestCol; //TODO: Code Comparison between best and second best & Prediction of 2 moves down the line
        }
        //if more than one check token surrounding move is 1 or more
        //if more than one predict players next turn and yours
        //place move
    } //TODO: IMPROVE

    /**
     * Returns a random valid move. If your agent doesn't know what to do, making a random move
     * can allow the game to go on anyway.
     *
     * @return a random valid move.
     */
    public int validRandomMove()
    {
        int col = -1;
        int row = -1;
        if (incriminate()){//All valid moves remaining are blacklisted therefore choose one
            while (row == -1)
            {
                col = r.nextInt(7);
                row = Connect4Game.getLowestEmptyIndex(col);
            }
        }
        else {//There is at least 1 valid non-blacklisted move remaining
            while (row == -1)
            {
                col = r.nextInt(7);
                row = Connect4Game.getLowestEmptyIndex(col);
                if (row != -1){
                    if (finalBlackValidMove(col)){
                        row = -1;
                    }
                }

            }
        }
        return col;
    }

    /**
     * Checks the String blackValidMoves if the random column is
     * @param col the random generated column
     * @return true if the column is valid AND Blacklisted
     */
    private boolean finalBlackValidMove(int col){
        char ch = ("" + col).charAt(0);
        for (int a = 0 ; a< blackValidMoves.length() ; a++){
            if (blackValidMoves.indexOf(ch) != -1){//The column was found as blackValid
                return true;
            }
        }
        return false;
    }

    /**
     * The only valid moves available are blacklisted therefore incriminate player to lose the game
     * @return true if player is left with no available non-blacklisted moves
     */
    private boolean incriminate() {
        int sum = 0;
        for (int row = 5; row >= 0; row--) {
            for (int col = 0; col <= 6; col++) {
                if (validlist[row][col]) {//The grid position is a valid move at the current state of the game
                    if (!blacklist[row][col]) {//The valid move is not blacklisted
                        return false;//Do not incriminate Player
                    } else {//The Valid Move is blacklisted
                        blackValidMoves += "" + col;
                        sum++;
                    }
                }

            }

        }
        if (sum == 7){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * The grid block at row and column is blacklisted - player must not place token in slot.
     * @param row the row position of the token
     * @param col the column position of the token
     */
    private void blacklist(int row, int col){
        blacklist[row][col] = true;
    }

    /**
     * Returns the column that would allow the agent to win.
     * @return the column that would allow the agent to win.
     */
    public int iCanWin()
    {
        char agentColor;
        if (iAmRed){//Check color of current player?
            agentColor = 'R';
        } else {
            agentColor = 'Y';
        }
        int winningColumn = canWin(agentColor);
        return winningColumn;
    }

    /**
     * Returns the column that would allow the opponent to win.
     *
     * You might want your agent to check to see if the opponent would have any winning moves
     * available so your agent can block them. Implement this method to return what column should
     * be blocked to prevent the opponent from winning.
     *
     * @return the column that would allow the opponent to win.
     */
    public int theyCanWin()
    {
        char agentColor;
        if (!iAmRed){//Check color of current player?
            agentColor = 'R';
        } else {
            agentColor = 'Y';
        }
        int winningColumn = canWin(agentColor);
        return winningColumn;
    }

    /**
     * Displays the game changing move either blocking or game winning
     * @param agentColor the color of the current player
     * @param move the type of 4-in-a-row move: UpWin, RightWin, DiagLeftWin, DiagRightWin
     */
    public void alertMove(char agentColor, String move){
        if (iAmRed && agentColor == 'R'){
            System.out.println(agentColor + " places WINNING Move with " + move);
        }
        else{
            System.out.println("BLOCKED " + agentColor + " from " + move);
        }
    }

    /**
     * Check if it is possible for agentColor to have a winning move and win the game
     * @param agentColor the char of the player who just played
     * @return the winning column or -1 if none
     */
    public int canWin(char agentColor){
        char [][] currentBoard = Board.currentBoard;
        int winningColumn = -1;
        for (int row = 5 ; row >= 0 ; row--){
            for (int col = 0 ; col < 7 ; col++){
                if (currentBoard[row][col] == agentColor){//Check slot to match opponents color
                    //////////////////////////////DiagLeftWin///////////////////////////////
                    if (row >= 3 && row <= 5 && col >= 3 && col <= 6){//Only Rows & Columns possible of a DiagLeftWin
                        if (currentBoard[row-1][col-1] == agentColor ){ //DiagLeftWin 2 in a row (R+R)
                            if (currentBoard[row-2][col-2] == agentColor ) { //DiagLeftWin 3 in a row (R+R+R)
                                if (currentBoard[row-3][col-3] == EMPTY){
                                    if (currentBoard[row-2][col-3] != EMPTY){//Winning Column is Level with Move
                                        alertMove(agentColor, "DiagLeftWin");
                                        winningColumn = col-3;
                                    }
                                    else if (currentBoard[row-2][col-3] == EMPTY && !iAmRed && agentColor == 'R'){//Winning Col is not level by 1 token && theyCanWin
                                        blacklist(row-1, col-3);//Blacklist row and col
                                    } else if (currentBoard[row-2][col-3] == EMPTY && iAmRed && agentColor == 'Y'){//Winning Col is not level by 1 token && theyCanWin
                                        blacklist(row-1, col-3);//Blacklist row and col
                                    }
                                }
                            }
                            else
                            if (currentBoard[row-3][col-3] == agentColor ){//R+R+B
                                if (currentBoard[row-2][col-2] == EMPTY){//Is R+R+B+R Not R+R+Y+R
                                    if (currentBoard[row-1][col-2] != EMPTY){//Winning Column is Level with Move
                                        alertMove(agentColor, "DiagLeftWin");
                                        winningColumn = col-2;
                                    }
                                    else if (currentBoard[row-1][col-2] == EMPTY && !iAmRed && agentColor == 'R'){//Winning Col is not level by 1 token && theyCanWin
                                        blacklist(row-1, col-2);//Blacklist row and col
                                    } else if (currentBoard[row-1][col-2] == EMPTY && iAmRed && agentColor == 'Y'){//Winning Col is not level by 1 token && theyCanWin
                                        blacklist(row-1, col-2);//Blacklist row and col
                                    }
                                }
                            }
                        }
                        else
                        if (currentBoard[row-2][col-2] == agentColor ){//R+B
                            if (currentBoard[row-3][col-3] == agentColor ){//R+B+R
                                if (currentBoard[row-1][col-1] == EMPTY){//Is R+B+R+R Not R+Y+R+R
                                    if (currentBoard[row][col-1] != EMPTY){//Winning Column is Level with Move
                                        alertMove(agentColor, "DiagLeftWin");
                                        winningColumn = col-1;
                                    }
                                    else if (currentBoard[row][col-1] == EMPTY && !iAmRed && agentColor == 'R'){//Winning Col is not level by 1 token && theyCanWin
                                        blacklist(row, col-1);//Blacklist row and col
                                    } else if (currentBoard[row][col-1] == EMPTY && iAmRed && agentColor == 'Y'){//Winning Col is not level by 1 token && theyCanWin
                                        blacklist(row, col-1);//Blacklist row and col
                                    }
                                }
                            }
                        }
                    }
                    //////////////////////////////DiagRightWin///////////////////////////////
                    if (row >= 3 && row <= 5 && col >= 0 && col <= 3){//Only Rows & Columns possible of a DiagRightWin
                        if (currentBoard[row-1][col+1] == agentColor ){ //DiagRightWin 2 in a row
                            if (currentBoard[row-2][col+2] == agentColor ) { //DiagRightWin 3 in a row
                                if (currentBoard[row-3][col+3] == EMPTY){
                                    if (currentBoard[row-2][col+3] != EMPTY){//Winning Column is Level with Move
                                        alertMove(agentColor, "DiagRightWin");
                                        winningColumn = col+3;
                                    }
                                    else if (currentBoard[row-2][col+3] == EMPTY && !iAmRed && agentColor == 'R'){//Winning Col is not level by 1 token && theyCanWin
                                        blacklist(row-1, col+3);//Blacklist row and col
                                    } else if (currentBoard[row-2][col+3] == EMPTY && iAmRed && agentColor == 'Y'){//Winning Col is not level by 1 token && theyCanWin
                                        blacklist(row-1, col+3);//Blacklist row and col
                                    }
                                }
                            }
                            else
                            if (currentBoard[row-3][col+3] == agentColor ){//R+R+B
                                if (currentBoard[row-2][col+2] == EMPTY){//Is R+R+B+R Not R+R+Y+R
                                    if (currentBoard[row-1][col+2] != EMPTY){//Winning Column is Level with Move
                                        alertMove(agentColor, "DiagRightWin");
                                        winningColumn = col+2;
                                    }
                                    else if (currentBoard[row-1][col+2] == EMPTY && !iAmRed && agentColor == 'R'){//Winning Col is not level by 1 token && theyCanWin
                                        blacklist(row-1, col+2);//Blacklist row and col
                                    } else if (currentBoard[row-1][col+2] == EMPTY && iAmRed && agentColor == 'Y'){//Winning Col is not level by 1 token && theyCanWin
                                        blacklist(row-1, col+2);//Blacklist row and col
                                    }
                                }
                            }
                        }
                        else
                        if (currentBoard[row-2][col+2] == agentColor ){//R+B
                            if (currentBoard[row-3][col+3] == agentColor ){//R+B+R
                                if (currentBoard[row-1][col+1] == EMPTY){//Is R+B+R+R Not R+Y+R+R
                                    if (currentBoard[row][col+1] != EMPTY){//Winning Column is Level with Move
                                        alertMove(agentColor, "DiagRightWin");
                                        winningColumn = col+1;
                                    }
                                    else if (currentBoard[row][col+1] == EMPTY && !iAmRed && agentColor == 'R'){//Winning Col is not level by 1 token && theyCanWin
                                        blacklist(row, col+1);//Blacklist row and col
                                    } else if (currentBoard[row][col+1] == EMPTY && iAmRed && agentColor == 'Y'){//Winning Col is not level by 1 token && theyCanWin
                                        blacklist(row, col+1);//Blacklist row and col
                                    }
                                }
                            }
                        }
                    }
                    ////////////////////////////RightWin/////////////////////////////
                    if (col >= 0 && col <= 3){//Only Columns possible of a RightWin
                        if (currentBoard[row][col+1] == agentColor ){ //RightWin 2 in a row
                            if (currentBoard[row][col+2] == agentColor ) { //RightWin 3 in a row
                                if (currentBoard[row][col+3] == EMPTY){
                                    if (row != 5){
                                        if (currentBoard[row+1][col+3] != EMPTY){//Winning Column is Level with Move
                                            alertMove(agentColor, "RightWin");
                                            winningColumn = col+3;
                                        }
                                        else if (row != 4 && currentBoard[row+1][col+3] == EMPTY && !iAmRed && agentColor == 'R'){
                                            blacklist(row+1, col+3);//Blacklist row and col
                                        }else if (row != 4 && currentBoard[row+1][col+3] == EMPTY && iAmRed && agentColor == 'Y'){
                                            blacklist(row+1, col+3);//Blacklist row and col
                                        }
                                    }
                                    else {
                                        alertMove(agentColor, "RightWin");
                                        winningColumn = col+3;
                                    }
                                }
                            }
                            else
                            if (currentBoard[row][col+3] == agentColor ){//R+R+B
                                if (currentBoard[row][col+2] == EMPTY){//Is //R+R+B+R Not R+R+Y+R
                                    if (row != 5){
                                        if (currentBoard[row+1][col+2] != EMPTY){//Winning Column is Level with Move
                                            alertMove(agentColor, "RightWin");
                                            winningColumn = col+2;
                                        }
                                        else if (row != 4 && currentBoard[row+1][col+2] == EMPTY && !iAmRed && agentColor == 'R'){
                                            blacklist(row+1, col+2);//Blacklist row and col
                                        }else if (row != 4 && currentBoard[row+1][col+2] == EMPTY && iAmRed && agentColor == 'Y'){
                                            blacklist(row+1, col+2);//Blacklist row and col
                                        }
                                    }
                                    else {
                                        alertMove(agentColor, "RightWin");
                                        winningColumn = col+2;
                                    }
                                }
                            }
                        }
                        else
                        if (currentBoard[row][col+2] == agentColor ){//R+B
                            if (currentBoard[row][col+3] == agentColor ){//R+B+R
                                if (currentBoard[row][col+1] == EMPTY){//Is R+B+R+R Not R+Y+R+R
                                    if (row != 5){
                                        if (currentBoard[row+1][col+1] != EMPTY){//Winning Column is Level with Move
                                            alertMove(agentColor, "RightWin");
                                            winningColumn = col+1;
                                        }
                                        else if (currentBoard[row+1][col+1] == EMPTY && !iAmRed && agentColor == 'R'){//Winning Col is not level by 1 token && theyCanWin
                                            blacklist(row+1, col+1);//Blacklist row and col
                                        }else if (currentBoard[row+1][col+1] == EMPTY && iAmRed && agentColor == 'Y'){//Winning Col is not level by 1 token && theyCanWin
                                            blacklist(row+1, col+1);//Blacklist row and col
                                        }
                                    }
                                    else {
                                        alertMove(agentColor, "RightWin");
                                        winningColumn = col+1;
                                    }
                                }
                            }
                        }
                    }
                    ////////////////////////////////UpWin///////////////////////
                    if (row == 3 || row == 4 || row == 5){//Only Rows possible of an UpWin
                        if (currentBoard[row-1][col] == agentColor ){ //UpWin 2 in a row
                            if (currentBoard[row-2][col] == agentColor ) { //UpWin 3 in a row
                                if (currentBoard[row-3][col] == EMPTY){
                                    alertMove(agentColor, "UpWin");
                                    winningColumn = col;
                                }
                            }
                        }
                    }
                }
                else if (currentBoard[row][col] == EMPTY) {// TODO: The first token is blank (B+R+R+R)
                    ////////////////////////////RightWin - (B+R+R+R)/////////////////////////////
                    if (col >= 0 && col <= 3){//Only Columns possible of a RightWin
                        if (currentBoard[row][col+1] == agentColor ){ //RightWin 2 in a row (B+R)
                            if (currentBoard[row][col+2] == agentColor ) { //RightWin 3 in a row (B+R+R)
                                if (currentBoard[row][col+3] == agentColor ) { //RightWin 4 in a row (B+R+R+R)
                                    if (row != 5){//Not the bottom row OutOfBoundsException
                                        if (currentBoard[row+1][col] != EMPTY){//Winning Column is Level with Move
                                            alertMove(agentColor, "RightWin");
                                            winningColumn = col;
                                        }
                                        else if (currentBoard[row+1][col] == EMPTY && !iAmRed && agentColor == 'R'){//Winning Col is not level by 1 token && theyCanWin
                                            blacklist(row+1, col);//Blacklist row and col
                                        }else if (currentBoard[row+1][col] == EMPTY && iAmRed && agentColor == 'Y'){//Winning Col is not level by 1 token && theyCanWin
                                            blacklist(row+1, col);//Blacklist row and col
                                        }
                                    }
                                    else {
                                        alertMove(agentColor, "RightWin");
                                        winningColumn = col;
                                    }

                                }
                            }
                        }
                    }
                    //////////////////////////////DiagLeftWin - (B+R+R+R)///////////////////////////////
                    if (row >= 3 && row <= 5 && col >= 3 && col <= 6){//Only Rows & Columns possible of a DiagLeftWin
                        if (currentBoard[row-1][col-1] == agentColor ){ //DiagLeftWin 2 in a row (B+R)
                            if (currentBoard[row-2][col-2] == agentColor ) { //DiagLeftWin 3 in a row (B+R+R)
                                if (currentBoard[row-3][col-3] == agentColor ) { //DiagLeftWin 4 in a row (B+R+R+R)
                                    if (row != 5){//Not the bottom row OutOfBoundsException
                                        if (currentBoard[row+1][col] != EMPTY){//Winning Column is Level with Move
                                            alertMove(agentColor, "DiagLeftWin");
                                            winningColumn = col;
                                        }
                                        else if (currentBoard[row+1][col] == EMPTY && !iAmRed && agentColor == 'R'){//Winning Col is not level by 1 token && theyCanWin
                                            blacklist(row+1, col);//Blacklist row and col
                                        }
                                        else if (currentBoard[row+1][col] == EMPTY && iAmRed && agentColor == 'Y'){//Winning Col is not level by 1 token && theyCanWin
                                            blacklist(row+1, col);//Blacklist row and col
                                        }

                                    }
                                    else {
                                        alertMove(agentColor, "DiagLeftWin");
                                        winningColumn = col;
                                    }



                                }
                            }
                        }
                    }
                    //////////////////////////////DiagRightWin - (B+R+R+R)///////////////////////////////
                    if (row >= 3 && row <= 5 && col >= 0 && col <= 3){//Only Rows & Columns possible of a DiagRightWin
                        if (currentBoard[row-1][col+1] == agentColor ){ //DiagRightWin 2 in a row (B+R)
                            if (currentBoard[row-2][col+2] == agentColor ) { //DiagRightWin 3 in a row (B+R+R)
                                if (currentBoard[row-3][col+3] == agentColor ) { //DiagRightWin 4 in a row (B+R+R+R)
                                    if (row != 5){//Not the bottom row OutOfBoundsException
                                        if (currentBoard[row+1][col] != EMPTY){//Winning Column is Level with Move
                                            alertMove(agentColor, "DiagRightWin");
                                            winningColumn = col;
                                        }
                                        else if (currentBoard[row+1][col] == EMPTY && !iAmRed && agentColor == 'R'){//Winning Col is not level by 1 token && theyCanWin
                                            blacklist(row+1, col);//Blacklist row and col
                                        } else if (currentBoard[row+1][col] == EMPTY && iAmRed && agentColor == 'Y'){//Winning Col is not level by 1 token && theyCanWin
                                            blacklist(row+1, col);//Blacklist row and col
                                        }
                                    }
                                    else {
                                        alertMove(agentColor, "DiagRightWin");
                                        winningColumn = col;
                                    }
                                }
                            }
                        }
                    }
                }//
            }
        }
        return winningColumn;
    } //TODO: SIMPLIFY

    /**
     * Returns the name of this agent.
     *
     * @return the agent's name
     */
    public String getName()
    {
        return name;
    }
}

