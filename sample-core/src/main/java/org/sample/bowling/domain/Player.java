package org.sample.bowling.domain;

import org.codehaus.jackson.annotate.JsonUnwrapped;


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
    public boolean isDone() {
        return frames.getCurrentFrame().isComplete();
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
        int totalScore = 0;
        for (Frame frame : frames) {
            totalScore += frame.getScore();
        }

        return totalScore;
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

}
