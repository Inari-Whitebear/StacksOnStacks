package com.tierzero.stacksonstacks.api;

import com.tierzero.stacksonstacks.util.StackUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class IngotPile {
	private static final String TAG_INGOT_STACK = "inventory";	
	private ItemStack ingotStack;
	
	private float x;
	private float y;
	private float z;
	
	public IngotPile(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public ItemStack getIngotStack() {
		return ingotStack;
	}
	
	public int getAmountStored() {
		if (ingotStack != null) {
			return ingotStack.stackSize;
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
		if(stack != null) {
			if (ingotStack == null && IngotRegistry.isValidIngot(stack)) {
				createPile(player, stack, shouldUseEntireStack(player));
				return true;
			} else if (ingotStack != null && ingotStack.getItem() != null && ingotStack.isItemEqual(stack)) {
				addToPile(player, stack, shouldUseEntireStack(player));
				return true;
			}
			
			return false;
		}

		return false;
		
	}
	
	public void createPile(EntityPlayer player, ItemStack stack, boolean entireStack) {
		int initialAmount = entireStack ? stack.stackSize : 1;
		ingotStack = StackUtils.getItemsFromStack(stack, initialAmount);
		
		StackUtils.decrementStack(player, stack, initialAmount);
	}
	
	public void addToPile(EntityPlayer player, ItemStack stack, boolean entireStack) {
		int remainingSpace = getMaxStored() - ingotStack.stackSize;

		if(remainingSpace != 0) {
			int amountToAdd = 1;
			
			if(entireStack) {
				amountToAdd = Math.min(remainingSpace, stack.stackSize);
			}
			
			ingotStack.stackSize += amountToAdd;
			StackUtils.decrementStack(player, stack, amountToAdd);	
		}
	}

	public void removeFromPile(World world, EntityPlayer player, boolean entireStack) {
		if(ingotStack != null) {
			int amountToRemove = 1;
			
			if(entireStack) {
				amountToRemove = ingotStack.stackSize;
			}
			
			ItemStack stackToDrop = StackUtils.getItemsFromStack(ingotStack, amountToRemove);
			StackUtils.decrementStack(ingotStack, amountToRemove);
			//System.out.println(ingotStack.stackSize);
			
			boolean addedToPlayer = player.inventory.addItemStackToInventory(stackToDrop);
			
			
			if(!addedToPlayer) {
				StackUtils.spawnItemInWorld(world, (int) x, (int) y, (int) z, stackToDrop);
			}		
		}
	}
		
	
	public void debugCreatePile(ItemStack stack) {
		ingotStack = stack;
	}
	

	public void readFromNBT(NBTTagCompound tag) {
		
		NBTTagCompound inventoryTag = tag.getCompoundTag(TAG_INGOT_STACK);
		ingotStack = ItemStack.loadItemStackFromNBT(inventoryTag);

		//If null then its probably saved as the pre 0.9.5 nbt format
		if(ingotStack == null) {
			ingotStack = StackUtils.getStackFromInfo(tag.getInteger("ingot"), tag.getByte("stackSize"), tag.getInteger("meta"));
		}
	}
	
	public void writeToNBT(NBTTagCompound tag) {		
		NBTTagCompound inventoryTag = new NBTTagCompound();
		if(ingotStack != null) {
			ingotStack.writeToNBT(inventoryTag);
			tag.setTag(TAG_INGOT_STACK, inventoryTag);
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
		return 64;
	}


}
