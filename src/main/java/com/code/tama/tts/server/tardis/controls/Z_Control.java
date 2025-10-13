/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.controls;

import com.code.tama.tts.client.TTSSounds;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class Z_Control extends AbstractControl {
  @Override
  public SoundEvent GetFailSound() {
    return SoundEvents.DISPENSER_FAIL;
  }

  @Override
  public String GetName() {
    return "z_control";
  }

  @Override
  public SoundEvent GetSuccessSound() {
    return TTSSounds.BUTTON_CLICK_01.get();
  }

  @Override
  public InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity entity) {
    itardisLevel
        .GetNavigationalData()
        .setDestination(
            itardisLevel
                .GetNavigationalData()
                .getDestination()
                .AddZ(
                    entity.isCrouching()
                        ? itardisLevel.GetNavigationalData().getIncrement()
                        : -itardisLevel.GetNavigationalData().getIncrement()));
    if (entity instanceof Player player)
      player.displayClientMessage(
          Component.literal(
              "Current Destination = "
                  + itardisLevel.GetNavigationalData().getDestination().ReadableString()),
          true);
    return InteractionResult.SUCCESS;
  }

  @Override
  public InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player) {
    itardisLevel
        .GetNavigationalData()
        .setDestination(
            itardisLevel
                .GetNavigationalData()
                .getDestination()
                .AddZ(
                    player.isCrouching()
                        ? -itardisLevel.GetNavigationalData().getIncrement()
                        : itardisLevel.GetNavigationalData().getIncrement()));
    player.displayClientMessage(
        Component.literal(
            "Current Destination = "
                + itardisLevel.GetNavigationalData().getDestination().ReadableString()),
        true);
    return InteractionResult.SUCCESS;
  }
}
