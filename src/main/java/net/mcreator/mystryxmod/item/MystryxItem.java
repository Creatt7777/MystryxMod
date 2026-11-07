package net.mcreator.mystryxmod.item;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;

import net.mcreator.mystryxmod.procedures.MystryxBootsTickEventProcedure;

import java.util.Map;

public abstract class MystryxItem extends Item {
	public static ArmorMaterial ARMOR_MATERIAL = new ArmorMaterial(15, Map.of(ArmorType.BOOTS, 4, ArmorType.LEGGINGS, 6, ArmorType.CHESTPLATE, 8, ArmorType.HELMET, 1, ArmorType.BODY, 8), 18,
			BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.EMPTY), 3f, 0.15f, TagKey.create(Registries.ITEM, Identifier.parse("mystryxmod:mystryx_repair_items")),
			ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.parse("mystryxmod:mystryx")));

	private MystryxItem(Item.Properties properties) {
		super(properties);
	}

	public static class Helmet extends MystryxItem {
		public Helmet(Item.Properties properties) {
			super(properties.rarity(Rarity.EPIC).humanoidArmor(ARMOR_MATERIAL, ArmorType.HELMET));
		}
	}

	public static class Chestplate extends MystryxItem {
		public Chestplate(Item.Properties properties) {
			super(properties.rarity(Rarity.EPIC).humanoidArmor(ARMOR_MATERIAL, ArmorType.CHESTPLATE));
		}
	}

	public static class Leggings extends MystryxItem {
		public Leggings(Item.Properties properties) {
			super(properties.rarity(Rarity.EPIC).humanoidArmor(ARMOR_MATERIAL, ArmorType.LEGGINGS));
		}
	}

	public static class Boots extends MystryxItem {
		public Boots(Item.Properties properties) {
			super(properties.rarity(Rarity.EPIC).humanoidArmor(ARMOR_MATERIAL, ArmorType.BOOTS));
		}

		@Override
		public void inventoryTick(ItemStack itemstack, ServerLevel world, Entity entity, @Nullable EquipmentSlot equipmentSlot) {
			super.inventoryTick(itemstack, world, entity, equipmentSlot);
			if (entity instanceof Player player && (equipmentSlot != null && equipmentSlot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR)) {
				MystryxBootsTickEventProcedure.execute(entity);
			}
		}
	}
}