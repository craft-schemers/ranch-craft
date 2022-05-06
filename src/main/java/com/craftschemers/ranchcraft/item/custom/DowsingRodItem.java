package com.craftschemers.ranchcraft.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class DowsingRodItem extends Item {


    public DowsingRodItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        if (context.getWorld().isClient()) {

            PlayerEntity player = context.getPlayer();

            assert player != null;
            player.sendMessage(Text.of("You used the item!"), false);

        }

        return super.useOnBlock(context);
    }




}
