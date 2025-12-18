package com.code.tama.tts.server.data;

import com.code.tama.triggerapi.universal.UniversalCommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class Tags {
    public static class Items {
        public static final TagKey<Item> SONICABLE = TagKey.create(Registries.ITEM, UniversalCommon.modRL("sonicable"));
    }

    public static class Blocks {
        public static final TagKey<Block> SONICABLE = TagKey.create(Registries.BLOCK, UniversalCommon.modRL("sonicable"));
    }

    public static class Entities {
        public static final TagKey<EntityType<?>> SONICABLE = TagKey.create(Registries.ENTITY_TYPE, UniversalCommon.modRL("sonicable"));
    }
}
