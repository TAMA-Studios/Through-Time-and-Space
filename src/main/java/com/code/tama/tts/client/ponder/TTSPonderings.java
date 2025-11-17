/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.ponder;

import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.createmod.ponder.foundation.instruction.RotateSceneInstruction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;

public class TTSPonderings {
	public static void base(SceneBuilder scene, SceneBuildingUtil util) {
		scene.title("console", "Configuring the Console");
		scene.configureBasePlate(0, 0, 5);
		scene.showBasePlate();
		scene.idle(5);

		BlockPos consolePos = util.grid().at(3, 3, 3);

		scene.idle(10);
		scene.overlay().showText(60).text("Consoles are the main way of piloting and controlling the TARDIS")
				.attachKeyFrame().pointAt(util.vector().blockSurface(consolePos, Direction.WEST)).placeNearTarget();
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

	public static void dematCircuit(SceneBuilder scene, SceneBuildingUtil util) {
		scene.title("demat_circuit", "Dematerialization Circuit");
		scene.configureBasePlate(0, 0, 5);
		scene.showBasePlate();
		scene.idle(5);

		BlockPos corePos = util.grid().at(3, 3, 3);

		scene.idle(10);
		scene.overlay().showText(60).text("The Dematerialization Circuit is essential to the flight of the TARDIS")
				.attachKeyFrame().pointAt(util.vector().blockSurface(corePos, Direction.WEST)).placeNearTarget();
		scene.idle(20);

		scene.overlay().showText(60).text("").pointAt(util.vector().blockSurface(corePos, Direction.WEST))
				.placeNearTarget();

		scene.idle(70);
	}

	public static void netherCore(SceneBuilder scene, SceneBuildingUtil util) {
		scene.title("nether_reactor", "Nether Reactor Core");
		scene.configureBasePlate(0, 0, 5);
		scene.showBasePlate();
		scene.idle(10);

		BlockPos corePos = util.grid().at(2, 2, 2);

		Selection bottomLayer = util.select().fromTo(1, 1, 1, 3, 1, 3);

		Selection midLayer = util.select().fromTo(1, 2, 1, 3, 2, 3);

		Selection topLayer = util.select().fromTo(1, 3, 1, 3, 3, 3);

		scene.world().showSection(bottomLayer, Direction.WEST);

		scene.idle(10);

		scene.world().showSection(midLayer, Direction.EAST);

		scene.idle(10);

		scene.world().showSection(topLayer, Direction.WEST);

		scene.idle(10);
		scene.overlay().showText(60)
				.text("The Nether Reactor Core is essential for the TARDISes inter-dimensional travel capabilities")
				.attachKeyFrame().pointAt(util.vector().blockSurface(corePos, Direction.WEST)).placeNearTarget();

		scene.idle(60);

		scene.addKeyframe();

		scene.addInstruction(new RotateSceneInstruction(-90, 0, false));

		scene.world().moveSection(scene.world().makeSectionIndependent(bottomLayer), Vec3.ZERO.add(4, 0, 0), 10);

		scene.world().moveSection(scene.world().makeSectionIndependent(topLayer), Vec3.ZERO.add(-4, 0, 0), 10);

		scene.idle(20);

		scene.overlay().showText(40).text("The Nether Reactor Core is assembled like so").attachKeyFrame()
				.pointAt(util.vector().blockSurface(corePos, Direction.WEST)).placeNearTarget();

		scene.idle(40);

		scene.world()
				.hideSection(scene.getScene().getSceneBuildingUtil().select().cuboid(
						new BlockPos(scene.getScene().getBasePlateOffsetX(), 0, scene.getScene().getBasePlateOffsetZ()),
						new Vec3i(scene.getScene().getBasePlateSize() - 1, 0, scene.getScene().getBasePlateSize() - 1)),
						Direction.NORTH);

		scene.overlay().showText(40).text("Bottom Layer").attachKeyFrame()
				.pointAt(util.vector().blockSurface(corePos.relative(Direction.WEST, 4).north(), Direction.WEST))
				.placeNearTarget();

		scene.overlay().showText(40).text("Middle Layer").attachKeyFrame()
				.pointAt(util.vector().blockSurface(corePos, Direction.WEST)).placeNearTarget();

		scene.overlay().showText(40).text("Top Layer").attachKeyFrame()
				.pointAt(util.vector().blockSurface(corePos.relative(Direction.EAST, 4).south(), Direction.WEST))
				.placeNearTarget();

		scene.idle(50);
	}

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
}
