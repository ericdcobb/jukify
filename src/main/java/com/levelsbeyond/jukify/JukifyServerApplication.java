package com.levelsbeyond.jukify;

import com.levelsbeyond.jukify.healthchecks.StatusResourceHealthCheck;
import com.levelsbeyond.jukify.resources.StatusResource;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
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
		final StatusResource statusResource = new StatusResource();
		environment.jersey().register(statusResource);

		environment.healthChecks().register("status", new StatusResourceHealthCheck());
	}
}
