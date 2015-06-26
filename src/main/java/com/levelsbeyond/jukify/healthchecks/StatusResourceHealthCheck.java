package com.levelsbeyond.jukify.healthchecks;

import com.codahale.metrics.health.HealthCheck;

/**
 *   
 *
 * @author Eric Cobb on 6/24/15.
 */
public class StatusResourceHealthCheck extends HealthCheck {
	@Override
	protected Result check() throws Exception {
		return Result.healthy();
	}
}
