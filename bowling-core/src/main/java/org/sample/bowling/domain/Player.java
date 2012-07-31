package org.sample.bowling.domain;

import org.codehaus.jackson.annotate.JsonUnwrapped;
import org.sample.bowling.exception.InvalidPlayerNameException;
import org.springframework.util.StringUtils;


/**
 * This class represents a player registered in a game of bowling. Players hold
 * the information of the frames they are playing in.
 *
 * @author jmnunezizu
 */
public class Player {

    private String name;

    @JsonUnwrapped
    private Frames frames = new Frames();

    /**
     * Static factory method for creating a new player with name {@code name}.
     *
     * @param name The name of the new player.
     * @return The newly created player.
     */
    public static Player newWithName(final String name) {
        if (!StringUtils.hasText(name)) {
            throw new InvalidPlayerNameException(name);
        }

        Player player = new Player();
        player.name = name;

        return player;
    }

    /**
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * @return evaluates whether or not this player is done i.e. has completed
     *      all of his throws for the current frame.
     */
    public boolean isDoneForCurrentFrame() {
        return frames.getCurrentFrame().isComplete();
    }

    /**
     * Evaluates whether or not this player has finished playing, i.e. has
     * completed all of his throws for all of the frames.
     *
     * @return true if he is done playing, false otherwise.
     */
    public boolean isDonePlaying() {
        Frame currentFrame = frames.getCurrentFrame();
        return currentFrame.isLastFrame() && currentFrame.isComplete();
    }

    /**
     * @return The frames for this player.
     */
    public Frames getFrames() {
        return frames;
    }

    /**
     * @return Returns the total score of this player.
     */
    public int getTotalScore() {
        return frames.getTotalScore();
    }

    /**
     * @return The current frame for this player.
     */
    public Frame getCurrentFrame() {
        return frames.getCurrentFrame();
    }

    /**
     * Evaluates whether or not this is the last player.
     *
     * @param players The players used for evaluation.
     * @return true if it is the last player, false otherwise.
     */
    public boolean isLastPlayer(final Players players) {
        return players.isLastPlayer(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Player)) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        Player player = (Player) obj;

        return this.name.equals(player.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

}
