package net.mcreator.mystryxmod.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

public class MystryxBlockBlock extends Block {
	public MystryxBlockBlock(BlockBehaviour.Properties properties) {
		super(properties.strength(1f, 10f));
	}
}