package com.craftschemers.ranchcraft.enchantment;

import com.craftschemers.ranchcraft.RanchCraftMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEnchantments {

    public static Enchantment AUTO_SMELTER_ENCHANTMENT = register("auto_smelter",
            new AutoSmelterEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.DIGGER, EquipmentSlot.MAINHAND));

    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registry.ENCHANTMENT, new Identifier(RanchCraftMod.MOD_ID, name), enchantment);
    }
    
    public static void registerModEnchantments() {
        System.out.println("Registering Enchantments for " + RanchCraftMod.MOD_ID);
    }
    
}
