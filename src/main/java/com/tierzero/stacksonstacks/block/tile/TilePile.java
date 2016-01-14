package com.tierzero.stacksonstacks.block.tile;

import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.block.BlockPile;
import com.tierzero.stacksonstacks.pile.Pile;
import com.tierzero.stacksonstacks.pile.PileItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

public class TilePile extends TileEntity {

	private static final String TAG_INVENTORY = "inventory";

	private Pile pile;

	public TilePile() {
		this.pile = new Pile();

	}

	public void debugCreatePile(ItemStack stack) {

		pile.debugCreatePile(stack);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(this.pos, -999, nbt);
	}

	public Pile getPile() {
		return this.pile;
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		this.readFromNBT(packet.getNbtCompound());
	}

	public void onLeftClicked(EntityPlayer player) {

		BlockPos positionAbove = pos.up();
		Block blockAbove = worldObj.getBlockState(positionAbove).getBlock();

		if (blockAbove instanceof BlockPile) {
			blockAbove.onBlockClicked(worldObj, positionAbove, player);
		} else {
			pile.onLeftClicked(this.worldObj, player, pos);

			if (pile.getAmountStored() <= 0) {
				this.worldObj.setBlockToAir(pos);
			}
		}
		update();

	}

	public boolean onRightClicked(EntityPlayer player, ItemStack stack) {
		update();
		BlockPos positionAbove = pos.up();
		if (stack != null) {
			Block blockAbove = worldObj.getBlockState(positionAbove).getBlock();
			if (worldObj.isAirBlock(positionAbove)) {
				if (pile.getAmountStored() == pile.getMaxStored() && pile.getType() != 2 && stack.stackSize > 0) {
					worldObj.setBlockState(positionAbove, SoS.blockPile.getDefaultState());
					
					IBlockState state = worldObj.getBlockState(positionAbove);
					state.getBlock().onBlockPlacedBy(worldObj, positionAbove, state, player, stack);
				} else {
					pile.onRightClicked(player, stack);
				}
				return true;
			} else if (blockAbove instanceof BlockPile) {
				return ((TilePile) worldObj.getTileEntity(positionAbove)).onRightClicked(player, stack);
			} else {
				pile.onRightClicked(player, stack);
			}
		}

		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		if(pile != null) {
			pile.readFromNBT(tag);

			if (pile.getItemStack() != null && pile.getItemStack().getItem() == null
					|| PileItemRegistry.getPileItem(pile.getItemStack()) == null) {
				this.worldObj.setBlockToAir(pos);
			}
		}
	}

	private boolean shouldUseEntireStack(EntityPlayer player) {
		return player.isSneaking();
	}

	public void update() {
		markDirty();
		worldObj.markBlockForUpdate(pos);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		pile.writeToNBT(tag);
	}
}
