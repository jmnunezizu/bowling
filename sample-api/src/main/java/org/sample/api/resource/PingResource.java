package org.sample.api.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

/**
 *
 * @author jmnunezizu
 */
@Component
@Path("/ping")
@Produces(MediaType.TEXT_HTML)
public class PingResource {

    private static final String PING_RESPONSE = "pong";

    @GET
    public String ping() {
        return PING_RESPONSE;
    }

}
