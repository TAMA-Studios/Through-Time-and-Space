package com.code.tama.mtm.client.UI;

import com.mojang.math.Axis;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public abstract class UIComponent {
    @Getter @Setter
    boolean State;

    public abstract ComponentTypes Type();

    /**
     * @return a map with an axis (XP or YP) and a float array (0 being minimum, 1 being maximum)
     **/
    public abstract Map<Axis, Float[]> XYBounds();
}
