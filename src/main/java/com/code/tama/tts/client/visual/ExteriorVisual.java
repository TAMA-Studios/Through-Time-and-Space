/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.visual;

import java.util.function.Consumer;

import com.code.tama.tts.client.TTSPartialModels;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.AbstractBlockEntityVisual;
import org.jetbrains.annotations.Nullable;

public class ExteriorVisual extends AbstractBlockEntityVisual<ExteriorTile> {
	private final TransformedInstance model;
	public ExteriorVisual(VisualizationContext ctx, ExteriorTile blockEntity, float partialTick) {
		super(ctx, blockEntity, partialTick);

		// PartialModel blazeModel = BlazeBurnerRenderer.getBlazeModel(heatLevel,
		// validBlockAbove);

		model = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(TTSPartialModels.MODEL))
				.createInstance();

		model.setIdentityTransform();
	}

	@Override
	public void collectCrumblingInstances(Consumer<@Nullable Instance> consumer) {

	}

	@Override
	public void updateLight(float partialTick) {
		// this.updateLight(partialTick);
	}

	@Override
	protected void _delete() {
		this.delete();
	}

}
