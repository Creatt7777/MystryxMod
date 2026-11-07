package net.mcreator.mystryxmod.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.Registries;

import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;

import java.util.function.Predicate;

public class MystryxOreBlock extends Block {
	public MystryxOreBlock(BlockBehaviour.Properties properties) {
		super(properties.strength(1f, 10f));
	}

	public static final Predicate<BiomeSelectionContext> GENERATE_BIOMES = BiomeSelectors.includeByKey(ResourceKey.create(Registries.BIOME, Identifier.parse("dark_forest")));
}