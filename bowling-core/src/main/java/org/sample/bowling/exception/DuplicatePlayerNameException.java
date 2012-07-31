package org.sample.bowling.exception;

/**
 *
 * @author jmnunezizu
 */
public class DuplicatePlayerNameException extends RuntimeException {

    public DuplicatePlayerNameException(final String msg) {
        super(msg);
    }

}
