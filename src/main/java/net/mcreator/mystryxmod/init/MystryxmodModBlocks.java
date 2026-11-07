/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.mystryxmod.init;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.Registries;

import net.mcreator.mystryxmod.block.MystryxOreBlock;
import net.mcreator.mystryxmod.block.MystryxBlockBlock;
import net.mcreator.mystryxmod.block.DarkMystryxOreBlock;
import net.mcreator.mystryxmod.MystryxmodMod;

import java.util.function.Function;

public class MystryxmodModBlocks {
	public static Block MYSTRYX_ORE;
	public static Block MYSTRYX_BLOCK;
	public static Block DARK_MYSTRYX_ORE;

	public static void load() {
		MYSTRYX_ORE = register("mystryx_ore", MystryxOreBlock::new);
		MYSTRYX_BLOCK = register("mystryx_block", MystryxBlockBlock::new);
		DARK_MYSTRYX_ORE = register("dark_mystryx_ore", DarkMystryxOreBlock::new);
	}

	// Start of user code block custom blocks
	// End of user code block custom blocks
	private static <B extends Block> B register(String name, Function<BlockBehaviour.Properties, B> supplier) {
		return (B) Blocks.register(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MystryxmodMod.MODID, name)), (Function<BlockBehaviour.Properties, Block>) supplier, BlockBehaviour.Properties.of());
	}
}