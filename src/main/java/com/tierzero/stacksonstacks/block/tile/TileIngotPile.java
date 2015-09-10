package com.tierzero.stacksonstacks.block.tile;

import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.api.IngotRegistry;
import com.tierzero.stacksonstacks.block.BlockIngotPile;
import com.tierzero.stacksonstacks.util.StackUtils;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileIngotPile extends TileEntity {
	private static final String TAG_INVENTORY = "inventory";
	
	private ItemStack inventory;
	public boolean placeMod = false;

	@Override
	public boolean canUpdate() {
		return false;
	}

	public int getInventoryCount() {
		if (inventory != null)
			return inventory.stackSize;
		return 0;
	}

	public ItemStack getInventory() {
		return inventory;
	}

	public void handlePlacement(EntityPlayer player, ItemStack stack) {

		if (inventory == null && IngotRegistry.isValidIngot(stack))
			create(player, stack);
		else if (!player.isSneaking())
			add(player, stack);
		else
			remove(player, stack);
		if (!(getInventoryCount() > 0))
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		update();
	}

	public void update() {
		markDirty();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

	}

	public void create(EntityPlayer player, ItemStack stack) {
		int add = 1;
		inventory = StackUtils.getItemsFromStack(stack, add);
		if (!player.capabilities.isCreativeMode)
			StackUtils.decrementStack(stack, add);
	}

	public void add(EntityPlayer player, ItemStack stack) {
		if (!StackUtils.compareTypes(stack, inventory))
			return;
		if (inventory.stackSize != 64) {
			int add = 1;
			int diff = 64 - inventory.stackSize;
			inventory.stackSize += add;
			if (!player.capabilities.isCreativeMode) {
				StackUtils.decrementStack(stack, add);
				if(stack.stackSize <= 0) {
					player.setCurrentItemOrArmor(0, null);
				}
			}

		} else {
			Block nextBlock = worldObj.getBlock(xCoord, yCoord + 1, zCoord);
			if (nextBlock == Blocks.air) {
				worldObj.setBlock(xCoord, yCoord + 1, zCoord, SoS.ingotPile);
				worldObj.getBlock(xCoord, yCoord + 1, zCoord).onBlockPlacedBy(worldObj, xCoord, yCoord + 1, zCoord,
						player, stack);
			} else if (nextBlock instanceof BlockIngotPile)
				nextBlock.onBlockClicked(worldObj, xCoord, yCoord + 1, zCoord, player);
		}
	}

	public void remove(EntityPlayer player, ItemStack stack) {
		Block nextBlock = worldObj.getBlock(xCoord, yCoord + 1, zCoord);
		if (nextBlock instanceof BlockIngotPile) {
			nextBlock.onBlockClicked(worldObj, xCoord, yCoord + 1, zCoord, player);
		} else {
			if (!player.inventory.addItemStackToInventory(StackUtils.getOneFromStack(inventory)))
				StackUtils.spawnItemInWorld(worldObj, xCoord, yCoord, zCoord, stack);
			StackUtils.decrementStack(inventory, 1);
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
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		
		NBTTagCompound inventoryTag = new NBTTagCompound();		
		inventory.writeToNBT(inventoryTag);
		tag.setTag(TAG_INVENTORY, inventoryTag);
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
