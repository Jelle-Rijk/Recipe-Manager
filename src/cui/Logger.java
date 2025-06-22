package cui;

import java.time.LocalTime;

public abstract class Logger {
	public static void log(String message, Object source) {
		System.out.printf("[%s - %s]: %s", source.getClass().getSimpleName(), LocalTime.now(), message);
	}

	public static void log(String message) {
		System.out.printf("[%s - %s]: %s", LocalTime.now(), message);
	}

	public static void logError(String message, Object source) {
		System.err.printf("[%s - %s]: %s", source.getClass().getSimpleName(), LocalTime.now(), message);
	}

	public static void logError(String message) {
		System.err.printf("[%s - %s]: %s", LocalTime.now(), message);
	}

}
