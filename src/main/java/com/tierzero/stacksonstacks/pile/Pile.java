package com.tierzero.stacksonstacks.pile;

import com.tierzero.stacksonstacks.util.ConfigHandler;
import com.tierzero.stacksonstacks.util.StackUtils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.client.audio.SoundList.SoundEntry;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;

public class Pile {

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
			
			if(type == 2) {
				player.worldObj.playSoundEffect(x, y, z, "dig.sand", .75f, player.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			} else {
				player.worldObj.playSoundEffect(x, y, z, "dig.stone", .75f, player.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}
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
			if(type == 2) {
				world.playSoundEffect(x, y, z, "dig.sand", .75f, player.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			} else {
				world.playSoundEffect(x, y, z, "random.pop", .25f, player.worldObj.rand.nextFloat() * 0.1F + 0.9F);
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
	}

	public void readFromNBT(NBTTagCompound tag) {

		NBTTagCompound inventoryTag = tag.getCompoundTag(TAG_PILE_STACK);
		type = tag.getInteger(TAG_TYPE);
		pileStack = ItemStack.loadItemStackFromNBT(inventoryTag);
		if (tag.getTag(TAG_PILE_STACKSIZE) != null) {
			pileStack.stackSize = tag.getInteger(TAG_PILE_STACKSIZE);
		}
		
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
			tag.setInteger(TAG_PILE_STACKSIZE, pileStack.stackSize);
			tag.setTag(TAG_PILE_STACK, inventoryTag);

		}
	}

	public void setPosition(int xCoord, int yCoord, int zCoord) {
		this.x = xCoord;
		this.y = yCoord;
		this.z = zCoord;
	}

	public int getMaxStored() {
		switch(type) {
		case 0: 
			return ConfigHandler.maxIngotStackSize;
		case 1:
			return ConfigHandler.maxGemStackSize;
		case 2:
			return ConfigHandler.maxDustStackSize;
		}
		
		return 64;
	}
}
