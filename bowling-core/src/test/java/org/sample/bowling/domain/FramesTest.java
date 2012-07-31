package org.sample.bowling.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 *
 * @author jmnunezizu
 */
public class FramesTest {

    @Test
    public void getTotalScoreWorks() {
        Frames frames = new Frames();

        assertEquals(0, frames.getTotalScore());

        frames.getCurrentFrame().dropPins(10);

        assertEquals(10, frames.getTotalScore());
    }

    @Test
    public void getFramesReturnsAllFrames() {
        assertEquals(10, new Frames().getFrames().size());
    }

    @Test
    public void isFirstFrameWorks() {
        Frames frames = new Frames();
        Frame firstFrame = frames.getCurrentFrame();
        assertTrue(frames.isFirstFrame(firstFrame));
    }

}
