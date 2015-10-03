package com.tierzero.stacksonstacks.render;

import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.block.tile.TilePile;
import com.tierzero.stacksonstacks.util.ClientUtils;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RenderTilePile implements ISimpleBlockRenderingHandler {

	private static RenderPile pileRender = new RenderPile();
	public static RenderBlocks render;

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderer) {
		render = renderer;
		TilePile tile = (TilePile) world.getTileEntity(x, y, z);

		ClientUtils.pushMatrix();

		Tessellator.instance.addTranslation(x, y, z);
		Tessellator.instance.setBrightness(world.getLightBrightnessForSkyBlocks(x, y, z, 1));
		pileRender.render(tile.getPileStack(), x, y, z);
		Tessellator.instance.addTranslation(-x, -y, -z);

		ClientUtils.popMatrix();

		return true;
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
