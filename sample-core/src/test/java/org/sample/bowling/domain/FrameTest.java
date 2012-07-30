package org.sample.bowling.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 *
 * @author jmnunezizu
 */
public class FrameTest {

    private static final int STRIKE = 10;

    @Test
    public void isCompleteReturnsFalseIfFrameWasNotPlayed() {
        Frames frames = new Frames();
        Frame frame = frames.getCurrentFrame();
        assertFalse(frame.isComplete());
    }

    @Test
    public void isCompleteReturnsFalseIfThereAreRemainingShots() {
        Frames frames = new Frames();
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(4);

        assertFalse(frame.isComplete());
    }

    @Test
    public void isCompleteWithStrike() {
        Frames frames = new Frames();
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(STRIKE);

        assertTrue(frame.isComplete());
    }

    @Test
    public void isCompleteWithSpare() {
        Frames frames = new Frames();
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(5);
        frame.dropPins(5);

        assertTrue(frame.isComplete());
    }

    @Test
    public void getRequiredThrowsForStrikeWorks() {
        Frames frames = new Frames();
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(STRIKE);
        assertEquals(1, frame.getRequiredThrows());
    }

    @Test
    public void getRequiredThrowsForNonStrikeWorks() {
        Frames frames = new Frames();
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(3);
        frame.dropPins(2);
        assertEquals(2, frame.getRequiredThrows());
    }

    @Test
    public void isStrikeWorks() {
        Frames frames = new Frames();
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(STRIKE);

        assertTrue(frame.isStrike());
    }

    @Test
    public void isSpareWorks() {
        Frames frames = new Frames();
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(4);
        frame.dropPins(6);

        assertTrue(frame.isSpare());
    }

    @Test
    public void isSpareReturnsFalseIfItIsStrike() {
        Frames frames = new Frames();
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(STRIKE);

        assertTrue(frame.isStrike());
        assertFalse(frame.isSpare());
    }

    @Test
    public void isStrikeReturnsFalseIfItIsSpare() {
        Frames frames = new Frames();
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(4);
        frame.dropPins(6);

        assertTrue(frame.isSpare());
        assertFalse(frame.isStrike());
    }

    @Test(expected = IllegalStateException.class)
    public void dropPinsThrowExceptionIfFrameIsCompleteWithSpare() {
        Frames frames = new Frames();
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(5);
        frame.dropPins(5);
        frame.dropPins(3); // shouldn't be allowed
    }

    @Test(expected = IllegalStateException.class)
    public void dropPinsThrowExceptionIfFrameIsCompleteWithStrike() {
        Frames frames = new Frames();
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(STRIKE);
        frame.dropPins(4); // shouldn't be allowed
    }

    @Test(expected = IllegalArgumentException.class)
    public void getPinsDroppedInThrowThrowsExceptionIfThereIsNotSuchAThrow() {
        Frames frames = new Frames();
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(4);
        frame.dropPins(4);

        frame.getPinsDroppedInThrow(3);
    }

    @Test
    public void getPinsDroppedInFirstThrowWorks() {
        Frames frames = new Frames();
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(5);

        assertEquals(5, frame.getPinsDroppedFirstThrow());
    }

    @Test
    public void getPinsDroppedInSecondThrowWorks() {
        Frames frames = new Frames();
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(5);
        frame.dropPins(4);

        assertEquals(4, frame.getPinsDroppedSecondThrow());
    }

    @Test
    public void getScoreWorksForStrikeWithSingleFrame() {
        Frames frames = new Frames();
        Frame frame = frames.getCurrentFrame();
        frame.dropPins(STRIKE);

        assertEquals(STRIKE, frame.getScore());
    }

    @Test
    public void getScoreWorksForStrikeWithTwoFramesInProgress() {
        Frames frames = new Frames();
        Frame firstFrame = frames.getCurrentFrame();
        firstFrame.dropPins(STRIKE);

        frames.nextFrame(); // we advance to the next frame

        Frame secondFrame = frames.getCurrentFrame();
        secondFrame.dropPins(4);

        assertEquals(STRIKE + 4, firstFrame.getScore());
    }

    @Test
    public void getScoreWorksForStrikeWithTwoFramesCompletedAsSpare() {
        Frames frames = new Frames();
        Frame firstFrame = frames.getCurrentFrame();
        firstFrame.dropPins(STRIKE);

        frames.nextFrame();

        Frame secondFrame = frames.getCurrentFrame();
        secondFrame.dropPins(6);
        secondFrame.dropPins(4);

        assertEquals(20, firstFrame.getScore());
    }

    @Test
    public void getScoreWorksForStrikeWithThreeFramesCompletedAsStrike() {
        Frames frames = new Frames();
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
    }

    @Test
    public void getScoreWorksForStrikeWithThreeFramesAndTwoOfThemAsStrike() {
        Frames frames = new Frames();
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
    }

}
