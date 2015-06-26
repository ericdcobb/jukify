package com.levelsbeyond.jukify.resources;

import org.fest.assertions.Assertions;
import org.junit.Test;

/**
 *   
 *
 * @author Eric Cobb on 3/19/15.
 */
public class StatusResourceTest {

	@Test
	public void testStatus() {
		StatusResource statusResource = new StatusResource();
		Assertions.assertThat(statusResource.getStatus()).isEqualTo("OK");
	}
}
