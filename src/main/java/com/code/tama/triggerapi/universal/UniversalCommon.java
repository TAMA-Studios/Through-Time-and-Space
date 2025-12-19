/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.universal;

import java.util.function.Supplier;

import org.joml.Vector3d;
import org.joml.Vector3f;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import com.code.tama.triggerapi.TriggerAPI;
import com.code.tama.triggerapi.networking.ImAPacket;

public class UniversalCommon {

	/**
	 * @return A new ResourceLocation with namespace "minecraft"
	 */
	public static ResourceLocation newRL(String path) {
		return new ResourceLocation(path);
	}

	public static ResourceLocation parse(String path) {
		return ResourceLocation.tryParse(path);
	}

	/**
	 * @return A new ResourceLocation
	 */
	public static ResourceLocation newRL(String namespace, String path) {
		return new ResourceLocation(path);
	}

	/**
	 * @return A new ResourceLocation with namespace being your mod id
	 */
	public static ResourceLocation modRL(String path) {
		return new ResourceLocation(TriggerAPI.getModId(), path);
	}

	public static class Pos {
		public static int x(BlockPos pos) {
			return pos.getX();
		}
		public static int y(BlockPos pos) {
			return pos.getY();
		}
		public static int z(BlockPos pos) {
			return pos.getZ();
		}

		public static double x(Vec3 pos) {
			return pos.x();
		}
		public static double y(Vec3 pos) {
			return pos.y();
		}
		public static double z(Vec3 pos) {
			return pos.z();
		}

		public static int x(Vec3i pos) {
			return pos.getZ();
		}
		public static int y(Vec3i pos) {
			return pos.getZ();
		}
		public static int z(Vec3i pos) {
			return pos.getZ();
		}

		public static float x(Vector3f pos) {
			return pos.x();
		}
		public static float y(Vector3f pos) {
			return pos.y();
		}
		public static float z(Vector3f pos) {
			return pos.z();
		}

		public static double x(Vector3d pos) {
			return pos.x();
		}
		public static double y(Vector3d pos) {
			return pos.y();
		}
		public static double z(Vector3d pos) {
			return pos.z();
		}
	}

	public static class Level {
		public BlockState getState(net.minecraft.world.level.Level world, BlockPos pos) {
			return world.getBlockState(pos);
		}
	}

	public static class Networking {
		private static int ID = 0;
		private static final String PROTOCOL = "1";

		private static final SimpleChannel Network = NetworkRegistry.ChannelBuilder
				.named(new ResourceLocation(TriggerAPI.getModId(), "main")).clientAcceptedVersions(PROTOCOL::equals)
				.serverAcceptedVersions(PROTOCOL::equals).networkProtocolVersion(() -> PROTOCOL).simpleChannel();

		public static SimpleChannel getInstance() {
			return Network;
		}

		@SuppressWarnings("unchecked")
		public static <MSG extends ImAPacket> void registerMsg(Class<MSG> packet) {
			register((Class<Object>) (Object) packet); // This works by casting packet into an object (which it is) and
														// casting that to Class<Object>, Prob shouldn't do this.
		}

		private static void register(Class<Object> packet) {
			getInstance().registerMessage(com.code.tama.tts.server.networking.Networking.id(), packet, (msg, buf) -> {
				try {
					packet.getMethod("encode", packet, FriendlyByteBuf.class).invoke(null, msg, buf);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}, (buf) -> {
				try {
					return packet.getMethod("decode", FriendlyByteBuf.class).invoke(null, buf);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}, (msg, ctx) -> {
				try {
					packet.getMethod("handle", packet, Supplier.class).invoke(null, msg, ctx);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});
		}

	}
}
