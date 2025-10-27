/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.miscs;

import java.util.Objects;

@FunctionalInterface
public interface TriConsumer<T, U, V> {
	/**
	 * Performs this operation on the given arguments.
	 */
	void accept(T var1, U var2, V var3);

	default TriConsumer<T, U, V> andThen(TriConsumer<? super T, ? super U, ? super V> after) {
		Objects.requireNonNull(after);
		return (l, c, r) -> {
			this.accept(l, c, r);
			after.accept(l, c, r);
		};
	}
}
