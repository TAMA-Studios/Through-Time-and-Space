/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.helpers;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.code.tama.triggerapi.miscs.TriConsumer;

public class ThreadUtils {

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
	}
}
