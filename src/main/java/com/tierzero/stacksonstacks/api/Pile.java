package com.tierzero.stacksonstacks.api;

import com.tierzero.stacksonstacks.util.StackUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class Pile {

	private int MAX_STACK_SIZE = 64;
	private static final String TAG_PILE_STACK = "inventory";
	private static final String TAG_PILE_STACKSIZE = "stacksize";
	private static final String TAG_TYPE = "pile_type";
	private ItemStack pileStack;
	private int type;
	private float x;
	private float y;
	private float z;

	public Pile(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getType() {
		return type;
	}

	public ItemStack getPileStack() {
		return pileStack;
	}

	public int getAmountStored() {
		if (pileStack != null) {
			return pileStack.stackSize;
		}

		return 0;
	}

	private boolean shouldUseEntireStack(EntityPlayer player) {
		return player.isSneaking();
	}

	public void onLeftClicked(World world, EntityPlayer player) {
		removeFromPile(world, player, shouldUseEntireStack(player));
	}

	public boolean onRightClicked(EntityPlayer player, ItemStack stack) {
		if (stack != null) {

			if (pileStack == null && (getType() != -1 && PileItemRegistry.isValidPileItem(stack))) {
				createPile(player, stack, shouldUseEntireStack(player));
				return true;
			} else if (pileStack != null && pileStack.getItem() != null && pileStack.isItemEqual(stack)) {
				addToPile(player, stack, shouldUseEntireStack(player));
				return true;
			}

			return false;
		}

		return false;

	}

	public void createPile(EntityPlayer player, ItemStack stack, boolean entireStack) {
		int initialAmount = entireStack ? stack.stackSize : 1;
		pileStack = StackUtils.getItemsFromStack(stack, initialAmount);
		findType(stack);
		StackUtils.decrementStack(player, stack, initialAmount);
	}

	public void addToPile(EntityPlayer player, ItemStack stack, boolean entireStack) {
		int remainingSpace = getMaxStored() - pileStack.stackSize;
		if (remainingSpace != 0) {
			int amountToAdd = 1;

			if (entireStack) {
				amountToAdd = Math.min(remainingSpace, stack.stackSize);
			}
			pileStack.stackSize += amountToAdd;
			StackUtils.decrementStack(player, stack, amountToAdd);
		}
	}

	public void removeFromPile(World world, EntityPlayer player, boolean entireStack) {
		if (pileStack != null) {
			int amountToRemove = 1;

			if (entireStack) {
				amountToRemove = pileStack.stackSize;
			}

			ItemStack stackToDrop = StackUtils.getItemsFromStack(pileStack, amountToRemove);
			StackUtils.decrementStack(pileStack, amountToRemove);

			boolean addedToPlayer = player.inventory.addItemStackToInventory(stackToDrop);

			if (!addedToPlayer) {
				StackUtils.spawnItemInWorld(world, (int) x, (int) y, (int) z, stackToDrop);
			}
		}
	}

	public void debugCreatePile(ItemStack stack) {
		findType(stack);
		pileStack = stack;
		pileStack.stackSize = getMaxStored() / 2;
	}

	public void findType(ItemStack stack) {

		type = PileItemRegistry.getPileType(stack);

		if (type == 1)
			MAX_STACK_SIZE = 256;
		else
			MAX_STACK_SIZE = 64;

	}

	public void readFromNBT(NBTTagCompound tag) {

		NBTTagCompound inventoryTag = tag.getCompoundTag(TAG_PILE_STACK);
		type = tag.getInteger(TAG_TYPE);
		pileStack = ItemStack.loadItemStackFromNBT(inventoryTag);
		if (tag.getTag(TAG_PILE_STACKSIZE) != null)
			pileStack.stackSize = tag.getInteger(TAG_PILE_STACKSIZE);
		// If null then its probably saved as the pre 0.9.5 nbt format
		if (pileStack == null) {
			// Will remove eventually, to stop breaking of existing ingot piles
			pileStack = StackUtils.getStackFromInfo(tag.getInteger("ingot"), tag.getByte("stackSize"),
					tag.getInteger("meta"));
		}

	}

	public void writeToNBT(NBTTagCompound tag) {
		NBTTagCompound inventoryTag = new NBTTagCompound();
		tag.setInteger(TAG_TYPE, this.type);
		if (pileStack != null) {
			pileStack.writeToNBT(inventoryTag);
			if (inventoryTag.getByte("Count") != 0 && type == 0)
				pileStack.stackSize = inventoryTag.getByte("Count");
			tag.setInteger(TAG_PILE_STACKSIZE, pileStack.stackSize);
			tag.setTag(TAG_PILE_STACK, inventoryTag);

		}
	}

	public void setX(int xCoord) {
		this.x = xCoord;
	}

	public void setY(int yCoord) {
		this.y = yCoord;
	}

	public void setZ(int zCoord) {
		this.z = zCoord;
	}

	public int getMaxStored() {
		return MAX_STACK_SIZE;
	}

}
