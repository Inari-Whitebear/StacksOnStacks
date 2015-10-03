package com.tierzero.stacksonstacks;

import com.tierzero.stacksonstacks.api.PileColorizer;
import com.tierzero.stacksonstacks.block.tile.TilePile;
import com.tierzero.stacksonstacks.compat.CompatHandler;
import com.tierzero.stacksonstacks.entity.EntityMinecartIngotPile;
import com.tierzero.stacksonstacks.render.PileTESR;
import com.tierzero.stacksonstacks.render.RenderEntityMinecartIngotPile;
import com.tierzero.stacksonstacks.render.RenderTilePile;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	public void registerRenders() {
		RenderingRegistry.registerBlockHandler(SoS.blockPile.getRenderType(), new RenderTilePile());
		ClientRegistry.bindTileEntitySpecialRenderer(TilePile.class, new PileTESR());
		RenderingRegistry.registerEntityRenderingHandler(EntityMinecartIngotPile.class,
				new RenderEntityMinecartIngotPile());
	}

	@Override
	public void postInit() {
		PileColorizer.registerPileColors();
		CompatHandler.clientSide();
	}
}
