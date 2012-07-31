package org.sample.bowling.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.sample.bowling.exception.InvalidNumberOfPinsException;

/**
 *
 * @author jmnunezizu
 */
public class FrameTest {

    private static final int STRIKE = 10;

    private Frames frames;

    @Before
    public void setUp() {
        frames = new Frames();
    }

    @Test
    public void isCompleteReturnsFalseIfFrameWasNotPlayed() {
        Frame frame = frames.getCurrentFrame();
        assertFalse(frame.isComplete());
    }

    @Test
    public void isCompleteReturnsFalseIfThereAreRemainingShots() {
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(4);

        assertFalse(frame.isComplete());
    }

    @Test
    public void isCompleteWithStrike() {
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(STRIKE);

        assertTrue(frame.isComplete());
    }

    @Test
    public void isCompleteWithSpare() {
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(5);
        frame.dropPins(5);

        assertTrue(frame.isComplete());
    }

    @Test
    public void getRequiredThrowsForStrikeWorks() {
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(STRIKE);
        assertEquals(1, frame.getRequiredThrows());
    }

    @Test
    public void getRequiredThrowsForNonStrikeWorks() {
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(3);
        frame.dropPins(2);
        assertEquals(2, frame.getRequiredThrows());
    }

    @Test
    public void isStrikeWorks() {
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(STRIKE);

        assertTrue(frame.isStrike());
    }

    @Test
    public void isSpareWorks() {
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(4);
        frame.dropPins(6);

        assertTrue(frame.isSpare());
    }

    @Test
    public void isSpareReturnsFalseIfItIsStrike() {
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(STRIKE);

        assertTrue(frame.isStrike());
        assertFalse(frame.isSpare());
    }

    @Test
    public void isStrikeReturnsFalseIfItIsSpare() {
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(4);
        frame.dropPins(6);

        assertTrue(frame.isSpare());
        assertFalse(frame.isStrike());
    }

    @Test(expected = IllegalStateException.class)
    public void dropPinsThrowExceptionIfFrameIsCompleteWithSpare() {
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(5);
        frame.dropPins(5);
        frame.dropPins(3); // shouldn't be allowed
    }

    @Test(expected = IllegalStateException.class)
    public void dropPinsThrowExceptionIfFrameIsCompleteWithStrike() {
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(STRIKE);
        frame.dropPins(4); // shouldn't be allowed
    }

    @Test(expected = IllegalArgumentException.class)
    public void getPinsDroppedInThrowThrowsExceptionIfThereIsNotSuchAThrow() {
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(4);
        frame.dropPins(4);

        frame.getPinsDroppedInThrow(3);
    }

    @Test
    public void getPinsDroppedInFirstThrowWorks() {
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(5);

        assertEquals(5, frame.getPinsDroppedFirstThrow());
    }

    @Test
    public void getPinsDroppedInSecondThrowWorks() {
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(5);
        frame.dropPins(4);

        assertEquals(4, frame.getPinsDroppedSecondThrow());
    }

    @Test
    public void getScoreWorksForStrikeWithSingleFrame() {
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(STRIKE);

        assertEquals(STRIKE, frame.getScore());
    }

    @Test
    public void getScoreWorksForStrikeWithTwoFramesInProgress() {
        Frame firstFrame = frames.getCurrentFrame();
        firstFrame.dropPins(STRIKE);

        frames.nextFrame(); // we advance to the next frame

        Frame secondFrame = frames.getCurrentFrame();
        secondFrame.dropPins(4);

        assertEquals(STRIKE + 4, firstFrame.getScore());
    }

    @Test
    public void getScoreWorksForStrikeWithTwoFramesCompletedAsSpare() {
        Frame firstFrame = frames.getCurrentFrame();
        firstFrame.dropPins(STRIKE);

        frames.nextFrame();

        Frame secondFrame = frames.getCurrentFrame();
        secondFrame.dropPins(6);
        secondFrame.dropPins(4);

        assertEquals(20, firstFrame.getScore());
        assertEquals(10, secondFrame.getScore());
        assertEquals(30, frames.getTotalScore());
    }

    @Test
    public void getScoreWorksForStrikeWithThreeFramesCompletedAsStrike() {
        Frame firstFrame = frames.getCurrentFrame();
        firstFrame.dropPins(STRIKE);

        frames.nextFrame();

        Frame secondFrame = frames.getCurrentFrame();
        secondFrame.dropPins(STRIKE);

        frames.nextFrame();

        Frame thirdFrame = frames.getCurrentFrame();
        thirdFrame.dropPins(STRIKE);

        assertEquals(30, firstFrame.getScore());
        assertEquals(20, secondFrame.getScore());
        assertEquals(10, thirdFrame.getScore());
        assertEquals(60, frames.getTotalScore());
    }

    @Test
    public void getScoreWorksForStrikeWithThreeFramesAndTwoOfThemAsStrike() {
        Frame firstFrame = frames.getCurrentFrame();
        firstFrame.dropPins(STRIKE);

        frames.nextFrame();

        Frame secondFrame = frames.getCurrentFrame();
        secondFrame.dropPins(STRIKE);

        frames.nextFrame();

        Frame thirdFrame = frames.getCurrentFrame();
        thirdFrame.dropPins(5);
        thirdFrame.dropPins(2);

        assertEquals(25, firstFrame.getScore());
        assertEquals(17, secondFrame.getScore());
        assertEquals(7, thirdFrame.getScore());
        assertEquals(49, frames.getTotalScore());
    }

    @Test
    public void isCurrentFrameWorks() {
        Frame frame = frames.getCurrentFrame();
        assertTrue(frame.isCurrentFrame());

        frame.dropPins(10);
        frames.nextFrame();

        assertFalse(frame.isCurrentFrame());
    }

    @Test
    public void isFirstFrameWorks() {
        Frame frame = frames.getCurrentFrame();

        assertTrue(frame.isFirstFrame());
    }

    @Test(expected = InvalidNumberOfPinsException.class)
    public void dropPinsDoesNotAcceptValuesLesserThanZero() {
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(-1);
    }

    @Test(expected = InvalidNumberOfPinsException.class)
    public void dropPinsDoesNotAcceptValuesGreaterThanTen() {
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(11);
    }

    @Test(expected = InvalidNumberOfPinsException.class)
    public void dropPinsDoesNotAcceptValuesGreaterThanTheMaximumLimitPerFrame() {
        Frame frame = frames.getCurrentFrame();

        frame.dropPins(5);
        frame.dropPins(7);
    }

    @Test
    public void getCurrentThrowReturnsRightValue() {
        Frame frame = frames.getCurrentFrame();

        assertEquals(1, frame.getCurrentThrow());

        frame.dropPins(1);

        assertEquals(2, frame.getCurrentThrow());
    }

    @Test
    public void hasBeenPlayedReturnsTrueIfAtLeastAThrowHasBeenMade() {
        Frame frame = frames.getCurrentFrame();

        assertFalse(frame.hasBeenPlayed());

        frame.dropPins(5);

        assertTrue(frame.hasBeenPlayed());
    }

    @Test
    public void getDisplayValueWorksForUnplayedFrame() {
        Frame frame = frames.getCurrentFrame();
        assertEquals("", frame.getDisplayValue());
    }

    @Test
    public void getDisplayValueWorksForStrike() {
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(10);
        assertEquals("X", frame.getDisplayValue());
    }

    @Test
    public void getDisplayValueWorksForSpare() {
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(5);
        frame.dropPins(5);
        assertEquals("5 | /", frame.getDisplayValue());
    }

    @Test
    public void getDisplayValueWorksForOpenFrame() {
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(4);
        frame.dropPins(3);
        assertEquals("4 | 3", frame.getDisplayValue());
    }

    @Test
    public void makeEMMAHappy() {
        Frame frame = frames.getCurrentFrame();
        frame.toString();
    }

}
