package org.sample.bowling.domain.repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.sample.bowling.domain.BowlingLane;
import org.springframework.stereotype.Component;

/**
 *
 * @author jmnunezizu
 */
@Component
public class BowlingRepository {

    private Map<String, BowlingLane> bowlingLanes = new ConcurrentHashMap<String, BowlingLane>();

    /**
     *
     * @param id
     * @return
     */
    public BowlingLane findBowlingLaneById(final String id) {
        return bowlingLanes.get(id);
    }

    /**
     *
     * @param bowlingLane
     * @return
     */
    public BowlingLane save(final BowlingLane bowlingLane) {
        String id = UUID.randomUUID().toString();
        bowlingLanes.put(id, bowlingLane);
        bowlingLane.setId(id);

        return bowlingLane;
    }

}
