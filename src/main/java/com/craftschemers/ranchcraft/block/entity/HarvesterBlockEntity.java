package com.craftschemers.ranchcraft.block.entity;

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
import org.jetbrains.annotations.Nullable;

public class HarvesterBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate; // we can track values on client and server
    private int cropsThatCanBeBroken = 0;
    // How many ticks it will take for the fuel to run out


    public HarvesterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.HARVESTER_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return HarvesterBlockEntity.this.cropsThatCanBeBroken;
            }

            @Override
            public void set(int index, int value) {
                if (index == 0) {
                    HarvesterBlockEntity.this.cropsThatCanBeBroken = value;
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
        cropsThatCanBeBroken = nbt.getInt("cropsThatCanBeBroken"); // retrieves
        Inventories.readNbt(nbt, this.inventory);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, this.inventory);
        nbt.putInt("cropsThatCanBeBroken", cropsThatCanBeBroken);
        super.writeNbt(nbt);
    }

    // called every tick
    public static void tick(World world, BlockPos pos, BlockState state, HarvesterBlockEntity entity) {

        if (hasFuel(entity)) {
            checkForHarvestableCrops(world, pos, entity, 5);
        } else {
            entity.resetProgress();
        }

    }

    private void resetProgress() {
        this.cropsThatCanBeBroken = 0;
    }

    private static boolean hasFuel(HarvesterBlockEntity entity) {

        ItemStack slot = entity.inventory.get(0);

        if (slot.getItem() == Items.WATER_BUCKET) {
            entity.cropsThatCanBeBroken = 200;
            entity.inventory.set(0, new ItemStack(Items.AIR));
            entity.inventory.set(1, new ItemStack(Items.BUCKET));
            return true;
        }

        return entity.cropsThatCanBeBroken > 0;

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
                        harvesterBlockEntity.cropsThatCanBeBroken--;

                        if (harvesterBlockEntity.cropsThatCanBeBroken <= 0) {
                            return;
                        }

                    }


                }

            }

        }
    }



    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        return slot == 0 && cropsThatCanBeBroken == 0;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        return slot == 1;
    }

}
