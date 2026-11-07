package net.mcreator.mystryxmod.item;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.Registries;

public class DarkMystryxPickaxeItem extends Item {
	private static final ToolMaterial TOOL_MATERIAL = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 8000, 32f, 0, 18, TagKey.create(Registries.ITEM, Identifier.parse("mystryxmod:dark_mystryx_pickaxe_repair_items")));

	public DarkMystryxPickaxeItem(Item.Properties properties) {
		super(properties.pickaxe(TOOL_MATERIAL, 6f, -3f).rarity(Rarity.EPIC));
	}
}
