package net.mcreator.mystryxmod.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class MystryxIngotItem extends Item {
	public MystryxIngotItem(Item.Properties properties) {
		super(properties.rarity(Rarity.EPIC));
	}
}