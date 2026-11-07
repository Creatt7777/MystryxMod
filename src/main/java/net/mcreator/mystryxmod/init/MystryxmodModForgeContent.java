package net.mcreator.mystryxmod.init;

import net.minecraft.network.chat.Component;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;

import net.mcreator.mystryxmod.MystryxmodMod;
import net.mcreator.mystryxmod.block.ForgeFurnaceBlock;
import net.mcreator.mystryxmod.block.entity.ForgeFurnaceBlockEntity;
import net.mcreator.mystryxmod.block.DarkMystryxOreBlock;
import net.mcreator.mystryxmod.inventory.ForgeFurnaceMenu;
import net.mcreator.mystryxmod.item.BurnedMystryxIngotItem;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;

import java.util.function.Function;

/**
 * Contenu "Forge Furnace" : Forge Furnace (bloc + entite + menu), Burned Mystryx Ingot
 * et 2e veine de Dark Mystryx Ore dans le Nether.
 * Classe volontairement separee des fichiers MCreator (qui sont regeneres) ;
 * elle est appelee depuis le bloc "mod init" de MystryxmodMod.
 */
public class MystryxmodModForgeContent {
	public static Block FORGE_FURNACE;
	public static Item FORGE_FURNACE_ITEM;
	public static Item BURNED_MYSTRYX_INGOT;
	public static BlockEntityType<ForgeFurnaceBlockEntity> FORGE_FURNACE_ENTITY;
	public static MenuType<ForgeFurnaceMenu> FORGE_FURNACE_MENU;

	public static void load() {
		// Bloc
		FORGE_FURNACE = Blocks.register(ResourceKey.create(Registries.BLOCK, id("forge_furnace")), (Function<BlockBehaviour.Properties, Block>) ForgeFurnaceBlock::new, BlockBehaviour.Properties.of());
		// Items
		FORGE_FURNACE_ITEM = Items.registerItem(ResourceKey.create(Registries.ITEM, id("forge_furnace")), prop -> new BlockItem(FORGE_FURNACE, prop), new Item.Properties().component(DataComponents.ITEM_NAME, Component.literal("Forge Furnace")));
		BURNED_MYSTRYX_INGOT = Items.registerItem(ResourceKey.create(Registries.ITEM, id("burned_mystryx_ingot")), prop -> new BurnedMystryxIngotItem(prop.component(DataComponents.ITEM_NAME, Component.literal("Burned Mystryx Ingot"))));
		// Entite de bloc + menu
		FORGE_FURNACE_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id("forge_furnace"), FabricBlockEntityTypeBuilder.create(ForgeFurnaceBlockEntity::new, FORGE_FURNACE).build());
		FORGE_FURNACE_MENU = Registry.register(BuiltInRegistries.MENU, id("forge_furnace"), new MenuType<>(ForgeFurnaceMenu::new, FeatureFlags.VANILLA_SET));

		// Onglet creatif
		CreativeModeTabEvents.modifyOutputEvent(MystryxmodModTabs.TAB_MYSTRYX_MOD).register(tabData -> {
			tabData.accept(BURNED_MYSTRYX_INGOT);
			tabData.accept(FORGE_FURNACE_ITEM);
		});

		// Generation : 2e veine (grosse) de Dark Mystryx Ore dans le Nether (la petite veine est enregistree par MystryxmodModFeatures)
		BiomeModifications.addFeature(DarkMystryxOreBlock.GENERATE_BIOMES, GenerationStep.Decoration.UNDERGROUND_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE, id("dark_mystryx_ore_large")));
	}

	private static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MystryxmodMod.MODID, path);
	}
}
