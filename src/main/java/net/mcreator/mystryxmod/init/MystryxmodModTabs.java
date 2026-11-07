/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.mystryxmod.init;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;

import net.mcreator.mystryxmod.MystryxmodMod;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;

public class MystryxmodModTabs {
	public static ResourceKey<CreativeModeTab> TAB_MYSTRYX_MOD = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(MystryxmodMod.MODID, "mystryx_mod"));

	public static void load() {
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_MYSTRYX_MOD,
				CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0).title(Component.translatable("item_group.mystryxmod.mystryx_mod")).icon(() -> new ItemStack(MystryxmodModItems.MYSTRYX_INGOT)).displayItems((parameters, tabData) -> {
					tabData.accept(MystryxmodModBlocks.MYSTRYX_ORE.asItem());
					tabData.accept(MystryxmodModItems.MYSTRYX_INGOT);
					tabData.accept(MystryxmodModBlocks.MYSTRYX_BLOCK.asItem());
					tabData.accept(MystryxmodModItems.MYSTRYX_SWORD);
					tabData.accept(MystryxmodModItems.MYSTRYX_PICKAXE);
					tabData.accept(MystryxmodModBlocks.DARK_MYSTRYX_ORE.asItem());
					tabData.accept(MystryxmodModItems.DARK_MYSTRYX_INGOT);
					tabData.accept(MystryxmodModItems.MYSELIUM);
					tabData.accept(MystryxmodModItems.DARK_MYSTRYX_SWORD);
					tabData.accept(MystryxmodModItems.DARK_MYSTRYX_PICKAXE);
				}).build());
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(tabData -> {
			tabData.accept(MystryxmodModItems.MYSTRYX_HELMET);
			tabData.accept(MystryxmodModItems.MYSTRYX_CHESTPLATE);
			tabData.accept(MystryxmodModItems.MYSTRYX_LEGGINGS);
			tabData.accept(MystryxmodModItems.MYSTRYX_BOOTS);
		});
	}
}