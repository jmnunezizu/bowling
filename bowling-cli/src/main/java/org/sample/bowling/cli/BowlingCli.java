package org.sample.bowling.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.sample.bowling.cli.exception.InvalidNumberOfPlayersException;
import org.sample.bowling.domain.BowlingLane;
import org.sample.bowling.domain.Player;
import org.sample.bowling.exception.DuplicatePlayerNameException;
import org.sample.bowling.exception.InvalidNumberOfPinsException;
import org.sample.bowling.exception.InvalidPlayerNameException;

/**
 *
 * @author jmnunezizu
 */
public class BowlingCli {

    private BowlingLane bl;
    private BufferedReader in;
    private BowlingConfiguration bc;
    private BowlingCliMessenger messenger;
    private BowlingLaneScoreBoardConsolePrinter scoreBoard;
    private PinsDroppedReader pinsDroppedReader;

    public BowlingCli() {
        bl = new BowlingLane();
        in = new BufferedReader(new InputStreamReader(System.in));
        bc = new BowlingConfiguration();
        messenger = new BowlingCliMessenger(bl);
        scoreBoard = new BowlingLaneScoreBoardConsolePrinter();
        pinsDroppedReader = new PinsDroppedReader(in, messenger);
    }

    public static void main(String[] args) {
        new BowlingCli().run();
    }

    private void run() {
        messenger.printWelcomeMessage();

        configure();
        startGame();

        messenger.printGameOverMessage();
        scoreBoard.printScoreBoard(bl);
    }

    private void configure() {
        try {
            configurePlayers();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * This is the game's main loop method.
     */
    private void startGame() {
        bl.start();
        while (!bl.isGameOver()) {
            boolean pinsDroppedNotEntered = true;
            while (pinsDroppedNotEntered) {
                int pinsDropped = pinsDroppedReader.getPinsDropped();
                try {
                    bl.dropPins(pinsDropped);
                    pinsDroppedNotEntered = false;
                } catch (InvalidNumberOfPinsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    /**
     *
     * @throws IOException
     */
    private void configurePlayers() throws IOException {
        while (!bc.isNumberOfPlayersSet()) {
            messenger.printRequestNumberOfPlayers();
            try {
                bc.setNumberOfPlayers(in.readLine());
            } catch (InvalidNumberOfPlayersException e) {
                System.out.println(e.getMessage());
            }
        }

        for (int i = 1; i <= bc.numberOfPlayers; i++) {
            boolean playerNameNotConfigured = true;
            while (playerNameNotConfigured) {
                System.out.print("> Enter the name for player " + i + ": ");
                String playerName = in.readLine();
                try {
                    bl.addPlayer(Player.newWithName(playerName));
                    playerNameNotConfigured = false;
                } catch (InvalidPlayerNameException e) {
                    System.out.println(e.getMessage());
                } catch (DuplicatePlayerNameException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    /**
     * Helper class used for holding the bowling configuration read from the cli.
     */
    private class BowlingConfiguration {

        private int numberOfPlayers;

        public boolean isNumberOfPlayersSet() {
            return numberOfPlayers > 0;
        }

        public void setNumberOfPlayers(final String numberOfPlayers) {
            try {
                this.numberOfPlayers = Integer.valueOf(numberOfPlayers).intValue();
                if (this.numberOfPlayers < 0) {
                    throw new InvalidNumberOfPlayersException(numberOfPlayers);
                }
            } catch (NumberFormatException e) {
                throw new InvalidNumberOfPlayersException(numberOfPlayers, e);
            }
        }

    }

}
