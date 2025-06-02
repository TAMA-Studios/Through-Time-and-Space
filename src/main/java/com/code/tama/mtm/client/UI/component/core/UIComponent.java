package com.code.tama.mtm.client.UI.component.core;

import com.code.tama.mtm.MTMMod;
import com.code.tama.mtm.client.UI.category.UICategory;
import com.code.tama.mtm.server.registries.UICategoryRegistry;
import com.mojang.math.Axis;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RequiredArgsConstructor
public class UIComponent extends AbstractUIComponent {
    public final Float x[];
    public final Float y[];
    public final ComponentTypes type;
    public UICategory category = UICategoryRegistry.ALL.get();
    public ResourceLocation icon = new ResourceLocation(MTMMod.MODID, "textures/gui/button.png");

    public UIComponent(Float x[], Float y[], ComponentTypes type, UICategory category) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.category = category;
    }

    @Override
    public ComponentTypes Type() {
        return this.type;
    }

    @Override
    public Map<Axis, Float[]> XYBounds() {
        Map<Axis, Float[]> bounds = new HashMap<>();
        bounds.put(Axis.XP,this.x);
        bounds.put(Axis.YP, this.y);
        return bounds;
    }

    @Override
    public ResourceLocation GetIcon() {
        return this.icon;
    }
}
