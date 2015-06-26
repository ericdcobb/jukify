package com.levelsbeyond.jukify.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.levelsbeyond.jukify.api.PlaylistItem;
import com.levelsbeyond.jukify.api.PlaylistItemBuilder;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 *   
 *
 * @author Eric Cobb on 6/26/15.
 */
public class PlaylistItemMapper implements ResultSetMapper<PlaylistItem> {

	@Override
	public PlaylistItem map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new PlaylistItemBuilder()
				.setId(resultSet.getInt("id"))
				.setPlayIndex(resultSet.getInt("play_index"))
				.setSpotifyTrackId(resultSet.getString("spotify_track_id"))
				.createPlaylistItem();
	}
}
