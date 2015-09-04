package com.tierzero.stacksonstacks;

import com.tierzero.stacksonstacks.api.Ingot;

import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class IngotPileHandler {
	@SideOnly(Side.CLIENT)
	@EventHandler
	public void playerJoin(PlayerLoggedInEvent e) {
		SoS.proxy.joinServer(e);
	}

	@SubscribeEvent
	public void handleIngotPilePlacement(PlayerInteractEvent e) {
		if (e.action != Action.RIGHT_CLICK_BLOCK)
			return;

		ItemStack current = e.entityPlayer.getCurrentEquippedItem();
		int x = e.x, y = e.y, z = e.z;
		Block block = e.world.getBlock(x, y, z);

		boolean canPlace = e.world.getBlock(x, y, z).getMaterial().isSolid();
		if (e.world.getTileEntity(x, y, z) instanceof IInventory && !e.entityPlayer.isSneaking())
			return;
		if (e.world.getTileEntity(x, y, z) instanceof TileIngotPile) {
			TileIngotPile tile = (TileIngotPile) e.world.getTileEntity(x, y, z);
			canPlace = (tile.getInventoryCount() == 64 && e.face == 1);
		}

		if (current != null && Ingot.isValidIngot(current) && canPlace) {
			int[] coords = getPlacementCoords(x, y, z, e.face);
			x = coords[0];
			y = coords[1];
			z = coords[2];
			if (!e.world.getBlock(x, y - 1, z).getMaterial().isSolid())
				return;
			if (e.world.getBlock(x, y, z).getMaterial().isReplaceable()) {
				e.world.setBlock(x, y, z, SoS.ingotPile);
				e.world.getBlock(x, y, z).onBlockPlacedBy(e.world, x, y, z, e.entityPlayer, current);

				TileIngotPile tile = (TileIngotPile) e.world.getTileEntity(x, y, z);
			}
		}
		if (current != null && e.entityPlayer.isSneaking())
			e.world.getBlock(x, y, z).onBlockClicked(e.world, x, y, z, e.entityPlayer);
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
