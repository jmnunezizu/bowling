package org.sample.bowling.exception;

/**
 *
 * @author jmnunezizu
 */
public class InvalidPlayerNameException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Invalid player with name %s";

    private String invalidPlayerName;

    public InvalidPlayerNameException(final String name) {
        super(String.format(MESSAGE_TEMPLATE, name));
        this.invalidPlayerName = name;
    }

    public String getInvalidPlayerName() {
        return invalidPlayerName;
    }

}
