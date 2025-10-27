/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.miscs;

import java.util.Objects;

@FunctionalInterface
public interface QuintConsumer<T, U, V, X, Z> {
	/**
	 * Performs this operation on the given arguments.
	 */
	void accept(T var1, U var2, V var3, X var4, Z var5);

	default QuintConsumer<T, U, V, X, Z> andThen(QuintConsumer<? super T, ? super U, ? super V, ? super X, Z> after) {
		Objects.requireNonNull(after);
		return (l, c, r, x, z) -> {
			this.accept(l, c, r, x, z);
			after.accept(l, c, r, x, z);
		};
	}
}
