package com.tierzero.stacksonstacks.pile;

import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.block.tile.TilePile;
import com.tierzero.stacksonstacks.util.StackUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PileHandler {

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void handleBlockPlacement(PlayerInteractEvent event) {
		if (event.action == Action.RIGHT_CLICK_BLOCK) {
			ItemStack heldItemStack = event.entityPlayer.getCurrentEquippedItem();
			if (heldItemStack != null && PileItemRegistry.isValidPileItem(heldItemStack)) {

				boolean playerSneaking = event.entityPlayer.isSneaking();

				BlockPos clickedPosition = event.pos;
				Block blockAtClickedPosition = event.world.getBlockState(clickedPosition).getBlock();
				TileEntity tileAtClickedPosition = event.world.getTileEntity(clickedPosition);
				
				if (tileAtClickedPosition != null) {
					if (tileAtClickedPosition instanceof TilePile) {
						((TilePile) tileAtClickedPosition).onRightClicked(event.entityPlayer, heldItemStack);
						return;
					}
					return;
				}

				BlockPos placementPosition = new BlockPos(clickedPosition.getX() + event.face.getFrontOffsetX(), clickedPosition.getY() + event.face.getFrontOffsetY(), clickedPosition.getZ() + event.face.getFrontOffsetZ());


				Block blockAtPlacementPosition = event.world.getBlockState(placementPosition).getBlock();
				TileEntity tileAtPlacementPosition = event.world.getTileEntity(placementPosition);

				if (blockAtPlacementPosition.isAir(event.world, placementPosition)) {

					Block blockBelowPlacementPosition = event.world.getBlockState(placementPosition.down()).getBlock();

					if (blockBelowPlacementPosition.getMaterial().isSolid() && blockBelowPlacementPosition != SoS.blockPile) {
						
						//TODO - Properly fix both of these
						//Prevent placing redstone as it causes a dupe bug
						if (heldItemStack.getItem().equals(Items.redstone) && blockAtPlacementPosition != Blocks.redstone_wire) {
							return;
						}
						
						//Prevent placing bonemeal on grass to avoid getting free plants/long grass.
						if (heldItemStack.getItem().equals(Items.dye) && heldItemStack.getItemDamage() == 15 && blockAtPlacementPosition.equals(Blocks.grass)) {
							return;
						}

						event.world.setBlockState(placementPosition, SoS.blockPile.getDefaultState());
						
						IBlockState state = event.world.getBlockState(placementPosition);
						state.getBlock().onBlockPlacedBy(event.world, placementPosition, state, event.entityPlayer, heldItemStack);

						// Fix the comparator output after placing down a full
						// stack
						if (playerSneaking) {
							ItemStack comparatorFixer = StackUtils.getItemsFromStack(heldItemStack, 0);
							state.getBlock().onBlockPlacedBy(event.world, placementPosition, state, event.entityPlayer, comparatorFixer);
						}

					}
				} else if (tileAtPlacementPosition instanceof TilePile) {
					blockAtPlacementPosition.onBlockPlacedBy(event.world, placementPosition, blockAtPlacementPosition.getDefaultState(), event.entityPlayer, heldItemStack);
				}
			}
		}
	}
}
