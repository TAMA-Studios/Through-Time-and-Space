/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.subsystems;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import com.code.tama.tts.server.tardis.subsystems.AbstractSubsystem;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class AbstractSubsystemBlock extends Block {
    @Getter
    private AbstractSubsystem subsystem;

    public AbstractSubsystemBlock(Properties p_49795_, AbstractSubsystem subsystem) {
        super(p_49795_);
        this.subsystem = subsystem;
    }

    public abstract void OnActivate(Level level, BlockPos blockPos);

    /** Called when the subsystem is tied into the engine block via fragment links
     * @param blockPos The position of the subsystem block being integrated, used for verifying that it in indeed a valid multiblock structure
     **/
    public void OnIntegration(Level level, BlockPos blockPos) {
        if(this.subsystem.IsValid(level, blockPos)) this.subsystem.OnActivate(level, blockPos);
    }
}