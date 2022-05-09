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

            // for scythe
            if (world.getBlockState(pos).getBlock() instanceof CropBlock c) {

                // if they aren't holding the Scythe, we don't care
                if (player.getMainHandStack().isEmpty() || player.getMainHandStack().getItem() != ModItems.SCYTHE) {
                    return true;
                }

                // if the crop they broke isn't ready to be harvested, we cancel the action
                if (world.getBlockState(pos).get(c.getAgeProperty()) < c.getMaxAge()) {
                    return false;
                }

                int RADIUS_TO_BREAK = 1;
                int x = pos.getX();
                int y = pos.getY();
                int z = pos.getZ();

                for (int curX = x - RADIUS_TO_BREAK; curX <= x + RADIUS_TO_BREAK; curX++) {
                    for (int curZ = z - RADIUS_TO_BREAK; curZ <= z + RADIUS_TO_BREAK; curZ++) {
                        BlockPos blockPos = new BlockPos(curX, y, curZ);
                        BlockState state1 = world.getBlockState(blockPos);
                        if (state1.getBlock() instanceof CropBlock cropBlock) {

                            int age = state1.get(cropBlock.getAgeProperty());
                            if (age >= cropBlock.getMaxAge()) {
                                // ready to harvest
                                world.breakBlock(blockPos, true, player);

                                int slot;
                                if ((slot = player.getInventory().getSlotWithStack(new ItemStack(Items.WHEAT_SEEDS))) >= 0) { // TODO: get respective seed
                                    ItemStack current = player.getInventory().getStack(slot);
                                    current.setCount(current.getCount() - 1);
                                    player.getInventory().setStack(slot, player.getInventory().getStack(slot));
                                    world.setBlockState(blockPos, Blocks.WHEAT.getDefaultState()); // TODO: just use the initial block broken instead of WHEAT
                                }

                            }

                        }

                    }
                }

                return false;
            }

            return true;
        });

    }

}
