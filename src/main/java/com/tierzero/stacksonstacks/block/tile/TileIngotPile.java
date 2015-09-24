package com.tierzero.stacksonstacks.block.tile;

import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.api.Ingot;
import com.tierzero.stacksonstacks.api.IngotRegistry;
import com.tierzero.stacksonstacks.block.BlockIngotPile;
import com.tierzero.stacksonstacks.util.StackUtils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileIngotPile extends TileEntity {
	private static final String TAG_INVENTORY = "inventory";
	private static final int MAX_STACK_SIZE = 64;
	
	private ItemStack inventory;
	public boolean placeMod = false;

	@Override
	public boolean canUpdate() {
		return false;
	}

	public int getInventoryCount() {
		if (inventory != null) {
			return inventory.stackSize;
		}
		
		return 0;
	}

	public ItemStack getInventory() {
		return inventory;
	}

	public void onClicked(EntityPlayer player) {
		removeFromPile(player, shouldUseEntireStack(player));
		update();
	}
	
	public boolean onActivated(EntityPlayer player, ItemStack stack) {	
		if(stack != null) {
			if (inventory == null && IngotRegistry.isValidIngot(stack)) {
				createPile(player, stack, shouldUseEntireStack(player));
				update();
				return true;
			} else if (inventory != null && inventory.getItem() != null && inventory.isItemEqual(stack)) {
				addToPile(player, stack, shouldUseEntireStack(player));
				update();
				return true;
			}
			
			return false;
		}

		return false;
		
	}

	
	private boolean shouldUseEntireStack(EntityPlayer player) {
		return player.isSneaking();
	}

	public void update() {
		markDirty();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

	}

	private void createPile(EntityPlayer player, ItemStack stack, boolean entireStack) {
		int initialAmount = entireStack ? stack.stackSize : 1;
		inventory = StackUtils.getItemsFromStack(stack, initialAmount);
		
		if (!player.capabilities.isCreativeMode) {
			StackUtils.decrementStack(player, stack, initialAmount);
		}
	}
	
	
	public void debugCreatePile(ItemStack stack) {
		inventory = stack;
	}

	private void addToPile(EntityPlayer player, ItemStack stack, boolean entireStack) {
		if (inventory.isItemEqual(stack)) {
						
			int amountToAdd = 1;
			if(entireStack) {
				amountToAdd = stack.stackSize;
			}
						
			int remainingSpace = MAX_STACK_SIZE - this.inventory.stackSize;
			
			if(remainingSpace >= amountToAdd) {				
				this.inventory.stackSize += amountToAdd;
				StackUtils.decrementStack(player, stack, amountToAdd);
			} else {
				this.inventory.stackSize += remainingSpace;
				StackUtils.decrementStack(player, stack, remainingSpace);
				
				Block blockAbove = this.getWorldObj().getBlock(xCoord, yCoord + 1, zCoord);
				//TileIngotPile tileAbove = (TileIngotPile) this.getWorldObj().getTileEntity(xCoord, yCoord + 1, zCoord);

				if (blockAbove instanceof BlockIngotPile ) {
					blockAbove.onBlockActivated(getWorldObj(), xCoord, yCoord + 1, zCoord, player, 0, 0, 0, 0);
				} else if(blockAbove == Blocks.air) {
					worldObj.setBlock(xCoord, yCoord + 1, zCoord, SoS.ingotPile);
					worldObj.getBlock(xCoord, yCoord + 1, zCoord).onBlockPlacedBy(worldObj, xCoord, yCoord + 1, zCoord,	player, stack);					
				}
			}
		}
	}

	private void removeFromPile(EntityPlayer player, boolean entireStack) {
		Block blockAbove = this.getWorldObj().getBlock(xCoord, yCoord + 1, zCoord);
		TileEntity tileAbove = this.getWorldObj().getTileEntity(xCoord, yCoord + 1, zCoord);

		
		if (blockAbove instanceof BlockIngotPile && tileAbove instanceof TileIngotPile && inventory.isItemEqual(((TileIngotPile) tileAbove).getInventory())) {
			blockAbove.onBlockClicked(getWorldObj(), xCoord, yCoord + 1, zCoord, player);
		} else {
			int amountToRemove = 1;
			
			if(entireStack) {
				amountToRemove = inventory.stackSize;
			}
			
			
			ItemStack drop = StackUtils.getItemsFromStack(inventory, amountToRemove);
			if (!player.inventory.addItemStackToInventory(drop)) {
				StackUtils.spawnItemInWorld(worldObj, xCoord, yCoord, zCoord, drop);
			}
			
			StackUtils.decrementStack(inventory, amountToRemove);
		}
		
		if(this.inventory.stackSize <= 0) {
			this.getWorldObj().setBlockToAir(xCoord, yCoord, zCoord);
		}
	}
	

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		
		NBTTagCompound inventoryTag = tag.getCompoundTag(TAG_INVENTORY);
		inventory = ItemStack.loadItemStackFromNBT(inventoryTag);

		
		//If null then its probably saved as the pre 0.9.5 nbt format
		if(inventory == null) {
			inventory = StackUtils.getStackFromInfo(tag.getInteger("ingot"), tag.getByte("stackSize"), tag.getInteger("meta"));
	
			

		}
		//If a ingot was removed from the game then this will be null

		if(inventory != null && inventory.getItem() == null) {
			Minecraft.getMinecraft().theWorld.setBlockToAir(xCoord, yCoord, zCoord);				
		}
		

	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		
		NBTTagCompound inventoryTag = new NBTTagCompound();
		if(inventory != null) {
			inventory.writeToNBT(inventoryTag);
			tag.setTag(TAG_INVENTORY, inventoryTag);
		}
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, -999, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.func_148857_g());
	}

}
