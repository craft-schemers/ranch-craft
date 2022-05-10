package com.craftschemers.ranchcraft.block.entity;

import com.craftschemers.ranchcraft.RanchCraftMod;
import com.craftschemers.ranchcraft.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {

    public static BlockEntityType<HarvesterBlockEntity> HARVESTER_BLOCK_ENTITY;

    public static void registerAllBlockEntities() {
        HARVESTER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(RanchCraftMod.MOD_ID, "harvester_block"),
                FabricBlockEntityTypeBuilder.create(HarvesterBlockEntity::new,
                        ModBlocks.HARVESTER_BLOCK).build(null));

    }

}
