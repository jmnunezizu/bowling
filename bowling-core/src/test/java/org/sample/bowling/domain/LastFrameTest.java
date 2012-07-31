package org.sample.bowling.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 *
 * @author jmnunezizu
 */
public class LastFrameTest {

    @Test
    public void getMaximumNumberOfDroppedPinsWorksForStrike() {
        Frames frames = new Frames();
        LastFrame frame = new LastFrame(frames);
        frame.dropPins(10);
        assertEquals(30, frame.getMaximumNumberOfDroppedPins());
    }

    @Test
    public void getMaximumNumberOfDroppedPinsWorksForSpare() {
        Frames frames = new Frames();
        LastFrame frame = new LastFrame(frames);
        frame.dropPins(5);
        frame.dropPins(5);
        assertEquals(30, frame.getMaximumNumberOfDroppedPins());
    }

    @Test
    public void getMaximumNumberOfDroppedPinsWorksForOpenFrame() {
        Frames frames = new Frames();
        LastFrame frame = new LastFrame(frames);
        frame.dropPins(5);
        frame.dropPins(2);
        assertEquals(10, frame.getMaximumNumberOfDroppedPins());
    }

    @Test
    public void getRequiredThrowsWorksForStrike() {
        Frames frames = new Frames();
        LastFrame frame = new LastFrame(frames);
        frame.dropPins(10);
        assertEquals(3, frame.getRequiredThrows());
    }

    @Test
    public void getRequiredThrowsWorksForSpare() {
        Frames frames = new Frames();
        LastFrame frame = new LastFrame(frames);
        frame.dropPins(7);
        frame.dropPins(3);
        assertEquals(3, frame.getRequiredThrows());
    }

    @Test
    public void getRequiredThrowsWorksForOpenFrame() {
        Frames frames = new Frames();
        LastFrame frame = new LastFrame(frames);
        frame.dropPins(7);
        frame.dropPins(2);
        assertEquals(2, frame.getRequiredThrows());
    }

    @Test
    public void getScoreWorksForStrike() {
        Frames frames = new Frames();
        LastFrame frame = new LastFrame(frames);
        frame.dropPins(10);
        frame.dropPins(10);
        frame.dropPins(10);

        assertEquals(30, frame.getScore());
    }

    @Test
    public void getScoreWorksForSpare() {
        Frames frames = new Frames();
        LastFrame frame = new LastFrame(frames);
        frame.dropPins(4);
        frame.dropPins(6);
        frame.dropPins(3);

        assertEquals(13, frame.getScore());
    }

    @Test
    public void getScoreWorksForOpenFrame() {
        Frames frames = new Frames();
        LastFrame frame = new LastFrame(frames);
        frame.dropPins(4);
        frame.dropPins(4);

        assertEquals(8, frame.getScore());
    }

    @Test
    public void getDisplayValueWorksForAllStrikes() {
        Frames frames = new Frames();
        LastFrame frame = new LastFrame(frames);
        frame.dropPins(10);
        frame.dropPins(10);
        frame.dropPins(10);

        assertEquals("X | X | X", frame.getDisplayValue());
    }

    @Test
    public void getDisplayValueWorksForTwoStrikes() {
        Frames frames = new Frames();
        LastFrame frame = new LastFrame(frames);
        frame.dropPins(10);
        frame.dropPins(10);
        frame.dropPins(5);

        assertEquals("X | X | 5", frame.getDisplayValue());
    }

    @Test
    public void getDisplayValueWorksForSingleStrike() {
        Frames frames = new Frames();
        LastFrame frame = new LastFrame(frames);
        frame.dropPins(10);
        frame.dropPins(5);
        frame.dropPins(5);

        assertEquals("X | 5 | 5", frame.getDisplayValue());
    }

    @Test
    public void getDisplayWorksForSpareAndStrike() {
        Frames frames = new Frames();
        LastFrame frame = new LastFrame(frames);
        frame.dropPins(5);
        frame.dropPins(5);
        frame.dropPins(10);

        assertEquals("5 | / | X", frame.getDisplayValue());
    }

    @Test
    public void getDisplayWorksForSpareAndOpenFrame() {
        Frames frames = new Frames();
        LastFrame frame = new LastFrame(frames);
        frame.dropPins(5);
        frame.dropPins(5);
        frame.dropPins(5);

        assertEquals("5 | / | 5", frame.getDisplayValue());
    }

}
