/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.subsystems;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

import java.util.HashMap;
import java.util.Map;

import com.code.tama.tts.core.registries.forge.TTSBlocks;
import lombok.NoArgsConstructor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@NoArgsConstructor
public class OxygenatorCircuit extends AbstractSubsystem {
	private static Map<BlockPos, BlockState> Map = null;

	public OxygenatorCircuit(BlockPos blockPos, boolean Activated) {
		super(Activated, blockPos);
	}

	@Override
	public Map<BlockPos, BlockState> BlockMap() {
		if (Map == null || Map.isEmpty()) {
			HashMap<Character, BlockState> key = new HashMap<>();
			String map[] = new String[]{"\n#\n", "\nP\n"};

			key.put('P', TTSBlocks.TARDIS_ENERGY_PORT.getDefaultState());
			key.put('#', TTSBlocks.OXYGENATOR_CIRCUIT_CORE.getDefaultState());

			Map = new SubsystemMapRecipeThing(map, key).getMap();
		}

		return Map;

		// return map;
	}

	@Override
	public boolean isActivated() {
		return super.isActivated();
	}

	@Override
	public void OnActivate(Level level, BlockPos blockPos) {
		this.Activated = true;
		GetTARDISCapSupplier(level).ifPresent(cap -> cap.GetEnvironmentalData().setOxygenLevel(20));
	}

	@Override
	public void OnDeActivate(Level level, BlockPos blockPos) {
		this.Activated = false;
		GetTARDISCapSupplier(level).ifPresent(cap -> cap.GetEnvironmentalData().setOxygenLevel(0));
	}

	@Override
	public String name() {
		return "dematerialization_circuit";
	}
}
