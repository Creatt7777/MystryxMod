package net.mcreator.mystryxmod.block.entity;

import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.Component;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.mcreator.mystryxmod.init.MystryxmodModItems;
import net.mcreator.mystryxmod.init.MystryxmodModForgeContent;
import net.mcreator.mystryxmod.inventory.ForgeFurnaceMenu;
import net.mcreator.mystryxmod.block.ForgeFurnaceBlock;

public class ForgeFurnaceBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
	public static final int SLOT_INPUT = 0;
	public static final int SLOT_FUEL = 1;
	public static final int SLOT_OUTPUT = 2;

	/** Duree de combustion d'un lingot de netherite (ticks). 2 lingots = 200 ticks = 1 cuisson. */
	public static final int BURN_TIME_PER_NETHERITE = 100;
	/** Temps de cuisson d'un Mystryx Ingot (ticks). */
	public static final int COOK_TIME_TOTAL = 200;

	private NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
	private int litTimeRemaining;
	private int litTotalTime;
	private int cookingProgress;
	private int cookingTotalTime = COOK_TIME_TOTAL;

	protected final ContainerData dataAccess = new ContainerData() {
		@Override
		public int get(int index) {
			return switch (index) {
				case 0 -> litTimeRemaining;
				case 1 -> litTotalTime;
				case 2 -> cookingProgress;
				case 3 -> cookingTotalTime;
				default -> 0;
			};
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
				case 0 -> litTimeRemaining = value;
				case 1 -> litTotalTime = value;
				case 2 -> cookingProgress = value;
				case 3 -> cookingTotalTime = value;
				default -> {
				}
			}
		}

		@Override
		public int getCount() {
			return 4;
		}
	};

	public ForgeFurnaceBlockEntity(BlockPos pos, BlockState state) {
		super(MystryxmodModForgeContent.FORGE_FURNACE_ENTITY, pos, state);
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return items;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> list) {
		this.items = list;
	}

	@Override
	public int getContainerSize() {
		return 3;
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("container.mystryxmod.forge_furnace");
	}

	@Override
	protected AbstractContainerMenu createMenu(int containerId, Inventory playerInventory) {
		return new ForgeFurnaceMenu(containerId, playerInventory, this, this.dataAccess);
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);
		this.litTimeRemaining = input.getIntOr("LitTimeRemaining", 0);
		this.litTotalTime = input.getIntOr("LitTotalTime", 0);
		this.cookingProgress = input.getIntOr("CookingProgress", 0);
		this.cookingTotalTime = input.getIntOr("CookingTotalTime", COOK_TIME_TOTAL);
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);
		output.putInt("LitTimeRemaining", this.litTimeRemaining);
		output.putInt("LitTotalTime", this.litTotalTime);
		output.putInt("CookingProgress", this.cookingProgress);
		output.putInt("CookingTotalTime", this.cookingTotalTime);
	}

	public static boolean isInput(ItemStack stack) {
		return stack.is(MystryxmodModItems.MYSTRYX_INGOT);
	}

	public static boolean isFuel(ItemStack stack) {
		return stack.is(Items.NETHERITE_INGOT);
	}

	private boolean canBurn() {
		ItemStack input = this.items.get(SLOT_INPUT);
		if (input.isEmpty() || !isInput(input))
			return false;
		ItemStack output = this.items.get(SLOT_OUTPUT);
		if (output.isEmpty())
			return true;
		return output.is(MystryxmodModForgeContent.BURNED_MYSTRYX_INGOT) && output.getCount() < output.getMaxStackSize();
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, ForgeFurnaceBlockEntity be) {
		boolean wasLit = be.litTimeRemaining > 0;
		boolean changed = false;
		if (wasLit)
			be.litTimeRemaining--;

		boolean canBurn = be.canBurn();
		ItemStack fuel = be.items.get(SLOT_FUEL);

		// Allume le four : consomme 1 lingot de netherite (100 ticks)
		if (be.litTimeRemaining <= 0 && canBurn && isFuel(fuel)) {
			be.litTimeRemaining = BURN_TIME_PER_NETHERITE;
			be.litTotalTime = BURN_TIME_PER_NETHERITE;
			fuel.shrink(1);
			changed = true;
		}

		if (be.litTimeRemaining > 0 && canBurn) {
			be.cookingProgress++;
			if (be.cookingProgress >= be.cookingTotalTime) {
				be.cookingProgress = 0;
				be.items.get(SLOT_INPUT).shrink(1);
				ItemStack output = be.items.get(SLOT_OUTPUT);
				if (output.isEmpty())
					be.items.set(SLOT_OUTPUT, new ItemStack(MystryxmodModForgeContent.BURNED_MYSTRYX_INGOT));
				else
					output.grow(1);
			}
			changed = true;
		} else if (be.cookingProgress > 0) {
			be.cookingProgress = Mth.clamp(be.cookingProgress - 2, 0, be.cookingTotalTime);
			changed = true;
		}

		boolean isLit = be.litTimeRemaining > 0;
		if (wasLit != isLit) {
			state = state.setValue(ForgeFurnaceBlock.LIT, isLit);
			level.setBlock(pos, state, 3);
			changed = true;
		}
		if (changed)
			setChanged(level, pos, state);
	}

	// --- Hoppers / automatisation ---
	@Override
	public boolean canPlaceItem(int slot, ItemStack stack) {
		return switch (slot) {
			case SLOT_INPUT -> isInput(stack);
			case SLOT_FUEL -> isFuel(stack);
			default -> false;
		};
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		if (side == Direction.DOWN)
			return new int[]{SLOT_OUTPUT};
		if (side == Direction.UP)
			return new int[]{SLOT_INPUT};
		return new int[]{SLOT_FUEL};
	}

	@Override
	public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction direction) {
		return canPlaceItem(slot, stack);
	}

	@Override
	public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
		return slot == SLOT_OUTPUT;
	}
}
