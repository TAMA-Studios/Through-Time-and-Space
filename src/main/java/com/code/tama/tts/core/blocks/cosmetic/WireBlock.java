/* (C) TAMA Studios 2026 */
package com.code.tama.tts.core.blocks.cosmetic;

import com.code.tama.tts.core.tileentities.WireBlockEntity;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class WireBlock extends BaseEntityBlock {

	public static final int MAX_LINK_RANGE = 16;

	public WireBlock(Properties props) {
		super(props);
	}

	@Nullable @Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new WireBlockEntity(pos, state);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
		super.onPlace(state, level, pos, oldState, movedByPiston);
		if (!level.isClientSide) {
			tryAutoLink(level, pos);
		}
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
		if (!level.isClientSide && !newState.is(this)) {
			// Unlink partner before removing
			if (level.getBlockEntity(pos) instanceof WireBlockEntity wire && wire.getLinkedPos() != null) {
				BlockPos partnerPos = wire.getLinkedPos();
				if (level.getBlockEntity(partnerPos) instanceof WireBlockEntity partner) {
					partner.setLinkedPos(null);
					partner.setChanged();
				}
			}
		}
		super.onRemove(state, level, pos, newState, movedByPiston);
	}

	/**
	 * Right-click: relink this wire. Useful when auto-link picked the wrong
	 * neighbor, or to manually break an existing link (shift+right-click).
	 */
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
			BlockHitResult hit) {
		if (!level.isClientSide && level.getBlockEntity(pos) instanceof WireBlockEntity wire) {
			if (player.isShiftKeyDown()) {
				// Break existing link
				if (wire.getLinkedPos() != null) {
					if (level.getBlockEntity(wire.getLinkedPos()) instanceof WireBlockEntity partner) {
						partner.setLinkedPos(null);
						partner.setChanged();
					}
					wire.setLinkedPos(null);
					wire.setChanged();
					player.displayClientMessage(net.minecraft.network.chat.Component.literal("Wire unlinked."), true);
				}
			} else {
				// Force re-scan for a new link
				tryAutoLink(level, pos);
				if (wire.getLinkedPos() != null) {
					player.displayClientMessage(net.minecraft.network.chat.Component.literal("Wire linked!"), true);
				} else {
					player.displayClientMessage(
							net.minecraft.network.chat.Component.literal("No nearby unlinked wire found."), true);
				}
			}
		}
		return InteractionResult.sidedSuccess(level.isClientSide);
	}

	/**
	 * Scans nearby blocks for the closest unlinked WireBlockEntity and links them.
	 * Called on placement and on manual right-click relink.
	 */
	public static void tryAutoLink(Level level, BlockPos pos) {
		if (!(level.getBlockEntity(pos) instanceof WireBlockEntity thisBE))
			return;
		// If already linked, don't overwrite
		if (thisBE.getLinkedPos() != null)
			return;

		BlockPos bestPos = null;
		double bestDist = Double.MAX_VALUE;

		// Scan a cube of radius MAX_LINK_RANGE
		for (BlockPos candidate : BlockPos.betweenClosed(pos.offset(-MAX_LINK_RANGE, -MAX_LINK_RANGE, -MAX_LINK_RANGE),
				pos.offset(MAX_LINK_RANGE, MAX_LINK_RANGE, MAX_LINK_RANGE))) {

			if (candidate.equals(pos))
				continue;
			if (!(level.getBlockEntity(candidate) instanceof WireBlockEntity candidateBE))
				continue;
			if (candidateBE.getLinkedPos() != null)
				continue; // already linked

			double dist = pos.distSqr(candidate);
			if (dist < bestDist) {
				bestDist = dist;
				bestPos = candidate.immutable();
			}
		}

		if (bestPos != null && level.getBlockEntity(bestPos) instanceof WireBlockEntity partnerBE) {
			thisBE.setLinkedPos(bestPos);
			thisBE.setChanged();
			partnerBE.setLinkedPos(pos.immutable());
			partnerBE.setChanged();
		}
	}
}