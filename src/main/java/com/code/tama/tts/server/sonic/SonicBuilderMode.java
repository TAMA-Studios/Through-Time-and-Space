/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.sonic;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

import javax.annotation.Nullable;
import java.util.Collection;

public class SonicBuilderMode extends SonicMode {

    @Override
    public Item getIcon() {
        return Items.LEVER;
    }

    @Override
    public String getName() {
        return "builder_mode";
    }

    @Override
    public void onUse(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        if (!level.isClientSide && player != null) {
            BlockPos blockpos = context.getClickedPos();
            if (!this.handleInteraction(
                    player,
                    level.getBlockState(blockpos),
                    level,
                    blockpos,
                    true,
                    context.getItemInHand(),
                    "item.tts.sonic_builder")) {
                return;
            }
        }
    }

    public boolean handleInteraction(
            Player p_150803_,
            BlockState p_150804_,
            LevelAccessor p_150805_,
            BlockPos p_150806_,
            boolean p_150807_,
            ItemStack p_150808_,
            String descID) {
        Block block = p_150804_.getBlock();
        StateDefinition<Block, BlockState> statedefinition = block.getStateDefinition();
        Collection<Property<?>> collection = statedefinition.getProperties();
        String s = BuiltInRegistries.BLOCK.getKey(block).toString();
        if (collection.isEmpty()) {
            message(p_150803_, Component.translatable(descID + ".empty", s));
            return false;
        } else {
            CompoundTag compoundtag = p_150808_.getOrCreateTagElement("DebugProperty");
            String s1 = compoundtag.getString(s);
            Property<?> property = statedefinition.getProperty(s1);
            if (p_150807_) {
                if (property == null) {
                    property = collection.iterator().next();
                }

                BlockState blockstate = cycleState(p_150804_, property, p_150803_.isSecondaryUseActive());
                p_150805_.setBlock(p_150806_, blockstate, 18);
                message(
                        p_150803_,
                        Component.translatable(
                                descID + ".update", property.getName(), getNameHelper(blockstate, property)));
            } else {
                property = getRelative(collection, property, p_150803_.isSecondaryUseActive());
                String s2 = property.getName();
                compoundtag.putString(s, s2);
                message(p_150803_, Component.translatable(descID + ".select", s2, getNameHelper(p_150804_, property)));
            }

            return true;
        }
    }

    private static <T extends Comparable<T>> BlockState cycleState(
            BlockState p_40970_, Property<T> p_40971_, boolean p_40972_) {
        return p_40970_.setValue(
                p_40971_, getRelative(p_40971_.getPossibleValues(), p_40970_.getValue(p_40971_), p_40972_));
    }

    private static <T> T getRelative(Iterable<T> p_40974_, @Nullable T p_40975_, boolean p_40976_) {
        return (T)
                (p_40976_
                        ? Util.findPreviousInIterable(p_40974_, p_40975_)
                        : Util.findNextInIterable(p_40974_, p_40975_));
    }

    private static void message(Player p_40957_, Component p_40958_) {
        p_40957_.sendSystemMessage(p_40958_);
    }

    private static <T extends Comparable<T>> String getNameHelper(BlockState p_40967_, Property<T> p_40968_) {
        return p_40968_.getName(p_40967_.getValue(p_40968_));
    }
}
