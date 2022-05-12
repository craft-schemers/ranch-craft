package com.craftschemers.ranchcraft.screen;

import com.craftschemers.ranchcraft.RanchCraftMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class HarvesterScreen extends HandledScreen<HarvesterScreenHandler> {
    // A path to the gui texture
    private static final Identifier TEXTURE = new Identifier(RanchCraftMod.MOD_ID, "textures/gui/harvester_block_gui.png");
    private int ticks = 0;
    private int frame = 0;
    private boolean increasing = true;

    public HarvesterScreen(HarvesterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 768);

        if (handler.hasWater()) {
            int progress = handler.getScaledProgress();
            drawTexture(matrices, x + 26, y + 19 + progress, 176, 55 + frame*16, 16, 47 - progress, 256, 768); // water
            drawTexture(matrices, x + 26, y + 19 + 3, 176, 14, 16, 41, 256, 768); // marks
        }

        // 176 55
        ticks++;
        if (ticks >= 15) {
            ticks = 0;
            if (increasing) {
                frame++;
                if (frame >= 20) {
                    increasing = false;
                }
            } else {
                frame--;
                if (frame <= 0) {
                    increasing = true;
                }
            }
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
}
