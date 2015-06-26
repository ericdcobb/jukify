package com.levelsbeyond.jukify.api;

public class PlaylistItemBuilder {
	private Integer id;
	private String spotifyTrackId;
	private Integer playIndex;

	public PlaylistItemBuilder setId(Integer id) {
		this.id = id;
		return this;
	}

	public PlaylistItemBuilder setSpotifyTrackId(String spotifyTrackId) {
		this.spotifyTrackId = spotifyTrackId;
		return this;
	}

	public PlaylistItemBuilder setPlayIndex(Integer playIndex) {
		this.playIndex = playIndex;
		return this;
	}

	public PlaylistItem createPlaylistItem() {
		return new PlaylistItem(id, spotifyTrackId, playIndex);
	}
}