package org.sample.bowling.cli;

import java.io.PrintStream;

import org.sample.bowling.domain.BowlingLane;
import org.sample.bowling.domain.Player;

/**
 * This is the class used to print messages to the console.
 *
 * @author jmnunezizu
 */
public class BowlingCliMessenger {

    private PrintStream out = System.out;
    private BowlingLane bl;

    /**
     * Constructor that takes in all needed dependencies.
     *
     * @param bl The bowlingLane game that is currently in execution.
     * @throws IllegalArgumentException If any of the arguments are invalid.
     */
    public BowlingCliMessenger(final BowlingLane bl) {
        if (bl == null) {
            throw new IllegalArgumentException("The bowlingLane cannot be null");
        }
        this.bl = bl;
    }

    public void printWelcomeMessage() {
        out.println("   _ 0");
        out.println(" o'-/-\\--------------------------------------------");
        out.println("   |\\                                           . o");
        out.println("   / |       '.                             . o . o");
        out.println("              .'                              o . o");
        out.println("             '                                    o");
        out.println(" __________________________________________________\n");

        out.println("Welcome to The Bowling Command Line Interface Program");
        out.println("-----------------------------------------------------\n");
    }

    public void printRequestNumberOfPlayers() {
        out.print("> Please enter the number of players: ");
    }

    public void printEnterDroppedPinsInFrameForCurrentPlayer() {
        Player player = bl.getCurrentPlayer();
        out.print("> Enter pins dropped by Player '" +
                player.getName() + "' in " +
                player.getCurrentFrame().toString() + ": "
        );
    }

    public void printGameOverMessage() {
        out.println("\nThe game is now over. Here is the final Score Board\n");
    }

}
