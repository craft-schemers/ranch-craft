package com.craftschemers.ranchcraft.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class WateringCanItem extends Item {

    private enum FertilizeResult {
        SUCCESS,
        FAILED_INVALID_TARGET,
        FAILED_OUT_OF_WATER,
    }

    public WateringCanItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        PlayerEntity player = context.getPlayer();
        FertilizeResult result = useOnFertilizable(context.getStack(), player, context.getWorld(), context.getBlockPos());

        if (context.getWorld().isClient) {

            if (player == null) {
                return super.useOnBlock(context);
            }

            if (result == FertilizeResult.SUCCESS) {
                player.playSound(SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1f, 1f);
            } else {
                player.playSound(SoundEvents.BLOCK_DISPENSER_FAIL, SoundCategory.BLOCKS, 1f, 1f);
            }

        } else if (result == FertilizeResult.SUCCESS) {
            context.getWorld().syncWorldEvent(WorldEvents.BONE_MEAL_USED, context.getBlockPos(), 0);
        }

        return super.useOnBlock(context);
    }

    private static FertilizeResult useOnFertilizable(ItemStack itemStack, PlayerEntity player, World world, BlockPos pos) {

        Fertilizable fertilizable;
        BlockState blockState = world.getBlockState(pos);
        if (itemStack.getDamage() == itemStack.getMaxDamage() - 1) {
            return FertilizeResult.FAILED_OUT_OF_WATER;
        }
        if (blockState.getBlock() instanceof Fertilizable && (fertilizable = (Fertilizable) blockState.getBlock()).isFertilizable(world, pos, blockState, world.isClient)) {
            if (fertilizable.canGrow(world, world.random, pos, blockState)) {
                if (world instanceof ServerWorld) {
                    fertilizable.grow((ServerWorld) world, world.random, pos, blockState);
                    itemStack.damage(1, player, (PlayerEntity p) -> {
                    });
                }
                return FertilizeResult.SUCCESS;
            }
        }
        return FertilizeResult.FAILED_INVALID_TARGET;
    }

}
