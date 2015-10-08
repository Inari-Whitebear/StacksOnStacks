package com.tierzero.stacksonstacks.render;

import com.tierzero.stacksonstacks.entity.EntityMinecartIngotPile;
import com.tierzero.stacksonstacks.util.ClientUtils;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.ItemStack;

public class RenderEntityMinecartIngotPile extends RenderMinecart {

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	@Override
	public void doRender(EntityMinecart minecart, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
		super.doRender(minecart, x, y, z, p_76986_8_, p_76986_9_);

		ItemStack ingotStack = ((EntityMinecartIngotPile) minecart).getPileStack();
		if (ingotStack != null) {

			Tessellator tes = Tessellator.instance;

			ClientUtils.pushMatrix();
			ClientUtils.translate(x, y, z);

			tes.startDrawingQuads();

			tes.draw();

			ClientUtils.popMatrix();

		}
	}

}
