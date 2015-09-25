package com.tierzero.stacksonstacks;

import com.tierzero.stacksonstacks.api.IngotRegistry;
import com.tierzero.stacksonstacks.block.BlockIngotPile;
import com.tierzero.stacksonstacks.block.tile.TileIngotPile;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class IngotPileHandler {

	@SubscribeEvent
	public void handleIngotPilePlacement(PlayerInteractEvent event) {
		if (event.action != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		
		ItemStack heldItemStack = event.entityPlayer.getCurrentEquippedItem();
		int x = event.x; 
		int y = event.y; 
		int z = event.z;
		Block block = event.world.getBlock(x, y, z);

		boolean canPlace = block.getMaterial().isSolid();
		
		TileEntity tile = event.world.getTileEntity(x, y, z);
		if (tile instanceof IInventory && !event.entityPlayer.isSneaking())
			return;
		if (tile instanceof TileIngotPile) {
			TileIngotPile ingotPileTile = (TileIngotPile) tile;
			canPlace = ingotPileTile.getAmountStored() == 64 && event.face == 1;
		}

		if (heldItemStack != null && IngotRegistry.isValidIngot(heldItemStack) && canPlace) {
			int[] coords = getPlacementCoords(x, y, z, event.face);
			x = coords[0];
			y = coords[1];
			z = coords[2];
			if (event.world.getBlock(x, y - 1, z).getMaterial().isSolid()) {
				event.world.setBlock(x, y, z, SoS.ingotPile);
				event.world.getBlock(x, y, z).onBlockPlacedBy(event.world, x, y, z, event.entityPlayer, heldItemStack);
			}
			
		}

		if(heldItemStack != null && event.entityPlayer.isSneaking()) {
			
			event.world.getBlock(x, y, z).onBlockActivated(event.world, x, y, z, event.entityPlayer, 0, 0, 0, 0);
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
