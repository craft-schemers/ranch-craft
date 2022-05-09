package com.craftschemers.ranchcraft.mixin;

import com.craftschemers.ranchcraft.event.custom.FarmGrowthCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(CropBlock.class)
public class FarmGrowthMixin {

    @Inject(at = @At("HEAD"), method = "grow", cancellable = true)
    private void onGrowth(ServerWorld world, Random random, BlockPos pos, BlockState state, CallbackInfo ci) {
        ActionResult result = FarmGrowthCallback.EVENT.invoker().interact((CropBlock) state.getBlock());

        if (result == ActionResult.FAIL) {
            ci.cancel();
        }

    }

}
