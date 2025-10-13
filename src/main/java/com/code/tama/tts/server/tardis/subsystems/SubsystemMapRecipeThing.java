/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.subsystems;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SubsystemMapRecipeThing {
  private String[] Map;
  private boolean flipped = false;
  private final Map<Character, BlockState> Key;

  @Getter private final Map<BlockPos, BlockState> map = new HashMap<>();

  public SubsystemMapRecipeThing(String map[], Map<Character, BlockState> key) {
    this.Map = map;
    this.Key = key;
    initMap();
  }

  private static void reverseArray(String arr[]) {
    int left = 0;
    int right = arr.length - 1;

    while (left < right) {
      // Swap elements at left and right pointers
      String temp = arr[left];
      arr[left] = arr[right];
      arr[right] = temp;

      // Move pointers towards the center
      left++;
      right--;
    }
  }

  public void initMap() {
    // Flip the Y of the map
    if (!flipped) {
      reverseArray(this.Map);
      flipped = true;
    }

    System.out.println(Arrays.toString(this.Map));

    // ----- Pass 1: find # position -----
    int originX = 0, originY = 0, originZ = 0;
    boolean foundOrigin = false;

    for (int y = 0; y < this.Map.length; y++) {
      String[] rows = this.Map[y].split("\n");
      for (int z = 0; z < rows.length; z++) {
        String row = rows[z];
        for (int x = 0; x < row.length(); x++) {
          if (row.charAt(x) == '#') {
            originX = x;
            originY = y;
            originZ = z;
            foundOrigin = true;
            break;
          }
        }
        if (foundOrigin) break;
      }
      if (foundOrigin) break;
    }

    // ----- Pass 2: fill map with offsets -----
    for (int y = 0; y < this.Map.length; y++) {
      String[] rows = this.Map[y].split("\n");
      for (int z = 0; z < rows.length; z++) {
        String row = rows[z];
        for (int x = 0; x < row.length(); x++) {
          char c = row.charAt(x);
          // skip the origin marker if you don’t want a block there:
          if (c == '#') continue;

          BlockState state = Key.getOrDefault(c, Blocks.AIR.defaultBlockState());

          // Offset so that # becomes (0,0,0):
          int worldX = x - originX;
          int worldY = y - originY;
          int worldZ = z - originZ;

          BlockPos pos = new BlockPos(worldX, worldY, worldZ);
          map.put(pos, state);
        }
      }
    }
  }
}
