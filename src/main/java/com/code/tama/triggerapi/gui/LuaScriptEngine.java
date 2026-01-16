/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.gui;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.luaj.vm2.*;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.*;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.lib.jse.JseMathLib;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

		// Create main mc table
		LuaTable mcTable = new LuaTable();

		// Add player subtable - FULLY FLESHED OUT
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
						player.hurt(player.damageSources().magic(), health.toint());
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
						item.set("id", net.minecraft.core.registries.BuiltInRegistries.ITEM
								.getKey(stack.getItem()).toString());
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
				if (stack.isEmpty()) return LuaValue.NIL;

				LuaTable item = new LuaTable();
				item.set("id", net.minecraft.core.registries.BuiltInRegistries.ITEM
						.getKey(stack.getItem()).toString());
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
					net.minecraft.world.effect.MobEffect effect = net.minecraft.core.registries.BuiltInRegistries.MOB_EFFECT.get(id);
					if (effect != null) {
						player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
								effect, duration.checkint(), amplifier.checkint()));
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
					net.minecraft.world.effect.MobEffect effect = net.minecraft.core.registries.BuiltInRegistries.MOB_EFFECT.get(id);
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
						net.minecraft.world.level.GameType gameType = net.minecraft.world.level.GameType.valueOf(modeStr);
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

		mcTable.set("player", playerTable);

		// Add level/world subtable
		LuaTable levelTable = new LuaTable();
		net.minecraft.world.level.Level level = player.level();

		levelTable.set("isClientSide", toLuaValue(level.isClientSide));
		levelTable.set("dimensionName", level.dimension().location().toString());
		levelTable.set("time", level.getDayTime());
		levelTable.set("isRaining", toLuaValue(level.isRaining()));
		levelTable.set("isThundering", toLuaValue(level.isThundering()));
		levelTable.set("difficulty", level.getDifficulty().toString());

		levelTable.set("getBlockAt", new ThreeArgFunction() {
			@Override
			public LuaValue call(LuaValue x, LuaValue y, LuaValue z) {
				net.minecraft.core.BlockPos pos = new net.minecraft.core.BlockPos(
						x.checkint(), y.checkint(), z.checkint());
				net.minecraft.world.level.block.state.BlockState state = level.getBlockState(pos);
				return LuaValue.valueOf(net.minecraft.core.registries.BuiltInRegistries.BLOCK
						.getKey(state.getBlock()).toString());
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
						net.minecraft.resources.ResourceLocation id = new net.minecraft.resources.ResourceLocation(blockId);
						net.minecraft.world.level.block.Block block = net.minecraft.core.registries.BuiltInRegistries.BLOCK.get(id);
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

						net.minecraft.resources.ResourceLocation id = new net.minecraft.resources.ResourceLocation(entityId);
						net.minecraft.world.entity.EntityType<?> entityType = net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.get(id);
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

		mcTable.set("level", levelTable);

		// Add instance subtable (for client-side info if available) - FULLY FLESHED OUT
		LuaTable instanceTable = new LuaTable();
		instanceTable.set("isClientSide", toLuaValue(level.isClientSide));

		// Add useful game information
		if (level.isClientSide) {
			net.minecraft.client.Minecraft minecraft = net.minecraft.client.Minecraft.getInstance();

			// Performance
			instanceTable.set("fps", minecraft.getFps());
			instanceTable.set("frameTimeNs", minecraft.getFrameTimeNs());

			// Options/Settings
			net.minecraft.client.Options options = minecraft.options;
			instanceTable.set("fov", options.fov().get());
			instanceTable.set("renderDistance", options.renderDistance().get());
			instanceTable.set("entityDistance", options.entityDistanceScaling().get());
			instanceTable.set("gamma", options.gamma().get());
			instanceTable.set("guiScale", options.guiScale().get());
			instanceTable.set("mouseSensitivity", options.sensitivity().get());
			instanceTable.set("soundVolume", options.getSoundSourceVolume(net.minecraft.sounds.SoundSource.MASTER));
			instanceTable.set("musicVolume", options.getSoundSourceVolume(net.minecraft.sounds.SoundSource.MUSIC));

			// Window info
			net.minecraft.client.gui.screens.Screen screen = minecraft.screen;
			instanceTable.set("currentScreen", screen != null ? screen.getClass().getSimpleName() : "null");
			instanceTable.set("windowWidth", minecraft.getWindow().getWidth());
			instanceTable.set("windowHeight", minecraft.getWindow().getHeight());
			instanceTable.set("isFullscreen", toLuaValue(minecraft.getWindow().isFullscreen()));
			instanceTable.set("guiWidth", minecraft.getWindow().getGuiScaledWidth());
			instanceTable.set("guiHeight", minecraft.getWindow().getGuiScaledHeight());

			// Game state
			instanceTable.set("isPaused", toLuaValue(minecraft.isPaused()));
			instanceTable.set("hasSingleplayerServer", toLuaValue(minecraft.hasSingleplayerServer()));

			// Camera info
			net.minecraft.client.Camera camera = minecraft.gameRenderer.getMainCamera();
			LuaTable cameraTable = new LuaTable();
			cameraTable.set("x", camera.getPosition().x);
			cameraTable.set("y", camera.getPosition().y);
			cameraTable.set("z", camera.getPosition().z);
			cameraTable.set("yaw", camera.getYRot());
			cameraTable.set("pitch", camera.getXRot());
			cameraTable.set("isDetached", toLuaValue(camera.isDetached()));
			instanceTable.set("camera", cameraTable);

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
				instanceTable.set("server", serverTable);
			}

			// Resource packs
			LuaTable resourcePacksTable = new LuaTable();
			int packIndex = 1;
			for (String pack : minecraft.getResourcePackRepository().getSelectedIds()) {
				resourcePacksTable.set(packIndex++, pack);
			}
			instanceTable.set("resourcePacks", resourcePacksTable);

			// Language
			instanceTable.set("language", options.languageCode);

			// Particle settings
			instanceTable.set("particleSetting", options.particles().get().toString());

			// Client functions
			instanceTable.set("setFov", new OneArgFunction() {
				@Override
				public LuaValue call(LuaValue fov) {
					options.fov().set(fov.checkint());
					return LuaValue.NIL;
				}
			});

			instanceTable.set("setGamma", new OneArgFunction() {
				@Override
				public LuaValue call(LuaValue gamma) {
					options.gamma().set(gamma.checkdouble());
					return LuaValue.NIL;
				}
			});

			instanceTable.set("setSoundVolume", new TwoArgFunction() {
				@Override
				public LuaValue call(LuaValue source, LuaValue volume) {
					try {
						net.minecraft.sounds.SoundSource soundSource =
								net.minecraft.sounds.SoundSource.valueOf(source.checkjstring().toUpperCase());
						options.getSoundSourceOptionInstance(soundSource).set(volume.checkdouble());
					} catch (Exception e) {
						LOGGER.error("Failed to set sound volume", e);
					}
					return LuaValue.NIL;
				}
			});

			instanceTable.set("openScreen", new OneArgFunction() {
				@Override
				public LuaValue call(LuaValue screenName) {
					try {
						String name = screenName.checkjstring().toLowerCase();
						switch (name) {
							case "inventory":
								minecraft.setScreen(new net.minecraft.client.gui.screens.inventory.InventoryScreen(player));
								break;
							case "options":
								minecraft.setScreen(new net.minecraft.client.gui.screens.OptionsScreen(minecraft.screen, minecraft.options));
								break;
							case "chat":
								minecraft.setScreen(new net.minecraft.client.gui.screens.ChatScreen(""));
								break;
							default:
								LOGGER.warn("Unknown screen: " + name);
						}
					} catch (Exception e) {
						LOGGER.error("Failed to open screen", e);
					}
					return LuaValue.NIL;
				}
			});

			instanceTable.set("closeScreen", new ZeroArgFunction() {
				@Override
				public LuaValue call() {
					minecraft.setScreen(null);
					return LuaValue.NIL;
				}
			});

			instanceTable.set("screenshot", new ZeroArgFunction() {
				@Override
				public LuaValue call() {
					net.minecraft.client.Screenshot.grab(
							minecraft.gameDirectory,
							minecraft.getMainRenderTarget(),
							(component) -> minecraft.execute(() ->
									minecraft.gui.getChat().addMessage(component))
					);
					return LuaValue.NIL;
				}
			});

			instanceTable.set("setRenderDistance", new OneArgFunction() {
				@Override
				public LuaValue call(LuaValue distance) {
					options.renderDistance().set(distance.checkint());
					return LuaValue.NIL;
				}
			});

			instanceTable.set("reload", new ZeroArgFunction() {
				@Override
				public LuaValue call() {
					minecraft.reloadResourcePacks();
					return LuaValue.NIL;
				}
			});

			instanceTable.set("getTargetedBlock", new ZeroArgFunction() {
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

			instanceTable.set("getTargetedEntity", new ZeroArgFunction() {
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
		} else {
			// Server-side instance info
			if (player instanceof ServerPlayer sp) {
				net.minecraft.server.MinecraftServer server = sp.getServer();
				if (server != null) {
					instanceTable.set("tps", getTPS(server));
					instanceTable.set("tickTime", server.getAverageTickTime() / 1_000_000.0); // ms
					instanceTable.set("playerCount", server.getPlayerCount());
					instanceTable.set("maxPlayers", server.getMaxPlayers());
					instanceTable.set("motd", server.getMotd());
					instanceTable.set("serverPort", server.getPort());
					instanceTable.set("isHardcore", toLuaValue(server.isHardcore()));
					instanceTable.set("isPvpAllowed", toLuaValue(server.isPvpAllowed()));
					instanceTable.set("difficulty", server.getWorldData().getDifficulty().toString());
				}
			}
		}

		mcTable.set("instance", instanceTable);

		// Add utility subtable
		LuaTable utilTable = new LuaTable();

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
				if (player instanceof ServerPlayer sp) {
					try {
						sp.getServer().getCommands().performPrefixedCommand(
								sp.createCommandSourceStack(), command.checkjstring());
					} catch (Exception e) {
						LOGGER.error("Failed to execute command", e);
					}
				}
				return LuaValue.NIL;
			}
		});

		mcTable.set("util", utilTable);

		// Set mc table globally
		globals.set("mc", mcTable);

		// Keep player table as alias for backwards compatibility
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

	private static String getSkinTexture(com.mojang.authlib.GameProfile profile) {
		com.mojang.authlib.properties.Property textures =
				profile.getProperties().get("textures").stream().findFirst().orElse(null);
		if (textures != null) {
			try {
				String json = new String(java.util.Base64.getDecoder().decode(textures.getValue()));
				com.google.gson.JsonObject obj = new com.google.gson.JsonParser().parse(json).getAsJsonObject();
				return obj.getAsJsonObject("textures").getAsJsonObject("SKIN").get("url").getAsString();
			} catch (Exception e) {
				LOGGER.error("Failed to parse skin texture", e);
			}
		}
		return "default";
	}

	private static String getSkinModel(com.mojang.authlib.GameProfile profile) {
		com.mojang.authlib.properties.Property textures =
				profile.getProperties().get("textures").stream().findFirst().orElse(null);
		if (textures != null) {
			try {
				String json = new String(java.util.Base64.getDecoder().decode(textures.getValue()));
				com.google.gson.JsonObject obj = new com.google.gson.JsonParser().parse(json).getAsJsonObject();
				com.google.gson.JsonObject skin = obj.getAsJsonObject("textures").getAsJsonObject("SKIN");
				if (skin.has("metadata")) {
					return skin.getAsJsonObject("metadata").get("model").getAsString();
				}
			} catch (Exception e) {
				LOGGER.error("Failed to parse skin model", e);
			}
		}
		return "default";
	}

	private static void applySkinChange(ServerPlayer player, String textureUrl, String model) {
		com.mojang.authlib.GameProfile profile = player.getGameProfile();

		// Remove existing textures property
		profile.getProperties().removeAll("textures");

		// Create new texture data
		com.google.gson.JsonObject texturesObj = new com.google.gson.JsonObject();
		com.google.gson.JsonObject skinObj = new com.google.gson.JsonObject();
		skinObj.addProperty("url", textureUrl);

		if ("slim".equalsIgnoreCase(model)) {
			com.google.gson.JsonObject metadata = new com.google.gson.JsonObject();
			metadata.addProperty("model", "slim");
			skinObj.add("metadata", metadata);
		}

		com.google.gson.JsonObject texturesRoot = new com.google.gson.JsonObject();
		texturesRoot.add("SKIN", skinObj);
		texturesObj.add("textures", texturesRoot);
		texturesObj.addProperty("timestamp", System.currentTimeMillis());
		texturesObj.addProperty("profileId", profile.getId().toString().replace("-", ""));
		texturesObj.addProperty("profileName", profile.getName());

		// Encode to base64
		String encoded = java.util.Base64.getEncoder().encodeToString(
				new com.google.gson.Gson().toJson(texturesObj).getBytes(java.nio.charset.StandardCharsets.UTF_8));

		// Add new property
		profile.getProperties().put("textures", new com.mojang.authlib.properties.Property("textures", encoded));

		// Refresh player for all clients
		refreshPlayerSkin(player);
	}

	private static void resetPlayerSkin(ServerPlayer player) {
		// This would typically involve fetching the original skin from Mojang's servers
		// For now, we'll trigger a profile refresh
		com.mojang.authlib.GameProfile profile = player.getGameProfile();

		// Remove custom textures
		profile.getProperties().removeAll("textures");

		// In a full implementation, you'd want to:
		// 1. Fetch the original skin from Mojang's session server
		// 2. Cache original skins before modifications
		// For now, this removes custom skins and may show Steve/Alex

		refreshPlayerSkin(player);
	}

	private static void copySkinFromPlayer(ServerPlayer player, String targetPlayerName) {
		net.minecraft.server.MinecraftServer server = player.getServer();
		if (server != null) {
			ServerPlayer targetPlayer = server.getPlayerList().getPlayerByName(targetPlayerName);
			if (targetPlayer != null) {
				com.mojang.authlib.GameProfile targetProfile = targetPlayer.getGameProfile();
				com.mojang.authlib.GameProfile playerProfile = player.getGameProfile();

				// Copy textures property from target to player
				playerProfile.getProperties().removeAll("textures");
				targetProfile.getProperties().get("textures").forEach(property ->
						playerProfile.getProperties().put("textures", property));

				refreshPlayerSkin(player);
			} else {
				LOGGER.warn("Target player not found: " + targetPlayerName);
			}
		}
	}

	private static void refreshPlayerSkin(ServerPlayer player) {
		// Method 1: Remove and re-add player to force skin refresh
		net.minecraft.server.MinecraftServer server = player.getServer();
		if (server != null) {
			// Send player info update packet to all clients
			net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket packet =
					new net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket(
							net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LISTED,
							player);

			for (ServerPlayer serverPlayer : server.getPlayerList().getPlayers()) {
				serverPlayer.connection.send(packet);
			}

			// Respawn player to force full refresh
			player.connection.send(new net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket(
					java.util.List.of(player.getUUID())));
			player.connection.send(new net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket(
					net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER,
					player));
		}
	}

	private static double getTPS(net.minecraft.server.MinecraftServer server) {
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