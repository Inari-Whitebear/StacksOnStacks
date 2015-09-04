package com.tierzero.stacksonstacks;

import com.tierzero.stacksonstacks.api.IngotFinder;

import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
	public void registerTiles() {
		GameRegistry.registerTileEntity(TileIngotPile.class, "tileIngotPile");
	}

	public void registerRenders() {
	}

	public void serverLoad(FMLServerStartingEvent event) {

	}

	public void joinServer(PlayerLoggedInEvent event) {
		IngotFinder.registerIngotColors();
	}

}