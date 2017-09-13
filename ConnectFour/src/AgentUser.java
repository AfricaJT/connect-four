/**
 * Created by Jesse T on 2017/07/04.
 */
import java.util.Random;
import java.util.Scanner;

public class AgentUser extends Agent
{
    private Random r;
    private String name;
    public static Winner69 winner69;

    /**
     * Constructs a new agent, giving it the game and telling it whether it is Red or Yellow.
     * @param game The game the agent will be playing.
     * @param iAmRed True if the agent is Red, False if the agent is Yellow.
     */
    public AgentUser(Connect4Game game, boolean iAmRed, String name)
    {
        super(game, iAmRed);
        r = new Random();
        this.name = name;
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
     * After the move() method is called, the game engine will check to make sure the move was
     * valid. A move might be invalid if:
     * - No token was place into the game.
     * - More than one token was placed into the game.
     * - A previous token was removed from the game.
     * - The color of a previous token was changed.
     * - There are empty spaces below where the token was placed.
     *
     * If an invalid move is made, the game engine will announce it and the game will be ended.
     *
     */
    public void move()
    {
        winner69.updateMatrix();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Col #: ");
        boolean valid = false;
        int col = -1;
        while (!valid) {
            col = sc.nextInt();
            col -= 1;
            if (col >= 0 && col <= 6) {
                if (Connect4Game.getLowestEmptyIndex(col) != -1) {
                    valid = true;
                }

            }
        }
        Connect4Game.dropToken(col, iAmRed);//If valid move - place token in column - user color
        System.out.println();
    }

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
