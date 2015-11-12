package com.tierzero.stacksonstacks;

import com.tierzero.stacksonstacks.api.PileColorizer;
import com.tierzero.stacksonstacks.render.RenderTilePile;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public boolean isClient() {
		return true;
	}

	public void registerRenders() {
		 RenderingRegistry.registerBlockHandler(SoS.blockPile.getRenderType(), new RenderTilePile());
	}

	@Override
	public void postInit() {
		PileColorizer.registerPileColors();

	}
}
