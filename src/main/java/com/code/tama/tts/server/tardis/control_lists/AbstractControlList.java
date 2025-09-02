/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.control_lists;

import java.util.ArrayList;
import lombok.Getter;

public abstract class AbstractControlList {
    @Getter
    public ArrayList<ControlEntityRecord> PositionSizeMap = new ArrayList<>();

    private int ID = 0;

    public int AddControl(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        PositionSizeMap.add(new ControlEntityRecord(minX, minY, minZ, maxX, maxY, maxZ, ID++));
        return ID;
    }

    //    public int AddControlntrol(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
    //        GetPositionSizeMap().add(new ControlListRecord(minX, minY, minZ, maxX, maxY, maxZ, ID++));
    //        return ID;
    //    }
}
