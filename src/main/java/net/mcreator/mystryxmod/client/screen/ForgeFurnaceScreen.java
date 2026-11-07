package net.mcreator.mystryxmod.client.screen;

import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import net.mcreator.mystryxmod.inventory.ForgeFurnaceMenu;

public class ForgeFurnaceScreen extends AbstractContainerScreen<ForgeFurnaceMenu> {
	private static final Identifier TEXTURE = Identifier.withDefaultNamespace("textures/gui/container/furnace.png");
	private static final Identifier LIT_PROGRESS = Identifier.withDefaultNamespace("container/furnace/lit_progress");
	private static final Identifier BURN_PROGRESS = Identifier.withDefaultNamespace("container/furnace/burn_progress");

	public ForgeFurnaceScreen(ForgeFurnaceMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float delta) {
		extractBackground(guiGraphics, mouseX, mouseY, delta);
		super.extractRenderState(guiGraphics, mouseX, mouseY, delta);
		extractTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void init() {
		super.init();
		titleLabelX = (imageWidth - font.width(title)) / 2;
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float delta) {
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;
		guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
		if (this.menu.isLit()) {
			int flame = this.menu.getLitProgress();
			guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, LIT_PROGRESS, 14, 14, 0, 14 - flame, x + 56, y + 36 + 14 - flame, 14, flame);
		}
		int arrow = this.menu.getBurnProgress();
		guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, BURN_PROGRESS, 24, 16, 0, 0, x + 79, y + 34, arrow, 16);
	}
}
