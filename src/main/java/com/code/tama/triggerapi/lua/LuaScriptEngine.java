/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.lua;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.world.entity.player.Player;

public class LuaScriptEngine {
	private static final Logger LOGGER = LoggerFactory.getLogger(LuaScriptEngine.class);

	public static ScriptResult executeScript(String script, Player player, ScriptContext context) {
		try {
			Globals globals = LuaExecutable.createSandboxedGlobals(player, context);

			LuaValue chunk = globals.load(script);
			LuaValue result = chunk.call();

			LuaTable playerTable = (LuaTable) globals.get("player");
			player.setHealth(playerTable.get("health").tofloat());
			player.experienceLevel = playerTable.get("level").toint();
			player.getFoodData().setFoodLevel(playerTable.get("foodLevel").toint());
			player.experienceProgress = playerTable.get("xpProgress").tofloat();

			return new ScriptResult(true, result.toString());
		} catch (LuaError e) {
			LOGGER.error("Lua script error: {}", e.getMessage());
			return new ScriptResult(false, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Script execution error", e);
			return new ScriptResult(false, e.getMessage());
		}
	}

	static double getTPS(net.minecraft.server.MinecraftServer server) {
		// Calculate TPS from tick times
		long[] tickTimes = server.tickTimes;
		long sum = 0;
		for (long time : tickTimes) {
			sum += time;
		}
		double avgTickTime = sum / (double) tickTimes.length;
		double tps = 1000000000.0 / avgTickTime;
		return Math.min(20.0, tps); // Cap at 20 TPS
	}

	static LuaValue toLuaValue(Object obj) {
		if (obj instanceof String)
			return LuaValue.valueOf((String) obj);
		if (obj instanceof Number)
			return LuaValue.valueOf(((Number) obj).doubleValue());
		if (obj instanceof Boolean)
			return LuaValue.valueOf((Boolean) obj);
		return LuaValue.NIL;
	}

	public static class ScriptResult {
		private final boolean success;
		private final String message;

		public ScriptResult(boolean success, String message) {
			this.success = success;
			this.message = message;
		}

		public boolean isSuccess() {
			return success;
		}

		public String getMessage() {
			return message;
		}
	}

	public static class ScriptContext {
		private final java.util.Map<String, Object> variables = new java.util.HashMap<>();

		public void set(String key, Object value) {
			variables.put(key, value);
		}

		public Object get(String key) {
			return variables.get(key);
		}

		public java.util.Map<String, Object> getVariables() {
			return variables;
		}
	}
}