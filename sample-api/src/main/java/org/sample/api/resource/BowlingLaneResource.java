package org.sample.api.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.sample.bowling.domain.BowlingLane;
import org.sample.bowling.domain.Player;
import org.sample.bowling.domain.repository.BowlingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * How to play a Bowling Game through the REST API.
 *
 * <ul>
 * <li>POST /bowlingLane > creates a new game
 * <li>POST /bowlingLane/players with body {"name":"player name"} > adds a
 * player named "player name" to the game
 * <li>POST /bowlingLane/{bowlingLaneId}/start > starts the game
 * <li>POST /bowlingLane/{bowlingLaneId}/droppins/{numberOfPins} > drops
 * {numberOfPins} for the current player
 *
 * @author jmnunezizu
 */
@Path("/bowlinglane")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class BowlingLaneResource {

    @Autowired
    private BowlingRepository bowlingRepository;

    @POST
    public BowlingLane createBowlingLane() {
        BowlingLane bowlingLane = new BowlingLane();
        bowlingRepository.save(bowlingLane);

        return bowlingLane;
    }

    /**
     *
     * @param id
     * @return
     */
    @GET
    @Path("/{bowlingLaneId}")
    public BowlingLane getBowlingLane(@PathParam("bowlingLaneId") final String id) {
        return bowlingRepository.findBowlingLaneById(id);
    }

    /**
     *
     * @param bowlinglaneId
     * @param player
     * @return
     */
    @POST
    @Path("/{bowlingLaneId}/players")
    public Response addPlayerToBowlingLane(
            @PathParam("bowlingLaneId") final String bowlinglaneId, final Player player) {
        BowlingLane bowlingLane = bowlingRepository.findBowlingLaneById(bowlinglaneId);
        bowlingLane.addPlayer(player);
        bowlingRepository.save(bowlingLane);

        return Response.ok(player).build();
    }

    /**
     *
     * @param id
     * @return
     */
    @POST
    @Path("/{bowlingLaneId}/start")
    public BowlingLane startBowlingLane(@PathParam("bowlingLaneId") final String id) {
        BowlingLane bowlingLane = bowlingRepository.findBowlingLaneById(id);
        bowlingLane.start();
        bowlingRepository.save(bowlingLane);

        return bowlingLane;
    }

    @POST
    @Path("/{bowlingLaneId}/droppins/{pinsDropped}")
    public BowlingLane throwBall(
            @PathParam("bowlingLaneId") final String id,
            @PathParam("pinsDropped") final int pinsDropped) {
        BowlingLane bowlingLane = bowlingRepository.findBowlingLaneById(id);
        bowlingLane.dropPins(pinsDropped);
        bowlingRepository.save(bowlingLane);

        return bowlingLane;
    }

}
