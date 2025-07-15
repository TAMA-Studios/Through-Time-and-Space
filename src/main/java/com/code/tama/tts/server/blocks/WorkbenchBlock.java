package com.code.tama.tts.server.blocks;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.server.registries.TTSTileEntities;
import com.code.tama.tts.server.tileentities.WorkbenchTile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class WorkbenchBlock extends Block implements EntityBlock {
    int size = 0;

    public WorkbenchBlock() {
        super(Properties.of().strength(3.0F));
    }

    public void tickFunction(BlockState p_225534_1_, ServerLevel p_225534_2_, BlockPos p_225534_3_, int timer) {

    }

    @Override
    public void onRemove(BlockState p_196243_1_, Level p_196243_2_, BlockPos p_196243_3_, BlockState p_196243_4_, boolean p_196243_5_) {
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
    public InteractionResult use(@NotNull BlockState p_225533_1_, @NotNull Level p_225533_2_, @NotNull BlockPos p_225533_3_, @NotNull Player player, @NotNull InteractionHand p_225533_5_, @NotNull BlockHitResult p_225533_6_) {
        BlockEntity tile = p_225533_2_.getBlockEntity(p_225533_3_);
        if ((WorkbenchTile) tile != null) {

            if (tile instanceof WorkbenchTile) {
                if (!((WorkbenchTile) tile).StoredItems.isEmpty())
                    for (int i = 0; i < ((WorkbenchTile) tile).StoredItems.size(); i++) {
                        if (!((WorkbenchTile) tile).StoredItems.get(i).equals(Items.AIR))
                            this.size++;
                    }
                if (!player.isCrouching() && !player.getMainHandItem().isEmpty() && player.getMainHandItem().getItem() != Items.AIR) {
                    if (this.size < 4) {
                        ((WorkbenchTile) tile).StoredItems.add(player.getMainHandItem().getItem());
                        player.getMainHandItem().shrink(1);
                        return InteractionResult.CONSUME;
                    }
                }
                if (player.isCrouching() && !((WorkbenchTile) tile).StoredItems.isEmpty()) {
                    ArrayList<Items> itemsArrayList = new ArrayList<>();
//                    for (int i = 0; i < 3; i++) {
//                        if (((WorkbenchTile) tile).StoredItems.get(i) == null) {
//                            ((WorkbenchTile) tile).StoredItems.add(i, Items.AIR);
//                        }
//                    }
                    for(int i = 0; i < 4; i++){
                        if(((WorkbenchTile) tile).StoredItems.size() < 4) ((WorkbenchTile) tile).StoredItems.add(Items.AIR);
                    }
                    if (TTSMod.WorkBenchRecipeHandler.IsValidRecipeFromArrayList(((WorkbenchTile) tile).StoredItems)) {
                        player.inventoryMenu.getItems().add(TTSMod.WorkBenchRecipeHandler.GetRecipeResultFromArrayList(((WorkbenchTile) tile).StoredItems).getDefaultInstance());

                        ((WorkbenchTile) tile).StoredItems.clear();
                        this.size = 0;
                        return InteractionResult.CONSUME;
                    }
                }
            }


        }
        return InteractionResult.FAIL;
    }
}