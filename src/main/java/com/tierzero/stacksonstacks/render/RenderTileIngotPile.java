package com.tierzero.stacksonstacks.render;

import java.awt.Color;
import java.util.ArrayList;

import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.api.Ingot;
import com.tierzero.stacksonstacks.api.IngotRegistry;
import com.tierzero.stacksonstacks.block.tile.TileIngotPile;
import com.tierzero.stacksonstacks.util.ClientUtils;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RenderTileIngotPile implements ISimpleBlockRenderingHandler {

	private static RenderIngotPile ingotPileRender = new RenderIngotPile();
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		TileIngotPile tile = (TileIngotPile) world.getTileEntity(x, y, z);

		ClientUtils.pushMatrix();
		Tessellator.instance.addTranslation(x, y, z);

		ingotPileRender.render(tile.getIngotStack());
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
		return SoS.ingotPile.getRenderType();
	}
}
