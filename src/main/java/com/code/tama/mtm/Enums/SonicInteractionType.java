package com.code.tama.mtm.Enums;

import net.minecraft.network.chat.Component;

public enum SonicInteractionType {
    BLOCKS(Component.translatable("aseoha.sonic.mode.block")),
    SCANNER(Component.translatable("aseoha.sonic.mode.scanner")),
    ENTITY(Component.translatable("aseoha.sonic.mode.entity"));

    private final Component Name;
    SonicInteractionType(Component Name) {
        this.Name = Name;
    }

    public Component Name() {
        return this.Name;
    }
}
