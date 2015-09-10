package com.tierzero.stacksonstacks.block;

import java.util.Random;

import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.block.tile.TileIngotPile;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockIngotPile extends BlockContainer {
	public IIcon icon;
	private int renderID;

	public BlockIngotPile(String name) {
		super(Material.iron);
		this.setBlockName(name);
		GameRegistry.registerBlock(this, name);
		this.renderID = RenderingRegistry.getNextAvailableRenderId();
		this.setHardness(25f);

	}

	

    
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if(!world.isRemote) {
			if(world.isAirBlock(x, y - 1, z)) {
				TileIngotPile tile = (TileIngotPile) world.getTileEntity(x, y, z);
				if(tile != null) {
					
					ItemStack ingotStack = tile.getInventory();
					System.out.println(ingotStack.stackSize);
					
					this.dropBlockAsItem(tile.getWorldObj(), x, y, z, ingotStack);
					tile.getWorldObj().setBlockToAir(x, y, z);
				}
			}
		}
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			return ((TileIngotPile) tile).getInventoryCount() / 4;
		} else {
			return 0;
		}
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		return 1;
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return icon;
	}

	@Override
	public void registerBlockIcons(IIconRegister ir) {
		this.icon = ir.registerIcon(SoS.TEXTURE_BASE + "ingotPile");
		super.registerBlockIcons(ir);
	}

	@Override
	public boolean onBlockEventReceived(World world, int x, int y, int z, int eventNum, int eventArg) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			return tile.receiveClientEvent(eventNum, eventArg);
		}
		
		return false;		
	}

	public void handleTileEntity(EntityPlayer player, World world, int x, int y, int z, ItemStack stack) {

		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			((TileIngotPile) tile).handlePlacement(player, stack);
		}

		BlockSand g;
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			return ((TileIngotPile) tile).getInventoryCount() > 0;
		}
		
		return false;
		
		
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		TileIngotPile tile = (TileIngotPile) world.getTileEntity(x, y, z);
		if (tile != null) {
			ItemStack ingotStack = tile.getInventory();
			if(ingotStack != null) {
				int numberOfIngots = tile.getInventory().stackSize;
				int height = 1 + numberOfIngots / 8;
				
				if(numberOfIngots % 8 == 0) {
					height -= 1;
				}
			
				this.setBlockBounds(0, 0, 0, 1,  height / 8.0f, 1);
			}
		}
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			return ((TileIngotPile) tile).getInventory();
		}
		
		return null;
	}

	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			dropBlockAsItem(world, x, y, z, ((TileIngotPile) tile).getInventory());
		}
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		return;
	}

	/*
	@Override
	public void dropBlockAsItem(World world, int x, int y, int z, ItemStack stack) {

		if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
			EntityItem item = new EntityItem(world);
			item.setPosition(x, y, z);
			item.delayBeforeCanPickup = 10;
			item.setEntityItemStack(stack);
			world.spawnEntityInWorld(item);
		}
	}
	*/

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_,	float p_149727_7_, float p_149727_8_, float p_149727_9_) {

		this.handleTileEntity(player, world, x, y, z, player.getCurrentEquippedItem());

		return true;
	}

	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
		this.handleTileEntity(player, world, x, y, z, player.getCurrentEquippedItem());
		world.markBlockForUpdate(x, y, z);
	
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		if (!entity.isSneaking()) {
			this.handleTileEntity((EntityPlayer) entity, world, x, y, z, stack);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileIngotPile();
	}

	@Override
	public int getRenderType() {

		return this.renderID;
	}

	@Override
	public boolean isOpaqueCube() {

		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {

		return false;
	}

}
