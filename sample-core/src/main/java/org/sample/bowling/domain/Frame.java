package org.sample.bowling.domain;

import org.codehaus.jackson.annotate.JsonIgnore;


/**
 * This class represents a Frame in a game of bowling. A frame has 10 pins and
 * stores the number of pins dropped in each throw, as well as the total score.
 *
 * @author jmnunezizu
 */
public class Frame {

    private static final int TOTAL_PINS = 10;
    private static final int REQUIRED_THROWS_FOR_STRIKE = 1;

    protected static final int FIRST_THROW = 0;
    protected static final int SECOND_THROW = 1;
    protected int[] pinsDropped;
    private Frames frames;

    private int throwsMade;

    /**
     *
     * @param frames
     */
    public Frame(final Frames frames) {
        this.frames = frames;
        initPinsDropped();
    }

    /**
     *
     */
    protected void initPinsDropped() {
        pinsDropped = new int[2];
    }

    /**
     * Evaluates whether or not this frame has been played, i.e. at least one
     * throw has been thrown in this frame.
     *
     * @return true if it has been played, false otherwise.
     */
    public boolean hasBeenPlayed() {
        return throwsMade > 0;
    }

    /**
     * Evaluates whether or not this frame is the current frame (currently
     * being played).
     *
     * @return true if it is the current frame, false otherwise.
     */
    public boolean isCurrentFrame() {
        return frames.getCurrentFrame().equals(this);
    }

    /**
     * Evaluates whether or not this frame is the first one.
     *
     * @return true if it is the first one, false otherwise.
     */
    @JsonIgnore
    public boolean isFirstFrame() {
        return frames.isFirstFrame(this);
    }

    /**
     * Evaluates whether or not this frame is the last one.
     *
     * @return true if it is the last one, false otherwise.
     */
    @JsonIgnore
    public boolean isLastFrame() {
        return frames.isLastFrame(this);
    }

    /**
     * @return Returns the frame that comes after this frame.
     */
    @JsonIgnore
    public Frame getNextFrame() {
        return frames.getNextFrame(this);
    }

    /**
     * Drops a total of {@code pinsDropped}.
     *
     * @param pinsDropped The total of pins that were dropped in the frame.
     */
    public void dropPins(final int pinsDropped) {
        if (isComplete()) {
            throw new IllegalStateException("No more throws are allowed for this frame");
        }
        this.pinsDropped[throwsMade++] = pinsDropped;
    }

    /**
     * Evaluates whether or not the frame has been complete, i.e. a strike has
     * been made, or a total of 2 throws have been executed.
     *
     * @return true if the frame is complete, false otherwise.
     */
    public boolean isComplete() {
        return throwsMade != 0 && throwsMade == getRequiredThrows();
    }

    /**
     * Evaluates whether or not the throws performed in this frame were a strike.
     * This means that all 10 pins have been dropped in the first throw.
     *
     * @return true if it is a strike, false otherwise.
     */
    public boolean isStrike() {
        return getPinsDroppedFirstThrow() == TOTAL_PINS;
    }

    /**
     * Evaluates whether or not the throws performed in this frame were a spare.
     * This means that all 10 pins have been dropped in the first and second throw.
     *
     * @return true if it is a spare, false otherwise.
     */
    public boolean isSpare() {
        return !isStrike() && getPinsDroppedFirstThrow() + getPinsDroppedSecondThrow() == TOTAL_PINS;
    }

    /**
     * @return Returns the number of throws for this Frame. If it is a strike,
     *      the number of required throws is 1, otherwise is 2.
     */
    @JsonIgnore
    protected int getRequiredThrows() {
        if (isStrike()) {
            return REQUIRED_THROWS_FOR_STRIKE;
        } else {
            return 2;
        }
    }

    /**
     * @return The number of pins dropped in the first throw.
     */
    public int getPinsDroppedFirstThrow() {
        return pinsDropped[FIRST_THROW];
    }

    /**
     * @return The number of pins dropped in the second throw.
     */
    public int getPinsDroppedSecondThrow() {
        return pinsDropped[SECOND_THROW];
    }

    /**
     * Returns the number of pins dropped in the throw {@code throwNumber}.
     *
     * @param throwNumber The throw number for which the number of dropped pins
     *      is required.
     * @return The number of dropped pins.
     */
    @JsonIgnore
    protected int getPinsDroppedInThrow(int throwNumber) {
        if (throwNumber > pinsDropped.length) {
            throw new IllegalArgumentException("There isn't a throw with number " + throwNumber);
        }
        return pinsDropped[throwNumber];
    }

    /**
     * Returns the score of this frame. The score is not only the total of pins
     * dropped by the first and second throw, since the terms "spare" and "strike"
     * come into play.
     *
     * <p>Rules
     * <ul>If the two throws don't knock the 10 pins, the score will be the sum
     * of throw 1 + throw 2, i.e. throw 1 = 4, throw = 2, score = 6
     * <ul>If the two throws knock down the 10 pins, the score will be 10 (
     * number of pins) + the pins dropped in the next throw (belonging to the
     * next frame), i.e. throw 1 = 5, throw 2 = 5, throw 3 = 4, score = 13.
     * <ul>If the 10 pins are knocked down in one throw (strike), and
     * the next throw from frame #2 is a strike too, the score will be
     * 10 (frame #1, first throw) + 10 (frame #2, first throw) +
     * N (frame #3, first throw).
     *
     * @return The total score for this frame.
     */
    public int getScore() {
        int score = 0;

        if (isSpare()) {
            score = TOTAL_PINS + getNextFrame().getBonusPointsInFirstThrow();
        } else if (isStrike()) {
            Frame nextFrame = getNextFrame();
            score = TOTAL_PINS + nextFrame.getBonusPointsInFirstThrow() + nextFrame.getBonusPointsInSecondThrow();
        } else { // open frame
            score = getPinsDroppedFirstThrow() + getPinsDroppedSecondThrow();
        }

        return score;
    }

    /**
     * This method is just for syntactic sugar as it provides the same as doing
     * {{@link #getPinsDroppedFirstThrow()}.
     *
     * @return The number of bonus points awarded for the first throw.
     */
    @JsonIgnore
    protected int getBonusPointsInFirstThrow() {
        return getPinsDroppedFirstThrow();
    }

    /**
     * Returns the bonus points awarded for the second throw. If the frame is a
     * strike, it means that there isn't a second throw, so the call will be
     * forwarded to the next frame. e.g:
     *
     * <ul>
     * <li>frame 1, first throw 10
     * <li>frame 2, first throw 10
     * <li>frame 3, first throw 2
     * </ul>
     *
     * That makes a total of 22 points for the first frame, i.e. 10 +
     * 10 (bonus points first throw) + 2 (bonus points second throw).
     *
     * @return The total of bonus points awarded by the first throw.
     */
    @JsonIgnore
    protected int getBonusPointsInSecondThrow() {
        if (isStrike()) {
            return getNextFrame().getPinsDroppedFirstThrow();
        }

        return pinsDropped[SECOND_THROW];
    }

    @Override
    public String toString() {
        return "Frame #" + (frames.getFrames().indexOf(this) + 1);
    }

}
