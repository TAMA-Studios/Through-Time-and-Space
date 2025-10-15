/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.cosmetic;

import com.code.tama.tts.server.blocks.core.WeatheringSteel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class StructuralSteelBlock extends Block implements WeatheringSteel {
    private final WeatheringSteel.WeatherState weatherState;

    public StructuralSteelBlock(WeatheringSteel.WeatherState p_154925_, BlockBehaviour.Properties p_154926_) {
        super(p_154926_);
        this.weatherState = p_154925_;
    }

    @SuppressWarnings("deprecation")
    public void randomTick(
            @NotNull BlockState state,
            @NotNull ServerLevel level,
            @NotNull BlockPos pos,
            @NotNull RandomSource source) {
        this.onRandomTick(state, level, pos, source);
    }

    public boolean isRandomlyTicking(BlockState state) {
        return WeatheringSteel.nextExists(state.getBlock());
    }

    public WeatheringSteel.@NotNull WeatherState getAge() {
        return this.weatherState;
    }
}
