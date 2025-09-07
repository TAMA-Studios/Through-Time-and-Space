/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.models.core;

import com.code.tama.tts.server.tileentities.ExteriorTile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IExteriorModel<T extends BlockEntity> {
    Map<Integer, ArrayList<Float>> list = new HashMap<>();

    ModelPart LeftDoor();

    ModelPart RightDoor();
    /**
     * The Map is setup like so: <br />
     * the Integer is 0 for left door, 1 for right door <br />
     * the ArrayList<Float> is like such: <br />
     * the first element (0) is 0 doors open <br />
     * the second element (1) is 1 door open <br />
     * the third element (2) is 2 doors open <br />
     * the ArrayList will be called for getting the rotation of the door at the
     * specified values, in the default implementation, the door rotations for the
     * right door are as follows: <br />
     * 0 doors open - 0f <br />
     * 1 doors open - Math.toRadians(75) <br />
     * 2 doors open - Math.toRadians(75) <br />
     *
     * @return A map containing each rotation value for both left and right doors
     *         <br />
     */
    default Map<Integer, ArrayList<Float>> RotationList() {
        Map<Integer, ArrayList<Float>> map = new HashMap<>();
        ArrayList<Float> LeftList = new ArrayList<>();
        ArrayList<Float> RightList = new ArrayList<>();

        LeftList.add(0f);
        LeftList.add(0f);
        LeftList.add(-(float) Math.toRadians(75));

        RightList.add(0f);
        RightList.add((float) Math.toRadians(75));
        RightList.add((float) Math.toRadians(75));

        map.put(0, LeftList);
        map.put(1, RightList);
        return map;
    }

    default void SetupAnimations(T tile, float ageInTicks) {
        this.setupDoorAngles(tile);
    }

    default void setupDoorAngles(T entity) {
        list.clear();
        if (entity instanceof ExteriorTile tile) {
            if (list.isEmpty()) {
                list.putAll(this.RotationList());
            }

            switch (tile.DoorsOpen()) {
                case 1: {
                    this.LeftDoor().yRot = list.get(0).get(1);
                    this.RightDoor().yRot = list.get(1).get(1);
                    break;
                }
                case 2: {
                    this.LeftDoor().yRot = list.get(0).get(2);
                    this.RightDoor().yRot = list.get(1).get(2);
                    break;
                }
                default: {
                    this.LeftDoor().yRot = list.get(0).get(0);
                    this.RightDoor().yRot = list.get(1).get(0);
                    break;
                }
            }
        }
    }
}
