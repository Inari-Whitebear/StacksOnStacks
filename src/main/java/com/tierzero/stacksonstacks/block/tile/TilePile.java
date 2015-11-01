package com.tierzero.stacksonstacks.block.tile;

import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.api.Pile;
import com.tierzero.stacksonstacks.api.PileItemRegistry;
import com.tierzero.stacksonstacks.block.BlockPile;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TilePile extends TileEntity {

	private static final String TAG_INVENTORY = "inventory";

	private Pile pile;

	public TilePile() {
		this.pile = new Pile(this.xCoord, this.yCoord, this.zCoord);

	}

	public void onLeftClicked(EntityPlayer player) {

		Block blockAbove = worldObj.getBlock(xCoord, yCoord + 1, zCoord);

		if (blockAbove instanceof BlockPile) {
			blockAbove.onBlockClicked(worldObj, xCoord, yCoord + 1, zCoord, player);
		} else {
			pile.onLeftClicked(this.worldObj, player);

			if (pile.getAmountStored() <= 0) {
				this.worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			}
		}
		update();

	}

	public boolean onRightClicked(EntityPlayer player, ItemStack stack) {
		update();
		if (stack == null)
			return false;
		Block blockAbove = worldObj.getBlock(xCoord, yCoord + 1, zCoord);
		if (worldObj.isAirBlock(xCoord, yCoord + 1, zCoord)) {
			if (pile.getAmountStored() == pile.getMaxStored() && pile.getType() != 2) {
				worldObj.setBlock(xCoord, yCoord + 1, zCoord, SoS.blockPile);
				worldObj.getBlock(xCoord, yCoord + 1, zCoord).onBlockPlacedBy(worldObj, xCoord, yCoord + 1, zCoord,
						player, stack);
			} else {
				pile.onRightClicked(player, stack);

			}
			return true;
		} else if (blockAbove instanceof BlockPile) {
			return ((TilePile) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord)).onRightClicked(player, stack);
		} else {
			pile.onRightClicked(player, stack);
		}
		return false;
	}

	private boolean shouldUseEntireStack(EntityPlayer player) {
		return player.isSneaking();
	}

	public void update() {
		markDirty();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		pile.setPosition(this.xCoord, this.yCoord, this.zCoord);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		pile.readFromNBT(tag);

		if (pile.getPileStack() != null && pile.getPileStack().getItem() == null
				|| PileItemRegistry.getPileItem(pile.getPileStack()) == null) {
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

	public Pile getPile() {
		return this.pile;
	}
}
