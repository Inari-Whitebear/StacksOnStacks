package com.tierzero.stacksonstacks;

import java.util.List;

import com.tierzero.stacksonstacks.api.Ingot;
import com.tierzero.stacksonstacks.api.IngotRegistry;
import com.tierzero.stacksonstacks.block.BlockIngotPile;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DebugHandler {

	public boolean isFirstTick = true;
	
	@SubscribeEvent
	public void onWorldTick(WorldTickEvent event) {
		
		if(event.side.isServer()) {
				List<Ingot> registeredIngots = IngotRegistry.getRegisteredIngots();
				int x = 0;
				int y = 5;
				int z = 0;
				World world = event.world;
				
				/*
					for(int xOffset = 0; xOffset <= 30; xOffset++) {
						for(int yOffset = 0; yOffset <= 25; yOffset++) {
							for(int zOffset = 0; zOffset <= 25; zOffset++) {
								world.setBlockToAir(x + xOffset, y + yOffset, z + zOffset);
							}
						}
					}
				*/

					
					
				
				for(Ingot ingot : registeredIngots) {
					world.setBlock(x, y - 1, z, Blocks.stone);
					world.setBlock(x, y, z, SoS.ingotPile);
					
					if(!world.isAirBlock(x, y, z)) {
						ItemStack ingotStack = ingot.getIngotStack();
						ingotStack.stackSize = 64;
						((BlockIngotPile) world.getBlock(x, y, z)).debugBlockPlaced(world, x, y, z, ingotStack);

					}			
					
					if(x > 25) {
						z++;
						x = 0;
					} else {
						x++;
					}
				}
			
		}		
	}
	
}
