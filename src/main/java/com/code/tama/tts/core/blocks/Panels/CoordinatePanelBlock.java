/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.blocks.Panels;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.code.tama.tts.client.TTSSounds;
import com.code.tama.tts.core.blocks.core.ImAnInteractableAnimatedPanel;
import com.code.tama.tts.core.blocks.core.VoxelRotatedShape;
import com.code.tama.tts.server.data.tardis.DataUpdateValues;
import com.code.tama.tts.server.misc.containers.SpaceTimeCoordinate;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import com.code.tama.triggerapi.animation.GeoHelper;
import com.code.tama.triggerapi.animation.GeoModel;
import com.code.tama.triggerapi.universal.UniversalCommon;

@SuppressWarnings("deprecation")
public class CoordinatePanelBlock extends HorizontalDirectionalBlock implements ImAnInteractableAnimatedPanel {

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
		shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.0625, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.171875, 0.0625, 0.484375, 0.390625, 0.09375, 0.703125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.390625, 0.0625, 0.203125, 0.609375, 0.09375, 0.421875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.609375, 0.0625, 0.484375, 0.828125, 0.09375, 0.703125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.625, 0.0625, 0.5, 0.8125, 0.125, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.40625, 0.0625, 0.21875, 0.59375, 0.125, 0.40625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.0625, 0.5, 0.375, 0.125, 0.6875), BooleanOp.OR);

		return shape;
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
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
				.setValue(PRESSED_BUTTON, 0);
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

			CoordinatePanelButtons button = this.getButton(
					(100.0F * (float) (hit.getLocation().x() - (double) pos.getX())) / 100.0F,
					(100.0F * (float) (hit.getLocation().z() - (double) pos.getZ())) / 100.0F,
					state.getValue(FACING).getOpposite());

			boolean Crouching = player.isCrouching();
			if (button == null)
				return InteractionResult.FAIL;
			else
				GetTARDISCapSupplier(world).ifPresent(tardisLevelCapability -> {
					SpaceTimeCoordinate destination = tardisLevelCapability.GetNavigationalData().getDestination();
					int DestOffset = tardisLevelCapability.GetNavigationalData().getIncrement();
					switch (button) {
						case X :
							tardisLevelCapability.GetNavigationalData()
									.setDestination(destination.AddX(Crouching ? -DestOffset : DestOffset));
							tardisLevelCapability.UpdateClient(DataUpdateValues.NAVIGATIONAL);
							player.displayClientMessage(
									Component.literal("Current Destination = " + destination.ReadableString()), true);
							// world.setBlock(pos, state.setValue(PRESSED_BUTTON, 1), 3);
							// world.scheduleTick(pos, this, 10);
							rClickAnim(world, pos);
							world.playSound(null, pos, TTSSounds.BUTTON_CLICK_01.get(), SoundSource.BLOCKS);
							break;
						case Y :
							tardisLevelCapability.GetNavigationalData()
									.setDestination(destination.AddY(Crouching ? -DestOffset : DestOffset));
							tardisLevelCapability.UpdateClient(DataUpdateValues.NAVIGATIONAL);
							player.displayClientMessage(
									Component.literal("Current Destination = " + destination.ReadableString()), true);
							// world.setBlock(pos, state.setValue(PRESSED_BUTTON, 2), 3);
							// world.scheduleTick(pos, this, 10);
							// TTSMod.LOGGER.info("Y!");
							mClickAnim(world, pos);
							world.playSound(null, pos, TTSSounds.BUTTON_CLICK_01.get(), SoundSource.BLOCKS);
							break;
						case Z :
							tardisLevelCapability.GetNavigationalData()
									.setDestination(destination.AddZ(Crouching ? -DestOffset : DestOffset));
							tardisLevelCapability.UpdateClient(DataUpdateValues.NAVIGATIONAL);
							player.displayClientMessage(
									Component.literal("Current Destination = " + destination.ReadableString()), true);
							// world.setBlock(pos, state.setValue(PRESSED_BUTTON, 3), 3);
							// world.scheduleTick(pos, this, 10);
							// TTSMod.LOGGER.info("Z!");
							lClickAnim(world, pos);
							world.playSound(null, pos, TTSSounds.BUTTON_CLICK_01.get(), SoundSource.BLOCKS);
							break;
						default :
							break;
					}
				});
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public GeoModel getGeoModel() {
		return GeoHelper.getModel("blockgeo/panel/coord");
	}

	@Override
	public ResourceLocation getGeoTexture() {
		return UniversalCommon.modRL("textures/block/panel/coord.png");
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState sF, boolean idfk) {
		this.onPlace(pos);
		super.onPlace(state, level, pos, sF, idfk);
	}

	public enum CoordinatePanelButtons {
		EMPTY(null, 0.0F, 0.0F, 0.0F, 0.0F), X("X", 3, 3, 10, 8), Y("Y", 3, 3, 6.5f, 3.25f), Z("Z", 3, 3, 2.75f, 8);

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
