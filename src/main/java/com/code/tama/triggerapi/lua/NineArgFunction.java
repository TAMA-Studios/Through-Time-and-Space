/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.lua;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.LibFunction;

public abstract class NineArgFunction extends LibFunction {

	public abstract LuaValue call(LuaValue a1, LuaValue a2, LuaValue a3, LuaValue a4, LuaValue a5, LuaValue a6,
			LuaValue a7, LuaValue a8, LuaValue a9);

	public NineArgFunction() {
		super();
	}

	@Override
	public Varargs invoke(Varargs args) {
		return call(args.arg1(), args.arg(2), args.arg(3), args.arg(4), args.arg(5), args.arg(6), args.arg(7),
				args.arg(8), args.arg(9));
	}
}
