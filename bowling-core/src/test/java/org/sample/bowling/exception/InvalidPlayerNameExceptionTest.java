package org.sample.bowling.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 *
 * @author jmnunezizu
 */
public class InvalidPlayerNameExceptionTest {

    @Test
    public void getNameWorks() {
        final String name = "test";
        InvalidPlayerNameException e = new InvalidPlayerNameException(name);
        assertEquals(name, e.getInvalidPlayerName());
    }

}
