package com.levelsbeyond.jukify.api;

/**
 *   
 *
 * @author Eric Cobb on 6/26/15.
 */
public class PlaylistItem {

	private final Integer id;
	private final String spotifyTrackId;
	private final Integer playIndex;

	public PlaylistItem(Integer id, String spotifyTrackId, Integer playIndex) {
		this.id = id;
		this.spotifyTrackId = spotifyTrackId;
		this.playIndex = playIndex;
	}

	public Integer getId() {
		return id;
	}

	public String getSpotifyTrackId() {
		return spotifyTrackId;
	}

	public Integer getPlayIndex() {
		return playIndex;
	}
}
