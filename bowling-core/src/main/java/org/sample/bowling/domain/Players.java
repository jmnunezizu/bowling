package org.sample.bowling.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.sample.bowling.exception.DuplicatePlayerNameException;

/**
 *
 * @author jmnunezizu
 */
public class Players implements Iterable<Player> {

    private static final int FIRST_PLAYER = 0;

    private List<Player> players = new ArrayList<Player>();
    private Player currentPlayer;

    /**
     * @return Returns the current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Advances the current player to the next player. If the current player is
     * the last player, it will set the current player to the first player.
     *
     * <p>It will only advance to the next player if the current player is done
     * with his frame.</p>
     */
    public void nextPlayer() {
        if (currentPlayer.isDoneForCurrentFrame()) {
            if (isLastPlayer(currentPlayer)) {
                currentPlayer = players.get(FIRST_PLAYER);
            } else {
                currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
            }
        }
    }

    /**
     * Sets the current player to {@code player}. The player has to belong to
     * the players that are part of this collection.
     *
     * @param player The player that must be set as the current player.
     * @throws IllegalArgumentException if player {@code player} does not belong
     *      to this list of players.
     */
    public void setCurrentPlayer(final Player player) {
        if (players.indexOf(player) == -1) {
            throw new IllegalArgumentException("That player is not registered in this game");
        }

        this.currentPlayer = player;
    }

    /**
     * @return A list with all the players.
     */
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    /**
     * Evaluates whether or not the player {@code player} is part of this
     * collection of players.
     *
     * @param player The player that must be evaluated.
     * @return true if it is part of the collection, false otherwise.
     */
    public boolean hasPlayer(final Player player) {
        return players.contains(player);
    }

    /**
     * Evaluates whether or not this collection has players.
     *
     * @return true if the collection has at least one player, false otherwise.
     */
    public boolean hasAny() {
        return !players.isEmpty();
    }

    /**
     * Adds the player {@code player} to this list of players.
     *
     * @param player The player that has to be added. It cannot be {@code null}.
     * @return The list of players for easy method chaining.
     * @throws IllegalArgumentException If the player {@code player} is {@code null}.
     */
    public Players addPlayer(final Player player) {
        if (player == null) {
            throw new IllegalArgumentException("The player cannot be null");
        }

        if (players.contains(player)) {
            throw new DuplicatePlayerNameException(
                    "The player with name " + player.getName() + " is already registered"
            );
        }

        players.add(player);
        return this;
    }

    /**
     * Returns the first player in this collection.
     * @return
     */
    @JsonIgnore
    public Player getFirstPlayer() {
        return players.get(FIRST_PLAYER);
    }

    /**
     * Returns the last player in this collection.
     * @return
     */
    @JsonIgnore
    public Player getLastPlayer() {
        return players.get(players.size() - 1);
    }

    /**
     * Evaluates whether or not the player {@code player} is the last player.
     *
     * @param player The player to be evaluated.
     * @return true if it is the last player, false otherwise.
     */
    public boolean isLastPlayer(final Player player) {
        return players.indexOf(player) == players.size() - 1;
    }

    /**
     * Returns the number of players in this collection.
     * @return
     */
    public int getNumberOfPlayers() {
        return players.size();
    }

    /**
     *
     */
    @Override
    public Iterator<Player> iterator() {
        return players.iterator();
    }

}
