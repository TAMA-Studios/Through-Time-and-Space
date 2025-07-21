/* (C) TAMA Studios 2025 */
package com.code.tama.tts.compat;

import com.code.tama.tts.server.worlds.biomes.MTerrablender;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import net.minecraftforge.fml.ModList;

public class ModCompat {
    private static ArrayList<CompatThingy> compatList = new ArrayList<>();
    private static int ID = 0;

    public static void RegisterCompats() {
        RegisterCompat("terrablender", new MTerrablender());
    }

    private static void RegisterCompat(String modid, CompatClass clazz) {
        compatList.add(ID++, new CompatThingy(modid, clazz));
    }

    public static void Run() {
        RegisterCompats();

        for (CompatThingy thingy : compatList) {
            if (ModList.get().isLoaded(thingy.modid)) {
                thingy.aClass.runCompat();
            }
        }
    }

    @Getter
    @Setter
    public static class CompatThingy {
        public CompatClass aClass;
        public String modid;

        public CompatThingy(String modid, CompatClass compatClass) {
            this.aClass = compatClass;
            this.modid = modid;
        }
    }
}
