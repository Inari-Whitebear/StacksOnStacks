package com.tierzero.stacksonstacks;

import com.tierzero.stacksonstacks.entity.EntityMinecartIngotPile;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class IngotPileHandler {

	@SubscribeEvent
	public void handleBlockPlacement(PlayerInteractEvent event) {
		if (event.action == Action.RIGHT_CLICK_BLOCK) {
			World world = event.world;

			ItemStack heldItemStack = event.entityPlayer.getCurrentEquippedItem();
			int clickedX = event.x;
			int clickedY = event.y;
			int clickedZ = event.z;
			
				
			int[] coords = getPlacementCoords(event.x, event.y, event.z, event.face);

			
			int placementX = coords[0]; 
			int placementY = coords[1]; 
			int placementZ = coords[2];
			Block blockAtPosition = event.world.getBlock(x, y, z);
			TileEntity tileAtPosition = event.world.getTileEntity(x, y, z);
			
			if(!event.entityPlayer.isSneaking()) {
				System.out.println("gweg");
					
				if(blockAtPosition.isAir(world, x, y, z)) {
					placePile(event, x, y, z);
				}
			}
				


			
		
			
			
		}
		


		
	}
	
	private void placePile(PlayerInteractEvent event, int x, int y, int z) {
		event.world.setBlock(x, y, z, SoS.ingotPile);
		event.world.getBlock(x, y, z).onBlockPlacedBy(event.world, x, y, z, event.entityPlayer, event.entityPlayer.getCurrentEquippedItem());
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
