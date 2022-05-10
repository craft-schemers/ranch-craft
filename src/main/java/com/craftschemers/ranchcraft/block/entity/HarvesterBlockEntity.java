package com.craftschemers.ranchcraft.block.entity;

import com.craftschemers.ranchcraft.RanchCraftMod;
import com.craftschemers.ranchcraft.block.ModBlocks;
import com.craftschemers.ranchcraft.block.custom.HarvesterBlock;
import com.craftschemers.ranchcraft.item.inventory.ImplementedInventory;
import com.craftschemers.ranchcraft.screen.HarvesterScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class HarvesterBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate; // we can track values on client and server
    private int progress = 0;
    // How many ticks it will take for the fuel to run out
    private int maxProgress = 200;


    public HarvesterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.HARVESTER_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> HarvesterBlockEntity.this.progress;
                    case 1 -> HarvesterBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> HarvesterBlockEntity.this.progress = value;
                    case 1 -> HarvesterBlockEntity.this.maxProgress = value;
                };
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }


    //These Methods are from the NamedScreenHandlerFactory Interface
    //createMenu creates the ScreenHandler itself
    //getDisplayName will Provide its name which is normally shown at the top

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        //We provide *this* to the screenHandler as our class Implements Inventory
        //Only the Server has the Inventory at the start, this will be synced to the client in the ScreenHandler
        return new HarvesterScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, this.inventory);
        super.writeNbt(nbt);
    }

    // called every tick
    public static void tick(World world, BlockPos pos, BlockState state, HarvesterBlockEntity entity) {

        if (hasFuel(entity)) {
            entity.progress++;
            checkForHarvestableCrops(world, pos, entity, 5);
            if (entity.progress > entity.maxProgress) {
                removeFuel(entity);
            }
        } else {
            entity.resetProgress();
        }

    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static boolean hasFuel(HarvesterBlockEntity entity) {

        ItemStack slot = entity.inventory.get(0);
        if (slot.isEmpty()) {
            return false;
        }
        return slot.getItem() == Items.WATER_BUCKET;
    }

    private static void removeFuel(HarvesterBlockEntity entity) {
        entity.inventory.set(0, new ItemStack(Items.BUCKET));
    }

    private static void checkForHarvestableCrops(World world, BlockPos pos, HarvesterBlockEntity harvesterBlockEntity, int radius) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        for (int curX = x - radius; curX <= x + radius; curX++) {
            for (int curZ = z - radius; curZ <= z + radius; curZ++) {
                BlockPos blockPos = new BlockPos(curX, y, curZ);
                BlockState state = world.getBlockState(blockPos);

                if (state.getBlock() instanceof CropBlock cropBlock) {

                    int age = state.get(cropBlock.getAgeProperty());
                    if (age >= cropBlock.getMaxAge()) {
                        // ready to harvest
                        world.breakBlock(blockPos, false); // TODO: only break if available inventory
                        world.setBlockState(blockPos, Blocks.WHEAT.getDefaultState()); // TODO: might remove
                    }


                }

            }

        }
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        return ImplementedInventory.super.canExtract(slot, stack, side);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
}
