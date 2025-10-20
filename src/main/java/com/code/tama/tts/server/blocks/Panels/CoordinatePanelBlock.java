/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.Panels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.client.TTSSounds;
import com.code.tama.tts.server.blocks.core.VoxelRotatedShape;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.data.tardis.DataUpdateValues;
import com.code.tama.tts.server.misc.SpaceTimeCoordinate;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class CoordinatePanelBlock extends HorizontalDirectionalBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	public static final IntegerProperty PRESSED_BUTTON = IntegerProperty.create("pressed_button", 0, 3);
	public static VoxelRotatedShape SHAPE = new VoxelRotatedShape(createVoxelShape().optimize());
	public static List<CoordinatePanelButtons> buttons = new ArrayList<>();

	public CoordinatePanelBlock(Properties p_49795_) {
		super(p_49795_);
		this.registerDefaultState(
				this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(PRESSED_BUTTON, 0));
	}

	public static VoxelShape createVoxelShape() {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Block.box(12.25, 1, 6.25, 13.5, 2, 9.75), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(0, 0, 5, 16, 1, 11), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(7.5, 1, 6.25, 8.75, 2, 9.75), BooleanOp.OR);
		return Shapes.join(shape, Block.box(2.5, 1, 6.25, 3.75, 2, 9.75), BooleanOp.OR);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> StateDefinition) {
		super.createBlockStateDefinition(StateDefinition);
		StateDefinition.add(FACING);
		StateDefinition.add(PRESSED_BUTTON);
	}

	public CoordinatePanelButtons getButton(double mouseX, double mouseZ, Direction facing) {

		for (CoordinatePanelButtons button : buttons) {
			if (button.values.containsKey(facing)) {
				Vec2 vec = button.values.get(facing);
				float width = button.width;
				float height = button.height;
				float x = vec.x;
				float z = vec.y;
				switch (facing) {
					case EAST :
						if (mouseX >= (double) x && mouseX <= (double) (x + height) && mouseZ <= (double) z
								&& mouseZ >= (double) (z - width)) {
							return button;
						}
						break;

					case SOUTH :
						if (mouseX >= (double) x && mouseZ >= (double) z && mouseX <= (double) (x + width)
								&& mouseZ <= (double) (z + height)) {
							return button;
						}
						break;

					case WEST :
						if (mouseX <= (double) x && mouseX >= (double) (x - height) && mouseZ >= (double) z
								&& mouseZ <= (double) (z + width)) {
							return button;
						}
						break;

					default :
						if (mouseX <= (double) x && mouseZ <= (double) z && mouseX >= (double) (x - width)
								&& mouseZ >= (double) (z - height)) {
							return button;
						}
				}
			}
		}

		return CoordinatePanelButtons.EMPTY;
	}

	@Override
	public @NotNull VoxelShape getShape(BlockState p_60555_, @NotNull BlockGetter p_60556_, @NotNull BlockPos p_60557_,
			@NotNull CollisionContext p_60558_) {
		return SHAPE.GetShapeFromRotation(p_60555_.getValue(FACING));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()).setValue(PRESSED_BUTTON, 0);
	}

	@Override
	public BlockState mirror(BlockState p_54122_, Mirror p_54123_) {
		return p_54122_.rotate(p_54123_.getRotation(p_54122_.getValue(FACING)));
	}

	@Override
	public BlockState rotate(BlockState p_54125_, Rotation p_54126_) {
		return p_54125_.setValue(FACING, p_54126_.rotate(p_54125_.getValue(FACING)));
	}

	public void tick(BlockState state, @NotNull ServerLevel serverLevel, @NotNull BlockPos pos,
			@NotNull RandomSource randomSource) {
		if (state.getValue(PRESSED_BUTTON) != 0) {
			serverLevel.setBlock(pos, state.setValue(PRESSED_BUTTON, 0), 3);
		}
	}

	@Override
	public @NotNull InteractionResult use(@NotNull BlockState state, Level world, @NotNull BlockPos pos,
			@NotNull Player player, @NotNull InteractionHand hand, BlockHitResult hit) {
		if (!world.isClientSide) {
			System.out.println("Block was hit on face: " + hit.getDirection());

			CoordinatePanelButtons button = this.getButton(
					(100.0F * (float) (hit.getLocation().x() - (double) pos.getX())) / 100.0F,
					(100.0F * (float) (hit.getLocation().z() - (double) pos.getZ())) / 100.0F, state.getValue(FACING));

			boolean Crouching = player.isCrouching();
			if (button == null)
				return InteractionResult.FAIL;
			else
				world.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent(tardisLevelCapability -> {
					SpaceTimeCoordinate destination = tardisLevelCapability.GetNavigationalData().getDestination();
					int DestOffset = tardisLevelCapability.GetNavigationalData().getIncrement();
					switch (button) {
						case X :
							tardisLevelCapability.GetNavigationalData()
									.setDestination(destination.AddX(Crouching ? -DestOffset : DestOffset));
							tardisLevelCapability.UpdateClient(DataUpdateValues.NAVIGATIONAL);
							player.displayClientMessage(
									Component.literal("Current Destination = " + destination.ReadableString()), true);
							world.setBlock(pos, state.setValue(PRESSED_BUTTON, 1), 3);
							world.scheduleTick(pos, this, 10);
							world.playSound(null, pos, TTSSounds.BUTTON_CLICK_01.get(), SoundSource.BLOCKS);
							break;
						case Y :
							tardisLevelCapability.GetNavigationalData()
									.setDestination(destination.AddY(Crouching ? -DestOffset : DestOffset));
							tardisLevelCapability.UpdateClient(DataUpdateValues.NAVIGATIONAL);
							player.displayClientMessage(
									Component.literal("Current Destination = " + destination.ReadableString()), true);
							world.setBlock(pos, state.setValue(PRESSED_BUTTON, 2), 3);
							world.scheduleTick(pos, this, 10);
							TTSMod.LOGGER.info("Y!");
							world.playSound(null, pos, TTSSounds.BUTTON_CLICK_01.get(), SoundSource.BLOCKS);
							break;
						case Z :
							tardisLevelCapability.GetNavigationalData()
									.setDestination(destination.AddZ(Crouching ? -DestOffset : DestOffset));
							tardisLevelCapability.UpdateClient(DataUpdateValues.NAVIGATIONAL);
							player.displayClientMessage(
									Component.literal("Current Destination = " + destination.ReadableString()), true);
							world.setBlock(pos, state.setValue(PRESSED_BUTTON, 3), 3);
							world.scheduleTick(pos, this, 10);
							TTSMod.LOGGER.info("Z!");
							world.playSound(null, pos, TTSSounds.BUTTON_CLICK_01.get(), SoundSource.BLOCKS);
							break;
						default :
							TTSMod.LOGGER.info("NOPE!");
					}
				});
		}
		return InteractionResult.SUCCESS;
	}

	public enum CoordinatePanelButtons {
		EMPTY(null, 0.0F, 0.0F, 0.0F, 0.0F), X("X", 1.25f, 3.50f, 12.25f, 6.25f), Y("Y", 1.25f, 3.50f, 7.50f,
				6.25f), Z("Z", 1.25f, 3.50f, 2.50f, 6.25f);

		Component displayName;
		final float height;
		final Map<Direction, Vec2> values = new HashMap<>();
		final float width;

		CoordinatePanelButtons(String s, float w, float h, float x1, float z1) {
			float f = 0.0625F;
			this.width = w * f;
			this.height = h * f;
			float x2 = 16.0F - x1;
			float z2 = 16.0F - z1;
			this.values.put(Direction.NORTH, new Vec2(x2 * f, z2 * f));
			this.values.put(Direction.EAST, new Vec2(z1 * f, x2 * f));
			this.values.put(Direction.SOUTH, new Vec2(x1 * f, z1 * f));
			this.values.put(Direction.WEST, new Vec2(z2 * f, x1 * f));
			if (s != null) {
				this.displayName = Component.literal(s);
			}
			CoordinatePanelBlock.buttons.add(this);
		}
	}
}
