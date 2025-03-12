package com.codersthatmatter.protojam.simulator.scenario;

public class AirTargetInstance {

	private final int trackNumber;
	private final Identity identity;
	private final ENUPosition pos;
	private final int speed;
	private final int updatedTime;

	public AirTargetInstance(int trackNumber, Identity identity, ENUPosition pos, int speed, int updatedTime) {
		this.trackNumber = trackNumber;
		this.identity = identity;
		this.pos = pos;
		this.speed = speed;
		this.updatedTime = updatedTime;
	}

	public int trackNumber() {
		return trackNumber;
	}

	public Identity identity() {
		return identity;
	}

	public ENUPosition pos() {
		return pos;
	}

	public int speed() {
		return speed;
	}

	public int updatedTime() {
		return updatedTime;
	}

	@Override
	public String toString() {
		return "[" + "Message=AirTarget" + ";TIME[ms]=" + updatedTime + ";TN=" + trackNumber + ";ID=" + identity
				+ ";POS[ENU]=" + pos + ";SPEED[m/s]=" + speed + "]";
	}

}
