package com.tierzero.stacksonstacks.render;

import org.lwjgl.opengl.GL11;

import com.tierzero.stacksonstacks.entity.EntityMinecartIngotPile;
import com.tierzero.stacksonstacks.util.ClientUtils;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLightningBolt;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.ItemStack;

public class RenderEntityMinecartIngotPile extends RenderMinecart {

	private static RenderIngotPile ingotPileRender = new RenderIngotPile();
	
	
	@Override
	public void doRender(EntityMinecart minecart, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
		super.doRender(minecart, x, y, z, p_76986_8_, p_76986_9_);
		
		ItemStack ingotStack = ((EntityMinecartIngotPile) minecart).getIngotStack();
		if(ingotStack != null) {

			Tessellator tes = Tessellator.instance;

			ClientUtils.pushMatrix();
			ClientUtils.translate(x, y, z);

				tes.startDrawingQuads();
	
				
				ingotPileRender.render(ingotStack);
	
				tes.draw();

			ClientUtils.popMatrix();

		}
	}
	
	

}
