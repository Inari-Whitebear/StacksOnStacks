package com.tierzero.stacksonstacks;

import com.tierzero.stacksonstacks.api.PileItemRegistry;
import com.tierzero.stacksonstacks.block.tile.TilePile;
import com.tierzero.stacksonstacks.util.StackUtils;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class PileHandler {

	private static Block[] blockedBlocks = { Blocks.crafting_table, Blocks.wooden_door, Blocks.trapdoor, Blocks.bed,
			Blocks.fence_gate };

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void handleBlockPlacement(PlayerInteractEvent event) {
		if (event.action == Action.RIGHT_CLICK_BLOCK) {
			ItemStack heldItemStack = event.entityPlayer.getCurrentEquippedItem();
			if (heldItemStack != null && PileItemRegistry.isValidPileItem(heldItemStack)) {
				int clickedX = event.x;
				int clickedY = event.y;
				int clickedZ = event.z;
				boolean playerSneaking = event.entityPlayer.isSneaking();

				Block blockAtClickedPosition = event.world.getBlock(clickedX, clickedY, clickedZ);
				TileEntity tileAtClickedPosition = event.world.getTileEntity(clickedX, clickedY, clickedZ);

				if (tileAtClickedPosition != null) {
					if (tileAtClickedPosition instanceof TilePile) {
						((TilePile) tileAtClickedPosition).onRightClicked(event.entityPlayer, heldItemStack);
						return;
					}
					return;
				}

				for (Block blockedBlock : blockedBlocks) {
					if (blockAtClickedPosition.equals(blockedBlock)) {
						return;
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
							&& blockBelowPlacementPosition != SoS.blockPile) {
						if (heldItemStack.getItem().equals(Items.redstone) && blockAtPlacementPosition != Blocks.redstone_wire) {
							return;
						}
						
						//Prevent placing bonemeal on grass to avoid getting free plants/long grass.
						if (heldItemStack.getItem().equals(Items.dye) && heldItemStack.getItemDamage() == 15 && blockAtPlacementPosition.equals(Blocks.grass) {
							return;
						}

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
