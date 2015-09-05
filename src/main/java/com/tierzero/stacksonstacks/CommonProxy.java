package com.tierzero.stacksonstacks;

import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
	public void registerTiles() {
		GameRegistry.registerTileEntity(TileIngotPile.class, "tileIngotPile");
	}

	public void registerRenders() {
	}

	public void postInit() {

	}

}