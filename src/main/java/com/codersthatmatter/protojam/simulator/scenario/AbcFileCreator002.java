
package com.codersthatmatter.protojam.simulator.scenario;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.PriorityQueue;
import java.util.Random;

import com.codersthatmatter.util.EnumUtil;

public class AbcFileCreator002 {
	// Sensor
	private static final int DEFAULT_RANGE = 20000;
	private static final int DEFAULT_MIN_ALTITUDE = 200;
	private static final int DEFAULT_MAX_ALTITUDE = 10000;
	private static final int DEFAULT_MIN_SPEED = 20;
	private static final int DEFAULT_MAX_SPEED = 300;

	// Scenario
	private static final int DEFAULT_DAYS = 0;
	private static final int DEFAULT_HOURS = 1;
	private static final int DEFAULT_MINUTES = 0;
	private static final int DEFAULT_SECONDS = 0;

	public static int START_TIME_SECONDS = 0;
	public static int SCENARIO_DURATION_SECONDS = (((DEFAULT_DAYS * 24 + DEFAULT_HOURS) * 60 + DEFAULT_MINUTES) * 60
			+ DEFAULT_SECONDS);
	public static int END_TIME_SECONDS = START_TIME_SECONDS + SCENARIO_DURATION_SECONDS;

	public static int NUMBER_OF_TARGETS = 100;

	public static Random RG = new Random(12345);

	public static void main(String[] args) {

		String filePath = "data0001.abc";

		PriorityQueue<AirTargetInstance> targetQueue = new PriorityQueue<>(
				(a, b) -> Long.compare(a.updatedTime(), b.updatedTime()));

		SensorModel sensorModel = new SensorModel(DEFAULT_RANGE, DEFAULT_MIN_ALTITUDE, DEFAULT_MAX_ALTITUDE);

		PriorityQueue<AirTargetModel> targetModels = getTargetModels(NUMBER_OF_TARGETS, sensorModel);

		PriorityQueue<AirTargetModel> activeTargetModels = new PriorityQueue<>(
				(a, b) -> Long.compare(a.endTime, b.endTime));

		System.out.println("Simulating data...");
		for (int timeInSeconds = START_TIME_SECONDS; timeInSeconds < END_TIME_SECONDS; timeInSeconds++) {
//			System.out.println("Time: " + timeInSeconds);
			int timeInMilliSeconds = timeInSeconds * 1000;

			// move all active elements to the active queue
			while (!targetModels.isEmpty() && targetModels.peek().startTime < timeInMilliSeconds) {
				AirTargetModel element = targetModels.poll();
//				System.out.println("Adding: " + element);
				activeTargetModels.offer(element);
			}

			// remove all inactive elements from the active queue
			while (!activeTargetModels.isEmpty() && activeTargetModels.peek().endTime < timeInMilliSeconds) {
				AirTargetModel element = activeTargetModels.poll();
//				System.out.println("Removing: " + element);
			}

//			System.out.println("# of active models: " + activeTargetModels.size());

			for (AirTargetModel model : activeTargetModels) {
				targetQueue.add(model.getInstance(timeInSeconds));
			}
		}

		System.out.println("Writing data...");

		if (false) {
			try (FileChannel channel = FileChannel.open(Paths.get(filePath), StandardOpenOption.CREATE,
					StandardOpenOption.WRITE)) {

				String lineSeparator = System.lineSeparator();

				// Prepare a reusable buffer (e.g., 16 MB)
				ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024 * 1024);

				while (!targetQueue.isEmpty()) {
					AirTargetInstance item = targetQueue.poll();

					String line = item.toString() + lineSeparator;
					byte[] data = line.getBytes(StandardCharsets.UTF_8);

					if (buffer.remaining() < data.length) {
						buffer.flip();
						channel.write(buffer);
						buffer.clear();
					}

					buffer.put(line.getBytes(StandardCharsets.UTF_8));
				}

				// Write remaining data
				buffer.flip();
				while (buffer.hasRemaining()) {
					channel.write(buffer);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (false) {
			int bufferSize = 1 * 1024 * 1024;
			try (BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8), bufferSize)) {

				while (!targetQueue.isEmpty()) {
					AirTargetInstance item = targetQueue.poll();
					writer.write(item.toString() + '\n');
				}

				// Only flush once at the end
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (true) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
				while (!targetQueue.isEmpty()) {
					AirTargetInstance item = targetQueue.poll();
					writer.write(item.toString() + '\n');
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private static PriorityQueue<AirTargetModel> getTargetModels(int numberOfTargets, SensorModel sensorModel) {
		PriorityQueue<AirTargetModel> targetModels = new PriorityQueue<>(
				(a, b) -> Long.compare(a.startTime, b.startTime));

		for (int i = 0; i < numberOfTargets; i++) {
			int speed = RG.nextInt(DEFAULT_MIN_SPEED, DEFAULT_MAX_SPEED);
			int startTime = RG.nextInt(START_TIME_SECONDS, END_TIME_SECONDS) * 1000;
			ENUPosition startPosition = sensorModel.calulateRandomPerimeterPosition();
			ENUPosition endPosition = sensorModel.calulateRandomPerimeterPosition();
			targetModels.add(AirTargetModel.of(i, EnumUtil.getRandomInstance(Identity.class, RG), startTime, speed,
					startPosition, endPosition));
		}

		return targetModels;
	}

}
