package com.levelsbeyond.jukify.services;

import static java.util.Arrays.*;
import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.levelsbeyond.jukify.api.PlaylistItem;
import com.levelsbeyond.jukify.api.PlaylistItemBuilder;
import com.levelsbeyond.jukify.jdbi.PlaylistItemDAO;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

/**
 *   
 *
 * @author Eric Cobb on 6/26/15.
 */
public class PlaylistItemServiceImplTest {

	PlaylistItemServiceImpl playlistItemService;
	PlaylistItemDAO dao;

	@Before
	public void setup() {
		dao = mock(PlaylistItemDAO.class);
		playlistItemService = new PlaylistItemServiceImpl(dao);
	}

	@Test
	public void testAddSetToEnd() {
		List<String> ids = asList("test", "one", "two");

		when(dao.insertAtEnd("test")).thenReturn(1);
		when(dao.insertAtEnd("one")).thenReturn(2);
		when(dao.insertAtEnd("two")).thenReturn(3);

		when(dao.getItem(1)).thenReturn(new PlaylistItemBuilder()
				.setId(1)
				.setPlayIndex(1)
				.setSpotifyTrackId("test")
				.createPlaylistItem());

		when(dao.getItem(2)).thenReturn(new PlaylistItemBuilder()
				.setId(2)
				.setPlayIndex(2)
				.setSpotifyTrackId("one")
				.createPlaylistItem());

		when(dao.getItem(3)).thenReturn(new PlaylistItemBuilder()
				.setId(3)
				.setPlayIndex(3)
				.setSpotifyTrackId("two")
				.createPlaylistItem());

		List<PlaylistItem> items = playlistItemService.addPlaylistItems(asList(new PlaylistItemBuilder()
				.setSpotifyTrackId("test")
				.createPlaylistItem(), new PlaylistItemBuilder().setSpotifyTrackId("one").createPlaylistItem(),
				new PlaylistItemBuilder().setSpotifyTrackId("two").createPlaylistItem()));

		assertThat(items).hasSize(3);

		InOrder inOrder = inOrder(dao);

		inOrder.verify(dao).insertAtEnd("test");
		inOrder.verify(dao).insertAtEnd("one");
		inOrder.verify(dao).insertAtEnd("two");

	}

	@Test
	public void testAddSet() {
		List<String> ids = asList("test", "one", "two");

		when(dao.insertAfter(0, "test")).thenReturn(1);
		when(dao.insertAtEnd("one")).thenReturn(2);
		when(dao.insertAfter(1, "two")).thenReturn(3);

		when(dao.getItem(1)).thenReturn(new PlaylistItemBuilder()
				.setId(1)
				.setPlayIndex(1)
				.setSpotifyTrackId("test")
				.createPlaylistItem());

		when(dao.getItem(2)).thenReturn(new PlaylistItemBuilder()
				.setId(2)
				.setPlayIndex(3)
				.setSpotifyTrackId("one")
				.createPlaylistItem());

		when(dao.getItem(3)).thenReturn(new PlaylistItemBuilder()
				.setId(3)
				.setPlayIndex(1)
				.setSpotifyTrackId("two")
				.createPlaylistItem());

		List<PlaylistItem> items = playlistItemService.addPlaylistItems(asList(
				new PlaylistItemBuilder()
						.setSpotifyTrackId("test")
						.setPlayIndex(1)
						.createPlaylistItem(),
				new PlaylistItemBuilder()
						.setSpotifyTrackId("one")
						.createPlaylistItem(),
				new PlaylistItemBuilder()
						.setSpotifyTrackId("two")
						.setPlayIndex(2)
						.createPlaylistItem()));

		assertThat(items).hasSize(3);

		InOrder inOrder = inOrder(dao);

		inOrder.verify(dao).insertAfter(0, "test");
		inOrder.verify(dao).insertAtEnd("one");
		inOrder.verify(dao).insertAfter(1, "two");

	}

	@Test
	public void testGetByRange() {
		List<PlaylistItem> expectedItems = asList(
				new PlaylistItemBuilder()
						.setSpotifyTrackId("test")
						.setId(1)
						.setPlayIndex(1)
						.createPlaylistItem(),
				new PlaylistItemBuilder()
						.setSpotifyTrackId("test")
						.setId(2)
						.setPlayIndex(2)
						.createPlaylistItem(),
				new PlaylistItemBuilder()
						.setSpotifyTrackId("test")
						.setId(3)
						.setPlayIndex(3)
						.createPlaylistItem());

		when(dao.getItemsByIndexRange(1, 3)).thenReturn(expectedItems);

		List<PlaylistItem> items = playlistItemService.getItemsByRange(1, 3);

		assertThat(items).isEqualTo(expectedItems);
	}

	@Test
	public void testGetSingleItem(){
		PlaylistItem item = new PlaylistItemBuilder()
				.setSpotifyTrackId("test")
				.setId(1)
				.setPlayIndex(1)
				.createPlaylistItem();

		when(dao.getItem(1)).thenReturn(item);
		com.google.common.base.Optional<PlaylistItem> actualItem = playlistItemService.getItemById(1);

		assertThat(actualItem.isPresent()).isTrue();
		assertThat(actualItem.get()).isEqualTo(item);
	}

	@Test
	public void testGetNext(){
		PlaylistItem item = new PlaylistItemBuilder()
				.setSpotifyTrackId("test")
				.setId(1)
				.setPlayIndex(3)
				.createPlaylistItem();

		when(dao.nextItem(2)).thenReturn(item);
		com.google.common.base.Optional<PlaylistItem> actualItem = playlistItemService.getNextItem(2);

		assertThat(actualItem.isPresent()).isTrue();
		assertThat(actualItem.get()).isEqualTo(item);
	}
}
