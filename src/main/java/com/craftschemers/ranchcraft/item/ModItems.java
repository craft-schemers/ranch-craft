package com.craftschemers.ranchcraft.item;

import com.craftschemers.ranchcraft.RanchCraftMod;
import com.craftschemers.ranchcraft.item.custom.ScytheItem;
import com.craftschemers.ranchcraft.item.custom.WateringCanItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item WATERING_CAN = registerItem("watering_can",
            new WateringCanItem(new FabricItemSettings().maxDamage(32).group(ModItemGroup.FARMING)));

    public static final Item SCYTHE = registerItem("scythe",
            new ScytheItem(new FabricItemSettings().group(ModItemGroup.FARMING)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(RanchCraftMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        RanchCraftMod.LOGGER.info("Registering Mod Items for " + RanchCraftMod.MOD_ID);
    }

}
