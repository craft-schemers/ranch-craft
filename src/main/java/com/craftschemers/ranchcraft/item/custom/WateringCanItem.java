package com.craftschemers.ranchcraft.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class WateringCanItem extends Item {

    // This item doesn't do much right now...

    public WateringCanItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        World world = context.getWorld();
        boolean used = useOnFertilizable(context.getWorld(), context.getBlockPos());

        if (context.getWorld().isClient()) {
            PlayerEntity player = context.getPlayer();

            if (player == null) {
                return super.useOnBlock(context);
            }

            BlockPos blockPos = context.getBlockPos();
            if (used) {
                world.playSound(
                        blockPos.getX(),
                        blockPos.getY(),
                        blockPos.getZ(),
                        SoundEvents.ITEM_BUCKET_EMPTY,
                        SoundCategory.BLOCKS,
                        1f,
                        1f,
                        true
                );
            } else {
                world.playSound(
                        blockPos.getX(),
                        blockPos.getY(),
                        blockPos.getZ(),
                        SoundEvents.BLOCK_DISPENSER_FAIL,
                        SoundCategory.BLOCKS,
                        1f,
                        1f,
                        true
                );
            }

        } else {
            world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, context.getBlockPos(), 0);
        }

        return super.useOnBlock(context);
    }

    public static boolean useOnFertilizable(World world, BlockPos pos) {

        Fertilizable fertilizable;
        BlockState blockState = world.getBlockState(pos);
        if (blockState.getBlock() instanceof Fertilizable && (fertilizable = (Fertilizable) blockState.getBlock()).isFertilizable(world, pos, blockState, world.isClient)) {
            if (world instanceof ServerWorld) {
                if (fertilizable.canGrow(world, world.random, pos, blockState)) {
                    fertilizable.grow((ServerWorld) world, world.random, pos, blockState);
                }
            }
            return true;
        }
        return false;
    }

}
