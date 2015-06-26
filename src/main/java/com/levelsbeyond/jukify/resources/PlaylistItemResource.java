package com.levelsbeyond.jukify.resources;

import static java.util.Arrays.*;
import static javax.ws.rs.core.Response.*;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.levelsbeyond.jukify.api.PlaylistItem;
import com.levelsbeyond.jukify.services.PlaylistItemService;

/**
 *   
 *
 * @author Eric Cobb on 6/26/15.
 */
@Path("/playlist-items")
@Produces(MediaType.APPLICATION_JSON)
public class PlaylistItemResource {

	private final PlaylistItemService service;

	public PlaylistItemResource(PlaylistItemService service) {
		this.service = service;
	}

	@POST
	public Response createItem(PlaylistItem item) {
		List<PlaylistItem> items = service.addPlaylistItems(asList(item));
		return ok(items.get(0)).build();
	}

	@POST
	@Path("/many")
	public Response createMany(List<PlaylistItem> items) {
		return ok(service.addPlaylistItems(items)).build();
	}

	@GET
	@Path("/{id}")
	public Response getItem(@PathParam("id") Integer id) {
		com.google.common.base.Optional<PlaylistItem> itemOptional = service.getItemById(id);
		if (itemOptional.isPresent()) {
			return ok(itemOptional.get()).build();
		}
		else {
			return status(404).build();
		}
	}

	@GET
	public Response getItems(@QueryParam("playIndexStart") Integer playIndexStart, @QueryParam("playIndexEnd") Integer playIndexEnd) {
		return ok(service.getItemsByRange(playIndexStart != null ? playIndexStart : 1, playIndexEnd != null ?
				playIndexEnd : 10)).build();
	}
}
