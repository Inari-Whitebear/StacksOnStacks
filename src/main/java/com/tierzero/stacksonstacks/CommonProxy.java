package com.tierzero.stacksonstacks;

import com.tierzero.stacksonstacks.block.tile.TileIngotPile;
import com.tierzero.stacksonstacks.entity.EntityMinecartIngotPile;
import com.tierzero.stacksonstacks.render.RenderEntityMinecartIngotPile;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.model.ModelCow;

public class CommonProxy {
	

	public void registerTiles() {
		GameRegistry.registerTileEntity(TileIngotPile.class, "tileIngotPile");
	}
	
	public void registerEntities() {
		EntityRegistry.registerModEntity(EntityMinecartIngotPile.class, "IngotPileCart", EntityRegistry.findGlobalUniqueEntityId(), SoS.instance, 256, 3, true);
	}
	
	public void registerRenders() {
		
	}

	public void postInit() {

	}

}