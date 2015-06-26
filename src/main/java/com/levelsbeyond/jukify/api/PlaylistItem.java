package com.levelsbeyond.jukify.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *   
 *
 * @author Eric Cobb on 6/26/15.
 */
public class PlaylistItem {

	private final Integer id;
	private final String spotifyTrackId;
	private final Integer playIndex;

	public PlaylistItem(@JsonProperty("id") Integer id,
			@JsonProperty("spotifyTrackId") String spotifyTrackId,
			@JsonProperty("playIndex") Integer playIndex) {
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		PlaylistItem that = (PlaylistItem) o;

		if (id != null ? !id.equals(that.id) : that.id != null)
			return false;
		if (spotifyTrackId != null ? !spotifyTrackId.equals(that.spotifyTrackId) : that.spotifyTrackId != null)
			return false;
		return !(playIndex != null ? !playIndex.equals(that.playIndex) : that.playIndex != null);

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (spotifyTrackId != null ? spotifyTrackId.hashCode() : 0);
		result = 31 * result + (playIndex != null ? playIndex.hashCode() : 0);
		return result;
	}
}
