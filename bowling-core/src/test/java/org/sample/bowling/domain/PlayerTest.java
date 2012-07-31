package org.sample.bowling.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.sample.bowling.exception.InvalidPlayerNameException;

/**
 *
 * @author jmnunezizu
 */
public class PlayerTest {

    private static final String DUMMY_NAME = "Mr Bowl";

    @Test(expected = InvalidPlayerNameException.class)
    public void newWithEmptyStringAsNameThrowsException() {
        Player.newWithName("");
    }

    @Test(expected = InvalidPlayerNameException.class)
    public void newWithNullAsNameThrowsException() {
        Player.newWithName(null);
    }

    @Test
    public void newWithNameCreatesANewPlayerWithTheRightName() {
        assertEquals(DUMMY_NAME, Player.newWithName(DUMMY_NAME).getName());
    }

    @Test
    public void testEquals() {
        assertEquals(Player.newWithName("test"), Player.newWithName("test"));
        assertFalse(Player.newWithName("test").equals(null));
        assertFalse(Player.newWithName("test").equals("test"));

        Player player = Player.newWithName("test");
        assertEquals(player, player);
    }

    @Test
    public void testHashCode() {
        Player player1 = Player.newWithName("test");
        Player player2 = Player.newWithName("test");
        assertEquals(player1.hashCode(), player2.hashCode());
    }

}
