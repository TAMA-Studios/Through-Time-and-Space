/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.blocks.tardis;

import com.code.tama.tts.client.gui.terminal.TARDISConsoleScreen;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * The "real" TARDIS console block - opens {@link TARDISConsoleScreen}, a
 * Linux-terminal-style command line, rather than the button-based
 * {@link TerminalBlock} dev GUI.
 */
public class ConsoleTerminalBlock extends HorizontalDirectionalBlock {

	public ConsoleTerminalBlock(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
			BlockHitResult hit) {
		if (level.isClientSide) {
			openConsole(level);
		}
		return InteractionResult.sidedSuccess(level.isClientSide);
	}

	@OnlyIn(Dist.CLIENT)
	private void openConsole(Level level) {
		ITARDISLevel tardis = TARDISLevelCapability.GetTARDISCap(level);
		if (tardis == null)
			return;

		Minecraft.getInstance().setScreen(new TARDISConsoleScreen(tardis));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
