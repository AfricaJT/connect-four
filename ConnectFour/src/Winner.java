/**
 * Created by Jesse T on 2017/07/05.
 */
public class Winner {
    private boolean upWin;
    private boolean rightWin;
    private boolean diagRightWin;
    private boolean diagLeftWin;
    private int winPercent;


    /**
     * The Default Constructor initialising each private variable as false and 0.
     */
    public Winner(){
        this.upWin = false;
        this.rightWin = false;
        this.diagLeftWin = false;
        this.diagRightWin = false;
        this.winPercent = 0;
    }

    /**
     * Mutator Method for winPercent
     */
    public void setWinPercent(){
        int sum = 0;
        if (this.upWin){
            sum++;
        }
        if (this.rightWin){
            sum++;
        }
        if (this.diagLeftWin){
            sum++;
        }
        if (this.diagRightWin){
            sum++;
        }
        winPercent = sum;
    }

    /**
     * Mutator Method for boolean upWin
     * @param upWin possibility of a Vertically Upwards winning move at exact row and column
     */
    public void setUpWin (boolean upWin){
        this.upWin = upWin;
    }

    /**
     * Mutator Method for boolean rightWin
     * @param rightWin possibility of a Horizontally Right winning move at exact row and column
     */
    public void setRightWin (boolean rightWin){
        this.rightWin = rightWin;
    }

    /**
     * Mutator Method for boolean diagLeftWin
     * @param diagLeftWin possibility of a Diagonal Left winning move at exact row and column
     */
    public void setDiagLeftWin (boolean diagLeftWin){
        this.diagLeftWin = diagLeftWin;
    }

    /**
     * Mutator Method for boolean diagRightWin
     * @param diagRightWin possibility of a Diagonally Right winning move at exact row and column
     */
    public void setDiagRightWin (boolean diagRightWin){
        this.diagRightWin = diagRightWin;
    }

    /**
     * Accessor Method for the instance variable upWin
     * @return upWin - possibility of a Vertically Upwards winning move at exact row and column
     */
    public boolean getUpWin(){
        return upWin;
    }

    /**
     * Accessor Method for the instance variable diagLeftWin
     * @return diagLeftWin - possibility of a Diagonal Left winning move at exact row and column
     */
    public boolean getDiagLeftWin(){
        return diagLeftWin;
    }

    /**
     * Accessor Method for the instance variable rightWin
     * @return rightWin - possibility of a Horizontally Right winning move at exact row and column
     */
    public boolean getRightWin(){
        return rightWin;
    }

    /**
     * Accessor Method for the instance variable diagRightWin
     * @return diagRightWin - possibility of a Diagonally Right winning move at exact row and column
     */
    public boolean getDiagRightWin(){
        return diagRightWin;
    }

    /**
     * Accessor Method for winPercent
     * @return winPercent the possibility of a winning move
     */
    public int getWinPercent(){
        return winPercent;
    }

    @Override
    public String toString() {
        return "" + winPercent;
    }
}
