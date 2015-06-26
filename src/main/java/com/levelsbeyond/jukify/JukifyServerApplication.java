package com.levelsbeyond.jukify;

import com.levelsbeyond.jukify.healthchecks.StatusResourceHealthCheck;
import com.levelsbeyond.jukify.jdbi.PlaylistItemDAO;
import com.levelsbeyond.jukify.resources.PlaylistItemResource;
import com.levelsbeyond.jukify.resources.StatusResource;
import com.levelsbeyond.jukify.services.PlaylistItemService;
import com.levelsbeyond.jukify.services.PlaylistItemServiceImpl;

import org.skife.jdbi.v2.DBI;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Hello world!
 */
public class JukifyServerApplication extends Application<JukifyServerConfiguration>
{
	public static void main(String[] args) throws Exception {
		new JukifyServerApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<JukifyServerConfiguration> bootstrap) {
		bootstrap.addBundle(new MigrationsBundle<JukifyServerConfiguration>() {
			@Override
			public DataSourceFactory getDataSourceFactory(JukifyServerConfiguration configuration) {
				return configuration.getDatabase();
			}
		});
	}

	@Override
	public void run(JukifyServerConfiguration configuration, Environment environment) throws Exception {

		// jdbi
		final DBIFactory factory = new DBIFactory();
		final DBI jdbi = factory.build(environment, configuration.getDatabase(), "postgresql");

		PlaylistItemDAO playlistItemDAO = jdbi.onDemand(PlaylistItemDAO.class);
		PlaylistItemService playlistItemService = new PlaylistItemServiceImpl(playlistItemDAO);

		final StatusResource statusResource = new StatusResource();
		environment.jersey().register(statusResource);

		environment.healthChecks().register("status", new StatusResourceHealthCheck());

		final PlaylistItemResource playlistItemResource = new PlaylistItemResource(playlistItemService);
		environment.jersey().register(playlistItemResource);
	}
}
