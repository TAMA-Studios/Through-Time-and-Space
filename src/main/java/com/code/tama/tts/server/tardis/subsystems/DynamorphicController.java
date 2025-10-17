/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.subsystems;

import java.util.HashMap;
import java.util.Map;

import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.registries.forge.TTSBlocks;
import lombok.NoArgsConstructor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

@NoArgsConstructor
public class DynamorphicController extends AbstractSubsystem {

	private static Map<BlockPos, BlockState> Map = null;

	public DynamorphicController(BlockPos blockPos, boolean Activated) {
		super(blockPos, Activated);
	}

	@Override
	public Map<BlockPos, BlockState> BlockMap() {

		// cssc
		// s s
		// s s
		// cssc

		// s s
		// ge
		// eg
		// s s

		// s s
		// eg
		// ge
		// s s

		// cssc
		// s s
		// s s
		// cssc

		if (Map == null || Map.isEmpty()) {
			HashMap<Character, BlockState> key = new HashMap<>();
			String map[] = new String[]{"   \n # \n   ", "MNM\nNMN\nMNM"};

			key.put('M', Blocks.COPPER_BLOCK.defaultBlockState());
			key.put('N', Blocks.IRON_BARS.defaultBlockState());
			key.put('#', TTSBlocks.TARDIS_ENGINES.get().defaultBlockState());

			Map = new SubsystemMapRecipeThing(map, key).getMap();
		}

		return Map;
	}

	@Override
	public void OnActivate(Level level, BlockPos blockPos) {
		this.Activated = true;
		level.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY)
				.ifPresent(cap -> cap.GetData().getSubSystemsData().setDynamorphicController(this));
	}

	@Override
	public void OnDeActivate(Level level, BlockPos blockPos) {
		this.Activated = false;
		level.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY)
				.ifPresent(cap -> cap.GetData().getSubSystemsData().setDynamorphicController(this));
	}

	@Override
	public String name() {
		return "dynamorphic_controller";
	}
}
