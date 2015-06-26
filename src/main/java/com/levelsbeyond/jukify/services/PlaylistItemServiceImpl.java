package com.levelsbeyond.jukify.services;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Optional;
import com.levelsbeyond.jukify.api.PlaylistItem;
import com.levelsbeyond.jukify.jdbi.PlaylistItemDAO;

/**
 *   
 *
 * @author Eric Cobb on 6/26/15.
 */
public class PlaylistItemServiceImpl implements PlaylistItemService {
	private final PlaylistItemDAO dao;

	public PlaylistItemServiceImpl(PlaylistItemDAO dao) {
		this.dao = dao;
	}

	@Override
	public List<PlaylistItem> addPlaylistItems(List<PlaylistItem> spotifyTracks) {
		//using forEach instead of a stream so we can guarantee we process in the correct order.
		List<Integer> results = new ArrayList<>();
		spotifyTracks.forEach(item -> {
			if (item.getPlayIndex() != null) {
				results.add(dao.insertAfter(item.getPlayIndex() - 1, item.getSpotifyTrackId()));
			}
				else {
					results.add(dao.insertAtEnd(item.getSpotifyTrackId()));
				}
			});
		return results.stream().map(dao::getItem).collect(toList());
	}

	@Override
	public List<PlaylistItem> getItemsByRange(Integer startPlayIndexInclusive, Integer endPlayIndexInclusive) {
		return dao.getItemsByIndexRange(startPlayIndexInclusive, endPlayIndexInclusive);
	}

	@Override
	public Optional<PlaylistItem> getItemById(Integer id) {
		return Optional.fromNullable(dao.getItem(id));
	}

}
