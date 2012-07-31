package org.sample.bowling.exception;

/**
 *
 * @author jmnunezizu
 */
public class InvalidNumberOfPinsException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Invalid number of pins %s";

    public InvalidNumberOfPinsException(final String numberOfPins) {
        super(String.format(MESSAGE_TEMPLATE, numberOfPins));
    }

}
