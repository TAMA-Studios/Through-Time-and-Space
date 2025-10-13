/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks;

import com.code.tama.tts.server.blocks.subsystems.AbstractSubsystemBlock;
import com.code.tama.tts.server.tardis.subsystems.DynamorphicController;

public class EnginesBlock extends AbstractSubsystemBlock {
  public EnginesBlock(Properties p_49795_) {
    super(p_49795_, new DynamorphicController());
  }
}
