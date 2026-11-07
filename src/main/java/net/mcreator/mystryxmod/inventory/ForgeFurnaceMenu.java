package net.mcreator.mystryxmod.inventory;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.Container;

import net.mcreator.mystryxmod.init.MystryxmodModForgeContent;
import net.mcreator.mystryxmod.block.entity.ForgeFurnaceBlockEntity;

public class ForgeFurnaceMenu extends AbstractContainerMenu {
	private final Container container;
	private final ContainerData data;

	/** Constructeur cote client. */
	public ForgeFurnaceMenu(int containerId, Inventory playerInventory) {
		this(containerId, playerInventory, new SimpleContainer(3), new SimpleContainerData(4));
	}

	/** Constructeur cote serveur. */
	public ForgeFurnaceMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
		super(MystryxmodModForgeContent.FORGE_FURNACE_MENU, containerId);
		this.container = container;
		this.data = data;

		// 0 : Mystryx Ingot (haut), 1 : netherite (bas), 2 : resultat
		this.addSlot(new Slot(container, ForgeFurnaceBlockEntity.SLOT_INPUT, 56, 17) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return ForgeFurnaceBlockEntity.isInput(stack);
			}
		});
		this.addSlot(new Slot(container, ForgeFurnaceBlockEntity.SLOT_FUEL, 56, 53) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return ForgeFurnaceBlockEntity.isFuel(stack);
			}
		});
		this.addSlot(new Slot(container, ForgeFurnaceBlockEntity.SLOT_OUTPUT, 116, 35) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}
		});

		for (int row = 0; row < 3; ++row)
			for (int col = 0; col < 9; ++col)
				this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
		for (int col = 0; col < 9; ++col)
			this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));

		this.addDataSlots(data);
	}

	@Override
	public boolean stillValid(Player player) {
		return this.container.stillValid(player);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack result = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack stack = slot.getItem();
			result = stack.copy();
			if (index == ForgeFurnaceBlockEntity.SLOT_OUTPUT) {
				if (!this.moveItemStackTo(stack, 3, 39, true))
					return ItemStack.EMPTY;
				slot.onQuickCraft(stack, result);
			} else if (index >= 3) {
				if (ForgeFurnaceBlockEntity.isInput(stack)) {
					if (!this.moveItemStackTo(stack, 0, 1, false))
						return ItemStack.EMPTY;
				} else if (ForgeFurnaceBlockEntity.isFuel(stack)) {
					if (!this.moveItemStackTo(stack, 1, 2, false))
						return ItemStack.EMPTY;
				} else if (index < 30) {
					if (!this.moveItemStackTo(stack, 30, 39, false))
						return ItemStack.EMPTY;
				} else if (!this.moveItemStackTo(stack, 3, 30, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(stack, 3, 39, false)) {
				return ItemStack.EMPTY;
			}
			if (stack.isEmpty())
				slot.setByPlayer(ItemStack.EMPTY);
			else
				slot.setChanged();
			if (stack.getCount() == result.getCount())
				return ItemStack.EMPTY;
			slot.onTake(player, stack);
		}
		return result;
	}

	public boolean isLit() {
		return this.data.get(0) > 0;
	}

	/** Hauteur de la flamme (0..14). */
	public int getLitProgress() {
		int total = this.data.get(1);
		if (total == 0)
			total = ForgeFurnaceBlockEntity.BURN_TIME_PER_NETHERITE;
		return this.data.get(0) * 14 / total;
	}

	/** Largeur de la fleche (0..24). */
	public int getBurnProgress() {
		int progress = this.data.get(2);
		int total = this.data.get(3);
		return total != 0 && progress != 0 ? progress * 24 / total : 0;
	}
}
