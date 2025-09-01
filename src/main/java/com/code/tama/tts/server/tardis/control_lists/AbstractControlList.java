/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.control_lists;

import java.util.ArrayList;

public abstract class AbstractControlList {
    public abstract ArrayList<ControlListRecord> GetPositionSizeMap();

    private int ID = 0;

    public int AddControl(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        GetPositionSizeMap().add(new ControlListRecord(minX, minY, minZ, maxX, maxY, maxZ, ID++));
        return ID;
    }

    public int AddControl(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        GetPositionSizeMap().add(new ControlListRecord(minX, minY, minZ, maxX, maxY, maxZ, ID++));
        return ID;
    }
}
