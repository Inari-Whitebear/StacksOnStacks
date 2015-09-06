package com.tierzero.stacksonstacks.render;

import java.awt.Color;
import java.util.ArrayList;

import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.api.Ingot;
import com.tierzero.stacksonstacks.api.IngotRegistry;
import com.tierzero.stacksonstacks.block.tile.TileIngotPile;
import com.tierzero.stacksonstacks.compat.GregTech6Compat;
import com.tierzero.stacksonstacks.util.ClientUtils;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderTileIngotPile implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderer) {
		ArrayList<IngotRender> ingots = new ArrayList<IngotRender>();
		TileIngotPile tile = (TileIngotPile) world.getTileEntity(x, y, z);

		Ingot ingot = IngotRegistry.getIngot(tile.getInventory());
		int length = tile.getInventoryCount();

		ClientUtils.pushMatrix();
		{

			float w = 0f, h = 0f, l = 0f;
			float a = 0f, s = 0f, d = 0f;
			boolean r = true;
			int e = 0;
			for (int i = 0; i < length; i++) {
				w = a / 4;
				l = d / 2;
				h = s / 8;

				ingots.add(new IngotRender(x + (r ? w : l), y + h, z + (r ? l : w), ingot, r));
				if (a < 3)
					a++;
				else {
					w = 0;
					a = 0;
					if (d == 0)
						d++;
					else {
						d = 0;
						if (s < 8)
							s++;
					}
				}
				if (e == 7) {
					e = 0;
					r = !r;
				} else
					e++;
			}

			for (IngotRender render : ingots) {
				render.render(world, block, tile);
			}
		}
		ClientUtils.popMatrix();
		return ingots.size() > 0;

	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {

		return false;
	}

	@Override
	public int getRenderId() {

		return SoS.ingotPile.getRenderType();
	}

	public class IngotRender {
		double height = .125;
		double width = .25;
		double length = .5;
		double slantW = 0.05;
		double slantL = 0.025;
		public Ingot ingot;
		boolean r;
		float x, y, z;

		public IngotRender(float x, float y, float z, Ingot ingot, boolean r) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.ingot = ingot;
			this.r = r;
		}

		public void render(IBlockAccess world, Block block, TileIngotPile tile) {
			Color color = ingot.getColor();
			ClientUtils.pushMatrix();
			{
				IIcon icon = null;
				try {
					icon = ingot.getIcon();
				} catch (Throwable e) {
				}
				Tessellator tessellator = Tessellator.instance;
				tessellator.addTranslation(x, y, z);
				if (GregTech6Compat.INSTANCE.isEnabled() || icon == null) {
					icon = block.getIcon(0, 0);
					tessellator.setColorOpaque(color.getRed(), color.getGreen(), color.getBlue());
				} else {
					tessellator.setColorOpaque(255, 255, 255);
				}

				double Umin = icon.getMinU();
				double Vmax = icon.getMaxV();
				double Vmin = icon.getMinV();
				double Umax = icon.getMaxU();
				// Render side 0 (down)
				int[] lightLevel = new int[6];
				lightLevel[0] = block.getMixedBrightnessForBlock(world, (int) x, (int) y - 1, (int) z);
				lightLevel[1] = world.getLightBrightnessForSkyBlocks((int) x, (int) y, (int) z, 1);
				lightLevel[2] = block.getMixedBrightnessForBlock(world, (int) x, (int) y, (int) z - 1);
				lightLevel[3] = block.getMixedBrightnessForBlock(world, (int) x, (int) y, (int) z + 1);
				lightLevel[4] = block.getMixedBrightnessForBlock(world, (int) x - 1, (int) y, (int) z);
				lightLevel[5] = block.getMixedBrightnessForBlock(world, (int) x + 1, (int) y, (int) z);

				ClientUtils.drawRectangularPrism(r ? width : length, r ? length : width, height, slantW, slantL, Umin,
						Vmin, Umax, Vmax, lightLevel, r);
				tessellator.addTranslation(-x, -y, -z);
			}
			ClientUtils.popMatrix();

		}

	}

}
