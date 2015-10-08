package com.tierzero.stacksonstacks;

import com.tierzero.stacksonstacks.api.PileColorizer;
import com.tierzero.stacksonstacks.block.tile.TilePile;
import com.tierzero.stacksonstacks.render.PileTESR;

import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

	public void registerRenders() {
		// RenderingRegistry.registerBlockHandler(SoS.blockPile.getRenderType(),
		// new RenderTilePile());
		ClientRegistry.bindTileEntitySpecialRenderer(TilePile.class, new PileTESR());
		// RenderingRegistry.registerEntityRenderingHandler(EntityMinecartIngotPile.class,
		// new RenderEntityMinecartIngotPile());
	}

	@Override
	public void postInit() {
		PileColorizer.registerPileColors();
		// CompatHandler.clientSide();
	}
}
