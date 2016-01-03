package com.tierzero.stacksonstacks.core;

import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.client.render.RenderTilePile;
import com.tierzero.stacksonstacks.pile.PileColorizer;

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
