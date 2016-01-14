package com.tierzero.stacksonstacks;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public class DebugHandler {

	public boolean isFirstTick = true;

	@SubscribeEvent
	public void onWorldTick(WorldTickEvent event) {
		/* Disabled for now
		if (event.side.isServer()) {
			List<PileItem> registeredPileItems = new ArrayList<PileItem>();
			for (List<PileItem> list : PileItemRegistry.registeredPileItems)
				registeredPileItems.addAll(list);
			int x = 0;
			int y = 30;
			int z = 0;
			World world = event.world;

			
			 * for(int xOffset = 0; xOffset <= 30; xOffset++) { for(int yOffset
			 * = 0; yOffset <= 25; yOffset++) { for(int zOffset = 0; zOffset <=
			 * 25; zOffset++) { world.setBlockToAir(x + xOffset, y + yOffset, z
			 * + zOffset); } } }
			 

			for (PileItem ingot : registeredPileItems) {
				BlockPos placementPosition = new BlockPos(x, y, z);
				world.setBlockState(placementPosition.down(), Blocks.stone.getDefaultState());
				world.setBlockState(placementPosition, SoS.blockPile.getDefaultState());

				if (!world.isAirBlock() {
					ItemStack ingotStack = ingot.getPileStack();

					((BlockPile) world.getBlock(x, y, z)).debugBlockPlaced(world, x, y, z, ingotStack);

				}

				if (x > 25) {
					z++;
					x = 0;
				} else {
					x++;
				}
			}

		}
	*/
	}

}
