package com.craftschemers.ranchcraft;

import com.craftschemers.ranchcraft.screen.HarvesterScreen;
import com.craftschemers.ranchcraft.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class RanchCraftClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ScreenRegistry.register(ModScreenHandlers.HARVESTER_BLOCK_SCREEN_HANDLER, HarvesterScreen::new);

    }
}
