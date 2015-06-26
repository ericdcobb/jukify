package com.levelsbeyond.jukify.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *   
 *
 * @author Eric Cobb on 3/18/15.
 */
@Path("/status")
public class StatusResource {

	@GET
	public String getStatus() {
		return "OK";
	}
}
