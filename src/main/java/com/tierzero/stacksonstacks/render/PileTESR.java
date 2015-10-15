package com.tierzero.stacksonstacks.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.api.PileItem;
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
import net.minecraft.util.IIcon;

public class PileTESR extends TileEntitySpecialRenderer {
	Tessellator tes = Tessellator.instance;

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {

		TilePile pile = (TilePile) tile;
		PileRender render = null;

		ItemStack item = pile.getPileStack();

		if (item == null || PileItemRegistry.getPileItem(item) == null)
			return;
		switch (pile.getType()) {
		case 2:
			render = new DustRender(item);
			break;
		case 1:
			render = new GemRender(item);
			break;
		case 0:
			render = new IngotRender(item);
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
			bindBlockMap(true);
			if (render != null)
				render.render();
			tes.addTranslation((float) -x, (float) -y, (float) -z);
			ClientUtils.enableCull();
			ClientUtils.enableLighting();
		}
		ClientUtils.popMatrix();
	}

	public static void bindBlockMap(boolean blockoritem) {
		TextureManager engine = Minecraft.getMinecraft().renderEngine;
		engine.bindTexture(blockoritem ? TextureMap.locationBlocksTexture : TextureMap.locationItemsTexture);
	}

	public abstract class PileRender {
		public int count;
		public ItemStack item;

		public PileRender(ItemStack item, int count) {
			this.item = item;
			this.count = count;
		}

		public abstract void render();
	}

	public class IngotRender extends PileRender {
		private static final double HEIGHT = 0.125;
		private static final double WIDTH = 0.25;
		private static final double LENGTH = 0.5;
		private static final double SLANT_WIDTH = 0.05;
		private static final double SLANT_LENGTH = 0.025;
		private PileItem pileitem = PileItemRegistry.getPileItem(item);

		public IngotRender(ItemStack item) {
			super(item, item.stackSize);
		}

		@Override
		public void render() {

			if (pileitem == null)
				return;

			float x = 0, y = 0, z = 0;
			boolean r = false;
			for (int i = 0; i < count; i++) {
				if (i != 0) {
					if (i % 4 == 0) {
						x += .5f;
						z = 0;
					}
					if (i % 8 == 0) {
						r = !r;
						x = 0;
						z = 0;
						y += .125f;
					}

				}
				ClientUtils.pushMatrix();
				ClientUtils.translate(r ? z : x, y, r ? x : z);
				ClientUtils.disableLighting();

				IIcon icon = pileitem.getIcon(0);
				double Umin = icon.getMinU();
				double Vmax = icon.getMaxV();
				double Vmin = icon.getMinV();
				double Umax = icon.getMaxU();
				ClientUtils.drawRectangularPrism(r ? WIDTH : LENGTH, r ? LENGTH : WIDTH, HEIGHT, SLANT_WIDTH,
						SLANT_LENGTH, Umin, Vmin, Umax, Vmax, pileitem.getColor());
				ClientUtils.disableBlend();
				ClientUtils.enableLighting();
				ClientUtils.popMatrix();
				z += .25f;
			}

		}
	}

	public class DustRender extends PileRender {

		public DustRender(ItemStack item) {
			super(item, item.stackSize);
		}

		@Override
		public void render() {
			PileItem pileitem = PileItemRegistry.getPileItem(item);
			Color color = pileitem.getColor();

			IIcon icon = SoS.blockPile.getIcon(0, 1);
			float Umin = icon.getMinU();
			float Vmax = icon.getMaxV();
			float Vmin = icon.getMinV();
			float Umax = icon.getMaxU();
			tes.startDrawing(GL11.GL_TRIANGLES);
			tes.setColorOpaque(color.getRed(), color.getGreen(), color.getBlue());
			tes.addVertexWithUV(.5D, count / 64D + 0.1D, 0.5D, (double) Umax, (double) Vmin);
			tes.addVertexWithUV(0D, 0D, 1D, (double) Umin, (double) Vmin);
			tes.addVertexWithUV(0D, 0D, 0D, (double) Umin, (double) Vmax);

			tes.addVertexWithUV(.5D, count / 64D + 0.1D, 0.5D, (double) Umax, (double) Vmin);
			tes.addVertexWithUV(1D, 0D, 0D, (double) Umin, (double) Vmin);
			tes.addVertexWithUV(0D, 0D, 0D, (double) Umin, (double) Vmax);

			tes.addVertexWithUV(.5D, count / 64D + 0.1D, 0.5D, (double) Umax, (double) Vmin);
			tes.addVertexWithUV(1D, 0D, 0D, (double) Umin, (double) Vmin);
			tes.addVertexWithUV(1D, 0D, 1D, (double) Umin, (double) Vmax);

			tes.addVertexWithUV(.5D, count / 64D + 0.1D, 0.5D, (double) Umax, (double) Vmin);
			tes.addVertexWithUV(0D, 0D, 1D, (double) Umin, (double) Vmin);
			tes.addVertexWithUV(1D, 0D, 1D, (double) Umin, (double) Vmax);
			tes.draw();

		}

	}

	public class GemRender extends PileRender {

		public GemRender(ItemStack item) {
			super(item, item.stackSize);

		}

		@Override
		public void render() {
			bindBlockMap(false);

			double x = 0, y = 0, z = 0;
			IIcon icon = item.getIconIndex();
			for (int i = 0; i < count; i++) {
				if (i != 0) {
					double c = i / 64d;
					if (c % 1 == 0) {
						y = 0;
						if (c == 1) {
							x = .5d;
						} else if (c == 2) {
							x = .5d;
							z = .5d;
						} else if (c == 3) {
							x = 0;
							z = .5d;
						} else {
							x = 0;
							y = 0;
						}
					}
				}
				y += 0.015625d;
				ClientUtils.pushMatrix();
				ClientUtils.translate(x, y, z);
				ClientUtils.drawItem(icon, .5d);
				ClientUtils.popMatrix();
			}

		}

	}
}
