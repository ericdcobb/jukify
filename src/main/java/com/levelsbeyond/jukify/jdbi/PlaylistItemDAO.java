package com.levelsbeyond.jukify.jdbi;

import com.levelsbeyond.jukify.api.PlaylistItem;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.Transaction;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

/**
 *   
 *
 * @author Eric Cobb on 6/26/15.
 */
public abstract class PlaylistItemDAO {
	/**
	 * Used primarily in tests (deployed app will use migrations)- create the table in the embedded DB.
	 */
	@SqlUpdate("CREATE TABLE playlist_items (id int primary key auto_increment, spotify_track_id varchar(1000)," +
			"play_index int UNIQUE)")
	abstract void createTable();

	@GetGeneratedKeys
	@SqlUpdate("INSERT INTO playlist_items (spotify_track_id, play_index) VALUES (:spotify_track_id, :play_index)")
	abstract int insert(@Bind("spotify_track_id") String spotifyTrackId, @Bind("play_index") Integer playIndex);

	@SqlQuery("SELECT max(play_index) FROM playlist_items")
	@Mapper(MaxMapper.class)
	abstract int getMaxPlayIndex();

	@Transaction
	public int insertAtEnd(String spotifyTrackId) {
		Integer max = getMaxPlayIndex();
		return insert(spotifyTrackId, max + 1);
	}

	@Transaction
	public int insertAfter(Integer targetPlayIndex, String spotifyTrackId  ) {
		incrementAfter(targetPlayIndex);
		return insert(spotifyTrackId, targetPlayIndex + 1);
	}

	@SqlUpdate("UPDATE playlist_items SET play_index = play_index + 1 WHERE play_index > :target_play_index ")
	abstract void incrementAfter(@Bind("target_play_index")Integer targetPlayIndex);

	@SqlQuery("SELECT * FROM playlist_items WHERE id = :id")
	@Mapper(PlaylistItemMapper.class)
	public abstract PlaylistItem getItem(@Bind("id") Integer id);

	/**
	 * close with no args is used to close the connection
	 */
	abstract void close();
}
