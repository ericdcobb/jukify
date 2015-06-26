package com.levelsbeyond.jukify.services;

import java.util.List;

import com.google.common.base.Optional;
import com.levelsbeyond.jukify.api.PlaylistItem;

/**
 *   
 *
 * @author Eric Cobb on 6/26/15.
 */
public interface PlaylistItemService {

	List<PlaylistItem> addPlaylistItems(List<PlaylistItem> spotifyTracks);

	List<PlaylistItem> getItemsByRange(Integer startPlayIndexInclusive, Integer endPlayIndexInclusive);

	Optional<PlaylistItem> getItemById(Integer id);
}
