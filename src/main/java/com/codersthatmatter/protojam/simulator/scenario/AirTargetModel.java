package com.codersthatmatter.protojam.simulator.scenario;

public class AirTargetModel {
	final int trackNumber;
	final Identity identity;
	final int startTime;
	final int speed;
	final ENUPosition startPos;
	final ENUPosition endPos;
	final int endTime;

	public AirTargetModel(int trackNumber, Identity identity, int startTime, int speed, ENUPosition startPos,
			ENUPosition endPos) {
		this(trackNumber, identity, startTime, speed, startPos, endPos,
				calculateEndTime(startTime, speed, startPos, endPos));
	}

	public AirTargetModel(int trackNumber, Identity identity, int startTime, int speed, ENUPosition startPos,
			ENUPosition endPos, int endTime) {
		this.trackNumber = trackNumber;
		this.identity = identity;
		this.startTime = startTime;
		this.speed = speed;
		this.startPos = startPos;
		this.endPos = endPos;
		this.endTime = endTime;
	}

	/**
	 * @param trackNumber a unique number
	 * @param identity
	 * @param startTime   [ms]
	 * @param speed       [m/s]
	 * @param startPos
	 * @param endPos
	 * @return
	 */
	public static AirTargetModel of(int trackNumber, Identity identity, int startTime, int speed, ENUPosition startPos,
			ENUPosition endPos) {
		return new AirTargetModel(trackNumber, identity, startTime, speed, startPos, endPos);
	}

	/**
	 * @return start time in ms
	 */
	public int startTime() {
		return startTime;
	}

	/**
	 * @return speed in m/s
	 */
	public int speed() {
		return speed;
	}

	public ENUPosition startPos() {
		return startPos;
	}

	public ENUPosition endPos() {
		return endPos;
	}

	/**
	 * @return end time in ms
	 */
	public int endTime() {
		return endTime;
	}

	/**
	 * @param time [s]
	 * @return the position at the specified time
	 */
	public ENUPosition pos(int time) {
		int timeInMillis = time * 1000;
		double timePart = (double) timeInMillis / (endTime - startTime);
		int posX = (int) (timePart * (endPos.east() - startPos.east())) + startPos.east();
		int posY = (int) (timePart * (endPos.north() - startPos.north())) + startPos.north();
		int posZ = (int) (timePart * (endPos.up() - startPos.up())) + startPos.up();

		return new ENUPosition(posX, posY, posZ);
	}

	public AirTargetInstance getInstance(int time) {
		ENUPosition pos = this.pos(time);
		int timeInMillis = time * 1000;
		int updatedTime = timeInMillis + (int) (Math.atan2(pos.east(), pos.north()) / (2 * Math.PI) * 1000);

		return new AirTargetInstance(this.trackNumber, this.identity, pos, this.speed, updatedTime);
	}

	private static int calculateEndTime(int startTime, int speed, ENUPosition startPos, ENUPosition endPos) {
		int distance = startPos.distanceTo(endPos);
		return startTime + (int) Math.ceil((double) distance / speed) * 1000;
	}

	@Override
	public String toString() {
		return "AirTargetModel [trackNumber=" + trackNumber + ", identity=" + identity + ", startTime=" + startTime
				+ ", speed=" + speed + ", startPos=" + startPos + ", endPos=" + endPos + ", endTime=" + endTime + "]";
	}

}
