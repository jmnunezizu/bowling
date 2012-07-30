package org.sample.bowling.helper;

import org.sample.bowling.domain.Frame;

import com.google.common.base.Predicate;

/**
 * Predicate used to filter played frames from a list of frames.
 *
 * <p>This predicate is used by Google Guava's in
 * {@link com.google.common.collect.Iterables#filter}.</p>
 *
 * @author jmnunezizu
 */
public class PlayedFramePredicate implements Predicate<Frame> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean apply(final Frame frame) {
        return frame.hasBeenPlayed();
    }

}
