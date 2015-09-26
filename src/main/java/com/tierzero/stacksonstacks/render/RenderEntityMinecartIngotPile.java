package com.tierzero.stacksonstacks.render;

import org.lwjgl.opengl.GL11;

import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.entity.EntityMinecartIngotPile;
import com.tierzero.stacksonstacks.util.ClientUtils;

import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraft.client.renderer.entity.RenderMinecartMobSpawner;
import net.minecraft.client.renderer.entity.RenderTntMinecart;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class RenderEntityMinecartIngotPile extends RenderMinecart {

	private static RenderIngotPile ingotPileRender = new RenderIngotPile();
	
	/*
	@Override
	public void doRender(EntityMinecart minecart, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
		super.doRender(minecart, x, y, z, p_76986_8_, p_76986_9_);
		
		
		ItemStack ingotStack = ((EntityMinecartIngotPile) minecart).getIngotStack();
		if(ingotStack != null) {
			//ingotPileRender.render(ingotStack, (float) minecart.posX, (float) (minecart.posY), (float) minecart.posZ);
		}
	}
	*/
	

}
