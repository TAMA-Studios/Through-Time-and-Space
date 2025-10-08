/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.data.codecs;

import com.code.tama.tts.server.tardis.subsystems.AbstractSubsystem;
import com.code.tama.tts.server.tardis.subsystems.DematerializationCircuit;
import com.code.tama.tts.server.tardis.subsystems.DynamorphicController;
import com.code.tama.tts.server.tardis.subsystems.NetherReactorCoreSubsystem;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;

public class SubsystemsCodecs {
    public static Codec<DematerializationCircuit> DEMAT_CIRCUIT = RecordCodecBuilder.create(instance -> instance.group(
                    BlockPos.CODEC.fieldOf("pos").forGetter(AbstractSubsystem::getBlockPos),
                    Codec.BOOL.fieldOf("active").forGetter(AbstractSubsystem::isActivated))
            .apply(instance, DematerializationCircuit::new));

    public static Codec<NetherReactorCoreSubsystem> DIMENSIONAL_CORE =
            RecordCodecBuilder.create(instance -> instance.group(
                            BlockPos.CODEC.fieldOf("pos").forGetter(AbstractSubsystem::getBlockPos),
                            Codec.BOOL.fieldOf("active").forGetter(AbstractSubsystem::isActivated))
                    .apply(instance, NetherReactorCoreSubsystem::new));

    public static Codec<DynamorphicController> DYNAMORPHIC_CONTROLLER =
            RecordCodecBuilder.create(instance -> instance.group(
                            BlockPos.CODEC.fieldOf("pos").forGetter(AbstractSubsystem::getBlockPos),
                            Codec.BOOL.fieldOf("active").forGetter(AbstractSubsystem::isActivated))
                    .apply(instance, DynamorphicController::new));
}
