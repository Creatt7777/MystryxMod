package net.mcreator.mystryxmod.item;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

/**
 * Myselium: right click = short dash in the direction you are looking at.
 * - 10 second cooldown (200 ticks)
 * - 10 uses before it breaks
 *
 * This element is "code locked" in MCreator so this file is never overwritten.
 */
public class MyseliumItem extends Item {
	public static final int MAX_USES = 10;
	public static final int COOLDOWN_TICKS = 10 * 20;
	public static final double DASH_STRENGTH = 1.6;
	public static final double DASH_LIFT = 0.2;

	public MyseliumItem(Item.Properties properties) {
		super(properties.durability(MAX_USES).rarity(Rarity.EPIC));
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		// cooldown first (the stack may disappear when it breaks below)
		player.getCooldowns().addCooldown(stack, COOLDOWN_TICKS);
		if (level instanceof ServerLevel serverLevel) {
			Vec3 look = player.getLookAngle();
			Vec3 dash = new Vec3(look.x, 0, look.z).normalize().scale(DASH_STRENGTH).add(0, DASH_LIFT, 0);
			player.setDeltaMovement(dash);
			player.hurtMarked = true; // send the new velocity to the client
			serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.0f, 1.0f);
			serverLevel.sendParticles(ParticleTypes.CLOUD, player.getX(), player.getY() + 0.1, player.getZ(), 12, 0.3, 0.1, 0.3, 0.02);
			stack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
		}
		return InteractionResult.SUCCESS;
	}
}
