/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.subsystems;

import com.code.tama.tts.server.registries.forge.TTSBlocks;
import lombok.NoArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

@NoArgsConstructor
public class DematerializationCircuit extends AbstractSubsystem {
	private static Map<BlockPos, BlockState> Map = null;

	public DematerializationCircuit(BlockPos blockPos, boolean Activated) {
		super(Activated, blockPos);
	}

	@Override
	public Map<BlockPos, BlockState> BlockMap() {
//		Map<BlockPos, BlockState> map = new java.util.HashMap<>(Map.of());
//		map.put(BlockPos.ZERO.below(), Blocks.GOLD_BLOCK.defaultBlockState());
//		map.put(BlockPos.ZERO.below().south(), Blocks.REDSTONE_BLOCK.defaultBlockState());
//		map.put(BlockPos.ZERO.below().west(), Blocks.REDSTONE_BLOCK.defaultBlockState());
//		map.put(BlockPos.ZERO.below().east(), Blocks.REDSTONE_BLOCK.defaultBlockState());
//		map.put(BlockPos.ZERO.below().north(), Blocks.LAPIS_BLOCK.defaultBlockState());
//		map.put(BlockPos.ZERO.below().north().east(), Blocks.COPPER_BLOCK.defaultBlockState());
//		map.put(BlockPos.ZERO.below().north().west(), Blocks.COPPER_BLOCK.defaultBlockState());
//		map.put(BlockPos.ZERO.below().south().east(), Blocks.COPPER_BLOCK.defaultBlockState());
//		map.put(BlockPos.ZERO.below().south().east(), Blocks.COPPER_BLOCK.defaultBlockState());
//
//		map.put(BlockPos.ZERO.south(), Blocks.IRON_TRAPDOOR.defaultBlockState());
//		map.put(BlockPos.ZERO.west(), Blocks.IRON_TRAPDOOR.defaultBlockState());
//		map.put(BlockPos.ZERO.east(), Blocks.IRON_TRAPDOOR.defaultBlockState());
//		map.put(BlockPos.ZERO.north().east(), Blocks.IRON_BLOCK.defaultBlockState());
//		map.put(BlockPos.ZERO.north().west(), Blocks.IRON_BLOCK.defaultBlockState());
//		map.put(BlockPos.ZERO.south().east(), Blocks.IRON_BLOCK.defaultBlockState());
//		map.put(BlockPos.ZERO.south().east(), Blocks.IRON_BLOCK.defaultBlockState());
//
//		map.put(BlockPos.ZERO.above(), Blocks.REDSTONE_BLOCK.defaultBlockState());
//		map.put(BlockPos.ZERO.above().south(), Blocks.REDSTONE_LAMP.defaultBlockState());
//		map.put(BlockPos.ZERO.above().west(), Blocks.REDSTONE_LAMP.defaultBlockState());
//		map.put(BlockPos.ZERO.above().east(), Blocks.REDSTONE_LAMP.defaultBlockState());
//		map.put(BlockPos.ZERO.above().north(), Blocks.REDSTONE_LAMP.defaultBlockState());
//		map.put(BlockPos.ZERO.above().north().east(), Blocks.COPPER_BLOCK.defaultBlockState());
//		map.put(BlockPos.ZERO.above().north().west(), Blocks.COPPER_BLOCK.defaultBlockState());
//		map.put(BlockPos.ZERO.above().south().east(), Blocks.COPPER_BLOCK.defaultBlockState());
//		map.put(BlockPos.ZERO.above().south().east(), Blocks.COPPER_BLOCK.defaultBlockState());

		if (Map == null || Map.isEmpty()) {
			HashMap<Character, BlockState> key = new HashMap<>();
			String map[] = new String[]{"CLC\nLRL\nCLC", "I I\nT#T\nITI", "CKC\nRGR\nCRC"};

			key.put('C', Blocks.COPPER_BLOCK.defaultBlockState());
			key.put('L', Blocks.REDSTONE_LAMP.defaultBlockState());
			key.put('R', Blocks.REDSTONE_BLOCK.defaultBlockState());
			key.put('I', Blocks.IRON_BLOCK.defaultBlockState());
			key.put('T', Blocks.IRON_TRAPDOOR.defaultBlockState());
			key.put('G', Blocks.GOLD_BLOCK.defaultBlockState());
			key.put('K', Blocks.LAPIS_BLOCK.defaultBlockState());
			key.put('#', TTSBlocks.DEMATERIALIZATION_CIRCUIT_CORE.get().defaultBlockState());

			Map = new SubsystemMapRecipeThing(map, key).getMap();
		}

		return Map;

//		return map;
	}

	@Override
	public void OnActivate(Level level, BlockPos blockPos) {
		this.Activated = true;
		GetTARDISCapSupplier(level)
				.ifPresent(cap -> cap.GetData().getSubSystemsData().setDematerializationCircuit(this));
	}

	@Override
	public void OnDeActivate(Level level, BlockPos blockPos) {
		this.Activated = false;
		GetTARDISCapSupplier(level)
				.ifPresent(cap -> cap.GetData().getSubSystemsData().setDematerializationCircuit(this));
	}

	@Override
	public String name() {
		return "dematerialization_circuit";
	}
}
