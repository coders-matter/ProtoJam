package com.codersthatmatter.protojam.simulator.scenario;

public record ENUPosition(int east, int north, int up) {

	public static ENUPosition origin() {
		return new ENUPosition(0, 0, 0);
	}

	public int distanceTo(ENUPosition other) {
		int dEast = other.east - east;
		int dNorth = other.north - north;
		int dUp = other.up - up;
		return (int) Math.sqrt(dEast * dEast + dNorth * dNorth + dUp * dUp);
	}

	@Override
	public final String toString() {
		return "(" + east + "," + north + "," + up + ")";
	}
}
