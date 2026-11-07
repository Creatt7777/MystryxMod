package net.mcreator.mystryxmod;

import net.mcreator.mystryxmod.init.MystryxmodModArmorModels;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ClientModInitializer;

@Environment(EnvType.CLIENT)
public class MystryxmodModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Start of user code block mod constructor
		// End of user code block mod constructor
		MystryxmodModArmorModels.clientLoad();
		// Start of user code block mod init
		net.minecraft.client.gui.screens.MenuScreens.register(net.mcreator.mystryxmod.init.MystryxmodModForgeContent.FORGE_FURNACE_MENU, net.mcreator.mystryxmod.client.screen.ForgeFurnaceScreen::new);
		// End of user code block mod init
	}
	// Start of user code block mod methods
	// End of user code block mod methods
}