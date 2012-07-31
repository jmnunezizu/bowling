package org.sample.bowling.cli.exception;

/**
 *
 * @author jmnunezizu
 */
public class InvalidNumberOfPlayersException extends RuntimeException {

    private final static String MESSAGE_TEMPLATE = "Invalid number of players %s";

    public InvalidNumberOfPlayersException(final String numberOfPlayers) {
        super(String.format(MESSAGE_TEMPLATE, numberOfPlayers));
    }

    public InvalidNumberOfPlayersException(final String numberOfPlayers, final Throwable t) {
        super(String.format(MESSAGE_TEMPLATE, numberOfPlayers), t);
    }

}
