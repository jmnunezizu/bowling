package org.sample.bowling.domain;


/**
 * The last frame is special, since it has an extra throw if a spare or strike
 * is performed in the first two throws.
 *
 * @author jmnunezizu
 */
public class LastFrame extends Frame {

    private static final int THIRD_THROW = 2;

    public LastFrame(final Frames frames) {
        super(frames);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initPinsDropped() {
        pinsDropped = new int[3];
    }

    /**
     * {@inheritDoc}
     *
     * The last frame can have 3 throws if a strike or spare is performed.
     */
    @Override
    protected int getRequiredThrows() {
        if (isStrike() || isSpare()) {
            return 3;
        } else {
            return 2;
        }
    }

    /**
     *
     */
    @Override
    public int getScore() {
        if (isStrike() || isSpare()) {
            return getPinsDroppedInThrow(FIRST_THROW) + getPinsDroppedInThrow(SECOND_THROW) + getPinsDroppedInThrow(THIRD_THROW);
        } else {
            return super.getScore();
        }
    }

    /**
     *
     */
    @Override
    protected int getBonusPointsInSecondThrow() {
        return pinsDropped[SECOND_THROW];
    }

}
