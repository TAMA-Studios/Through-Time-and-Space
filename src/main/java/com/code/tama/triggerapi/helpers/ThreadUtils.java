/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.helpers;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.code.tama.triggerapi.miscs.TriConsumer;

public class ThreadUtils {
	/** THIS SHOULD ONLY BE CALLED IF CONDITION IS UPDATED ON A SEPARATE THREAD **/
	public static void Pause(Boolean condition) {
		while (condition) {
			DoNothing();
		}
	}

	/**
	 * "Pauses" the current thread by making a while loop for a duration of time
	 *
	 * @param ms
	 *            Milliseconds to pause for
	 */
	public static void Pause(long ms) {
		// AtomicBoolean b = new AtomicBoolean();
		// b.set(true);
		// long t = 0;
		// RunThread((prevMS, condition) -> {
		// while (prevMS < ms) {
		// prevMS++;
		// }
		//
		// condition.set(false);
		// }, t, b);

		// Pause(b.get());

		long prevMS = 0;
		while (prevMS < ms) {
			prevMS++;
		}
	}

	public static void RunThread(Runnable run, String name) {
		new Thread(run, name).start();
	}

	public static <T> void RunThread(Consumer<T> consumer, T object, String name) {
		new Thread(() -> consumer.accept(object), name).start();
	}

	public static <T, V> void RunThread(BiConsumer<T, V> consumer, T object, V object2, String name) {
		new Thread(() -> consumer.accept(object, object2), name).start();
	}

	public static <T, V, X> void RunThread(TriConsumer<T, V, X> consumer, T object, V object2, X object3, String name) {
		new Thread(() -> consumer.accept(object, object2, object3), name).start();
	}

	public static <T> Thread NewThread(Consumer<T> consumer, T object, String name) {
		return new Thread(() -> consumer.accept(object), name);
	}

	public static <T, V> Thread NewThread(BiConsumer<T, V> consumer, T object, V object2, String name) {
		return new Thread(() -> consumer.accept(object, object2), name);
	}

	public static <T, V, X> Thread NewThread(TriConsumer<T, V, X> consumer, T object, V object2, X object3,
			String name) {
		return new Thread(() -> consumer.accept(object, object2, object3), name);
	}

	/**
	 * So intj doesn't complain abt "If statement has empty body"
	 */
	private static void DoNothing() {
	};
}
