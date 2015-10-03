package com.tierzero.stacksonstacks;

import com.tierzero.stacksonstacks.block.tile.TilePile;

import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void registerTiles() {
		GameRegistry.registerTileEntity(TilePile.class, "tileIngotPile");
	}

	public void registerEntities() {
		// EntityRegistry.registerModEntity(EntityMinecartIngotPile.class,
		// "IngotPileCart", EntityRegistry.findGlobalUniqueEntityId(),
		// SoS.instance, 256, 3, true);
	}

	public void registerRenders() {

	}

	public void postInit() {

	}

}