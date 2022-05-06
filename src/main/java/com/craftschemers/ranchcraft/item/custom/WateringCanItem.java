package com.craftschemers.ranchcraft.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

import java.util.Objects;

public class WateringCanItem extends Item {
    public WateringCanItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        if (context.getWorld().isClient()) {
            Objects.requireNonNull(context.getPlayer()).sendMessage(Text.of("You used the watering can!"), false);

        }
        return super.useOnBlock(context);
    }
}
