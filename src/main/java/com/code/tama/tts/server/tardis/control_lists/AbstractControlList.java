/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.control_lists;

import lombok.Getter;

import java.util.ArrayList;

public abstract class AbstractControlList {
    @Getter
    public ArrayList<ControlEntityRecord> PositionSizeMap = new ArrayList<>();

    private int ID = 0;

    public AbstractControlList() {}

    public int AddControl(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        PositionSizeMap.add(new ControlEntityRecord(minX, minY, minZ, maxX, maxY, maxZ, ID++));
        return ID;
    }
}
