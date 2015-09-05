package com.tierzero.stacksonstacks;

import com.tierzero.stacksonstacks.api.IngotFinder;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	public void registerRenders() {
		RenderingRegistry.registerBlockHandler(SoS.ingotPile.getRenderType(), new RenderTileIngotPile());
	}

	@Override
	public void postInit() {
		IngotFinder.registerIngotColors();
	}
}
