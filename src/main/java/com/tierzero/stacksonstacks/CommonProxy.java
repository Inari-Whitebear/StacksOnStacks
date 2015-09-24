package com.tierzero.stacksonstacks;

import java.io.File;

import com.tierzero.stacksonstacks.block.tile.TileIngotPile;
import com.tierzero.stacksonstacks.compat.CompatHandler;
import com.tierzero.stacksonstacks.util.ConfigHandler;

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