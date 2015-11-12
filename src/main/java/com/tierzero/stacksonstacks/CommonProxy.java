package com.tierzero.stacksonstacks;

import com.tierzero.stacksonstacks.block.tile.TilePile;

import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
	public boolean isClient() {
		return false;
	}

	public void registerTiles() {
		GameRegistry.registerTileEntity(TilePile.class, "tileIngotPile");
	}

	public void registerEntities() {
	}

	public void registerRenders() {

	}

	public void postInit() {

	}

}