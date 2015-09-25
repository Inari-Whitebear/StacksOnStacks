package com.tierzero.stacksonstacks.block.tile;

import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.api.IngotPile;
import com.tierzero.stacksonstacks.block.BlockIngotPile;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileIngotPile extends TileEntity {
	private static final String TAG_INVENTORY = "inventory";
	private static final int MAX_STACK_SIZE = 64;
	
	private IngotPile pile;
	public boolean placeMod = false;
	
	public TileIngotPile() {
		this.pile = new IngotPile(this.xCoord, this.yCoord, this.zCoord);
	}

	public ItemStack getIngotStack() {
		return pile.getIngotStack();
	}
	
	public int getAmountStored() {
		return pile.getAmountStored();
	}

	public void onLeftClicked(EntityPlayer player) {
		
		Block blockAbove = worldObj.getBlock(xCoord, yCoord + 1, zCoord);
		
		if(blockAbove instanceof BlockIngotPile) {
			blockAbove.onBlockClicked(worldObj, xCoord, yCoord + 1, zCoord, player);
		} else {
			pile.onLeftClicked(this.worldObj, player);
			
			if(pile.getAmountStored() <= 0) {
				this.worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			} else {
			}
		}
		
		update();

	}
	
	public boolean onRightClicked(EntityPlayer player, ItemStack stack) {	
		update();
		Block blockAbove = worldObj.getBlock(xCoord, yCoord + 1, zCoord);
		
		if(worldObj.isAirBlock(xCoord, yCoord + 1, zCoord)) {
			if(pile.getAmountStored() >= pile.getMaxStored() && stack != null) {
				worldObj.setBlock(xCoord, yCoord + 1, zCoord, SoS.ingotPile);
				worldObj.getBlock(xCoord, yCoord + 1, zCoord).onBlockActivated(worldObj, xCoord, yCoord + 1, zCoord, player, 0, 0, 0, 0);
			} else {
				pile.onRightClicked(player, stack);
			}
			
			return true;
		} else if(blockAbove instanceof BlockIngotPile){
			return blockAbove.onBlockActivated(worldObj, xCoord, yCoord + 1, zCoord, player, 0, 0, 0, 0);
		}

		
		return false;		
	}
	
	private boolean shouldUseEntireStack(EntityPlayer player) {
		return player.isSneaking();
	}

	public void update() {
		markDirty();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		pile.setX(this.xCoord);
		pile.setY(this.yCoord);
		pile.setZ(this.zCoord);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		
		pile.readFromNBT(tag);
		
		if(pile.getIngotStack() != null && pile.getIngotStack().getItem() == null) {
			this.worldObj.setBlockToAir(xCoord, yCoord, zCoord);				
		}
		

	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		
		pile.writeToNBT(tag);
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

	public void debugCreatePile(ItemStack stack) {
		pile.debugCreatePile(stack);
	}

}
