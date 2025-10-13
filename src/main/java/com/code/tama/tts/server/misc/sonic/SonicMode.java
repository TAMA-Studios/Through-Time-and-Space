/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc.sonic;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public abstract class SonicMode {
    public abstract Item getIcon();

    public abstract String getName();

    public abstract void onUse(UseOnContext context);
}
