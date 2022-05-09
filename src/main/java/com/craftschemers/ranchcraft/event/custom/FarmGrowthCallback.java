package com.craftschemers.ranchcraft.event.custom;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.CropBlock;
import net.minecraft.util.ActionResult;

public interface FarmGrowthCallback {

    Event<FarmGrowthCallback> EVENT = EventFactory.createArrayBacked(FarmGrowthCallback.class,
        (listeners) -> (crop) -> {
            for (FarmGrowthCallback listener : listeners) {
                ActionResult result = listener.interact(crop);
                if (result != ActionResult.PASS) return result;
            }
            return ActionResult.PASS;
        });


    ActionResult interact(CropBlock crop);

}
