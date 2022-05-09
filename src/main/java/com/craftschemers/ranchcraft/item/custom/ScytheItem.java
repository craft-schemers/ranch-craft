package com.craftschemers.ranchcraft.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ScytheItem extends Item {
    public ScytheItem(Settings settings) {
        super(settings);
    }
    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        player.sendMessage(Text.of("onClicked"), false);
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.sendMessage(Text.of("use"), false);
        return super.use(world, user, hand);
    }



}
