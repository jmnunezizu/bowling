package org.sample.bowling.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.sample.bowling.exception.NoSuchFrameException;
import org.sample.bowling.helper.PlayedFramePredicate;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * This is a helper wrapper class that provides abstraction and utility methods
 * for dealing with {@link Frame}.
 *
 * <p>This class also implements {@link Iterable<T>} which means that can be
 * used in loops to iterate its items.</p>
 *
 * @author jmnunezizu
 */
public class Frames implements Iterable<Frame> {

    private static final int FIRST_FRAME = 0;
    private static final int LAST_FRAME = 10;

    private static final Predicate<Frame> playedFramePredicate = new PlayedFramePredicate();

    private Frame currentFrame;
    private List<Frame> frames = new ArrayList<Frame>();

    /**
     *
     */
    public Frames() {
        for (int i = 1; i < LAST_FRAME; i++) {
            frames.add(new Frame(this));
        }
        frames.add(new LastFrame(this));

        currentFrame = frames.get(FIRST_FRAME);
    }

    public List<Frame> getFrames() {
        return Collections.unmodifiableList(frames);
    }

    /**
     * @return Returns the current frame.
     */
    @JsonIgnore
    public Frame getCurrentFrame() {
        return currentFrame;
    }

    /**
     * Returns the frame that comes after the frame {@code frame}.
     *
     * @param frame The frame used to get the next frame.
     * @return The frame that comes after {@code frame}.
     * @throws NoSuchFrameException If there are no more frames after frame
     *      {@code frame}.
     */
    @JsonIgnore
    public Frame getNextFrame(final Frame frame) {
        if (frames.indexOf(frame) == frames.size() - 1) {
            throw new NoSuchFrameException("There isn't a frame after frame " + frame.toString());
        }

        return frames.get(frames.indexOf(frame) + 1);
    }

    /**
     * Returns the frame that comes before the frame {@code frame}.
     *
     * @param frame The frame used to get the previous frame.
     * @return The frame that comes before {@code frame}.
     * @throws NoSuchFrameException If there are no more frames before the frame
     *      {@code frame}.
     */
    @JsonIgnore
    public Frame getPreviousFrame(final Frame frame) {
        if (frames.indexOf(frame) == 0) {
            throw new NoSuchFrameException("There is no previous frame before frame " + frame.toString());
        }

        return frames.get(frames.indexOf(frame) - 1);
    }

    /**
     * Evaluates whether or not the frame {@code frame} is the first frame.
     *
     * @param frame The frame that must be evaluated.
     * @return true if it is the first frame, false otherwise.
     */
    @JsonIgnore
    public boolean isFirstFrame(final Frame frame) {
        return frames.indexOf(frame) == 0;
    }

    /**
     * Evaluates whether or not the frame {@code frame} is the last frame.
     *
     * @param frame The frame that must be evaluated.
     * @return true if it is the last frame, false otherwise.
     */
    @JsonIgnore
    public boolean isLastFrame(final Frame frame) {
        return frames.indexOf(frame) == frames.size() - 1;
    }

    /**
     * Advances the current frame to the next frame.
     */
    public void nextFrame() {
        currentFrame = frames.get(frames.indexOf(currentFrame) + 1);
    }

    /**
     * @return Returns all frames that have been or are being played.
     */
    @JsonIgnore
    public Iterable<Frame> getPlayedFrames() {
        return Iterables.filter(frames, playedFramePredicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Frame> iterator() {
        return frames.iterator();
    }

}
