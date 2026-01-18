/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.lua;

import static com.code.tama.triggerapi.lua.LuaScriptEngine.toLuaValue;
import static com.code.tama.triggerapi.lua.LuaTableCreators.*;
import static com.code.tama.tts.TTSMod.LOGGER;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.StringLib;
import org.luaj.vm2.lib.TableLib;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.lib.jse.JseMathLib;
import org.luaj.vm2.lib.jse.JsePlatform;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class LuaExecutable {
	private final String code;

	public LuaExecutable(String code) {
		this.code = code;
	}

	public LuaScriptEngine.ScriptResult executeScript(Minecraft mc, LuaScriptEngine.ScriptContext context) {
		try {
			Globals globals = createSandboxedGlobals(mc, context);

			LuaValue chunk = globals.load(code);
			LuaValue result = chunk.call();

			// Extract modified context back from Lua
			extractContextChanges(globals, context);

			return new LuaScriptEngine.ScriptResult(true, result.toString());
		} catch (Exception e) {
			LOGGER.error("Script execution error", e);
			return new LuaScriptEngine.ScriptResult(false, e.getMessage());
		}
	}

	public LuaScriptEngine.ScriptResult executeScript(Player player, LuaScriptEngine.ScriptContext context) {
		try {
			Globals globals = createSandboxedGlobals(player, context);

			LuaValue chunk = globals.load(code);
			LuaValue result = chunk.call();

			// Extract modified context back from Lua
			extractContextChanges(globals, context);

			return new LuaScriptEngine.ScriptResult(true, result.toString());
		} catch (Exception e) {
			LOGGER.error("Script execution error", e);
			return new LuaScriptEngine.ScriptResult(false, e.getMessage());
		}
	}

	public LuaScriptEngine.ScriptResult executeScript(Level level, LuaScriptEngine.ScriptContext context) {
		try {
			Globals globals = createSandboxedGlobals(level, context);

			LuaValue chunk = globals.load(code);
			LuaValue result = chunk.call();

			// Extract modified context back from Lua
			extractContextChanges(globals, context);

			return new LuaScriptEngine.ScriptResult(true, result.toString());
		} catch (LuaError e) {
			LOGGER.error("Lua script error: {}", e.getMessage());
			return new LuaScriptEngine.ScriptResult(false, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Script execution error", e);
			return new LuaScriptEngine.ScriptResult(false, e.getMessage());
		}
	}

	/**
	 * Extract any changes made to ctx in Lua back into the Java context
	 */
	private static void extractContextChanges(Globals globals, LuaScriptEngine.ScriptContext context) {
		try {
			LuaValue ctxValue = globals.get("ctx");
			if (ctxValue != null && ctxValue.istable()) {
				LuaTable ctxTable = ctxValue.checktable();

				// Iterate over all keys in the Lua ctx table
				LuaValue key = LuaValue.NIL;
				while (true) {
					org.luaj.vm2.Varargs next = ctxTable.next(key);
					key = next.arg(1);
					if (key.isnil())
						break;

					LuaValue value = next.arg(2);
					String keyStr = key.tojstring();

					// Convert Lua value back to Java and update context
					Object javaValue = fromLuaValue(value);
					context.set(keyStr, javaValue);

					DEBUG("[Lua Extract] Extracted ctx." + keyStr + " = " + javaValue);
				}
			}
		} catch (Exception e) {
			LOGGER.debug("Failed to extract context changes from Lua", e);
		}
	}

	/**
	 * Convert a Lua value back to a Java object
	 */
	private static Object fromLuaValue(LuaValue value) {
		if (value.isstring()) {
			return value.tojstring();
		} else if (value.isnumber()) {
			return value.todouble();
		} else if (value.isboolean()) {
			return value.toboolean();
		} else if (value.isnil()) {
			return null;
		}
		return value.toString();
	}

	public static Globals createSandboxedGlobals(Player player, LuaScriptEngine.ScriptContext context) {
		Globals globals = JsePlatform.standardGlobals();

		LuaC.install(globals);

		globals.load(new JseBaseLib());
		globals.load(new PackageLib());
		globals.load(new StringLib());
		globals.load(new TableLib());
		globals.load(new JseMathLib());

		// Create main mc table
		LuaTable mcTable = new LuaTable();

		mcTable.set("isClientSide", toLuaValue(player.level().isClientSide));
		mcTable.set("level", levelTable(player.level()));
		mcTable.set("client", clientTable(player.level()));
		mcTable.set("server", serverTable(player.level()));
		mcTable.set("hasPlayer", toLuaValue(true));
		mcTable.set("player", playerTable(player));
		mcTable.set("util", utilTable(player.level()));
		if (TARDISLevelCapability.GetTARDISCapSupplier(player.level()).isPresent()) {
			mcTable.set("isTardis", toLuaValue(true));
			mcTable.set("tardis",
					tardisTable(TARDISLevelCapability.GetTARDISCapSupplier(player.level()).orElseGet(null)));
		} else
			mcTable.set("isTardis", toLuaValue(false));

		globals.set("mc", mcTable);
		globals.set("ctx", contextTable(context));
		return globals;
	}

	public static Globals createSandboxedGlobals(Minecraft mc, LuaScriptEngine.ScriptContext context) {
		Globals globals = JsePlatform.standardGlobals();

		LuaC.install(globals);

		globals.load(new JseBaseLib());
		globals.load(new PackageLib());
		globals.load(new StringLib());
		globals.load(new TableLib());
		globals.load(new JseMathLib());

		// Create main mc table
		LuaTable mcTable = new LuaTable();

		assert mc.level != null;
		mcTable.set("isClientSide", toLuaValue(mc.level.isClientSide));
		mcTable.set("level", levelTable(mc.level));
		mcTable.set("client", clientTable(mc.level));
		mcTable.set("server", serverTable(mc.level));
		assert mc.player != null;
		mcTable.set("hasPlayer", toLuaValue(true));
		mcTable.set("player", playerTable(mc.player));
		mcTable.set("util", utilTable(mc.level));
		globals.set("mc", mcTable);
		globals.set("ctx", contextTable(context));

		return globals;
	}

	public static Globals createSandboxedGlobals(Level level, LuaScriptEngine.ScriptContext context) {
		Globals globals = JsePlatform.standardGlobals();

		LuaC.install(globals);

		globals.load(new JseBaseLib());
		globals.load(new PackageLib());
		globals.load(new StringLib());
		globals.load(new TableLib());
		globals.load(new JseMathLib());

		// Create main mc table
		LuaTable mcTable = new LuaTable();

		mcTable.set("isClientSide", toLuaValue(level.isClientSide));
		mcTable.set("level", levelTable(level));
		mcTable.set("client", clientTable(level));
		mcTable.set("server", serverTable(level));
		mcTable.set("hasPlayer", toLuaValue(false));
		mcTable.set("player", new LuaTable());
		mcTable.set("util", utilTable(level));
		globals.set("mc", mcTable);
		globals.set("ctx", contextTable(context));

		return globals;
	}

	public static LuaTable contextTable(LuaScriptEngine.ScriptContext context) {
		LuaTable contextTable = new LuaTable();

		// DEBUG
		DEBUG("[Lua Context] Creating Lua context table with {} variables" + context.getVariables().size());

		for (java.util.Map.Entry<String, Object> entry : context.getVariables().entrySet()) {
			LuaValue luaValue = toLuaValue(entry.getValue());
			contextTable.set(entry.getKey(), luaValue);

			// DEBUG
			DEBUG("[Lua Context]   {} = {} (converted to {})", entry.getKey(), "" + entry.getValue(),
					luaValue.typename());
		}
		return contextTable;
	}

	public static void DEBUG(String... s) {
		if (false) {
			TTSMod.LOGGER.info(s);
		}
	}
}