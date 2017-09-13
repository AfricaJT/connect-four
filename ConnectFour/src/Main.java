/**
 * The main driver of the program. This file will create the game and create the two agents
 */
public class Main
{
    public static void main(String[] args)
    {
        new Board();
        Connect4Game game = new Connect4Game(); // create the game with a 7x6 grid;
        Agent redPlayer = new AgentAI(game, true, "WATSON"); // create the red player, any subclass of Agent
        //Agent redPlayer = new AgentUser(game, true, "Finn");
        //Agent yellowPlayer = new AgentAI(game, false, "ALEXA");
        Agent yellowPlayer = new AgentUser(game, false, "Jake"); // create the yellow player, any subclass of Agent

        new Connect4Arcade(game, redPlayer, yellowPlayer); // Start the game
    }
}
    