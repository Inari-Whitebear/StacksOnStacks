package com.tierzero.stacksonstacks.block;

import java.util.List;

import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.api.Pile;
import com.tierzero.stacksonstacks.api.PileItem;
import com.tierzero.stacksonstacks.block.tile.TilePile;
import com.tierzero.stacksonstacks.util.ConfigHandler;
import com.tierzero.stacksonstacks.util.StackUtils;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPile extends BlockContainer {
	public IIcon icon[][];
	private int renderID;

	public static String[][] textureNames = {
			{ "VanillaGold", "VanillaIron", "ThermalFoundation", "Metallurgy4", "Mekanism" }, 
			{ "VanillaSand" } 		
	};

	public BlockPile(String name) {
		super(Material.iron);
		this.setBlockName(name);
		GameRegistry.registerBlock(this, name);
		this.renderID = RenderingRegistry.getNextAvailableRenderId();
		this.setHardness(25f);
		icon = new IIcon[2][textureNames[0].length];
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if (!world.isRemote) {
			if (world.isAirBlock(x, y - 1, z)) {
				world.setBlockToAir(x, y, z);
			} else if (world.getBlock(x, y - 1, z) instanceof BlockPile) {
				TilePile tilePile = (TilePile) world.getTileEntity(x, y - 1, z);

				if (tilePile != null) {
					if (tilePile.getPile().getAmountStored() < tilePile.getPile().getMaxStored()) {
						world.setBlockToAir(x, y, z);
					}
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
			return ((TilePile) tile).getPile().getAmountStored() / 4;
		} else {
			return 0;
		}
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		// use meta as pile type
		int index = meta == 0 ? ConfigHandler.ingotTextureToUse : ConfigHandler.dustTextureToUse;
		PileItem.setIcon(icon[meta][index], meta);
		return icon[meta][index];
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegistry) {
		// could do this better, don't feel like it
		for (int i = 0; i < textureNames[0].length; i++) {
			this.icon[0][i] = iconRegistry.registerIcon(SoS.TEXTURE_BASE + textureNames[0][i]);
		}
		for (int i = 0; i < textureNames[1].length; i++) {
			this.icon[1][i] = iconRegistry.registerIcon(SoS.TEXTURE_BASE + textureNames[1][i]);
		}
		PileItem.setIcon(icon[0][ConfigHandler.ingotTextureToUse], 0);
		PileItem.setIcon(icon[1][ConfigHandler.dustTextureToUse], 1);
		super.registerBlockIcons(iconRegistry);
	}

	@Override
	public boolean onBlockEventReceived(World world, int x, int y, int z, int eventNum, int eventArg) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			return tile.receiveClientEvent(eventNum, eventArg);
		}

		return false;
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			return ((TilePile) tile).getPile().getAmountStored() > 0;
		}

		return false;

	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		TilePile tile = (TilePile) world.getTileEntity(x, y, z);
		if (tile != null) {
			Pile pile = tile.getPile();
			
			ItemStack pileStack = pile.getPileStack();
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
					width = .5f;
					length = .5f;
					if (amountStored < 64)
						height += amountStored / 64f;
					else
						height = 1;
					if (amountStored > 64)
						width = 1;
					if (amountStored > 128)
						length = 1;

				} else
					height = 1;
				this.setBlockBounds(0, 0, 0, width, height, length);
			}
		}
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			ItemStack pileStack = ((TilePile) tile).getPile().getPileStack();
			
			int stackSize = pileStack.stackSize;
			
			if(stackSize > 64) {
				stackSize = 64;
			}
			
			
			return StackUtils.getItemsFromStack(pileStack, stackSize);
		}

		return null;
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list,
			Entity entity) {
		super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		this.setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			ItemStack stackToDrop = ((TilePile) tile).getPile().getPileStack();

			if (stackToDrop != null && stackToDrop.getItem() != null) {
				dropBlockAsItem(world, x, y, z, ((TilePile) tile).getPile().getPileStack());
			}
		}
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		return;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_,	float p_149727_7_, float p_149727_8_, float p_149727_9_) {
			//This is already handled in the pile handler
			return false;
	}

	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {

		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			((TilePile) tile).onLeftClicked(player);

			world.notifyBlocksOfNeighborChange(x, y, z, this);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			((TilePile) tile).onRightClicked((EntityPlayer) player, stack);
		}
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {

		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && ((TilePile) tile).getPile().getPileStack() != null) {
			Item item = ((TilePile) tile).getPile().getPileStack().getItem();
			if (item != null && item == Items.glowstone_dust)
				return 15;
		}
		return 0;
	}

	public void debugBlockPlaced(World world, int x, int y, int z, ItemStack stack) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			((TilePile) tile).debugCreatePile(stack);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TilePile();
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
