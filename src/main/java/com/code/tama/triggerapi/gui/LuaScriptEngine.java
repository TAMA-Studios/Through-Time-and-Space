/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.gui;

import org.luaj.vm2.*;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.*;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.lib.jse.JseMathLib;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class LuaScriptEngine {
	private static final Logger LOGGER = LoggerFactory.getLogger(LuaScriptEngine.class);

	public static ScriptResult executeScript(String script, Player player, ScriptContext context) {
		try {
			Globals globals = createSandboxedGlobals(player, context);

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

	private static Globals createSandboxedGlobals(Player player, ScriptContext context) {
		Globals globals = JsePlatform.standardGlobals();

		LuaC.install(globals);

		globals.load(new JseBaseLib());
		globals.load(new PackageLib());
		globals.load(new StringLib());
		globals.load(new TableLib());
		globals.load(new JseMathLib());

		// Add player API
		LuaTable playerTable = new LuaTable();
		playerTable.set("name", player.getName().getString());
		playerTable.set("uuid", player.getStringUUID());
		playerTable.set("health", player.getHealth());
		playerTable.set("maxHealth", player.getMaxHealth());
		playerTable.set("level", player.experienceLevel);
		playerTable.set("foodLevel", player.getFoodData().getFoodLevel());
		playerTable.set("xpProgress", player.experienceProgress);

		// Player functions
		playerTable.set("sendMessage", new OneArgFunction() {
			@Override
			public LuaValue call(LuaValue msg) {
				player.sendSystemMessage(net.minecraft.network.chat.Component.literal(msg.checkjstring()));
				return LuaValue.NIL;
			}
		});

		playerTable.set("giveItem", new TwoArgFunction() {
			@Override
			public LuaValue call(LuaValue itemId, LuaValue count) {
				if (player instanceof ServerPlayer sp) {
					try {
						net.minecraft.resources.ResourceLocation id = new net.minecraft.resources.ResourceLocation(
								itemId.checkjstring());
						net.minecraft.world.item.Item item = net.minecraft.core.registries.BuiltInRegistries.ITEM
								.get(id);
						sp.addItem(new net.minecraft.world.item.ItemStack(item, count.checkint()));
					} catch (Exception e) {
						LOGGER.error("Failed to give item", e);
					}
				}
				return LuaValue.NIL;
			}
		});

		playerTable.set("closeGui", new ZeroArgFunction() {
			@Override
			public LuaValue call() {
				player.closeContainer();
				return LuaValue.NIL;
			}
		});

		playerTable.set("openGui", new OneArgFunction() {
			@Override
			public LuaValue call(LuaValue guiId) {
				if (player instanceof ServerPlayer sp) {
					try {
						net.minecraft.resources.ResourceLocation loc = new net.minecraft.resources.ResourceLocation(
								guiId.checkjstring());
						GuiRegistry.openGui(sp, loc);
					} catch (Exception e) {
						LOGGER.error("Failed to open GUI", e);
					}
				}
				return LuaValue.NIL;
			}
		});

		playerTable.set("hurt", new OneArgFunction() {
			@Override
			public LuaValue call(LuaValue health) {
				if (player instanceof ServerPlayer sp) {
					try {
						player.hurt(player.damageSources().magic(), health.toint());
					} catch (Exception e) {
						LOGGER.error("Failed to harm player ", e);
					}
				}
				return LuaValue.NIL;
			}
		});

		playerTable.set("playSound", new TwoArgFunction() {
			@Override
			public LuaValue call(LuaValue soundId, LuaValue volume) {
				if (player instanceof ServerPlayer sp) {
					try {
						net.minecraft.resources.ResourceLocation id = new net.minecraft.resources.ResourceLocation(
								soundId.checkjstring());
						net.minecraft.sounds.SoundEvent sound = net.minecraft.sounds.SoundEvent
								.createVariableRangeEvent(id);
						sp.playNotifySound(sound, net.minecraft.sounds.SoundSource.PLAYERS,
								(float) volume.checkdouble(), 1.0f);
					} catch (Exception e) {
						LOGGER.error("Failed to play sound", e);
					}
				}
				return LuaValue.NIL;
			}
		});

		globals.set("player", playerTable);

		// Add context variables
		LuaTable contextTable = new LuaTable();
		for (java.util.Map.Entry<String, Object> entry : context.getVariables().entrySet()) {
			contextTable.set(entry.getKey(), toLuaValue(entry.getValue()));
		}
		globals.set("ctx", contextTable);

		// Add utility functions
		globals.set("print", new VarArgFunction() {
			@Override
			public Varargs invoke(Varargs args) {
				StringBuilder sb = new StringBuilder();
				for (int i = 1; i <= args.narg(); i++) {
					if (i > 1)
						sb.append("\t");
					sb.append(args.arg(i).tojstring());
				}
				LOGGER.info("[Script] {}", sb);
				return LuaValue.NIL;
			}
		});

		return globals;
	}

	private static LuaValue toLuaValue(Object obj) {
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