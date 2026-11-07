package net.mcreator.mystryxmod.item;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.Registries;

public class MystryxSwordItem extends Item {
	private static final ToolMaterial TOOL_MATERIAL = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 5000, 12f, 0, 15, TagKey.create(Registries.ITEM, Identifier.parse("mystryxmod:mystryx_sword_repair_items")));

	public MystryxSwordItem(Item.Properties properties) {
		super(properties.sword(TOOL_MATERIAL, 7f, -2.1f).rarity(Rarity.EPIC));
	}
}