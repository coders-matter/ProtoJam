package com.codersthatmatter.protojam.simulator.scenario;

import java.util.Random;

public enum Identity {
	Friend("F"), Hostile("H"), Unknown("U"), Neutral("N");

	private final String id;

	Identity(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id;
	}

}
