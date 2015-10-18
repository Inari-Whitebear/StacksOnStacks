package com.tierzero.stacksonstacks.render;

import com.tierzero.stacksonstacks.api.Pile;
import com.tierzero.stacksonstacks.api.PileItemRegistry;
import com.tierzero.stacksonstacks.block.tile.TilePile;
import com.tierzero.stacksonstacks.util.ClientUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class PileTESR extends TileEntitySpecialRenderer {
	Tessellator tes = Tessellator.instance;

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {

		TilePile tilePile = (TilePile) tile;
		Pile pile = tilePile.getPile();
		PileRender render = null;

		boolean blockOrItem = true;
		
		ItemStack item = pile.getPileStack();

		if (item == null || PileItemRegistry.getPileItem(item) == null)
			return;
		switch (pile.getType()) {
		case 2:
			render = new PileRenderDust(item);
			break;
		case 1:
			render = new PileRenderGem(item);
			blockOrItem = false;
			break;
		case 0:
			render = new PileRenderIngot(item);
			break;
		default:
			return;
		}
		ClientUtils.pushMatrix();
		{
			ClientUtils.disableLighting();
			ClientUtils.disableBlend();
			ClientUtils.disableCull();
			tes.addTranslation((float) x, (float) y, (float) z);
			bindBlockMap(blockOrItem);
			if (render != null)
				render.render();
			tes.addTranslation((float) -x, (float) -y, (float) -z);
			ClientUtils.enableCull();
			ClientUtils.enableLighting();
		}
		ClientUtils.popMatrix();
	}

	public static void bindBlockMap(boolean blockOrItem) {
		TextureManager engine = Minecraft.getMinecraft().renderEngine;
		engine.bindTexture(blockOrItem ? TextureMap.locationBlocksTexture : TextureMap.locationItemsTexture);
	}
}
