/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.subsystems;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

import java.util.HashMap;
import java.util.Map;

import com.code.tama.tts.server.registries.forge.TTSBlocks;
import lombok.NoArgsConstructor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@NoArgsConstructor
public class DynamorphicController extends AbstractSubsystem {

	private static Map<BlockPos, BlockState> Map = null;

	public DynamorphicController(BlockPos blockPos, boolean Activated) {
		super(Activated, blockPos);
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
			String map[] = new String[]{" Z \nZZZ\n Z ", "ZBZ\nBZB\nZBZ", "B B\n # \nB B", "ZBZ\nBZB\nZBZ"};

			key.put('Z', TTSBlocks.ZEITON_BLOCK.get().defaultBlockState());
			key.put('B', TTSBlocks.BRUSHED_STEEL.get().defaultBlockState());
			key.put('#', TTSBlocks.DYNAMORPHIC_CONTROLLER_CORE.get().defaultBlockState());

			Map = new SubsystemMapRecipeThing(map, key).getMap();
		}

		return Map;
	}

	@Override
	public void OnActivate(Level level, BlockPos blockPos) {
		this.Activated = true;
		GetTARDISCapSupplier(level).ifPresent(cap -> cap.GetData().getSubSystemsData().setDynamorphicController(this));
	}

	@Override
	public void OnDeActivate(Level level, BlockPos blockPos) {
		this.Activated = false;
		GetTARDISCapSupplier(level).ifPresent(cap -> cap.GetData().getSubSystemsData().setDynamorphicController(this));
	}

	@Override
	public String name() {
		return "dynamorphic_controller";
	}
}
