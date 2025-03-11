package com.quentaurus.defaults;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@Test
	void testHelloMessage() {
		assertEquals("Hello World!", App.token());
	}
}