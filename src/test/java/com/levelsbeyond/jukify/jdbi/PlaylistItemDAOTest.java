package com.levelsbeyond.jukify.jdbi;

import static org.fest.assertions.Assertions.*;

import com.levelsbeyond.jukify.api.PlaylistItem;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;

/**
 *   
 *
 * @author Eric Cobb on 6/26/15.
 */
public class PlaylistItemDAOTest {
	static PlaylistItemDAO dao;

	@BeforeClass
	public static void configureDao() {
		DBI dbi = new DBI("jdbc:h2:mem:test");
		dao = dbi.open(PlaylistItemDAO.class);
		dao.createTable();
	}

	@Test
	public void testInsert() {
		int id = dao.insertAtEnd("test");
		assertThat(id).isGreaterThan(0);
	}

	@Test
	public void testGet() {
		int id = dao.insertAtEnd("test");
		assertThat(id).isGreaterThan(0);

		int id2 = dao.insertAtEnd("test");
		assertThat(id2).isGreaterThan(0);

		PlaylistItem item1 = dao.getItem(id);

		PlaylistItem item2 = dao.getItem(id2);

		assertThat(item1.getPlayIndex()).isLessThan(item2.getPlayIndex());

		assertThat(item1.getSpotifyTrackId()).isEqualTo("test");
	}

	@Test
	public void testInsertNext() {
		int id = dao.insertAtEnd("test");
		assertThat(id).isGreaterThan(0);

		int id2 = dao.insertAtEnd("test");
		assertThat(id2).isGreaterThan(0);

		PlaylistItem item1 = dao.getItem(id);
		PlaylistItem item2 = dao.getItem(id2);

		Integer desiredPlayIndexForNew = item2.getPlayIndex();

		int nextId = dao.insertAfter(item1.getPlayIndex(), "test2");

		assertThat(nextId).isGreaterThan(0);

		PlaylistItem nextItem = dao.getItem(nextId);
		//reload item2
		item2 = dao.getItem(id2);
		assertThat(item2.getPlayIndex()).isGreaterThan(nextItem.getPlayIndex());
		assertThat(nextItem.getPlayIndex()).isEqualTo(desiredPlayIndexForNew);

	}

	@AfterClass
	public static void cleanupDao() {
		dao.close();
	}
}
