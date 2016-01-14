package com.tierzero.stacksonstacks.block;

import com.tierzero.stacksonstacks.block.tile.TilePile;
import com.tierzero.stacksonstacks.pile.Pile;
import com.tierzero.stacksonstacks.util.StackUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockPile extends BlockContainer {

	public BlockPile(String name) {
		super(Material.iron);
		this.setUnlocalizedName(name);
		GameRegistry.registerBlock(this, name);
		this.setHardness(25f);
	}
	
	@Override
	public void breakBlock(World world, BlockPos position, IBlockState state) {
		TileEntity tile = world.getTileEntity(position);
		if (tile != null) {
			ItemStack stackToDrop = ((TilePile) tile).getPile().getItemStack();

			if (stackToDrop != null && stackToDrop.getItem() != null) {
				spawnAsEntity(world, position, ((TilePile) tile).getPile().getItemStack());
			}
		}		
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TilePile();
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos position, IBlockState state) {
		this.setBlockBoundsBasedOnState(world, position);
		return super.getCollisionBoundingBox(world, position, state);
	}
	
	@Override
	public int getComparatorInputOverride(World world, BlockPos position) {
		TilePile tilePile = (TilePile) world.getTileEntity(position);
		
		if(tilePile != null) {
			return tilePile.getPile().getAmountStored() / 4;
		}
		
		return 0;
	}

	@Override
	public int getLightValue(IBlockAccess world, BlockPos position) {
		TilePile tilePile = (TilePile) world.getTileEntity(position);
		if (tilePile != null && tilePile.getPile().getItemStack() != null) {
			Item item = tilePile.getPile().getItemStack().getItem();
			if (item != null && item == Items.glowstone_dust) {
				return 15;
			}
		}
		return 0;	
	}

	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos position) {
		TileEntity tile = world.getTileEntity(position);
		if (tile != null) {
			ItemStack pileStack = ((TilePile) tile).getPile().getItemStack();

			int stackSize = pileStack.stackSize;

			if (stackSize > 64) {
				stackSize = 64;
			}

			return StackUtils.getItemsFromStack(pileStack, stackSize);
		}

		return null;
	}	

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}


	@Override
	public boolean onBlockActivated(World world, BlockPos posisition, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		//NO-OP, this is already handled in the placement event handler
		return false;
	}

	@Override
	public void onBlockClicked(World world, BlockPos position, EntityPlayer player) {

		TileEntity tile = world.getTileEntity(position);
		if (tile != null) {
			((TilePile) tile).onLeftClicked(player);

			world.notifyNeighborsOfStateChange(position, this);
		}
	}
	
	

	@Override
	public void onBlockPlacedBy(World world, BlockPos position, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		TileEntity tile = world.getTileEntity(position);
		if (tile != null) {
			((TilePile) tile).onRightClicked((EntityPlayer) placer, stack);
		}
	}


	@Override
	public void onNeighborBlockChange(World world, BlockPos position, IBlockState state, Block neighborBlock) {
		//Check to see if the pile still has a block or full pile below it, if not then destroy the pile
		if(!world.isRemote) {
			//We only care to check the rest if it is the block underneath that has been updated
			BlockPos positionUnderneath = position.down();
			if(position.equals(positionUnderneath)) { 
				if(world.isAirBlock(positionUnderneath)) {
					world.setBlockToAir(position);
				} else if(world.getBlockState(position).getBlock() instanceof BlockPile) {
					TilePile tilePile = (TilePile) world.getTileEntity(positionUnderneath);
					
					if(tilePile != null) {
						if(tilePile.getPile().getAmountStored() < tilePile.getPile().getMaxStored()) {
							world.setBlockToAir(position);
						}
					}
				}
			}
		}		
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos position) {
		TilePile tile = (TilePile) world.getTileEntity(position);
		if (tile != null) {
			Pile pile = tile.getPile();

			ItemStack pileStack = pile.getItemStack();
			int type = pile.getType();
			if (pileStack != null) {

				int amountStored = pile.getAmountStored();
				float height = 0;
				float width = 1f, length = 1f;
				if (type == 0) {
					height = 1 + amountStored / (pile.getMaxStored() / 8);

					if (amountStored % (pile.getMaxStored() / 8) == 0) {
						height -= 1;
					}
					height /= 8;
				} else if (type == 1) {
					width = 1;
					height = 1;
					length = 1;
				} else
					height = 1;
				this.setBlockBounds(0, 0, 0, width, height, length);
			}
		}
	}
}
