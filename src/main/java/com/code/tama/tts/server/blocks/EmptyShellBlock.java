package com.code.tama.tts.server.blocks;

import com.code.tama.tts.server.registries.forge.TTSBlocks;
import com.code.tama.tts.server.registries.forge.TTSItems;
import com.code.tama.tts.server.registries.forge.TTSTileEntities;
import com.code.tama.tts.server.tardis.ExteriorState;
import com.code.tama.tts.server.tileentities.EmptyArtificialShellTile;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class EmptyShellBlock extends Block implements EntityBlock {

    public EmptyShellBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return TTSTileEntities.EMPTY_SHELL.get().create(p_153215_, p_153216_);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult hit) {
        if (level.getBlockEntity(blockPos) != null
                && level.getBlockEntity(blockPos) instanceof EmptyArtificialShellTile shell) {
            if (shell.getWeldProgress() != 100) {
                ItemStack used = player.getItemInHand(interactionHand);
                if (used.getItem().equals(TTSItems.GROWTH_CAKE.get())) {
                    used.shrink(1);
                    shell.ShouldMakeExt = true;
                }

                if (used.getItem().equals(TTSItems.STRUCTURAL_BEAMS.get()) && shell.StructuralBeams < 4) {
                    shell.StructuralBeams++;
                    used.shrink(1);
                }

                if (used.getItem().equals(TTSItems.PLASMIC_SHELL_PLATING.get())
                        && shell.PlasmicShellPlates < 5) {
                    // Make sure you have more structural beams than you do plasmic shell plates
                    if (shell.StructuralBeams - shell.PlasmicShellPlates >= 1) {
                        shell.PlasmicShellPlates++;
                        used.shrink(1);
                    }
                }

                if (shell.PlasmicShellPlates == 4 && shell.StructuralBeams == 4
                        && shell.getWeldProgress() == 100) {
                    shell.ShouldMakeExt = true;
                }

                System.out.printf("Beams: %s, Plates: %s, Weld: %s%n", shell.StructuralBeams,
                        shell.PlasmicShellPlates, shell.getWeldProgress());

                return InteractionResult.SUCCESS;
            }
            else if(shell.getWeldProgress() >= 100) shell.ShouldMakeExt = true;

            if(shell.ShouldMakeExt) {
                level.removeBlock(blockPos, false);
                level.removeBlockEntity(blockPos);

                BlockState state1 = TTSBlocks.EXTERIOR_BLOCK.get().defaultBlockState();
                ExteriorTile tile = TTSTileEntities.EXTERIOR_TILE.create(blockPos, state1);

                tile.PlacerName = player.getName().getString();
                tile.PlacerUUID = player.getUUID();
                tile.state = ExteriorState.LANDED;
                tile.isArtificial = true;

                level.setBlockAndUpdate(blockPos, state1);
                level.setBlockEntity(tile);

                tile.ShouldMakeDimOnNextTick = true;
            }
        }
        return super.use(state, level, blockPos, player, interactionHand, hit);
    }
}
