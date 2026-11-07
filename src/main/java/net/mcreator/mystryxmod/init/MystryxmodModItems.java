/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.mystryxmod.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.Registries;

import net.mcreator.mystryxmod.item.*;
import net.mcreator.mystryxmod.MystryxmodMod;

import java.util.function.Function;

public class MystryxmodModItems {
	public static Item MYSTRYX_ORE;
	public static Item MYSTRYX_INGOT;
	public static Item MYSTRYX_BLOCK;
	public static Item MYSTRYX_HELMET;
	public static Item MYSTRYX_CHESTPLATE;
	public static Item MYSTRYX_LEGGINGS;
	public static Item MYSTRYX_BOOTS;
	public static Item MYSTRYX_SWORD;
	public static Item MYSTRYX_PICKAXE;
	public static Item DARK_MYSTRYX_ORE;
	public static Item DARK_MYSTRYX_INGOT;
	public static Item MYSELIUM;
	public static Item DARK_MYSTRYX_SWORD;
	public static Item DARK_MYSTRYX_PICKAXE;

	public static void load() {
		MYSTRYX_ORE = block(MystryxmodModBlocks.MYSTRYX_ORE, "mystryx_ore");
		MYSTRYX_INGOT = register("mystryx_ingot", MystryxIngotItem::new);
		MYSTRYX_BLOCK = block(MystryxmodModBlocks.MYSTRYX_BLOCK, "mystryx_block");
		MYSTRYX_HELMET = register("mystryx_helmet", MystryxItem.Helmet::new);
		MYSTRYX_CHESTPLATE = register("mystryx_chestplate", MystryxItem.Chestplate::new);
		MYSTRYX_LEGGINGS = register("mystryx_leggings", MystryxItem.Leggings::new);
		MYSTRYX_BOOTS = register("mystryx_boots", MystryxItem.Boots::new);
		MYSTRYX_SWORD = register("mystryx_sword", MystryxSwordItem::new);
		MYSTRYX_PICKAXE = register("mystryx_pickaxe", MystryxPickaxeItem::new);
		DARK_MYSTRYX_ORE = block(MystryxmodModBlocks.DARK_MYSTRYX_ORE, "dark_mystryx_ore", new Item.Properties().rarity(Rarity.RARE));
		DARK_MYSTRYX_INGOT = register("dark_mystryx_ingot", DarkMystryxIngotItem::new);
		MYSELIUM = register("myselium", MyseliumItem::new);
		DARK_MYSTRYX_SWORD = register("dark_mystryx_sword", DarkMystryxSwordItem::new);
		DARK_MYSTRYX_PICKAXE = register("dark_mystryx_pickaxe", DarkMystryxPickaxeItem::new);
	}

	// Start of user code block custom items
	// End of user code block custom items
	private static <I extends Item> I register(String name, Function<Item.Properties, ? extends I> supplier) {
		return (I) Items.registerItem(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MystryxmodMod.MODID, name)), (Function<Item.Properties, Item>) supplier);
	}

	private static Item block(Block block, String name) {
		return block(block, name, new Item.Properties());
	}

	private static Item block(Block block, String name, Item.Properties properties) {
		return Items.registerItem(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MystryxmodMod.MODID, name)), prop -> new BlockItem(block, prop), properties);
	}
}