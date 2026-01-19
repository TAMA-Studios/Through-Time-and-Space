/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.lua;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.LibFunction;

public abstract class FourArgFunction extends LibFunction {

	public abstract LuaValue call(LuaValue a1, LuaValue a2, LuaValue a3, LuaValue a4);

	public FourArgFunction() {
		super();
	}

	public final LuaValue call() {
		return call(NIL, NIL, NIL, NIL);
	}

	public final LuaValue call(LuaValue a1) {
		return call(a1, NIL, NIL, NIL);
	}

	public final LuaValue call(LuaValue a1, LuaValue a2) {
		return call(a1, a2, NIL, NIL);
	}

	public final LuaValue call(LuaValue a1, LuaValue a2, LuaValue a3) {
		return call(a1, a2, a3, NIL);
	}

	@Override
	public Varargs invoke(Varargs args) {
		return call(args.arg1(), args.arg(2), args.arg(3), args.arg(4));
	}
}
