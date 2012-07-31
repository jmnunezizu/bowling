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

    @Override
    protected int getMaximumNumberOfDroppedPins() {
        if (isStrike() || isSpare()) {
            return 30;
        } else {
            return 10;
        }
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

    @Override
    public String getDisplayValue() {
        StringBuilder sb = new StringBuilder();

        // first throw
        if (getPinsDroppedFirstThrow() == 10) { // strike
            sb.append("X");
        } else {
            sb.append(getPinsDroppedFirstThrow());
        }

        sb.append(" | ");

        // second throw
        if (getPinsDroppedSecondThrow() == 10) { // strike
            sb.append("X");
        } else if (getPinsDroppedFirstThrow() + getPinsDroppedSecondThrow() == 10) { // spare
            sb.append("/");
        } else {
            sb.append(getPinsDroppedSecondThrow());
        }

        // third throw only if it is strike or spare
        if (isSpare() || isStrike()) {
            if (getPinsDroppedInThrow(THIRD_THROW) == 10) {
                sb.append(" | X");
            } else {
                sb.append(" | " + getPinsDroppedInThrow(THIRD_THROW));
            }
        }

        return sb.toString();
    }

    /**
     *
     */
    @Override
    protected int getBonusPointsInSecondThrow() {
        return pinsDropped[SECOND_THROW];
    }

}
