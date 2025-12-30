/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.subsystems;

import java.util.HashMap;
import java.util.Map;

import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.registries.forge.TTSBlocks;
import lombok.NoArgsConstructor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@NoArgsConstructor
public class DynamorphicGeneratorStack extends AbstractSubsystem {

	public DynamorphicGeneratorStack(BlockPos blockPos, boolean Activated) {
		super(Activated, blockPos);
	}

	private static Map<BlockPos, BlockState> Map = null;

	@Override
	public Map<BlockPos, BlockState> BlockMap() {

		if (Map == null || Map.isEmpty()) {
			HashMap<Character, BlockState> key = new HashMap<>();
			String map[] = new String[]{" Z \nZZZ\n Z ", "ZBZ\nBZB\nZBZ", "B B\n # \nB B", "ZBZ\nBZB\nZBZ"};

			key.put('Z', TTSBlocks.ZEITON_BLOCK.get().defaultBlockState());
			key.put('B', TTSBlocks.BRUSHED_STEEL.get().defaultBlockState());
			key.put('#', TTSBlocks.DYNAMORPHIC_GENERATOR_STACK.get().defaultBlockState());

			Map = new SubsystemMapRecipeThing(map, key).getMap();
		}

		return Map;
	}

	@Override
	public void OnActivate(Level level, BlockPos blockPos) {
		TARDISLevelCapability.GetTARDISCapSupplier(level)
				.ifPresent(cap -> cap.GetData().getSubSystemsData().getDynamorphicGeneratorStacks().add(this));
	}

	@Override
	public void OnDeActivate(Level level, BlockPos blockPos) {
		TARDISLevelCapability.GetTARDISCapSupplier(level)
				.ifPresent(cap -> cap.GetData().getSubSystemsData().getDynamorphicGeneratorStacks().remove(this));
	}

	@Override
	public String name() {
		return "dynamorphic_generator_stack";
	}
}
