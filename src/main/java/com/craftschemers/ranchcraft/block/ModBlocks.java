package com.craftschemers.ranchcraft.block;

import com.craftschemers.ranchcraft.RanchCraftMod;
import com.craftschemers.ranchcraft.block.custom.HarvesterBlock;
import com.craftschemers.ranchcraft.item.ModItemGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public static final Block HARVESTER_BLOCK = registerBlock("harvester_block",
            new HarvesterBlock(FabricBlockSettings.copyOf(Blocks.CHEST)), ModItemGroup.FARMING);

    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(RanchCraftMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(RanchCraftMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }

    public static void registerModBlocks() {
        RanchCraftMod.LOGGER.info("Registering ModBlocks for " + RanchCraftMod.MOD_ID);
    }

}
