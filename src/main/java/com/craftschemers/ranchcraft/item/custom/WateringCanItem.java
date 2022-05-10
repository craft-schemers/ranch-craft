package com.craftschemers.ranchcraft.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;

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
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult hitResult = BucketItem.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (((HitResult) hitResult).getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack);
        }
        if (((HitResult) hitResult).getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = hitResult.getBlockPos();
            if (!world.canPlayerModifyAt(user,blockPos)) {
                return TypedActionResult.pass(itemStack);
            }
            if (world.getFluidState(blockPos).isIn(FluidTags.WATER)) {
                world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                world.emitGameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
                itemStack.setDamage(0);
                return TypedActionResult.success(itemStack, world.isClient());
            }
        }
        return TypedActionResult.pass(itemStack);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        FertilizeResult result = useOnFertilizable(context.getStack(), player, context.getWorld(), context.getBlockPos());

        if (world.isClient) {

            if (player == null) {
                return super.useOnBlock(context);
            }

            if (result == FertilizeResult.SUCCESS) {
                player.playSound(SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1f, 1f);
            } else if (result == FertilizeResult.FAILED_OUT_OF_WATER) {
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
