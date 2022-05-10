package com.craftschemers.ranchcraft.mixin;

import com.craftschemers.ranchcraft.RanchCraftMod;
import com.craftschemers.ranchcraft.event.custom.FarmGrowthCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(CropBlock.class)
public class FarmGrowthMixin {

    @Inject(at = @At("TAIL"), method = "randomTick")
    private void onGrowth(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {

        CropBlock cropBlock = (CropBlock) state.getBlock();

        if (world.getBlockState(pos).get(cropBlock.getAgeProperty()) >= cropBlock.getMaxAge()) {
            FarmGrowthCallback.EVENT.invoker().interact(state, world, pos);
        }

    }

}
