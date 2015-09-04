package com.tierzero.stacksonstacks;
import java.awt.Color;
import java.util.ArrayList;

import com.tierzero.stacksonstacks.api.Ingot;
import com.tierzero.stacksonstacks.compat.GregTechCompat;
import com.tierzero.stacksonstacks.util.ClientUtils;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderTileIngotPile implements ISimpleBlockRenderingHandler {
	private int brightness = 0;

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderer) {
		ArrayList<IngotRender> ingots = new ArrayList<IngotRender>();
		TileIngotPile tile = null;

		if (world.getTileEntity(x, y, z) != null)
			tile = (TileIngotPile) world.getTileEntity(x, y, z);

		Color color = tile.getColor();
		int length = tile.getInventoryCount();

		ClientUtils.pushMatrix();
		{
			ClientUtils.rotate(90, 0, 1, 0);
			float w = 0f, h = 0f, l = 0f;
			float a = 0f, s = 0f, d = 0f;
			boolean r = true;
			int e = 0;
			for (int i = 0; i < length; i++) {
				w = a / 4;
				l = d / 2;
				h = s / 8;

				ingots.add(new IngotRender(x + (r ? w : l), y + h, z + (r ? l : w), color).setR(r));
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
		public Color color;
		float x, y, z;
		boolean r = true;

		public IngotRender(float x, float y, float z, Color color) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.color = color;

		}

		public IngotRender setR(boolean r) {
			this.r = r;
			return this;
		}

		public void render(IBlockAccess world, Block block, TileIngotPile tile) {

			if (color == null && Ingot.getIngotColor(tile.getInventory()) != null)
				color = Ingot.getIngotColor(tile.getInventory());
			else if (color == null)
				color = Color.black;
			IIcon icon = null;
			try {
				icon = Ingot.getIngot(tile.getInventory()).getIcon();
			} catch (NullPointerException e) {
			}

			ClientUtils.pushMatrix();
			{

				Tessellator tessellator = Tessellator.instance;
				tessellator.addTranslation(x, y, z);
				if (GregTechCompat.INSTANCE.isEnabled() || icon == null) {
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

				calcBrightness(world, (int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z), tessellator);

				ClientUtils.drawRectangularPrism(r ? width : length, r ? length : width, height, r ? slantW : slantL,
						r ? slantL : slantW, Umin, Vmin, Umax, Vmax);
				tessellator.addTranslation(-x, -y, -z);
			}
			ClientUtils.popMatrix();

		}

	}

	public void calcBrightness(IBlockAccess world, int x, int y, int z, Tessellator tes) {
		brightness = world.getLightBrightnessForSkyBlocks(x, y, z, 0);
		tes.setBrightness(brightness);
	}
}
