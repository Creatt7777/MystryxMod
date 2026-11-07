package net.mcreator.mystryxmod.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;

import java.util.function.Predicate;

public class DarkMystryxOreBlock extends Block {
	public DarkMystryxOreBlock(BlockBehaviour.Properties properties) {
		super(properties.strength(4f, 20f).requiresCorrectToolForDrops());
	}

	public static final Predicate<BiomeSelectionContext> GENERATE_BIOMES = BiomeSelectors.foundInTheNether();
}
