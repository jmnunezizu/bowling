package org.sample.bowling.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 *
 * @author jmnunezizu
 */
public class BowlingLaneTest {

    @Test
    public void getIdReturnsNullIIfItHasntBeenPersisted() {
        BowlingLane bl = new BowlingLane();
        assertNull(bl.getId());
    }

    // -----------------------------------------------------------------------
    // start game
    // -----------------------------------------------------------------------

    @Test(expected = IllegalStateException.class)
    public void startThrowsExceptionIfThereAreNoPlayers() {
        new BowlingLane().start();
    }

    @Test(expected = IllegalStateException.class)
    public void startThrowsExceptionIfTheGameIsAlreadyInProgress() {
        BowlingLane bl = new BowlingLane();
        bl.addPlayer(Player.newWithName("The Player"));
        bl.start();
        bl.start();
    }

    @Test
    public void startSartsTheGameIfThereArePlayers() {
        BowlingLane bl = new BowlingLane();
        bl.addPlayer(Player.newWithName("The Player"));
        bl.start();
    }

    // -----------------------------------------------------------------------
    // add player
    // -----------------------------------------------------------------------

    @Test
    public void addPlayerWorks() {
        BowlingLane bl = new BowlingLane();
        Player player = Player.newWithName("Testman");
        bl.addPlayer(player);

        // assertions
        assertEquals("The total number of players should have been 1",
                1, bl.getPlayers().getNumberOfPlayers());
        assertEquals("The player wasn't registered properly",
                player, bl.getPlayers().getFirstPlayer());
    }

    @Test(expected = IllegalStateException.class)
    public void addPlayerThrowsExceptionIfTheGameIsInProgress() {
        BowlingLane bl = new BowlingLane();
        bl.addPlayer(Player.newWithName("Mr Nice"));
        bl.start();
        bl.addPlayer(Player.newWithName("Mr Exception"));
    }

    // -----------------------------------------------------------------------
    // Game execution testing
    // -----------------------------------------------------------------------

    @Test
    public void playAllFramesEndsTheGame() {
        BowlingLane bl = new BowlingLane();
        bl.addPlayer(Player.newWithName("Mr Bowl"));
        bl.start();

        assertFalse(bl.isGameOver());

        for (int i = 1; i <= 10; i++) {
            System.out.println("Throwing frame number " + i);
            bl.dropPins(5);
            bl.dropPins(3);
        }

        assertTrue(bl.isGameOver());
    }

    @Test(expected = IllegalStateException.class)
    public void playMoreThanTenFramesThrowsException() {
        BowlingLane bl = new BowlingLane();
        bl.addPlayer(Player.newWithName("Mr Bowl"));
        bl.start();

        for (int i = 1; i <= 11; i++) {
            System.out.println("Throwing frame number " + i);
            bl.dropPins(3);
            bl.dropPins(5);
        }
    }

    @Test
    public void throwAllStrikeEndsTheGameWithPerfectScoring() {
        BowlingLane bl = new BowlingLane();
        Player player = Player.newWithName("Mr Bowl");
        bl.addPlayer(player);
        bl.start();

        bl.dropPins(10); // frame #1
        assertEquals(10, player.getTotalScore());

        bl.dropPins(10); // frame #2
        assertEquals(30, player.getTotalScore());

        bl.dropPins(10); // frame #3
        assertEquals(60, player.getTotalScore());

        bl.dropPins(10); // frame #4
        assertEquals(90, player.getTotalScore());

        bl.dropPins(10); // frame #5
        assertEquals(120, player.getTotalScore());

        bl.dropPins(10); // frame #6
        assertEquals(150, player.getTotalScore());

        bl.dropPins(10); // frame #7
        assertEquals(180, player.getTotalScore());

        bl.dropPins(10); // frame #8
        assertEquals(210, player.getTotalScore());

        bl.dropPins(10); // frame #9
        assertEquals(240, player.getTotalScore());

        bl.dropPins(10); // frame #10 strike #1
        assertEquals(270, player.getTotalScore());

        bl.dropPins(10); // frame #10 strike #2
        assertEquals(290, player.getTotalScore());

        bl.dropPins(10); // frame #10 strike #3
        assertEquals(300, player.getTotalScore());

        bl.printScore();

        assertEquals(300, player.getTotalScore());
    }

}
