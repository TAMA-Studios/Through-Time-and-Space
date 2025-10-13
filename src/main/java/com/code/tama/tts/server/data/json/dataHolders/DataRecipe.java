/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json.dataHolders;

import lombok.Builder;
import net.minecraft.resources.ResourceLocation;

@Builder
public class DataRecipe {
  public ResourceLocation item1;
  public ResourceLocation item2;
  public ResourceLocation item3;
  public ResourceLocation item4;
  public ResourceLocation item5;
  public ResourceLocation item6;
  public ResourceLocation nozzle;
  public ResourceLocation result;
  public int TimeInTicks;

  @Override
  public String toString() {
    return "DataRecipe{"
        + "item1="
        + item1
        + ", item2="
        + item2
        + ", item3="
        + item3
        + ", item4="
        + item4
        + ", item5="
        + item5
        + ", item6="
        + item6
        + ", nozzle="
        + nozzle
        + ", result="
        + result
        + ", TimeInTicks="
        + TimeInTicks
        + '}';
  }
}
