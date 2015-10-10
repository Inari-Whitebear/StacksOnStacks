package com.tierzero.stacksonstacks.block.tile;

import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.api.Pile;
import com.tierzero.stacksonstacks.api.Pile.Type;
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
	public boolean placeMod = false;

	public TilePile() {
		this.pile = new Pile(this.xCoord, this.yCoord, this.zCoord);

	}

	public Type getType() {
		return pile.getType();
	}

	public ItemStack getPileStack() {
		return pile.getPileStack();
	}

	public int getAmountStored() {
		return pile.getAmountStored();
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
		Block blockAbove = worldObj.getBlock(xCoord, yCoord + 1, zCoord);
		// not the best place to put this
		int t = getType().ordinal();
		if (worldObj.isAirBlock(xCoord, yCoord + 1, zCoord)) {
			if (pile.getAmountStored() >= pile.getMaxStored() && stack != null
					&& PileItemRegistry.isValidPileItem(stack) && stack.stackSize > 0 && t != 2) {

				worldObj.setBlock(xCoord, yCoord + 1, zCoord, SoS.blockPile);
				worldObj.getBlock(xCoord, yCoord + 1, zCoord).onBlockPlacedBy(worldObj, xCoord, yCoord, zCoord, player,
						stack);
			} else {
				pile.onRightClicked(player, stack);
			}
			return true;
		} else if (blockAbove instanceof BlockPile) {
			return blockAbove.onBlockActivated(worldObj, xCoord, yCoord + 1, zCoord, player, 0, 0, 0, 0);
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
		pile.setX(this.xCoord);
		pile.setY(this.yCoord);
		pile.setZ(this.zCoord);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		pile.readFromNBT(tag);

		if (pile.getPileStack() != null && pile.getPileStack().getItem() == null
				|| PileItemRegistry.getPileItem(getPileStack()) == null) {
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
