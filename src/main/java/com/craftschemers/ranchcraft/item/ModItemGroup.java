package com.craftschemers.ranchcraft.item;

import com.craftschemers.ranchcraft.RanchCraftMod;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroup {

    public static final ItemGroup FARMING = FabricItemGroupBuilder.build(
            new Identifier(RanchCraftMod.MOD_ID, "farming"),
            () -> new ItemStack(ModItems.WATERING_CAN));

}
