package org.sample.bowling.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 *
 * @author jmnunezizu
 */
public class PlayerTest {

    private static final String DUMMY_NAME = "Mr Bowl";

    @Test
    public void newWithNameCreatesANewPlayerWithTheRightName() {
        assertEquals(DUMMY_NAME, Player.newWithName(DUMMY_NAME).getName());
    }

}
