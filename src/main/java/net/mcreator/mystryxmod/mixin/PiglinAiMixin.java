package net.mcreator.mystryxmod.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.EquipmentSlot;

@Mixin(PiglinAi.class)
public abstract class PiglinAiMixin {
	@Inject(method = "isWearingSafeArmor(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
	private static void isWearingSafeArmor(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
		for (EquipmentSlot equipmentslot : EquipmentSlotGroup.ARMOR) {
		}
	}
}