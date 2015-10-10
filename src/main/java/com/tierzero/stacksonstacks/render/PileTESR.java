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
		ClientUtils.pushMatrix();
		{

			TilePile pile = (TilePile) tile;
			PileRender render = null;

			ItemStack item = pile.getPileStack();
			if (item == null)
				return;
			switch (pile.getType()) {
			case DUST:
				render = new DustRender(item);
				break;
			case GEM:
				render = new GemRender(item);
				break;
			case INGOT:
				render = new IngotRender(item);
				break;
			default:
				return;
			}
			tes.addTranslation((float) x, (float) y, (float) z);
			bindBlockMap(true);
			render.render();
			tes.addTranslation((float) -x, (float) -y, (float) -z);

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
		PileItem pileitem = PileItemRegistry.getPileItem(item);

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
				new Ingot(pileitem, r, r ? z : x, y, r ? x : z).render();
				z += .25f;
			}

		}

		public class Ingot {
			private static final double HEIGHT = 0.125;
			private static final double WIDTH = 0.25;
			private static final double LENGTH = 0.5;
			private static final double SLANT_WIDTH = 0.05;
			private static final double SLANT_LENGTH = 0.025;

			public PileItem ingot;
			boolean rotate;
			public float x, y, z;
			public Color color;

			public Ingot(PileItem ingot, boolean rotate, float x, float y, float z) {
				this.x = x;
				this.y = y;
				this.z = z;
				this.ingot = ingot;
				this.rotate = rotate;
				color = ingot.getColor();

			}

			public void render() {

				ClientUtils.pushMatrix();
				{
					ClientUtils.translate(x, y, z);
					ClientUtils.enableBlend();
					ClientUtils.disableLighting();

					IIcon icon = ingot.getIcon(0);
					double Umin = icon.getMinU();
					double Vmax = icon.getMaxV();
					double Vmin = icon.getMinV();
					double Umax = icon.getMaxU();
					ClientUtils.drawRectangularPrism(rotate ? WIDTH : LENGTH, rotate ? LENGTH : WIDTH, HEIGHT,
							SLANT_WIDTH, SLANT_LENGTH, Umin, Vmin, Umax, Vmax, color);
					ClientUtils.disableBlend();
					ClientUtils.enableLighting();
				}
				ClientUtils.popMatrix();
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
			ClientUtils.pushMatrix();
			{
				ClientUtils.disableCull();
				ClientUtils.disableLighting();
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
			ClientUtils.popMatrix();
		}

	}

	public class GemRender extends PileRender {

		public GemRender(ItemStack item) {
			super(item, item.stackSize);

		}

		@Override
		public void render() {
			bindBlockMap(false);

			ClientUtils.pushMatrix();
			{
				ClientUtils.disableCull();
				ClientUtils.disableLighting();
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
					new Gem(x, y, z, icon).render();

				}

				ClientUtils.enableLighting();
				ClientUtils.enableCull();
			}
			ClientUtils.popMatrix();
		}

		public class Gem {

			double x, y, z;
			double[] offsets;
			IIcon icon;

			public Gem(double x, double y, double z, IIcon icon) {
				this.x = x;
				this.y = y;
				this.z = z;
				this.icon = icon;

			}

			public void render() {
				ClientUtils.pushMatrix();
				{

					ClientUtils.translate(x, y, z);
					ClientUtils.drawItem(icon, .5d);
					ClientUtils.popMatrix();
				}
			}
		}
	}
	/*
	 * public static void renderDustPile(ItemStack stack) { Tessellator tes =
	 * Tessellator.instance; TextureManager engine =
	 * Minecraft.getMinecraft().renderEngine;
	 * engine.bindTexture(TextureMap.locationBlocksTexture);
	 * ClientUtils.pushMatrix(); {
	 * 
	 * ClientUtils.disableLighting();
	 * 
	 * IIcon icon = PileItem.getIcon(1); Color color = Color.white; if
	 * (PileItemRegistry.getPileItem(stack) != null) color =
	 * PileItemRegistry.getPileItem(stack).getColor(); float Umin =
	 * icon.getMinU(); float Vmax = icon.getMaxV(); float Vmin = icon.getMinV();
	 * float Umax = icon.getMaxU(); double width = .0625; tes.setNormal(0.0F,
	 * 0.5F, 0.0F); ClientUtils.disableCull();
	 * 
	 * 0D, 0D, 0D .5D, .5D, .5D .5D, 0.5D, .5D 0D, 0D, 1D
	 * 
	 * 
	 * 
	 * 
	 * ClientUtils.enableLighting();
	 * 
	 * }ClientUtils.popMatrix();}
	 * 
	 * public static class GemRender { public ItemStack gem; public float x, y,
	 * z, w, h, l, t;
	 * 
	 * public GemRender(ItemStack item, float x, float y, float z, float w,
	 * float h, float l, float t, int i) {
	 * 
	 * this.x = x; this.y = y; this.z = z; this.w = w; this.h = h; this.l = l;
	 * this.t = t; gem = item; render(i); }
	 * 
	 * public void render(int i) { Tessellator tes = Tessellator.instance;
	 * TextureManager engine = Minecraft.getMinecraft().renderEngine;
	 * engine.bindTexture(TextureMap.locationItemsTexture);
	 * ClientUtils.pushMatrix(); { ClientUtils.disableCull();
	 * ClientUtils.disableLighting(); ClientUtils.scale(0.5d, 0.25d, 0.5d);
	 * ClientUtils.rotate(90, 1, 0, 0); ClientUtils.translate(w, l, h - .0625f);
	 * IIcon icon = gem.getIconIndex(); float Umin = icon.getMinU(); float Vmax
	 * = icon.getMaxV(); float Vmin = icon.getMinV(); float Umax =
	 * icon.getMaxU(); ClientUtils.drawItem(Umax, Vmin, Umin, Vmax, t);
	 * engine.bindTexture(Textur.locationBlocksTexture);
	 * ClientUtils.enableLighting(); ClientUtils.enableCull(); }
	 * ClientUtils.popMatrix(); }
	 * 
	 * }
	 * 
	 * public void renderGemPile(ItemStack stack, double x, double y, double z)
	 * {
	 * 
	 * float w = 0f, h = .0625f, l = 0f, t = .0625f; for (int i = 0; i <
	 * stack.stackSize; i++) { if (i != 0) { if (i % 64 == 0) { w++; h = .0625f;
	 * t = 0; } if (i % 128 == 0) { w = 0; l++; } } new GemRender(stack, (float)
	 * x, (float) y, (float) z, w, h, l, t, i); t += 0.062f; }
	 * 
	 * }
	 */
}
