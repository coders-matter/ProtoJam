package com.codersthatmatter.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public final class EnumUtil {

	private EnumUtil() {
		// Prevent instantiation
	}

	/**
	 * Converts a string to an enum value safely. Returns `Optional.empty()` if the
	 * value is invalid.
	 */
	public static <E extends Enum<E>> Optional<E> fromString(Class<E> enumClass, String value) {
		try {
			return Optional.of(Enum.valueOf(enumClass, value));
		} catch (IllegalArgumentException | NullPointerException e) {
			return Optional.empty();
		}
	}

	/**
	 * Returns all values of an enum as a list.
	 */
	public static <E extends Enum<E>> List<E> getAllValues(Class<E> enumClass) {
		return List.of(enumClass.getEnumConstants());
	}

	/**
	 * Returns a random value of an enum.
	 */
	public static <E extends Enum<E>> E getRandomInstance(Class<E> enumClass, Random rg) {
		int ix = rg.nextInt(enumClass.getEnumConstants().length);
		return enumClass.getEnumConstants()[ix];
	}

	/**
	 * Checks if a string is a valid enum name.
	 */
	public static <E extends Enum<E>> boolean isValidEnum(Class<E> enumClass, String value) {
		return Arrays.stream(enumClass.getEnumConstants()).anyMatch(e -> e.name().equals(value));
	}

	/**
	 * Returns a map of enum names to their corresponding values.
	 */
	public static <E extends Enum<E>> Map<String, E> getEnumMap(Class<E> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).collect(Collectors.toMap(Enum::name, e -> e));
	}

	/**
	 * Returns a map of enum ordinals to their corresponding values.
	 */
	public static <E extends Enum<E>> Map<Integer, E> getEnumOrdinalMap(Class<E> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).collect(Collectors.toMap(Enum::ordinal, e -> e));
	}
}
