package com.craftschemers.ranchcraft;

import com.craftschemers.ranchcraft.block.ModBlocks;
import com.craftschemers.ranchcraft.block.entity.ModBlockEntities;
import com.craftschemers.ranchcraft.enchantment.ModEnchantments;
import com.craftschemers.ranchcraft.item.ModItems;
import com.craftschemers.ranchcraft.listener.ModListeners;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RanchCraftMod implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
 	public static final String MOD_ID = "ranchcraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

		ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModEnchantments.registerModEnchantments();
        ModListeners.registerModListeners();
        ModBlockEntities.registerAllBlockEntities();

    }

}
