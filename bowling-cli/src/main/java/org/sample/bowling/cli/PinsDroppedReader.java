package org.sample.bowling.cli;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * This is the class used to read the number of pins dropped in each throw. It
 * provides validation -up to a certain level- to the input.
 *
 * <p>The rest of the validation is handled by the core of the bowling game.</p>
 *
 * @author jmnunezizu
 */
public class PinsDroppedReader {

    private BufferedReader reader;
    private BowlingCliMessenger messenger;

    /**
     * Constructor that exposes the needed dependencies.
     *
     * @param reader The reader used to read from the CLI.
     * @param messenger The messenger used to print messages to the CLI.
     * @throws IllegalArgumentException If any of the dependencies is invalid.
     */
    public PinsDroppedReader(final BufferedReader reader, final BowlingCliMessenger messenger) {
        if (reader == null) {
            throw new IllegalArgumentException("the bufferedReader cannot be null");
        }

        if (messenger == null) {
            throw new IllegalArgumentException("the bowlingCliMessenger cannot be null");
        }

        this.reader = reader;
        this.messenger = messenger;
    }

    /**
     *
     * @return
     */
    public int getPinsDropped() {
        int pinsDropped = 0;
        boolean pinsDroppedRead = false;
        while (!pinsDroppedRead) {
            messenger.printEnterDroppedPinsInFrameForCurrentPlayer();
            try {
                pinsDropped = Integer.valueOf(reader.readLine()).intValue();
                pinsDroppedRead = true;
            } catch (NumberFormatException e) {
                System.out.println("The number of pins has to be a number");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        return pinsDropped;

    }

}
