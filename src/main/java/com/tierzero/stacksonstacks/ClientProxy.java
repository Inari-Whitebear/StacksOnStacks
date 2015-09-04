package com.tierzero.stacksonstacks;

import com.tierzero.stacksonstacks.api.IngotFinder;
import com.tierzero.stacksonstacks.compat.CompatHandler;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class ClientProxy extends CommonProxy {
	public void registerRenders() {
		RenderingRegistry.registerBlockHandler(SoS.ingotPile.getRenderType(), new RenderTileIngotPile());
	}

	@Override
	public void serverLoad(FMLServerStartingEvent event) {
		IngotFinder.registerIngotColors();
		CompatHandler.serverLoad();
	}
}
