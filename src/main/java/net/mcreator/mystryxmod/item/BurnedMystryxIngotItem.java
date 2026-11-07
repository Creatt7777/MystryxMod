package net.mcreator.mystryxmod.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class BurnedMystryxIngotItem extends Item {
	public BurnedMystryxIngotItem(Item.Properties properties) {
		super(properties.rarity(Rarity.RARE));
	}
}
