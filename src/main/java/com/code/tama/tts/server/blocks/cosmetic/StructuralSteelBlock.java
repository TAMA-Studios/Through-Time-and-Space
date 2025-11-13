/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.cosmetic;

import com.code.tama.tts.server.blocks.core.WeatheringSteel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

public class StructuralSteelBlock extends Block implements WeatheringSteel {
	private final WeatheringSteel.WeatherState weatherState;

	public StructuralSteelBlock(WeatheringSteel.WeatherState p_154925_, Properties properties) {
		super(properties.mapColor(MapColor.COLOR_LIGHT_GRAY).strength(5.0F, 6.0F).noOcclusion()
				.sound(SoundType.METAL));
		this.weatherState = p_154925_;
	}

	public WeatheringSteel.@NotNull WeatherState getAge() {
		return this.weatherState;
	}

	public boolean isRandomlyTicking(BlockState state) {
		return WeatheringSteel.nextExists(state.getBlock());
	}

	@SuppressWarnings("deprecation")
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos,
			@NotNull RandomSource source) {
		this.onRandomTick(state, level, pos, source);
	}
}
