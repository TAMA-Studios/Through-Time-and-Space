/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.ponder;

import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.WorldSectionElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class TTSPonderings {
	public static void sticker(SceneBuilder scene, SceneBuildingUtil util) {
		// CreateSceneBuilder scene = new CreateSceneBuilder(builder);
		scene.title("sticker", "Attaching blocks using the Sticker");
		scene.configureBasePlate(0, 0, 5);
		scene.showBasePlate();
		scene.idle(5);

		Selection redstone = util.select().fromTo(0, 2, 2, 2, 2, 2);
		BlockPos stickerPos = util.grid().at(2, 2, 2);
		Selection stickerSelect = util.select().position(stickerPos);
		BlockPos buttonPos = util.grid().at(0, 2, 2);
		BlockPos bearingPos = util.grid().at(2, 1, 2);

		scene.world().showSection(util.select().fromTo(2, 1, 2, 0, 2, 2).substract(stickerSelect), Direction.DOWN);
		scene.idle(10);
		ElementLink<WorldSectionElement> sticker = scene.world().showIndependentSection(stickerSelect, Direction.DOWN);
		scene.idle(10);
		ElementLink<WorldSectionElement> plank = scene.world().showIndependentSection(util.select().position(2, 2, 1),
				Direction.SOUTH);
		scene.world().configureCenterOfRotation(sticker, util.vector().centerOf(stickerPos));
		scene.world().configureCenterOfRotation(plank, util.vector().centerOf(stickerPos));
		scene.overlay().showText(60).text("Stickers are ideal for Redstone-controlled block attachment")
				.attachKeyFrame().pointAt(util.vector().blockSurface(stickerPos, Direction.WEST)).placeNearTarget();
		scene.idle(70);

		scene.world().toggleRedstonePower(redstone);
		// scene.world().modifyBlock(stickerPos, s -> s.setValue(StickerBlock.EXTENDED,
		// true), false);
		scene.effects().indicateRedstone(buttonPos);
		// scene.world().modifyBlockEntityNBT(stickerSelect, StickerBlockEntity.class,
		// nbt -> {
		// });
		scene.idle(20);

		scene.world().toggleRedstonePower(redstone);
		scene.idle(20);

		scene.overlay().showText(60).text("Upon receiving a signal, it will toggle its state")
				.pointAt(util.vector().blockSurface(stickerPos, Direction.WEST)).placeNearTarget();
		scene.idle(70);

		// scene.world().rotateBearing(bearingPos, 180 * 3, 80);
		scene.world().rotateSection(sticker, 0, 180 * 3, 0, 80);
		scene.world().rotateSection(plank, 0, 180 * 3, 0, 80);
		scene.overlay().showText(70).text("If it is now moved in a contraption, the block will move with it")
				.pointAt(util.vector().topOf(stickerPos)).placeNearTarget();
		scene.idle(90);
		scene.addKeyframe();

		scene.world().toggleRedstonePower(redstone);
		// scene.world().modifyBlock(stickerPos, s -> s.setValue(StickerBlock.EXTENDED,
		// false), false);
		scene.effects().indicateRedstone(buttonPos);
		// scene.world().modifyBlockEntityNBT(stickerSelect, StickerBlockEntity.class,
		// nbt -> {
		// });
		scene.idle(20);

		scene.world().toggleRedstonePower(redstone);
		scene.idle(20);

		scene.overlay().showText(60).text("Toggled once again, the block is no longer attached")
				.pointAt(util.vector().blockSurface(stickerPos, Direction.WEST)).placeNearTarget();
		scene.idle(70);

		// scene.world().rotateBearing(bearingPos, 180 * 3, 80);
		scene.world().rotateSection(sticker, 0, 180 * 3, 0, 80);
	}

	public static void console(SceneBuilder scene, SceneBuildingUtil util) {
		// CreateSceneBuilder scene = new CreateSceneBuilder(builder);
		scene.title("console", "Configuring the Console");
		scene.configureBasePlate(0, 0, 5);
		scene.showBasePlate();
		scene.idle(5);

		BlockPos consolePos = util.grid().at(2, 2, 2);
		Selection stickerSelect = util.select().position(consolePos);

		scene.world().showSection(util.select().fromTo(2, 1, 2, 0, 2, 2).substract(stickerSelect), Direction.DOWN);
		scene.idle(10);
		ElementLink<WorldSectionElement> sticker = scene.world().showIndependentSection(stickerSelect, Direction.DOWN);
		scene.idle(10);
		ElementLink<WorldSectionElement> plank = scene.world().showIndependentSection(util.select().position(2, 2, 1),
				Direction.SOUTH);
		scene.world().configureCenterOfRotation(sticker, util.vector().centerOf(consolePos));
		scene.world().configureCenterOfRotation(plank, util.vector().centerOf(consolePos));
		scene.overlay().showText(60).text("Consoles are the main way of piloting and controlling the TARDIS")
				.attachKeyFrame().pointAt(util.vector().blockSurface(consolePos, Direction.WEST)).placeNearTarget();
		scene.idle(20);

		scene.overlay().showText(60).text("Upon receiving a signal, it will toggle its state")
				.pointAt(util.vector().blockSurface(consolePos, Direction.WEST)).placeNearTarget();

		scene.idle(70);

		// scene.world().rotateBearing(bearingPos, 180 * 3, 80);
		scene.world().rotateSection(sticker, 0, 180 * 3, 0, 80);
		scene.world().rotateSection(plank, 0, 180 * 3, 0, 80);
		scene.overlay().showText(70).text("If it is now moved in a contraption, the block will move with it")
				.pointAt(util.vector().topOf(consolePos)).placeNearTarget();
		scene.idle(90);
		scene.addKeyframe();

		scene.overlay().showText(60).text("Toggled once again, the block is no longer attached")
				.pointAt(util.vector().blockSurface(consolePos, Direction.WEST)).placeNearTarget();
		scene.idle(70);

		// scene.world().rotateBearing(bearingPos, 180 * 3, 80);
		scene.world().rotateSection(sticker, 0, 180 * 3, 0, 80);
	}
}
