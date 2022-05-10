package com.craftschemers.ranchcraft.screen;

import com.craftschemers.ranchcraft.RanchCraftMod;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {

    public static ScreenHandlerType<HarvesterScreenHandler> HARVESTER_BLOCK_SCREEN_HANDLER =
            ScreenHandlerRegistry.registerSimple(new Identifier(RanchCraftMod.MOD_ID, "harvester_block"),
                    HarvesterScreenHandler::new);

}
