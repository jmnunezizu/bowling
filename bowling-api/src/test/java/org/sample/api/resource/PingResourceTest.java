package org.sample.api.resource;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 *
 * @author jmnunezizu
 */
public class PingResourceTest {

    private PingResource underTest = new PingResource();

    @Test
    public void pingReturnsRightMessage()
    {
        assertEquals("pong", underTest.ping());
    }

}
