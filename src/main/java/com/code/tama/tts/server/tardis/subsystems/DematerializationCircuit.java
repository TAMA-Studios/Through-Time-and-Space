/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.subsystems;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

import java.util.HashMap;
import java.util.Map;

import com.code.tama.tts.core.registries.forge.TTSBlocks;
import lombok.NoArgsConstructor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

@NoArgsConstructor
public class DematerializationCircuit extends AbstractSubsystem {
	private static Map<BlockPos, BlockState> Map = null;

	public DematerializationCircuit(BlockPos blockPos, boolean Activated) {
		super(Activated, blockPos);
	}

	public static Map<BlockPos, BlockState> GetOrCreateMap() {
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
	}

	@Override
	public Map<BlockPos, BlockState> BlockMap() {
		return GetOrCreateMap();
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
