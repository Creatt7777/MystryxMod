package net.mcreator.mystryxmod.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Final;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.client.resources.model.EquipmentAssetManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.model.Model;

import net.mcreator.mystryxmod.init.MystryxmodModArmorModels;

import java.util.function.Function;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

@Mixin(EquipmentLayerRenderer.class)
public abstract class EquipmentLayerRendererMixin {
	@Shadow
	@Final
	private EquipmentAssetManager equipmentAssets;
	@Shadow
	@Final
	private Function<EquipmentLayerRenderer.LayerTextureKey, Identifier> layerTextureLookup;
	@Shadow
	@Final
	private Function<EquipmentLayerRenderer.TrimSpriteKey, TextureAtlasSprite> trimSpriteLookup;

	@Inject(method = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;II)V", at = @At("HEAD"), cancellable = true)
	public <S> void renderLayers(EquipmentClientInfo.LayerType layerType, ResourceKey<EquipmentAsset> resourceKey, Model<? super S> model, S state, ItemStack itemStack, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords,
			@Nullable Identifier playerTextureOverride, int outlineColor, int order, CallbackInfo ci) {
		if (!MystryxmodModArmorModels.ARMOR_MODELS.containsKey(itemStack.getItem()) || layerType == EquipmentClientInfo.LayerType.WINGS)
			return;
		MystryxmodModArmorModels.ArmorModel armorModel = MystryxmodModArmorModels.ARMOR_MODELS.get(itemStack.getItem());
		if (armorModel.getHumanoidArmorModel(itemStack, layerType, model) != null)
			model = armorModel.getGenericArmorModel(itemStack, layerType, model);
		List<EquipmentClientInfo.Layer> layers = this.equipmentAssets.get(resourceKey).getLayers(layerType);
		if (layers.isEmpty()) {
			ci.cancel();
			return;
		}
		int dyeColor = DyedItemColor.getOrDefault(itemStack, 0);
		boolean renderFoil = itemStack.hasFoil();
		int nextOrder = order;
		for (EquipmentClientInfo.Layer layer : layers) {
			int color = ((EquipmentLayerRenderer) (Object) this).getColorForLayer(layer, dyeColor);
			if (color != 0) {
				Identifier layerTexture = layer.usePlayerTexture() && playerTextureOverride != null ? playerTextureOverride : this.layerTextureLookup.apply(new EquipmentLayerRenderer.LayerTextureKey(layerType, layer));
				submitNodeCollector.order(nextOrder++).submitModel(model, state, poseStack, RenderTypes.armorCutoutNoCull(layerTexture), lightCoords, OverlayTexture.NO_OVERLAY, color, null, outlineColor, null);
				if (renderFoil) {
					submitNodeCollector.order(nextOrder++).submitModel(model, state, poseStack, RenderTypes.armorEntityGlint(), lightCoords, OverlayTexture.NO_OVERLAY, color, null, outlineColor, null);
				}
				renderFoil = false;
			}
		}
		ArmorTrim trim = (ArmorTrim) itemStack.get(DataComponents.TRIM);
		if (trim != null && layerType != EquipmentClientInfo.LayerType.HUMANOID_BABY) {
			TextureAtlasSprite sprite = this.trimSpriteLookup.apply(new EquipmentLayerRenderer.TrimSpriteKey(trim, layerType, resourceKey));
			RenderType renderType = Sheets.armorTrimsSheet(trim.pattern().value().decal());
			submitNodeCollector.order(order++).submitModel(model, state, poseStack, renderType, lightCoords, OverlayTexture.NO_OVERLAY, -1, sprite, outlineColor, (ModelFeatureRenderer.CrumblingOverlay) null);
		}
		ci.cancel();
	}
}