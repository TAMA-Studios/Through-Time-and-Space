/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks;

import com.code.tama.tts.server.items.SonicItem;
import com.code.tama.tts.server.items.core.NozzleItem;
import com.code.tama.tts.server.registries.RecipeRegistry;
import com.code.tama.tts.server.registries.TTSTileEntities;
import com.code.tama.tts.server.tileentities.WorkbenchTile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class WorkbenchBlock extends Block implements EntityBlock {
    int size = 0;

    public WorkbenchBlock() {
        super(Properties.of().strength(3.0F));
    }

    public void tickFunction(BlockState p_225534_1_, ServerLevel p_225534_2_, BlockPos p_225534_3_, int timer) {}

    @Override
    public void onRemove(
            BlockState p_196243_1_,
            Level p_196243_2_,
            BlockPos p_196243_3_,
            BlockState p_196243_4_,
            boolean p_196243_5_) {
        BlockEntity tile = p_196243_2_.getBlockEntity(p_196243_3_);
        if ((WorkbenchTile) tile != null) {
            if (tile instanceof WorkbenchTile) {
                ((WorkbenchTile) tile).StoredItems.clear();
                tile.setRemoved();
            }
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return TTSTileEntities.WORKBENCH_TILE.get().create(blockPos, blockState);
    }

    @NotNull
    @Override
    public InteractionResult use(
            @NotNull BlockState p_225533_1_,
            @NotNull Level level,
            @NotNull BlockPos blockPos,
            @NotNull Player player,
            @NotNull InteractionHand interactionHand,
            @NotNull BlockHitResult blockHitResult) {
        BlockEntity tile = level.getBlockEntity(blockPos);
        if (tile != null && tile instanceof WorkbenchTile workbenchTile) {
            if (interactionHand == InteractionHand.OFF_HAND) return InteractionResult.PASS;
            // If player's holding a sonic, it should attempt to craft the result
            if (player.getMainHandItem().getItem() instanceof SonicItem) {
                //                if(RecipeRegistry.RECIPES.contains())
                Item result = RecipeRegistry.GetRecipeResult(
                        !workbenchTile.StoredItems.isEmpty() ? workbenchTile.StoredItems.get(0) : Items.AIR,
                        workbenchTile.StoredItems.size() > 1 ? workbenchTile.StoredItems.get(1) : Items.AIR,
                        workbenchTile.StoredItems.size() > 2 ? workbenchTile.StoredItems.get(2) : Items.AIR,
                        workbenchTile.StoredItems.size() > 3 ? workbenchTile.StoredItems.get(3) : Items.AIR,
                        workbenchTile.StoredItems.size() > 4 ? workbenchTile.StoredItems.get(4) : Items.AIR,
                        workbenchTile.StoredItems.size() > 5 ? workbenchTile.StoredItems.get(5) : Items.AIR,
                        workbenchTile.nozzle != null ? workbenchTile.nozzle : Items.AIR);
                if (result != Items.AIR) {
                    assert result != null;
                    player.getInventory().add(result.getDefaultInstance());
                    workbenchTile.StoredItems.clear();
                    return InteractionResult.CONSUME;
                }
                return InteractionResult.PASS;
            }

            // If player isn't holding sonic, and the workbench isn't full, (and they're not holding a nozzle) add the
            // players mainhand item
            else if (workbenchTile.StoredItems.size() < 6
                    && !(player.getMainHandItem().getItem() instanceof NozzleItem)) {
                if (!player.getMainHandItem().isEmpty()
                        && player.getMainHandItem().getItem() != Items.AIR) {
                    workbenchTile.StoredItems.add(player.getMainHandItem().getItem());
                    player.getMainHandItem().shrink(1);
                    return InteractionResult.CONSUME;
                }
            }

            // If the player is crouching, and holding a nozzleitem, attempt to add it to the workbench
            if (player.getMainHandItem().getItem() instanceof NozzleItem nozzleItem) {
                if (workbenchTile.nozzle != null && workbenchTile.nozzle != Items.AIR)
                    player.getInventory().add(workbenchTile.nozzle.getDefaultInstance());
                workbenchTile.nozzle = nozzleItem;
                player.getMainHandItem().shrink(1);
                return InteractionResult.PASS;
            }

            //
            //            if (tile instanceof WorkbenchTile) {
            //                if (!((WorkbenchTile) tile).StoredItems.isEmpty())
            //                    for (int i = 0; i < ((WorkbenchTile) tile).StoredItems.size(); i++) {
            //                        if (!((WorkbenchTile) tile).StoredItems.get(i).equals(Items.AIR)) this.size++;
            //                    }
            //                if (!player.isCrouching()
            //                        && !player.getMainHandItem().isEmpty()
            //                        && player.getMainHandItem().getItem() != Items.AIR) {
            //                    if (this.size < 4) {
            //                        ((WorkbenchTile) tile)
            //                                .StoredItems.add(player.getMainHandItem().getItem());
            //                        player.getMainHandItem().shrink(1);
            //                        return InteractionResult.CONSUME;
            //                    }
            //                }
            //                if (player.isCrouching() && !((WorkbenchTile) tile).StoredItems.isEmpty()) {
            //                    ArrayList<Items> itemsArrayList = new ArrayList<>();
            //                    //                    for (int i = 0; i < 3; i++) {
            //                    //                        if (((WorkbenchTile) tile).StoredItems.get(i) == null) {
            //                    //                            ((WorkbenchTile) tile).StoredItems.add(i, Items.AIR);
            //                    //                        }
            //                    //                    }
            //                    for (int i = 0; i < 4; i++) {
            //                        if (((WorkbenchTile) tile).StoredItems.size() < 4)
            //                            ((WorkbenchTile) tile).StoredItems.add(Items.AIR);
            //                    }
            //                    if (TTSMod.WorkBenchRecipeHandler.IsValidRecipeFromArrayList(((WorkbenchTile)
            // tile).StoredItems)) {
            //                        player.inventoryMenu
            //                                .getItems()
            //                                .add(TTSMod.WorkBenchRecipeHandler.GetRecipeResultFromArrayList(
            //                                                ((WorkbenchTile) tile).StoredItems)
            //                                        .getDefaultInstance());
            //
            //                        ((WorkbenchTile) tile).StoredItems.clear();
            //                        this.size = 0;
            //                        return InteractionResult.CONSUME;
            //                    }
            //                }
            //            }
        }
        return InteractionResult.PASS;
    }
}
