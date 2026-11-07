package net.mcreator.mystryxmod.client.renderer.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.Identifier;
import net.minecraft.client.resources.model.EquipmentClientInfo;

import net.mcreator.mystryxmod.init.MystryxmodModItems;
import net.mcreator.mystryxmod.init.MystryxmodModArmorModels;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class MystryxArmor {
	public static void clientLoad() {
		MystryxmodModArmorModels.ARMOR_MODELS.put(MystryxmodModItems.MYSTRYX_HELMET, new MystryxmodModArmorModels.ArmorModel() {
			private final Identifier armorTexture = Identifier.parse("mystryxmod:textures/entity/equipment/humanoid/amethyst_layer_1.png");

			@Override
			public Identifier getArmorTexture(ItemStack stack, EquipmentClientInfo.LayerType type, EquipmentClientInfo.Layer layer, Identifier original) {
				return armorTexture;
			}
		});
		MystryxmodModArmorModels.ARMOR_MODELS.put(MystryxmodModItems.MYSTRYX_CHESTPLATE, new MystryxmodModArmorModels.ArmorModel() {
			private final Identifier armorTexture = Identifier.parse("mystryxmod:textures/entity/equipment/humanoid/amethyst_layer_1.png");

			@Override
			public Identifier getArmorTexture(ItemStack stack, EquipmentClientInfo.LayerType type, EquipmentClientInfo.Layer layer, Identifier original) {
				return armorTexture;
			}
		});
		MystryxmodModArmorModels.ARMOR_MODELS.put(MystryxmodModItems.MYSTRYX_LEGGINGS, new MystryxmodModArmorModels.ArmorModel() {
			private final Identifier armorTexture = Identifier.parse("mystryxmod:textures/entity/equipment/humanoid_leggings/amethyst_layer_2.png");

			@Override
			public Identifier getArmorTexture(ItemStack stack, EquipmentClientInfo.LayerType type, EquipmentClientInfo.Layer layer, Identifier original) {
				return armorTexture;
			}
		});
		MystryxmodModArmorModels.ARMOR_MODELS.put(MystryxmodModItems.MYSTRYX_BOOTS, new MystryxmodModArmorModels.ArmorModel() {
			private final Identifier armorTexture = Identifier.parse("mystryxmod:textures/entity/equipment/humanoid/amethyst_layer_1.png");

			@Override
			public Identifier getArmorTexture(ItemStack stack, EquipmentClientInfo.LayerType type, EquipmentClientInfo.Layer layer, Identifier original) {
				return armorTexture;
			}
		});
	}
}