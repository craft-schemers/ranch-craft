package com.craftschemers.ranchcraft;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RanchCraftMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");
	private static final String MOD_NAME = "Rain Gauge";
	private static final Block RAIN_GAUGE =
		new Block(FabricBlockSettings.of(Material.GLASS).strength(2.0f));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		// TODO first parm in Identifier
		Registry.register(Registry.BLOCK, Identifier("TODO", "rain_gauge"), RAIN_GAUGE);
		// TODO double check .REDSTONE
		Registry.register(Registry.ITEM, Identifier("TODO", "rain_gauge"),
			BlockItem(RAIN_GAUGE, FabricItemSettings().group(ItemGroup.REDSTONE)));

	}
}
