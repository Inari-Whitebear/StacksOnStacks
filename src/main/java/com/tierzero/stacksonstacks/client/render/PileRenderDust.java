package com.tierzero.stacksonstacks.client.render;

import java.awt.Color;

import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.pile.PileItem;
import com.tierzero.stacksonstacks.pile.PileItemRegistry;
import com.tierzero.stacksonstacks.util.ClientUtils;
import com.tierzero.stacksonstacks.util.ConfigHandler;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class PileRenderDust extends PileRender {

	@Override
	public void render(ItemStack itemStack) {
		setCount(itemStack.stackSize, ConfigHandler.maxDustStackSize, 64);
		PileItem pileitem = PileItemRegistry.getPileItem(itemStack);
		Color color = pileitem.getColor();

		IIcon icon = SoS.blockPile.getIcon(0, 1);
		float Umin = icon.getMinU();
		float Vmax = icon.getMaxV();
		float Vmin = icon.getMinV();
		float Umax = icon.getMaxU();

		double height = count / ConfigHandler.maxDustStackSize;
		double xz = 0;
		double y = 0;
		for (int i = 0; i < 8; i++) {
			ClientUtils.drawRectangularPrism(0 + xz, 0 + y, 0 + xz, 1 - xz, .125d + y, 1 - xz, 0, 0, Umin, Vmin, Umax,	Vmax, color);

			xz += 1 / 16d;
			y += 1 / 8d;
		}
	}

}