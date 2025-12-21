/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.ponder;

import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class GadgetPonderings {

	public static void engineInterface(SceneBuilder scene, SceneBuildingUtil util) {
		scene.title("engine_interface", "TARDIS Remote Engine Interface");
		scene.configureBasePlate(0, 0, 5);
		scene.showBasePlate();

		BlockPos corePos = util.grid().at(2, 1, 2);
		Selection links = util.select().fromTo(0, 1, 1, 5, 1, 0);
		Selection coreSelect = util.select().position(corePos);

		scene.world().showSection(coreSelect, Direction.DOWN);

		scene.idle(10);

		scene.overlay().showText(60)
				.text("The TARDIS Remote Engine Interface is required to connect subsystems to the TARDIS engines")
				.attachKeyFrame().pointAt(util.vector().blockSurface(corePos, Direction.WEST)).placeNearTarget();

		scene.idle(60);

		scene.world().showSection(links, Direction.DOWN);

		scene.overlay().showText(60).text("Subsystems are connected to the Interface via the use of Fragment Links")
				.attachKeyFrame().pointAt(util.vector().blockSurface(corePos, Direction.WEST)).placeNearTarget();

		scene.idle(10);
	}

	public static void console(SceneBuilder scene, SceneBuildingUtil util) {
		// CreateSceneBuilder scene = new CreateSceneBuilder(builder);
		scene.title("console", "Configuring the Console");
		scene.configureBasePlate(0, 0, 5);
		scene.showBasePlate();
		scene.idle(5);

		BlockPos consolePos = util.grid().at(3, 3, 3);

		scene.idle(10);
		scene.overlay().showText(60).text("Consoles are the main way of piloting and controlling the TARDIS")
				.attachKeyFrame().pointAt(util.vector().blockSurface(consolePos, Direction.WEST)).placeNearTarget();
		scene.idle(20);

		scene.overlay().showText(60).text("Upon receiving a signal, it will toggle its state")
				.pointAt(util.vector().blockSurface(consolePos, Direction.WEST)).placeNearTarget();

		scene.idle(70);
	}
}
