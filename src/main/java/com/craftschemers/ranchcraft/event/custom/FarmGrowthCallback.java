package com.craftschemers.ranchcraft.event.custom;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface FarmGrowthCallback {

    Event<FarmGrowthCallback> EVENT = EventFactory.createArrayBacked(FarmGrowthCallback.class,
        (listeners) -> (state, world, pos) -> {
            for (FarmGrowthCallback listener : listeners) {
                ActionResult result = listener.interact(state, world, pos);
                if (result != ActionResult.PASS) return result;
            }
            return ActionResult.PASS;
        });


    ActionResult interact(BlockState state, ServerWorld world, BlockPos pos);

}
