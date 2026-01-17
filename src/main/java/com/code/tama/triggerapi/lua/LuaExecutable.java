/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.lua;

import static com.code.tama.triggerapi.helpers.SkinHelper.*;
import static com.code.tama.triggerapi.lua.LuaScriptEngine.getTPS;
import static com.code.tama.triggerapi.lua.LuaScriptEngine.toLuaValue;
import static com.code.tama.tts.TTSMod.LOGGER;

import java.util.UUID;

import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.data.tardis.data.TARDISData;
import com.code.tama.tts.server.data.tardis.data.TARDISEnvironmentalData;
import com.code.tama.tts.server.data.tardis.data.TARDISFlightData;
import com.code.tama.tts.server.data.tardis.data.TARDISNavigationalData;
import org.luaj.vm2.*;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.*;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.lib.jse.JseMathLib;
import org.luaj.vm2.lib.jse.JsePlatform;

import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import com.code.tama.triggerapi.codec.lua.LuaCodecBridge;
import com.code.tama.triggerapi.gui.GuiRegistry;

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

			return new LuaScriptEngine.ScriptResult(true, result.toString());
		} catch (LuaError e) {
			LOGGER.error("Lua script error: {}", e.getMessage());
			return new LuaScriptEngine.ScriptResult(false, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Script execution error", e);
			return new LuaScriptEngine.ScriptResult(false, e.getMessage());
		}
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
		globals.set("mc", mcTable);
		globals.set("ctx", contextTable(context));
		if (TARDISLevelCapability.GetTARDISCapSupplier(player.level()).isPresent()) {
			globals.set("isTardis", toLuaValue(true));
			globals.set("tardis",
					tardisTable(TARDISLevelCapability.GetTARDISCapSupplier(player.level()).orElseGet(null)));
		} else
			globals.set("isTardis", toLuaValue(false));

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
		for (java.util.Map.Entry<String, Object> entry : context.getVariables().entrySet()) {
			contextTable.set(entry.getKey(), toLuaValue(entry.getValue()));
		}
		return contextTable;
	}

	public static LuaTable utilTable(Level level) {
		LuaTable utilTable = new LuaTable();

		utilTable.set("print", new VarArgFunction() {
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

		utilTable.set("broadcast", new OneArgFunction() {
			@Override
			public LuaValue call(LuaValue msg) {
				if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
					serverLevel.getServer().getPlayerList().broadcastSystemMessage(
							net.minecraft.network.chat.Component.literal(msg.checkjstring()), false);
				}
				return LuaValue.NIL;
			}
		});

		utilTable.set("executeCommand", new OneArgFunction() {
			@Override
			public LuaValue call(LuaValue command) {
				if (level instanceof ServerLevel lv) {
					try {
						lv.getServer().getCommands().performPrefixedCommand(lv.getServer().createCommandSourceStack(),
								command.checkjstring());
					} catch (Exception e) {
						LOGGER.error("Failed to execute command", e);
					}
				}
				return LuaValue.NIL;
			}
		});
		return utilTable;
	}

	public static LuaTable levelTable(Level level) {
		LuaTable levelTable = new LuaTable();

		levelTable.set("isClientSide", toLuaValue(level.isClientSide));
		levelTable.set("dimensionName", level.dimension().location().toString());
		levelTable.set("time", level.getDayTime());
		levelTable.set("isRaining", toLuaValue(level.isRaining()));
		levelTable.set("isThundering", toLuaValue(level.isThundering()));
		levelTable.set("difficulty", level.getDifficulty().toString());

		levelTable.set("getBlockAt", new ThreeArgFunction() {
			@Override
			public LuaValue call(LuaValue x, LuaValue y, LuaValue z) {
				net.minecraft.core.BlockPos pos = new net.minecraft.core.BlockPos(x.checkint(), y.checkint(),
						z.checkint());
				net.minecraft.world.level.block.state.BlockState state = level.getBlockState(pos);
				return LuaValue.valueOf(
						net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString());
			}
		});

		levelTable.set("setBlockAt", new VarArgFunction() {
			@Override
			public Varargs invoke(Varargs args) {
				if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
					try {
						int x = args.checkint(1);
						int y = args.checkint(2);
						int z = args.checkint(3);
						String blockId = args.checkjstring(4);

						net.minecraft.core.BlockPos pos = new net.minecraft.core.BlockPos(x, y, z);
						net.minecraft.resources.ResourceLocation id = new net.minecraft.resources.ResourceLocation(
								blockId);
						net.minecraft.world.level.block.Block block = net.minecraft.core.registries.BuiltInRegistries.BLOCK
								.get(id);
						serverLevel.setBlock(pos, block.defaultBlockState(), 3);
					} catch (Exception e) {
						LOGGER.error("Failed to set block", e);
					}
				}
				return LuaValue.NIL;
			}
		});

		levelTable.set("spawnEntity", new VarArgFunction() {
			@Override
			public Varargs invoke(Varargs args) {
				if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
					try {
						String entityId = args.checkjstring(1);
						double x = args.checkdouble(2);
						double y = args.checkdouble(3);
						double z = args.checkdouble(4);

						net.minecraft.resources.ResourceLocation id = new net.minecraft.resources.ResourceLocation(
								entityId);
						net.minecraft.world.entity.EntityType<?> entityType = net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE
								.get(id);
						net.minecraft.world.entity.Entity entity = entityType.create(serverLevel);
						if (entity != null) {
							entity.moveTo(x, y, z);
							serverLevel.addFreshEntity(entity);
						}
					} catch (Exception e) {
						LOGGER.error("Failed to spawn entity", e);
					}
				}
				return LuaValue.NIL;
			}
		});

		levelTable.set("setTime", new OneArgFunction() {
			@Override
			public LuaValue call(LuaValue time) {
				if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
					serverLevel.setDayTime(time.checklong());
				}
				return LuaValue.NIL;
			}
		});

		return levelTable;
	}

	public static LuaTable clientTable(Level level) {
		LuaTable table = new LuaTable();
		table.set("exists", toLuaValue(level.isClientSide()));
		if (level.isClientSide) {
			net.minecraft.client.Minecraft minecraft = net.minecraft.client.Minecraft.getInstance();

			// Performance
			table.set("fps", minecraft.getFps());
			table.set("frameTimeNs", minecraft.getFrameTimeNs());

			// Options/Settings
			net.minecraft.client.Options options = minecraft.options;
			table.set("fov", options.fov().get());
			table.set("renderDistance", options.renderDistance().get());
			table.set("entityDistance", options.entityDistanceScaling().get());
			table.set("gamma", options.gamma().get());
			table.set("guiScale", options.guiScale().get());
			table.set("mouseSensitivity", options.sensitivity().get());
			table.set("soundVolume", options.getSoundSourceVolume(net.minecraft.sounds.SoundSource.MASTER));
			table.set("musicVolume", options.getSoundSourceVolume(net.minecraft.sounds.SoundSource.MUSIC));

			// Window info
			net.minecraft.client.gui.screens.Screen screen = minecraft.screen;
			table.set("currentScreen", screen != null ? screen.getClass().getSimpleName() : "null");
			table.set("windowWidth", minecraft.getWindow().getWidth());
			table.set("windowHeight", minecraft.getWindow().getHeight());
			table.set("isFullscreen", toLuaValue(minecraft.getWindow().isFullscreen()));
			table.set("guiWidth", minecraft.getWindow().getGuiScaledWidth());
			table.set("guiHeight", minecraft.getWindow().getGuiScaledHeight());

			// Game state
			table.set("isPaused", toLuaValue(minecraft.isPaused()));
			table.set("hasSingleplayerServer", toLuaValue(minecraft.hasSingleplayerServer()));

			// Camera info
			net.minecraft.client.Camera camera = minecraft.gameRenderer.getMainCamera();
			LuaTable cameraTable = new LuaTable();
			cameraTable.set("x", camera.getPosition().x);
			cameraTable.set("y", camera.getPosition().y);
			cameraTable.set("z", camera.getPosition().z);
			cameraTable.set("yaw", camera.getYRot());
			cameraTable.set("pitch", camera.getXRot());
			cameraTable.set("isDetached", toLuaValue(camera.isDetached()));
			table.set("camera", cameraTable);

			// Connected server info
			if (minecraft.getCurrentServer() != null) {
				net.minecraft.client.multiplayer.ServerData serverData = minecraft.getCurrentServer();
				LuaTable serverTable = new LuaTable();
				serverTable.set("ip", serverData.ip);
				serverTable.set("name", serverData.name);
				serverTable.set("motd", serverData.motd.getString());
				serverTable.set("playerCount", serverData.players != null ? serverData.players.online() : 0);
				serverTable.set("maxPlayers", serverData.players != null ? serverData.players.max() : 0);
				serverTable.set("version", serverData.version.getString());
				table.set("server", serverTable);
			}

			// Resource packs
			LuaTable resourcePacksTable = new LuaTable();
			int packIndex = 1;
			for (String pack : minecraft.getResourcePackRepository().getSelectedIds()) {
				resourcePacksTable.set(packIndex++, pack);
			}
			table.set("resourcePacks", resourcePacksTable);

			// Language
			table.set("language", options.languageCode);

			// Particle settings
			table.set("particleSetting", options.particles().get().toString());

			// Client functions
			table.set("setFov", new OneArgFunction() {
				@Override
				public LuaValue call(LuaValue fov) {
					options.fov().set(fov.checkint());
					return LuaValue.NIL;
				}
			});

			table.set("setGamma", new OneArgFunction() {
				@Override
				public LuaValue call(LuaValue gamma) {
					options.gamma().set(gamma.checkdouble());
					return LuaValue.NIL;
				}
			});

			table.set("setSoundVolume", new TwoArgFunction() {
				@Override
				public LuaValue call(LuaValue source, LuaValue volume) {
					try {
						net.minecraft.sounds.SoundSource soundSource = net.minecraft.sounds.SoundSource
								.valueOf(source.checkjstring().toUpperCase());
						options.getSoundSourceOptionInstance(soundSource).set(volume.checkdouble());
					} catch (Exception e) {
						LOGGER.error("Failed to set sound volume", e);
					}
					return LuaValue.NIL;
				}
			});

			table.set("openScreen", new OneArgFunction() {
				@Override
				public LuaValue call(LuaValue screenName) {
					try {
						String name = screenName.checkjstring().toLowerCase();
						switch (name) {
							case "inventory" :
								assert Minecraft.getInstance().player != null;
								minecraft.setScreen(new net.minecraft.client.gui.screens.inventory.InventoryScreen(
										Minecraft.getInstance().player));
								break;
							case "options" :
								minecraft.setScreen(new net.minecraft.client.gui.screens.OptionsScreen(minecraft.screen,
										minecraft.options));
								break;
							case "chat" :
								minecraft.setScreen(new net.minecraft.client.gui.screens.ChatScreen(""));
								break;
							default :
								LOGGER.warn("Unknown screen: " + name);
						}
					} catch (Exception e) {
						LOGGER.error("Failed to open screen", e);
					}
					return LuaValue.NIL;
				}
			});

			table.set("closeScreen", new ZeroArgFunction() {
				@Override
				public LuaValue call() {
					minecraft.setScreen(null);
					return LuaValue.NIL;
				}
			});

			table.set("screenshot", new ZeroArgFunction() {
				@Override
				public LuaValue call() {
					net.minecraft.client.Screenshot.grab(minecraft.gameDirectory, minecraft.getMainRenderTarget(),
							(component) -> minecraft.execute(() -> minecraft.gui.getChat().addMessage(component)));
					return LuaValue.NIL;
				}
			});

			table.set("setRenderDistance", new OneArgFunction() {
				@Override
				public LuaValue call(LuaValue distance) {
					options.renderDistance().set(distance.checkint());
					return LuaValue.NIL;
				}
			});

			table.set("reload", new ZeroArgFunction() {
				@Override
				public LuaValue call() {
					minecraft.reloadResourcePacks();
					return LuaValue.NIL;
				}
			});

			table.set("getTargetedBlock", new ZeroArgFunction() {
				@Override
				public LuaValue call() {
					net.minecraft.world.phys.HitResult hit = minecraft.hitResult;
					if (hit != null && hit.getType() == net.minecraft.world.phys.HitResult.Type.BLOCK) {
						net.minecraft.world.phys.BlockHitResult blockHit = (net.minecraft.world.phys.BlockHitResult) hit;
						LuaTable result = new LuaTable();
						result.set("x", blockHit.getBlockPos().getX());
						result.set("y", blockHit.getBlockPos().getY());
						result.set("z", blockHit.getBlockPos().getZ());
						result.set("face", blockHit.getDirection().toString());
						return result;
					}
					return LuaValue.NIL;
				}
			});

			table.set("getTargetedEntity", new ZeroArgFunction() {
				@Override
				public LuaValue call() {
					net.minecraft.world.phys.HitResult hit = minecraft.hitResult;
					if (hit != null && hit.getType() == net.minecraft.world.phys.HitResult.Type.ENTITY) {
						net.minecraft.world.phys.EntityHitResult entityHit = (net.minecraft.world.phys.EntityHitResult) hit;
						net.minecraft.world.entity.Entity entity = entityHit.getEntity();
						LuaTable result = new LuaTable();
						result.set("type", net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE
								.getKey(entity.getType()).toString());
						result.set("uuid", entity.getStringUUID());
						if (entity.hasCustomName()) {
							result.set("name", entity.getCustomName().getString());
						}
						return result;
					}
					return LuaValue.NIL;
				}
			});
		}
		return table;
	}

	public static LuaTable serverTable(Level level) {
		LuaTable table = new LuaTable();
		table.set("exists", toLuaValue(!level.isClientSide()));

		if (!level.isClientSide && level instanceof ServerLevel sl) {
			net.minecraft.server.MinecraftServer server = sl.getServer();
			table.set("tps", getTPS(server));
			table.set("tickTime", server.getAverageTickTime() / 1_000_000.0); // ms
			table.set("playerCount", server.getPlayerCount());
			table.set("maxPlayers", server.getMaxPlayers());
			table.set("motd", server.getMotd());
			table.set("serverPort", server.getPort());
			table.set("isHardcore", toLuaValue(server.isHardcore()));
			table.set("isPvpAllowed", toLuaValue(server.isPvpAllowed()));
			table.set("difficulty", server.getWorldData().getDifficulty().toString());
		}
		return table;
	}

	public static LuaTable playerTable(Player player) {
		LuaTable playerTable = new LuaTable();

		// Basic info
		playerTable.set("name", player.getName().getString());
		playerTable.set("uuid", player.getStringUUID());
		playerTable.set("displayName", player.getDisplayName().getString());

		// Stats
		playerTable.set("health", player.getHealth());
		playerTable.set("maxHealth", player.getMaxHealth());
		playerTable.set("level", player.experienceLevel);
		playerTable.set("foodLevel", player.getFoodData().getFoodLevel());
		playerTable.set("saturation", player.getFoodData().getSaturationLevel());
		playerTable.set("xpProgress", player.experienceProgress);
		playerTable.set("totalXp", player.totalExperience);
		playerTable.set("armor", player.getArmorValue());
		playerTable.set("absorptionAmount", player.getAbsorptionAmount());
		playerTable.set("airSupply", player.getAirSupply());
		playerTable.set("maxAirSupply", player.getMaxAirSupply());

		// Position & rotation
		playerTable.set("x", player.getX());
		playerTable.set("y", player.getY());
		playerTable.set("z", player.getZ());
		playerTable.set("yaw", player.getYRot());
		playerTable.set("pitch", player.getXRot());
		playerTable.set("dimension", player.level().dimension().location().toString());

		// State
		playerTable.set("isOnGround", toLuaValue(player.onGround()));
		playerTable.set("isInWater", toLuaValue(player.isInWater()));
		playerTable.set("isInLava", toLuaValue(player.isInLava()));
		playerTable.set("isSneaking", toLuaValue(player.isCrouching()));
		playerTable.set("isSprinting", toLuaValue(player.isSprinting()));
		playerTable.set("isSwimming", toLuaValue(player.isSwimming()));
		playerTable.set("isSleeping", toLuaValue(player.isSleeping()));
		playerTable.set("isOnFire", toLuaValue(player.isOnFire()));
		playerTable.set("isAlive", toLuaValue(player.isAlive()));
		playerTable.set("isCreative", toLuaValue(player.isCreative()));
		playerTable.set("isSpectator", toLuaValue(player.isSpectator()));
		playerTable.set("isFallFlying", toLuaValue(player.isFallFlying()));

		// GameProfile info (for skin)
		com.mojang.authlib.GameProfile profile = player.getGameProfile();
		playerTable.set("skinTexture", getSkinTexture(profile));
		playerTable.set("skinModel", getSkinModel(profile));

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
						sp.hurt(sp.damageSources().magic(), health.toint());
					} catch (Exception e) {
						LOGGER.error("Failed to harm player", e);
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

		playerTable.set("teleport", new ThreeArgFunction() {
			@Override
			public LuaValue call(LuaValue x, LuaValue y, LuaValue z) {
				if (player instanceof ServerPlayer sp) {
					sp.teleportTo(x.checkdouble(), y.checkdouble(), z.checkdouble());
				}
				return LuaValue.NIL;
			}
		});

		playerTable.set("getPosition", new ZeroArgFunction() {
			@Override
			public LuaValue call() {
				LuaTable pos = new LuaTable();
				pos.set("x", player.getX());
				pos.set("y", player.getY());
				pos.set("z", player.getZ());
				pos.set("yaw", player.getYRot());
				pos.set("pitch", player.getXRot());
				return pos;
			}
		});

		playerTable.set("setVelocity", new ThreeArgFunction() {
			@Override
			public LuaValue call(LuaValue x, LuaValue y, LuaValue z) {
				player.setDeltaMovement(x.checkdouble(), y.checkdouble(), z.checkdouble());
				player.hurtMarked = true;
				return LuaValue.NIL;
			}
		});

		playerTable.set("addVelocity", new ThreeArgFunction() {
			@Override
			public LuaValue call(LuaValue x, LuaValue y, LuaValue z) {
				player.push(x.checkdouble(), y.checkdouble(), z.checkdouble());
				return LuaValue.NIL;
			}
		});

		playerTable.set("getInventory", new ZeroArgFunction() {
			@Override
			public LuaValue call() {
				LuaTable inv = new LuaTable();
				net.minecraft.world.entity.player.Inventory inventory = player.getInventory();

				for (int i = 0; i < inventory.getContainerSize(); i++) {
					net.minecraft.world.item.ItemStack stack = inventory.getItem(i);
					if (!stack.isEmpty()) {
						LuaTable item = new LuaTable();
						item.set("id", net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem())
								.toString());
						item.set("count", stack.getCount());
						item.set("displayName", stack.getHoverName().getString());
						inv.set(i, item);
					}
				}
				return inv;
			}
		});

		playerTable.set("getHeldItem", new ZeroArgFunction() {
			@Override
			public LuaValue call() {
				net.minecraft.world.item.ItemStack stack = player.getMainHandItem();
				if (stack.isEmpty())
					return LuaValue.NIL;

				LuaTable item = new LuaTable();
				item.set("id", net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());
				item.set("count", stack.getCount());
				item.set("displayName", stack.getHoverName().getString());
				return item;
			}
		});

		playerTable.set("getActiveEffects", new ZeroArgFunction() {
			@Override
			public LuaValue call() {
				LuaTable effects = new LuaTable();
				int index = 1;
				for (net.minecraft.world.effect.MobEffectInstance effect : player.getActiveEffects()) {
					LuaTable effectTable = new LuaTable();
					effectTable.set("id", net.minecraft.core.registries.BuiltInRegistries.MOB_EFFECT
							.getKey(effect.getEffect()).toString());
					effectTable.set("duration", effect.getDuration());
					effectTable.set("amplifier", effect.getAmplifier());
					effectTable.set("ambient", toLuaValue(effect.isAmbient()));
					effectTable.set("visible", toLuaValue(effect.isVisible()));
					effects.set(index++, effectTable);
				}
				return effects;
			}
		});

		playerTable.set("addEffect", new ThreeArgFunction() {
			@Override
			public LuaValue call(LuaValue effectId, LuaValue duration, LuaValue amplifier) {
				try {
					net.minecraft.resources.ResourceLocation id = new net.minecraft.resources.ResourceLocation(
							effectId.checkjstring());
					net.minecraft.world.effect.MobEffect effect = net.minecraft.core.registries.BuiltInRegistries.MOB_EFFECT
							.get(id);
					if (effect != null) {
						player.addEffect(new net.minecraft.world.effect.MobEffectInstance(effect, duration.checkint(),
								amplifier.checkint()));
					}
				} catch (Exception e) {
					LOGGER.error("Failed to add effect", e);
				}
				return LuaValue.NIL;
			}
		});

		playerTable.set("removeEffect", new OneArgFunction() {
			@Override
			public LuaValue call(LuaValue effectId) {
				try {
					net.minecraft.resources.ResourceLocation id = new net.minecraft.resources.ResourceLocation(
							effectId.checkjstring());
					net.minecraft.world.effect.MobEffect effect = net.minecraft.core.registries.BuiltInRegistries.MOB_EFFECT
							.get(id);
					if (effect != null) {
						player.removeEffect(effect);
					}
				} catch (Exception e) {
					LOGGER.error("Failed to remove effect", e);
				}
				return LuaValue.NIL;
			}
		});

		playerTable.set("setHealth", new OneArgFunction() {
			@Override
			public LuaValue call(LuaValue health) {
				player.setHealth((float) health.checkdouble());
				return LuaValue.NIL;
			}
		});

		playerTable.set("heal", new OneArgFunction() {
			@Override
			public LuaValue call(LuaValue amount) {
				player.heal((float) amount.checkdouble());
				return LuaValue.NIL;
			}
		});

		playerTable.set("setFoodLevel", new OneArgFunction() {
			@Override
			public LuaValue call(LuaValue level) {
				player.getFoodData().setFoodLevel(level.checkint());
				return LuaValue.NIL;
			}
		});

		playerTable.set("setSaturation", new OneArgFunction() {
			@Override
			public LuaValue call(LuaValue saturation) {
				player.getFoodData().setSaturation((float) saturation.checkdouble());
				return LuaValue.NIL;
			}
		});

		playerTable.set("setGameMode", new OneArgFunction() {
			@Override
			public LuaValue call(LuaValue mode) {
				if (player instanceof ServerPlayer sp) {
					String modeStr = mode.checkjstring().toUpperCase();
					try {
						net.minecraft.world.level.GameType gameType = net.minecraft.world.level.GameType
								.valueOf(modeStr);
						sp.setGameMode(gameType);
					} catch (Exception e) {
						LOGGER.error("Invalid game mode: " + modeStr, e);
					}
				}
				return LuaValue.NIL;
			}
		});

		playerTable.set("setFlying", new OneArgFunction() {
			@Override
			public LuaValue call(LuaValue flying) {
				if (player instanceof ServerPlayer sp) {
					sp.getAbilities().flying = flying.checkboolean();
					sp.onUpdateAbilities();
				}
				return LuaValue.NIL;
			}
		});

		playerTable.set("setAllowFlying", new OneArgFunction() {
			@Override
			public LuaValue call(LuaValue allow) {
				if (player instanceof ServerPlayer sp) {
					sp.getAbilities().mayfly = allow.checkboolean();
					sp.onUpdateAbilities();
				}
				return LuaValue.NIL;
			}
		});

		playerTable.set("setInvulnerable", new OneArgFunction() {
			@Override
			public LuaValue call(LuaValue invulnerable) {
				player.setInvulnerable(invulnerable.checkboolean());
				return LuaValue.NIL;
			}
		});

		playerTable.set("setSkin", new VarArgFunction() {
			@Override
			public Varargs invoke(Varargs args) {
				if (player instanceof ServerPlayer sp) {
					try {
						String textureUrl = args.checkjstring(1);
						String model = args.narg() > 1 ? args.checkjstring(2) : "default"; // "default" or "slim"

						applySkinChange(sp, textureUrl, model);

					} catch (Exception e) {
						LOGGER.error("Failed to set skin", e);
					}
				}
				return LuaValue.NIL;
			}
		});

		playerTable.set("resetSkin", new ZeroArgFunction() {
			@Override
			public LuaValue call() {
				if (player instanceof ServerPlayer sp) {
					try {
						resetPlayerSkin(sp);
					} catch (Exception e) {
						LOGGER.error("Failed to reset skin", e);
					}
				}
				return LuaValue.NIL;
			}
		});

		playerTable.set("setSkinFromPlayer", new OneArgFunction() {
			@Override
			public LuaValue call(LuaValue playerName) {
				if (player instanceof ServerPlayer sp) {
					try {
						String targetName = playerName.checkjstring();
						copySkinFromPlayer(sp, targetName);
					} catch (Exception e) {
						LOGGER.error("Failed to copy skin", e);
					}
				}
				return LuaValue.NIL;
			}
		});

		return playerTable;
	}

	public static LuaTable tardisTable(ITARDISLevel tardis) {
		LuaTable TARDIS = new LuaTable();
		TARDIS.set("data", LuaCodecBridge.encodeToLua(TARDISData.CODEC, tardis.GetData()));
		TARDIS.set("flight", LuaCodecBridge.encodeToLua(TARDISFlightData.CODEC, tardis.GetFlightData()));
		TARDIS.set("navigational",
				LuaCodecBridge.encodeToLua(TARDISNavigationalData.CODEC, tardis.GetNavigationalData()));
		TARDIS.set("environmental",
				LuaCodecBridge.encodeToLua(TARDISEnvironmentalData.CODEC, tardis.GetEnvironmentalData()));

		LuaTable util = new LuaTable();

		// Basic info
		util.set("ticks", tardis.getTicks());

		// Owner info
		UUID ownerUUID = tardis.GetData().getOwnerUUID();
		util.set("ownerUUID", ownerUUID != null ? toLuaValue(ownerUUID.toString()) : LuaValue.NIL);
		Player owner = tardis.GetData().GetOwner();
		util.set("owner", owner != null ? LuaExecutable.playerTable(owner) : LuaValue.NIL);

		// Functions
		util.set("cycleVariant", new VarArgFunction() {
			@Override
			public Varargs invoke(Varargs args) {
				tardis.GetData().CycleVariant();
				return LuaValue.NIL;
			}
		});

		util.set("setPowered", new VarArgFunction() {
			@Override
			public Varargs invoke(Varargs args) {
				tardis.GetData().SetPowered(args.checkboolean(1));
				return LuaValue.NIL;
			}
		});

		TARDIS.set("util", util);

		return TARDIS;
	}
}
