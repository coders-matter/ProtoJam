package com.codersthatmatter.protojam;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.codersthatmatter.protojam.App;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@Test
	void testHelloMessage() {
		assertEquals("Hello World!", App.token());
	}
}