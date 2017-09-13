import java.util.Random;
import java.util.Scanner;

/**
 * Created by Jesse T on 2017/07/11.
 */
public class Connect4Arcade {

    private Connect4Game myGame;    // the game itself
    private Agent redPlayer, yellowPlayer;   // the two players playing the game
    private boolean redPlayerturn, gameActive, firstMove = true;  // booleans controlling whose turn it is and whether a game is ongoing
    private Random r;   // a random number generator to randomly decide who plays first
    private int sum;

    /**
     * Creates a new Connect4Arcade with a given game and pair of players.
     *
     * @param game the game itself.
     * @param redPlayer the agent playing as the red tokens.
     * @param yellowPlayer the agent playing as the yellow tokens.
     */
    public Connect4Arcade(Connect4Game game, Agent redPlayer, Agent yellowPlayer)
    {
        this.myGame = game;   // stores the game itself
        this.redPlayer = redPlayer;   // stores the red player
        this.yellowPlayer = yellowPlayer; //stores the yellow player
        gameActive = false;   // initially sets that no game is active
        r = new Random();   // creates the random number generator

        this.newGame();
        this.playToEnd();
    }

    /**
     * Displays Banner with Next Players Turn
     */
    private void alertNextTurn(){//TODO: DEBUG
        sum++;
        if(redPlayerturn)
        {
            if (redPlayer instanceof AgentUser){
                //System.out.println(redPlayer.toString() + " plays next...");
                System.out.println(redPlayer.toString() + " plays next..." + sum);
            }
            else {
                //System.out.println(redPlayer.toString() + " dropped its token...");
                System.out.println(redPlayer.toString() + " dropped its token..." + sum);
            }

        }
        else
        {
            if (yellowPlayer instanceof AgentUser){
                //System.out.println(yellowPlayer.toString() + " plays next...");
                System.out.println(yellowPlayer.toString() + " plays next..." + sum);
            }
            else {
                //System.out.println(yellowPlayer.toString() + " dropped its token...");
                System.out.println(yellowPlayer.toString() + " dropped its token..." + sum);
            }
        }
    }

    /**
     * Alternates moves between AI only players
     */
    private void nextTurnAI(){
        if(redPlayerturn) // if it's the red player's turn, run their move
        {
            if (firstMove){
                redPlayer.move();
                myGame.displayBoard();
            }
            else {
                firstMove = true;
                redPlayer.move();
            }
        }
        else // if it's the yellow player's turn, run their move
        {
            if (firstMove){
                yellowPlayer.move();
                myGame.displayBoard();
            }
            else {
                firstMove = true;
                yellowPlayer.move();
            }

        }
        redPlayerturn = !redPlayerturn;   // switch whose turn it is

    }

    /**
     * Alternates moves between User only players'
     */
    private void nextTurnUser(){
        if(redPlayerturn) // if it's the red player's turn, run their move
        {
            redPlayer.move();
        }
        else // if it's the yellow player's turn, run their move
        {
            yellowPlayer.move();
        }
        myGame.displayBoard();
        redPlayerturn = !redPlayerturn;   // switch whose turn it is
    }

    /**
     * Alternates moves between User vs AI Players only
     */
    private void nextTurnUserAI(){

        if(redPlayerturn) // if it's the red player's turn, run their move
        {
            if (firstMove && redPlayer instanceof AgentAI){ //If first move of the game and player is AI
                redPlayer.move();
                myGame.displayBoard();
            }
            else if (firstMove && redPlayer instanceof AgentUser){ //If first move of the game and player is User
                myGame.displayBoard();
                redPlayer.move();
            }
            else if (!firstMove && redPlayer instanceof AgentAI){//If NOT first move of the game and player is AI
                redPlayer.move();
                myGame.displayBoard();
            }
            else if (!firstMove && redPlayer instanceof AgentUser){ //If NOT first move of the game and player is User
                redPlayer.move();
            }
        }
        else // if it's the yellow player's turn, run their move
        {
            if (firstMove && yellowPlayer instanceof AgentAI){ //If first move of the game and player is AI
                yellowPlayer.move();
                myGame.displayBoard();
            }
            else if (firstMove && yellowPlayer instanceof AgentUser){ //If first move of the game and player is User
                myGame.displayBoard();
                yellowPlayer.move();
            }
            else if (!firstMove && yellowPlayer instanceof AgentAI){//If NOT first move of the game and player is AI
                yellowPlayer.move();
                myGame.displayBoard();
            }
            else if (!firstMove && yellowPlayer instanceof AgentUser){ //If NOT first move of the game and player is User
                yellowPlayer.move();
            }
        }
        firstMove = false;
        redPlayerturn = !redPlayerturn;   // switch whose turn it is
    }

    /**
     * Alternating turns based on who is playing
     */
    private void nextTurn (){
        if (redPlayer instanceof AgentAI && yellowPlayer instanceof AgentAI){
            nextTurnAI();
        }
        else
        if (redPlayer instanceof AgentUser && yellowPlayer instanceof AgentUser){
            nextTurnUser();
        }
        else{
            nextTurnUserAI();
        }
    }

    /**
     * Checks the Status of the Game - Win, Lose, or Draw
     */
    private void checkStatus(){
        char won = myGame.gameWon();
        if (won != 'N') // if the game has been won...
        {
            gameActive = false;
            if (won == 'R') // if red won, say so
            {
                System.out.println(redPlayer.toString() + " wins!");
                myGame.displayBoard();
            }
            else if (won == 'Y') // if yellow won, say so
            {
                System.out.println(yellowPlayer.toString() + " wins!");
                myGame.displayBoard();
            }
        }
        else if (Board.boardFull()) // if the board is full...
        {
            System.out.println("The game ended in a draw!");// announce the draw
            myGame.displayBoard();
            gameActive = false;
        }
        else {
            alertNextTurn();
        }
    }

    /**
     * Runs the next move of the game.
     */
    private void nextMove()
    {
        nextTurn();
        myGame.setBoardMatrix();
        checkStatus();
        //TODO: playAgain();
    }

    /**
     * Clear the board and start a new game.
     */
    private void newGame()
    {
        myGame.newBoard();
        gameActive = true;
        redPlayerturn = r.nextBoolean();
        if (redPlayerturn)
        {
            System.out.println(redPlayer.toString() + " plays first!");
            myGame.setRedPlayedFirst(true);
        }
        else
        {
            System.out.println(yellowPlayer.toString() + " plays first!");
            myGame.setRedPlayedFirst(false);
        }
        myGame.setBoardMatrix();
    }

    /**
     * Runs the game until it's over.
     */
    private void playToEnd()
    {
        while (gameActive) // keep playing the next move until the game ends
        {
            nextMove();
        }
    }
}
