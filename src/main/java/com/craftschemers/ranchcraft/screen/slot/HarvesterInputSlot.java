package com.craftschemers.ranchcraft.screen.slot;

import com.craftschemers.ranchcraft.screen.HarvesterScreenHandler;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

public class HarvesterInputSlot extends Slot {

    private final HarvesterScreenHandler handler;
    public HarvesterInputSlot(HarvesterScreenHandler handler, Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.handler = handler;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if (stack.isEmpty()) return false;
        if (stack.getItem() != Items.WATER_BUCKET) return false;
        return !handler.hasWater();
    }

}
