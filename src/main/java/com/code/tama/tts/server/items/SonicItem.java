/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.items;

import com.code.tama.tts.server.items.core.IAmAttunable;
import com.code.tama.tts.server.misc.GrammarNazi;
import com.code.tama.tts.server.misc.sonic.SonicBlockMode;
import com.code.tama.tts.server.misc.sonic.SonicBuilderMode;
import com.code.tama.tts.server.misc.sonic.SonicMode;
import com.code.tama.tts.server.registries.SonicModeRegistry;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SonicItem extends IAmAttunable {
    @Getter
    @Setter
    public @NotNull SonicMode InteractionType = new SonicBlockMode();

    public SonicItem(Properties properties) {
        super(properties);
    }

    public boolean canAttackBlock(
            @NotNull BlockState p_40962_, Level p_40963_, @NotNull BlockPos p_40964_, @NotNull Player p_40965_) {
        if (this.InteractionType instanceof SonicBuilderMode sonicBuilderMode) {
            sonicBuilderMode.handleInteraction(
                    p_40965_,
                    p_40962_,
                    p_40963_,
                    p_40964_,
                    false,
                    p_40965_.getItemInHand(InteractionHand.MAIN_HAND),
                    this.getDescriptionId());
            return false;
        }

        return false;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(
            @NotNull Level level, @NotNull Player player, @NotNull InteractionHand interactionHand) {
        if (interactionHand == InteractionHand.OFF_HAND) return super.use(level, player, interactionHand);
        if (player.isCrouching()) {
            for (int i = 0; i < SonicModeRegistry.SONIC_MODE.getEntries().size(); i++) {
                SonicMode mode = SonicModeRegistry.SONIC_MODE.getEntries().stream()
                        .toList()
                        .get(i)
                        .get();
                if (mode.getClass().equals(this.InteractionType.getClass())) {
                    RegistryObject<SonicMode> nextMode = SonicModeRegistry.SONIC_MODE.getEntries().stream()
                            .toList()
                            .get((i + 1
                                                    < SonicModeRegistry.SONIC_MODE
                                                            .getEntries()
                                                            .size()
                                            ? i + 1
                                            : 0)
                                    % SonicModeRegistry.SONIC_MODE.getEntries().size());
                    this.InteractionType = nextMode.get();
                    assert nextMode.getId() != null;
                    if (!player.level().isClientSide)
                        player.sendSystemMessage(Component.literal(GrammarNazi.CapitalizeFirstLetters(
                                GrammarNazi.ScoreToSpace(nextMode.get().getName()))));
                    break;
                }
            }
        } else
            this.useOn(new UseOnContext(
                    player,
                    interactionHand,
                    new BlockHitResult(
                            player.getEyePosition(),
                            player.getDirection(),
                            BlockPos.containing(player.getEyePosition()
                                    .add(player.getViewVector(1.0F).scale(5.0D))),
                            false)));
        return super.use(level, player, interactionHand);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext useOnContext) {
        this.InteractionType.onUse(useOnContext);
        return InteractionResult.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    private @NotNull HitResult calculateHitResult(LivingEntity livingEntity) {
        final double MAX_BRUSH_DISTANCE = Math.sqrt(ServerGamePacketListenerImpl.MAX_INTERACTION_DISTANCE) - 1.0;
        return ProjectileUtil.getHitResultOnViewVector(
                livingEntity, entity -> !entity.isSpectator() && entity.isPickable(), MAX_BRUSH_DISTANCE);
    }

    @Override
    public void onUseTick(
            @NotNull Level level, @NotNull LivingEntity livingEntity, @NotNull ItemStack itemStack, int i) {
        //        if (this.InteractionType != SonicInteractionType.SCANNER) return;
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
