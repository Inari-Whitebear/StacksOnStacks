package com.tierzero.stacksonstacks.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.api.PileItem;
import com.tierzero.stacksonstacks.api.PileItemRegistry;
import com.tierzero.stacksonstacks.util.ClientUtils;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class PileRenderDust extends PileRender {

	public PileRenderDust(ItemStack item) {
		super(item, item.stackSize, 64, 64);
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
		
		Tessellator tes = Tessellator.instance;
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