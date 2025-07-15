/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.items;

import com.code.tama.tts.server.capabilities.CapabilityConstants;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.enums.SonicInteractionType;
import com.code.tama.tts.server.misc.sonic.SonicMode;
import com.code.tama.tts.server.registries.SonicModeRegistry;
import com.code.tama.tts.server.registries.TTSBlocks;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SonicItem extends Item {
    public SonicMode InteractionType = SonicModeRegistry.BLOCKS.get();

    public SonicItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(
            Level level, Player player, InteractionHand interactionHand) {

        return super.use(level, player, interactionHand);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext useOnContext) {

        return InteractionResult.SUCCESS;
    }

    public InteractionResult BlockInteraction(@NotNull UseOnContext useOnContext) {
        BlockState State = useOnContext.getLevel().getBlockState(useOnContext.getClickedPos());
        Level Level = useOnContext.getLevel();
        BlockPos Pos = useOnContext.getClickedPos();

        if (State.getBlock().equals(TTSBlocks.EXTERIOR_BLOCK.get())) {
            if (Level.getBlockEntity(Pos) instanceof ExteriorTile exteriorTile) {
                if (exteriorTile.GetInterior() != null)
                    ServerLifecycleHooks.getCurrentServer()
                            .getLevel(exteriorTile.GetInterior())
                            .getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY)
                            .ifPresent(ITARDISLevel::Dematerialize);
            }
        }

        if (State.getBlock().equals(Blocks.IRON_DOOR)) {
            State = State.cycle(DoorBlock.OPEN);
            Level.setBlockAndUpdate(Pos, State);
            this.playDoorSound(
                    useOnContext.getPlayer(), Level, Pos, State.getValue(BlockStateProperties.OPEN), (DoorBlock)
                            State.getBlock());
            Level.gameEvent(
                    useOnContext.getPlayer(),
                    ((DoorBlock) State.getBlock()).isOpen(State) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE,
                    Pos);
            return InteractionResult.SUCCESS;
        }

        if (State.getBlock() instanceof RedstoneLampBlock) {
            if (!Level.isClientSide) Level.setBlockAndUpdate(Pos, State.cycle(RedstoneLampBlock.LIT));
            return InteractionResult.SUCCESS;
        }

        if (State.getBlock().equals(Blocks.IRON_TRAPDOOR)) {
            Level.setBlockAndUpdate(useOnContext.getClickedPos(), State.cycle(TrapDoorBlock.OPEN));
            return InteractionResult.SUCCESS;
        }

        if (State.getBlock().equals(Blocks.TNT)) {
            TntBlock.explode(Level, useOnContext.getClickedPos());
            Level.removeBlock(Pos, false);
            return InteractionResult.SUCCESS;
        }

        if (State.getBlock() instanceof SandBlock) {
            Level.setBlockAndUpdate(Pos, Blocks.GLASS.defaultBlockState());
            return InteractionResult.SUCCESS;
        }

        if (State.getBlock() instanceof GlassBlock) {
            Level.removeBlock(Pos, false);
            Level.playSound(
                    useOnContext.getPlayer(),
                    Pos,
                    SoundEvents.GLASS_BREAK,
                    SoundSource.BLOCKS,
                    1.0F,
                    Level.getRandom().nextFloat() * 0.1F + 0.9F);
            return InteractionResult.SUCCESS;
        }

        if (State.getBlock().equals(Blocks.BRICKS)) {
            Level.removeBlock(Pos, false);
            return InteractionResult.SUCCESS;
        }

        if (State.getBlock().equals(Blocks.BRICK_SLAB)) {
            Level.removeBlock(Pos, false);
            return InteractionResult.SUCCESS;
        }

        if (State.getBlock().equals(Blocks.BRICK_WALL)) {
            Level.removeBlock(Pos, false);
            return InteractionResult.SUCCESS;
        }

        if (State.getBlock().equals(Blocks.BRICK_STAIRS)) {
            Level.removeBlock(Pos, false);
            return InteractionResult.SUCCESS;
        }

        if (State.getBlock() instanceof PistonBaseBlock pistonBaseBlock) {
            pistonBaseBlock.triggerEvent(State, Level, Pos, 1, 2);
        }

        return InteractionResult.PASS;
    }

    private @NotNull HitResult calculateHitResult(LivingEntity livingEntity) {
        final double MAX_BRUSH_DISTANCE = Math.sqrt(ServerGamePacketListenerImpl.MAX_INTERACTION_DISTANCE) - 1.0;
        return ProjectileUtil.getHitResultOnViewVector(
                livingEntity, entity -> !entity.isSpectator() && entity.isPickable(), MAX_BRUSH_DISTANCE);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int i) {
        if (this.InteractionType != SonicInteractionType.SCANNER) return;
        if (i >= 0 && livingEntity instanceof Player player) {
            HitResult hitResult = this.calculateHitResult(livingEntity);
            if (hitResult instanceof BlockHitResult blockHitResult && hitResult.getType() == HitResult.Type.BLOCK) {
                int j = this.getUseDuration(itemStack) - i + 1;
                boolean bl = j % 10 == 5;
                if (bl) {
                    BlockPos blockPos = blockHitResult.getBlockPos();
                    BlockState blockState = level.getBlockState(blockPos);
                    HumanoidArm humanoidArm = livingEntity.getUsedItemHand() == InteractionHand.MAIN_HAND
                            ? player.getMainArm()
                            : player.getMainArm().getOpposite();
                    SoundEvent soundEvent;
                    if (blockState.getBlock() instanceof BrushableBlock brushableBlock) {
                        soundEvent = brushableBlock.getBrushSound();
                    } else {
                        soundEvent = SoundEvents.BRUSH_GENERIC;
                    }

                    level.playSound(player, blockPos, soundEvent, SoundSource.BLOCKS);
                    if (!level.isClientSide()
                            && level.getBlockEntity(blockPos) instanceof BrushableBlockEntity brushableBlockEntity) {
                        boolean bl2 =
                                brushableBlockEntity.brush(level.getGameTime(), player, blockHitResult.getDirection());
                        if (bl2) {
                            EquipmentSlot equipmentSlot = itemStack.equals(player.getItemBySlot(EquipmentSlot.OFFHAND))
                                    ? EquipmentSlot.OFFHAND
                                    : EquipmentSlot.MAINHAND;
                            itemStack.hurtAndBreak(
                                    1, livingEntity, livingEntityx -> livingEntityx.broadcastBreakEvent(equipmentSlot));
                        }
                    }
                }

                return;
            }

            livingEntity.releaseUsingItem();
        } else {
            livingEntity.releaseUsingItem();
        }
    }

    public void playDoorSound(
            @Nullable Entity entity, @NotNull Level level, BlockPos blockPos, boolean bl, DoorBlock Block) {
        level.playSound(
                entity,
                blockPos,
                bl ? Block.type().doorOpen() : Block.type().doorClose(),
                SoundSource.BLOCKS,
                1.0F,
                level.getRandom().nextFloat() * 0.1F + 0.9F);
    }
}
