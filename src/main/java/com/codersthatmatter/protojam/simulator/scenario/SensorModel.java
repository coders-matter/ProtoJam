package com.codersthatmatter.protojam.simulator.scenario;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SensorModel {

	private static final int DEFAULT_SEED = 12345;

	private final int range;
	private final int minAltitude;
	private final int maxAltitude;
	private final Random rg;

	public SensorModel(int range, int minAltitude, int maxAltitude) {
		this(range, minAltitude, maxAltitude, DEFAULT_SEED);
	}

	public SensorModel(int range, int minAltitude, int maxAltitude, int seed) {
		this.range = range;
		this.minAltitude = minAltitude;
		this.maxAltitude = maxAltitude;
		this.rg = new Random(seed);
	}

	public ENUPosition calulateRandomPerimeterPosition() {
		int xTmp = rg.nextInt(-range, range);
		int yTmp = rg.nextInt(-range, range);
		int z = rg.nextInt(minAltitude, maxAltitude);

		double k = Math.sqrt((double) (range * range - z * z) / (xTmp * xTmp + yTmp * yTmp));
		int x = (int) (k * xTmp);
		int y = (int) (k * yTmp);

		return new ENUPosition(x, y, z);
	}
}
