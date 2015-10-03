package com.tierzero.stacksonstacks.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.tierzero.stacksonstacks.api.Pile.Type;
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
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		ClientUtils.pushMatrix();
		TilePile pile = (TilePile) tile;
		ClientUtils.translate(x, y, z);
		if (pile.getType() == Type.GEM)
			renderGemPile(pile.getPileStack(), x, y, z);
		else if (pile.getType() == Type.DUST)
			renderDustPile(pile.getPileStack());
		ClientUtils.popMatrix();
	}

	public static void renderDustPile(ItemStack stack) {
		Tessellator tes = Tessellator.instance;
		TextureManager engine = Minecraft.getMinecraft().renderEngine;
		engine.bindTexture(TextureMap.locationBlocksTexture);
		ClientUtils.pushMatrix();
		{

			ClientUtils.disableLighting();

			IIcon icon = PileItem.getIcon(1);
			Color color = Color.white;
			if (PileItemRegistry.getPileItem(stack) != null)
				color = PileItemRegistry.getPileItem(stack).getColor();
			float Umin = icon.getMinU();
			float Vmax = icon.getMaxV();
			float Vmin = icon.getMinV();
			float Umax = icon.getMaxU();
			double width = .0625;
			tes.setNormal(0.0F, 0.5F, 0.0F);
			ClientUtils.disableCull();

			/*
			 * 0D, 0D, 0D .5D, .5D, .5D .5D, 0.5D, .5D 0D, 0D, 1D
			 */

			for (int i = 0; i < 4; i++) {
				ClientUtils.pushMatrix();

				ClientUtils.rotate(90 * i, 0, 1, 0);
				int x = 0, z = 0;
				switch (i) {

				case 1:
					x--;
					break;
				case 2:
					x--;
					z--;
					break;
				case 3:
					z--;
					break;
				}
				ClientUtils.translate(x, 0, z);
				tes.startDrawing(GL11.GL_TRIANGLES);
				tes.setColorOpaque(color.getRed(), color.getGreen(), color.getBlue());
				tes.addVertexWithUV(.5D, stack.stackSize / 64D + 0.1D, 0.5D, (double) Umax, (double) Vmin);
				tes.addVertexWithUV(0D, 0D, 1D, (double) Umin, (double) Vmin);
				tes.addVertexWithUV(0D, 0D, 0D, (double) Umin, (double) Vmax);

				tes.draw();
				ClientUtils.popMatrix();

			}

			ClientUtils.enableLighting();

		}
		ClientUtils.popMatrix();
	}

	public static class GemRender {
		public ItemStack gem;
		public float x, y, z, w, h, l, t;

		public GemRender(ItemStack item, float x, float y, float z, float w, float h, float l, float t, int i) {

			this.x = x;
			this.y = y;
			this.z = z;
			this.w = w;
			this.h = h;
			this.l = l;
			this.t = t;
			gem = item;
			render(i);
		}

		public void render(int i) {
			Tessellator tes = Tessellator.instance;
			TextureManager engine = Minecraft.getMinecraft().renderEngine;
			engine.bindTexture(TextureMap.locationItemsTexture);
			ClientUtils.pushMatrix();
			{
				ClientUtils.disableCull();
				ClientUtils.disableLighting();
				ClientUtils.scale(0.5d, 0.25d, 0.5d);
				ClientUtils.rotate(90, 1, 0, 0);
				ClientUtils.translate(w, l, h - .0625f);
				IIcon icon = gem.getIconIndex();
				float Umin = icon.getMinU();
				float Vmax = icon.getMaxV();
				float Vmin = icon.getMinV();
				float Umax = icon.getMaxU();
				ClientUtils.drawItem(Umax, Vmin, Umin, Vmax, t);
				engine.bindTexture(TextureMap.locationBlocksTexture);
				ClientUtils.enableLighting();
				ClientUtils.enableCull();
			}
			ClientUtils.popMatrix();
		}
	}

	public void renderGemPile(ItemStack stack, double x, double y, double z) {

		float w = 0f, h = .0625f, l = 0f, t = .0625f;
		for (int i = 0; i < stack.stackSize; i++) {
			if (i != 0) {
				if (i % 64 == 0) {
					w++;
					h = .0625f;
					t = 0;
				}
				if (i % 128 == 0) {
					w = 0;
					l++;
				}
			}
			new GemRender(stack, (float) x, (float) y, (float) z, w, h, l, t, i);
			t += 0.062f;
		}

	}

}
