package com.levelsbeyond.jukify.resources;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import javax.ws.rs.core.Response;

import com.google.common.base.Optional;
import com.levelsbeyond.jukify.api.PlaylistItem;
import com.levelsbeyond.jukify.services.PlaylistItemService;

import org.junit.Before;
import org.junit.Test;

/**
 *   
 *
 * @author Eric Cobb on 6/26/15.
 */
public class PlaylistItemResourceTest {

	PlaylistItemResource resource;
	PlaylistItemService service;

	@Before
	public void setup() {
		service = mock(PlaylistItemService.class);
		resource = new PlaylistItemResource(service);
	}

	@Test
	public void testCreateOne() {
		PlaylistItem itemToCreate = mock(PlaylistItem.class);
		PlaylistItem createdItem = mock(PlaylistItem.class);
		when(service.addPlaylistItems(Arrays.asList(itemToCreate))).thenReturn(Arrays.asList(createdItem));

		Response response = resource.createItem(itemToCreate);
		assertThat(response.getStatus()).isEqualTo(200);
		assertThat(response.getEntity()).isEqualTo(createdItem);

	}

	@Test
	@SuppressWarnings("unchecked")
	public void testCreateMany(){
		List<PlaylistItem> itemList = mock(List.class);
		List<PlaylistItem> createdItemList = mock(List.class);
		when(service.addPlaylistItems(itemList)).thenReturn(createdItemList);

		Response response = resource.createMany(itemList);
		assertThat(response.getStatus()).isEqualTo(200);
		assertThat(response.getEntity()).isEqualTo(createdItemList);
	}

	@Test
	public void testGetById(){
		PlaylistItem item = mock(PlaylistItem.class);

		when(service.getItemById(1)).thenReturn(Optional.fromNullable(item));
		when(service.getItemById(2)).thenReturn(Optional.absent());

		Response response = resource.getItem(1);
		assertThat(response.getStatus()).isEqualTo(200);
		assertThat(response.getEntity()).isEqualTo(item);

		Response missing = resource.getItem(2);
		assertThat(missing.getStatus()).isEqualTo(404);

	}

	@Test
	@SuppressWarnings("unchecked")
	public void testGetRange(){
		List<PlaylistItem> smallList = mock(List.class);
		List<PlaylistItem> defaultList = mock(List.class);

		when(service.getItemsByRange(2,4)).thenReturn(smallList);
		when(service.getItemsByRange(1, 10)).thenReturn(defaultList);

		Response smallResponse = resource.getItems(2, 4);
		assertThat(smallResponse.getStatus()).isEqualTo(200);
		assertThat(smallResponse.getEntity()).isEqualTo(smallList);

		Response bigResponse = resource.getItems(null, null);
		assertThat(bigResponse.getStatus()).isEqualTo(200);
		assertThat(bigResponse.getEntity()).isEqualTo(defaultList);

	}

	@Test
	public void testGetNext(){
		PlaylistItem item = mock(PlaylistItem.class);

		when(service.getNextItem(1)).thenReturn(Optional.fromNullable(item));
		when(service.getNextItem(2)).thenReturn(Optional.absent());

		Response response = resource.nextItem(1);
		assertThat(response.getStatus()).isEqualTo(200);
		assertThat(response.getEntity()).isEqualTo(item);

		Response missing = resource.nextItem(2);
		assertThat(missing.getStatus()).isEqualTo(404);
	}
}
