package com.craftschemers.ranchcraft.listener;

import com.craftschemers.ranchcraft.RanchCraftMod;
import com.craftschemers.ranchcraft.enchantment.ModEnchantments;
import com.craftschemers.ranchcraft.item.ModItems;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class ModListeners {

    public static void registerModListeners() {
        RanchCraftMod.LOGGER.info("Registering Listeners for " + RanchCraftMod.MOD_ID);

        // listeners
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, entity) ->
        {

            if (EnchantmentHelper.getLevel(ModEnchantments.AUTO_SMELTER_ENCHANTMENT, player.getMainHandStack()) > 0) {
                // has the enchantment
                Optional<SmeltingRecipe> optional = world.getRecipeManager().getFirstMatch(RecipeType.SMELTING,
                        new SimpleInventory(new ItemStack(state.getBlock())), world);

                ItemStack itemStack;
                if (optional.isPresent() && !(itemStack = optional.get().getOutput()).isEmpty()) {
                    // able to be smelted
                    world.removeBlock(pos, false);
                    world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(),
                            itemStack)); // itemstack contains the smelted result

                    return false; // cancels the block break
                }
            }

            return true;
        });

    }

}
