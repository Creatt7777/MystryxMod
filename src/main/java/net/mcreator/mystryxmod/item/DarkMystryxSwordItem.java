package net.mcreator.mystryxmod.item;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.Registries;

public class DarkMystryxSwordItem extends Item {
	private static final ToolMaterial TOOL_MATERIAL = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 8000, 14f, 0, 18, TagKey.create(Registries.ITEM, Identifier.parse("mystryxmod:dark_mystryx_sword_repair_items")));

	public DarkMystryxSwordItem(Item.Properties properties) {
		super(properties.sword(TOOL_MATERIAL, 9f, -2.1f).rarity(Rarity.EPIC));
	}
}
