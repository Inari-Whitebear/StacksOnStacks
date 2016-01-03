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
	private ItemStack itemStack;
	private int type;

	public void onLeftClicked(World world, EntityPlayer player, int pilePosX, int pilePosY, int pilePosZ) {
		removeFromPile(world, player, pilePosX, pilePosY, pilePosZ);
	}
	
	public boolean onRightClicked(EntityPlayer player, ItemStack stack) {
		if (stack != null) {
			if (itemStack == null && (getType() != -1 && PileItemRegistry.isValidPileItem(stack))) {
				createPile(player, stack);
				return true;
			} else if (itemStack != null && itemStack.getItem() != null && itemStack.isItemEqual(stack)) {
				addToPile(player, stack);
				return true;
			}
		}

		return false;
	}

	private void createPile(EntityPlayer player, ItemStack stack) {
		int initialAmount = shouldUseEntireStack(player) ? stack.stackSize : 1;
		itemStack = StackUtils.getItemsFromStack(stack, initialAmount);
		findType(stack);
		StackUtils.decrementStack(player, stack, initialAmount);
		playSoundPlaced(player);
	}

	private void addToPile(EntityPlayer player, ItemStack stack) {
		int remainingSpace = getMaxStored() - itemStack.stackSize;
		if (remainingSpace != 0) {
			int amountToAdd = shouldUseEntireStack(player) ? Math.min(remainingSpace, stack.stackSize) : 1;

			itemStack.stackSize += amountToAdd;
			StackUtils.decrementStack(player, stack, amountToAdd);
			
			playSoundPlaced(player);
		}
	}
	
	private void removeFromPile(World world, EntityPlayer player, int pilePosX, int pilePosY, int pilePosZ) {
		if (itemStack != null) {
			int amountToRemove = shouldUseEntireStack(player) ? itemStack.stackSize : 1;

			ItemStack stackToDrop = StackUtils.getItemsFromStack(itemStack, amountToRemove);
			StackUtils.decrementStack(itemStack, amountToRemove);

			boolean couldPlaceStackInPlayerInventory = player.inventory.addItemStackToInventory(stackToDrop);

			if (!couldPlaceStackInPlayerInventory) {
				StackUtils.spawnItemInWorld(world, pilePosX, pilePosY, pilePosZ, stackToDrop);
			}
			
			playSoundRemoved(player);
		}
	}
	
	private void playSoundPlaced(EntityPlayer player) {
		if(type == 2) {
			player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "dig.sand", .75f, player.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		} else {
			player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "dig.stone", .75f, player.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}
	}
	
	private void playSoundRemoved(EntityPlayer player) {
		if(type == 2) {
			player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "dig.sand", .75f, player.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		} else {
			player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "random.pop", .25f, player.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}
	}
	
	private boolean shouldUseEntireStack(EntityPlayer player) {
		return player.isSneaking();
	}
		
	private void findType(ItemStack stack) {
		type = PileItemRegistry.getPileType(stack);
	}
	
	public int getMaxStored() {
		switch(type) {
		case 0: 
			return ConfigHandler.maxIngotStackSize;
		case 1:
			return ConfigHandler.maxGemStackSize;
		case 2:
			return ConfigHandler.maxDustStackSize;
		default:
			return 64;
		}
	}
	
	public void readFromNBT(NBTTagCompound tag) {
		NBTTagCompound inventoryTag = tag.getCompoundTag(TAG_PILE_STACK);
		
		this.type = tag.getInteger(TAG_TYPE);
		this.itemStack = ItemStack.loadItemStackFromNBT(inventoryTag);
		this.itemStack.stackSize = tag.getInteger(TAG_PILE_STACKSIZE);
	}

	public void writeToNBT(NBTTagCompound tag) {
		NBTTagCompound inventoryTag = new NBTTagCompound();
		if (itemStack != null) {
			itemStack.writeToNBT(inventoryTag);
			tag.setInteger(TAG_PILE_STACKSIZE, itemStack.stackSize);
			tag.setTag(TAG_PILE_STACK, inventoryTag);
		}
		
		tag.setInteger(TAG_TYPE, this.type);
	}

	public void debugCreatePile(ItemStack stack) {
		findType(stack);
		itemStack = stack;
		itemStack.stackSize = getMaxStored() / 2;
	}

	public int getAmountStored() {
		if (itemStack != null) {
			return itemStack.stackSize;
		}

		return 0;
	}

	public int getType() {
		return type;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}
}
