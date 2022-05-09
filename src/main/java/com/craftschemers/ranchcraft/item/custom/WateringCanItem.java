package com.craftschemers.ranchcraft.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

import java.util.Objects;

public class WateringCanItem extends Item {

    // This item doesn't do much right now...

    public WateringCanItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        if (context.getWorld().isClient()) {
            PlayerEntity player = context.getPlayer();

            if (player == null) {
                return super.useOnBlock(context);
            }

            player.playSound(SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1f, 1f);

        }
        return super.useOnBlock(context);
    }
}
