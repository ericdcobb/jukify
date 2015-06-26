package com.levelsbeyond.jukify;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

/**
 *   
 *
 * @author Eric Cobb on 6/26/15.
 */
public class JukifyServerConfiguration extends Configuration {

	@Valid
	@NotNull
	@JsonProperty("database")
	private DataSourceFactory database = new DataSourceFactory();

	@JsonProperty("database")
	public DataSourceFactory getDatabase() {
		return database;
	}
}
