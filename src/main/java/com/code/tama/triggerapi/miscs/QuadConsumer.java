/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.miscs;

import java.util.Objects;

@FunctionalInterface
public interface QuadConsumer<T, U, V, X> {
	/**
	 * Performs this operation on the given arguments.
	 */
	void accept(T var1, U var2, V var3, X var4);

	default QuadConsumer<T, U, V, X> andThen(QuadConsumer<? super T, ? super U, ? super V, ? super X> after) {
		Objects.requireNonNull(after);
		return (l, c, r, x) -> {
			this.accept(l, c, r, x);
			after.accept(l, c, r, x);
		};
	}
}
