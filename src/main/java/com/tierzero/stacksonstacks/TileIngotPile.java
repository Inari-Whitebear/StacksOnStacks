package com.tierzero.stacksonstacks;

import java.awt.Color;

import com.tierzero.stacksonstacks.api.Ingot;
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
	private ItemStack inventory;
	private Color color;
	public boolean placeMod = false;

	@Override
	public boolean receiveClientEvent(int p_145842_1_, int p_145842_2_) {
		return true;
	}

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
		if (inventory != null)
			return inventory;
		return null;
	}

	public Color getColor() {
		return color;
	}

	public void handlePlacement(EntityPlayer player, ItemStack stack) {

		if (inventory == null && Ingot.isValidIngot(stack))
			create(player, stack);
		else if (!player.isSneaking())
			add(player, stack);
		else
			remove(player, stack);

		if ((getInventoryCount() <= 0))
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		update();
	}

	public void update() {
		markDirty();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

	}

	public void create(EntityPlayer player, ItemStack stack) {
		int add = 1;
		// if (placeMod)
		// add = stack.stackSize;
		inventory = StackUtils.getItemsFromStack(stack, add);
		if (!player.capabilities.isCreativeMode)
			StackUtils.decrementStack(stack, add);
		color = Ingot.getIngotColor(stack);
	}

	public void add(EntityPlayer player, ItemStack stack) {
		if (!StackUtils.compareTypes(stack, inventory))
			return;
		if (inventory.stackSize != 64) {
			int add = 1;
			int diff = 64 - inventory.stackSize;
			// if (placeMod)
			// add = (stack.stackSize > diff) ? diff : stack.stackSize;
			inventory.stackSize += add;
			if (!player.capabilities.isCreativeMode)
				StackUtils.decrementStack(stack, add);
			color = Ingot.getIngotColor(StackUtils.getOneFromStack(stack));
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
			color = Ingot.getIngotColor(StackUtils.getOneFromStack(stack));
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.readIngotsFromNBT(tag);

	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		this.writeIngotsToNBT(tag);
	}

	public void writeIngotsToNBT(NBTTagCompound tag) {
		if (inventory != null) {
			tag.setInteger("ingot", Item.getIdFromItem(this.inventory.getItem()));
			tag.setByte("stackSize", (byte) this.inventory.stackSize);
			tag.setInteger("meta", this.inventory.getItemDamage());
		} else
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		if (worldObj.isRemote)
			System.out.println("HEO");
	}

	public void readIngotsFromNBT(NBTTagCompound tag) {
		ItemStack stack = StackUtils.getStackFromInfo(tag.getInteger("ingot"), tag.getByte("stackSize"),
				tag.getInteger("meta"));
		if (stack != null) {
			this.inventory = stack;
		} else
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);

		// int[] rgb = tag.getIntArray("color");
		// this.color = new Color(rgb[0], rgb[1], rgb[2]);
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
