package net.mcreator.mystryxmod.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class DarkMystryxIngotItem extends Item {
	public DarkMystryxIngotItem(Item.Properties properties) {
		super(properties.rarity(Rarity.EPIC));
	}
}
