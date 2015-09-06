package com.tierzero.stacksonstacks.block;

import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.block.tile.TileIngotPile;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockContainer;
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
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
		TileIngotPile tile;
		if (world.getTileEntity(x, y, z) != null) {
			tile = (TileIngotPile) world.getTileEntity(x, y, z);
		} else
			return 0;
		return tile.getInventoryCount() / 4;
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
		return tile != null ? tile.receiveClientEvent(eventNum, eventArg) : false;
	}

	public void handleTileEntity(EntityPlayer player, World world, int x, int y, int z, ItemStack stack) {

		TileIngotPile tile;
		if (world.getTileEntity(x, y, z) != null) {
			tile = (TileIngotPile) world.getTileEntity(x, y, z);
		} else {

			return;
		}

		tile.handlePlacement(player, stack);
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		TileIngotPile tile;
		if (world.getTileEntity(x, y, z) != null) {
			tile = (TileIngotPile) world.getTileEntity(x, y, z);
		} else {
			return false;
		}
		return tile.getInventoryCount() > 0;

	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		TileIngotPile tile;
		if (world.getTileEntity(x, y, z) != null) {
			tile = (TileIngotPile) world.getTileEntity(x, y, z);
		} else {
			return;
		}
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		TileIngotPile tile;
		if (world.getTileEntity(x, y, z) != null) {
			tile = (TileIngotPile) world.getTileEntity(x, y, z);
		} else {
			return null;
		}
		return tile.getInventory();

	}

	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
		TileIngotPile tile;
		if (world.getTileEntity(x, y, z) != null) {
			tile = (TileIngotPile) world.getTileEntity(x, y, z);
		} else {
			return;
		}
		dropBlockAsItem(world, x, y, z, tile.getInventory());
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		return;
	}

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

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_,
			float p_149727_7_, float p_149727_8_, float p_149727_9_) {

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
		if (!entity.isSneaking())
			this.handleTileEntity((EntityPlayer) entity, world, x, y, z, stack);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
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
