package com.tierzero.stacksonstacks.render;

import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.api.Pile;
import com.tierzero.stacksonstacks.block.tile.TilePile;
import com.tierzero.stacksonstacks.util.ClientUtils;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.world.IBlockAccess;

public class RenderTilePile implements ISimpleBlockRenderingHandler {

	private static PileRender ingotRender = new PileRenderIngot();
	private static PileRender gemRender = new PileRenderGem();
	private static PileRender dustRender = new PileRenderDust();

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		TilePile tilePile = (TilePile) world.getTileEntity(x, y, z);
		Pile pile = tilePile.getPile();
		if(pile != null) {
			
			Tessellator tessellator = Tessellator.instance;
			
			ClientUtils.pushMatrix();
			
				tessellator.addTranslation(x, y, z);
				switch(pile.getType()) {
				case 0:
					ingotRender.render(pile.getPileStack());
					break;
				case 1:
					tessellator.draw();
					Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);
					tessellator.startDrawingQuads();
					gemRender.render(pile.getPileStack());
					tessellator.draw();
					Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
					tessellator.startDrawingQuads();
					break;
				case 2:
					dustRender.render(pile.getPileStack());
					break;
				}
				tessellator.addTranslation(-x, -y, -z);
			ClientUtils.popMatrix();
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return SoS.blockPile.getRenderType();
	}

}
