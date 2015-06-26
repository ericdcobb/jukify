package com.levelsbeyond.jukify.jdbi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 *   
 *
 * @author Eric Cobb on 6/26/15.
 */
public class MaxMapper implements ResultSetMapper<Integer> {
	@Override
	public Integer map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return resultSet.getInt(1);
	}
}
