package org.sample.bowling.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sample.bowling.exception.DuplicatePlayerNameException;

/**
 *
 * @author jmnunezizu
 */
public class PlayersTest {

    @Test
    public void getPlayersReturnsEmptyListIfItHasNoPlayers() {
        Players players = new Players();
        assertTrue(players.getPlayers().isEmpty());
    }

    @Test
    public void getPlayersReturnsTheRegisteredPlayers() {
        Players players = new Players();
        Player player = Player.newWithName("player");
        players.addPlayer(player);

        assertTrue(players.getPlayers().contains(player));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCurrentPlayerDoesNotAcceptUnregisteredPlayers() {
        Players players = new Players();
        players.setCurrentPlayer(Player.newWithName("player"));
    }

    @Test
    public void hasPlayerWorks() {
        Players players = new Players();
        Player registeredPlayer = Player.newWithName("player");
        players.addPlayer(registeredPlayer);

        assertTrue(players.hasPlayer(registeredPlayer));
        assertFalse(players.hasPlayer(Player.newWithName("oa")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addPlayerDoesNotAcceptNullPlayers() {
        new Players().addPlayer(null);
    }

    @Test(expected = DuplicatePlayerNameException.class)
    public void addPlayerDoesNotAcceptPlayersWithDuplicateNames() {
        Players players = new Players();
        players.addPlayer(Player.newWithName("player")).addPlayer(Player.newWithName("player"));
    }

    @Test
    public void getLastPlayerWorks() {
        Players players = new Players();
        Player player1 = Player.newWithName("player 1");
        Player player2 = Player.newWithName("player 2");
        Player player3 = Player.newWithName("player 3");
        players.addPlayer(player1).addPlayer(player2).addPlayer(player3);

        assertEquals(player3, players.getLastPlayer());
    }

    @Test
    public void isLastPlayerWorks() {
        Players players = new Players();
        Player player1 = Player.newWithName("player 1");
        Player player2 = Player.newWithName("player 2");

        players.addPlayer(player1).addPlayer(player2);

        assertTrue(players.isLastPlayer(player2));
    }

    @Test
    public void nextPlayerAdvancesToTheNextPlayerIfCurrentPlayerIsDoneWithTheCurrentFrame() {
        Players players = new Players();
        Player player1 = Player.newWithName("player 1");
        Player player2 = Player.newWithName("player 2");
        players.addPlayer(player1).addPlayer(player2);

        players.setCurrentPlayer(player1);

        assertEquals(player1, players.getCurrentPlayer());

        player1.getCurrentFrame().dropPins(10);

        players.nextPlayer();

        assertEquals(player2, players.getCurrentPlayer());
    }

    @Test
    public void nextPlayerAdvancesToTheFirstPlayerIfCurrentPlayerIsLastPlayerAndIsDoneWithCurrentFrame() {
        Players players = new Players();
        Player player1 = Player.newWithName("player 1");
        Player player2 = Player.newWithName("player 2");
        players.addPlayer(player1).addPlayer(player2);

        players.setCurrentPlayer(player1);

        assertEquals(player1, players.getCurrentPlayer());

        player1.getCurrentFrame().dropPins(10);

        players.nextPlayer();

        assertEquals(player2, players.getCurrentPlayer());

        player2.getCurrentFrame().dropPins(10);

        players.nextPlayer();

        assertEquals(player1, players.getCurrentPlayer());
    }

    @Test
    public void iteratorToMakeEMMAHappy() {
        new Players().iterator();
    }

}
