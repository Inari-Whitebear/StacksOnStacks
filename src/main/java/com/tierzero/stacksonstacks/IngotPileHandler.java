package com.tierzero.stacksonstacks;

import com.tierzero.stacksonstacks.api.IngotRegistry;
import com.tierzero.stacksonstacks.block.tile.TileIngotPile;
import com.tierzero.stacksonstacks.compat.GeneralCompat;
import com.tierzero.stacksonstacks.entity.EntityMinecartIngotPile;
import com.tierzero.stacksonstacks.util.StackUtils;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class IngotPileHandler {

	@SubscribeEvent
	public void handleBlockPlacement(PlayerInteractEvent event) {		
 		if (event.action == Action.RIGHT_CLICK_BLOCK) {
 			ItemStack heldItemStack = event.entityPlayer.getCurrentEquippedItem();
			if(heldItemStack != null && IngotRegistry.isValidIngot(heldItemStack)) {

				int clickedX = event.x;
				int clickedY = event.y;
				int clickedZ = event.z;
				boolean playerSneaking = event.entityPlayer.isSneaking();

				TileEntity tileAtClickedPosition = event.world.getTileEntity(clickedX, clickedY, clickedZ);
				
				if(tileAtClickedPosition != null) {
					if(!playerSneaking) {
						return;
					} else {
						if(tileAtClickedPosition instanceof TileIngotPile) {
							((TileIngotPile) tileAtClickedPosition).onRightClicked(event.entityPlayer, heldItemStack);
							return;
						}
						
						if(tileAtClickedPosition instanceof TileEntityBeacon && Loader.isModLoaded(GeneralCompat.MOD_BOTANIA)) {
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
				
				if(blockAtPlacementPosition.isAir(event.world,placementX, placementY, placementZ)) {
					Block blockBelowPlacementPosition = event.world.getBlock(placementX, placementY - 1, placementZ);
					if(blockBelowPlacementPosition.getMaterial().isSolid()) {
						event.world.setBlock(placementX, placementY, placementZ, SoS.ingotPile);
						event.world.getBlock(placementX, placementY, placementZ).onBlockPlacedBy(event.world, placementX, placementY, placementZ, event.entityPlayer, heldItemStack);
						
						
						//Fix the comparator output after placing down a full stack
						if(playerSneaking) {
							ItemStack comparatorFixer = StackUtils.getItemsFromStack(heldItemStack, 0);
							event.world.getBlock(placementX, placementY, placementZ).onBlockPlacedBy(event.world, placementX, placementY, placementZ, event.entityPlayer, comparatorFixer);
						}

					}
				} else if(tileAtPlacementPosition instanceof TileIngotPile){
					//blockAtPlacementPosition.onBlockActivated(event.world, placementX, placementY, placementZ, event.entityPlayer, 0, 0, 0, 0);
				}
			}			
		}
	}

	@SubscribeEvent
	public void handleMinecartPlacement(EntityInteractEvent event) {
		if(event.target instanceof EntityMinecart) {
			//Replace minecart with ingot pile cart
		} else if(event.target instanceof EntityMinecartIngotPile) {
			//Check to see if pile is empty
			//If so then replace with regular minecart
		}
	}

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

}
