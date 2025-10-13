/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json;

import com.code.tama.tts.server.misc.ARSStructure;
import com.code.tama.tts.server.registries.ARSRegistry;
import java.util.List;
import lombok.Getter;

public class DataARSList {
  @Getter private static List<ARSStructure> StructureList;

  public static void setList(List<ARSStructure> list) {
    StructureList = list;
    ARSRegistry.STRUCTURES.clear();
    ARSRegistry.STRUCTURES.addAll(list);
  }
}
