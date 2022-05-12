package com.craftschemers.ranchcraft.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ScytheItem extends Item {
    public ScytheItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {

        if (state.getBlock() instanceof CropBlock targetCropBlock
                && state.get(targetCropBlock.getAgeProperty()) >= targetCropBlock.getMaxAge()) {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();

            int radius = 1;
            for (int curX = x - radius; curX <= x + radius; curX++) {
                for (int curZ = z - radius; curZ <= z + radius; curZ++) {
                    if (state.getBlock() instanceof CropBlock cropBlock) {

                        BlockPos blockPos = new BlockPos(curX, y, curZ);
                        int age = state.get(cropBlock.getAgeProperty());
                        if (age >= cropBlock.getMaxAge()) {
                            if (stack.getDamage() < stack.getMaxDamage()) {
                                world.breakBlock(blockPos, true, miner);
                                stack.damage(1, miner, (LivingEntity entity) -> {
                                });
                            } else {
                                return true;
                            }
                        }

                    }

                }
            }
        } else {
            stack.damage(1, miner, (LivingEntity entity) -> {
            });
        }

        return true;
    }

}
