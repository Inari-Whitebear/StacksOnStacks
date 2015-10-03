package com.tierzero.stacksonstacks;

import com.tierzero.stacksonstacks.api.PileItemRegistry;
import com.tierzero.stacksonstacks.block.tile.TilePile;
import com.tierzero.stacksonstacks.compat.GeneralCompat;
import com.tierzero.stacksonstacks.entity.EntityMinecartIngotPile;
import com.tierzero.stacksonstacks.util.StackUtils;

import cpw.mods.fml.client.GuiIngameModOptions;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class PileHandler {

	@SubscribeEvent
	public void handleBlockPlacement(PlayerInteractEvent event) {
		if (event.action == Action.RIGHT_CLICK_BLOCK) {
			ItemStack heldItemStack = event.entityPlayer.getCurrentEquippedItem();
			if (heldItemStack != null && PileItemRegistry.isValidPileItem(heldItemStack)) {

				int clickedX = event.x;
				int clickedY = event.y;
				int clickedZ = event.z;
				boolean playerSneaking = event.entityPlayer.isSneaking();

				TileEntity tileAtClickedPosition = event.world.getTileEntity(clickedX, clickedY, clickedZ);

				if (tileAtClickedPosition != null) {
					if (!playerSneaking) {
						return;
					} else {
						if (tileAtClickedPosition instanceof TilePile) {
							((TilePile) tileAtClickedPosition).onRightClicked(event.entityPlayer, heldItemStack);
							return;
						}

						if (tileAtClickedPosition instanceof TileEntityBeacon
								&& Loader.isModLoaded(GeneralCompat.MOD_BOTANIA)) {
							return;
						}
					}
				}

				int[] coords = getPlacementCoords(clickedX, clickedY, clickedZ, event.face);

				int placementX = coords[0];
				int placementY = coords[1];
				int placementZ = coords[2];

				Block blockAtPlacementPosition = event.world.getBlock(placementX, placementY, placementZ);
				TileEntity tileAtPlacementPosition = event.world.getTileEntity(placementX, placementY, placementZ);

				if (blockAtPlacementPosition.isAir(event.world, placementX, placementY, placementZ)) {

					Block blockBelowPlacementPosition = event.world.getBlock(placementX, placementY - 1, placementZ);
					if (blockBelowPlacementPosition.getMaterial().isSolid()
							&& (blockBelowPlacementPosition != Blocks.crafting_table
									|| blockBelowPlacementPosition != Blocks.bed)) {
						if (heldItemStack.getItem().equals(Items.redstone) && (!playerSneaking))
							return;
						event.world.setBlock(placementX, placementY, placementZ, SoS.blockPile);
						event.world.getBlock(placementX, placementY, placementZ).onBlockPlacedBy(event.world,
								placementX, placementY, placementZ, event.entityPlayer, heldItemStack);

						// Fix the comparator output after placing down a full
						// stack
						if (playerSneaking) {
							ItemStack comparatorFixer = StackUtils.getItemsFromStack(heldItemStack, 0);
							event.world.getBlock(placementX, placementY, placementZ).onBlockPlacedBy(event.world,
									placementX, placementY, placementZ, event.entityPlayer, comparatorFixer);
						}

					}
				} else if (tileAtPlacementPosition instanceof TilePile) {
					blockAtPlacementPosition.onBlockActivated(event.world, placementX, placementY, placementZ,
							event.entityPlayer, 0, 0, 0, 0);
				}
			}
		}
	}

	/*
	@SubscribeEvent
	public void handleMinecartPlacement(EntityInteractEvent event) {
		
		Entity targetCart = event.target;
		EntityPlayer player = event.entityPlayer;
		ItemStack stackInHand = player.getCurrentEquippedItem();

		if (!player.worldObj.isRemote) {
			if (event.target instanceof EntityMinecartEmpty && PileItemRegistry.isValidPileItem(stackInHand)) {
				EntityMinecartIngotPile ingotPileCart = new EntityMinecartIngotPile(player.worldObj, targetCart.posX,
						targetCart.posY, targetCart.posZ);
				ingotPileCart.motionX = targetCart.motionX;
				ingotPileCart.motionY = targetCart.motionY;
				ingotPileCart.motionZ = targetCart.motionZ;

				player.worldObj.spawnEntityInWorld(ingotPileCart);
				targetCart.setDead();
			} else if (event.target instanceof EntityMinecartIngotPile) {
				// Check to see if pile is empty
				// If so then replace with regular minecart
			}
		}

	}
	 */
	public static int[] getPlacementCoords(int x, int y, int z, int side) {
		int x1 = x, y1 = y, z1 = z;
		switch (ForgeDirection.getOrientation(side)) {
		case DOWN:
			y1--;
			break;
		case EAST:
			x1++;
			break;
		case NORTH:
			z1--;
			break;
		case SOUTH:
			z1++;
			break;
		case UP:
			y1++;
			break;
		case WEST:
			x1--;
			break;
		default:
			break;
		}
		return new int[] { x1, y1, z1 };
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(GuiOpenEvent event) {
		if (event.gui instanceof GuiIngameModOptions) {
			event.gui = new GuiConfigSOS(null);
		}
	}

}
