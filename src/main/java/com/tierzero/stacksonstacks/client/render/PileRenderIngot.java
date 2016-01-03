package com.tierzero.stacksonstacks.client.render;

import java.awt.Color;

import com.tierzero.stacksonstacks.pile.PileItem;
import com.tierzero.stacksonstacks.pile.PileItemRegistry;
import com.tierzero.stacksonstacks.util.ClientUtils;
import com.tierzero.stacksonstacks.util.ConfigHandler;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class PileRenderIngot extends PileRender {
	private static final double HEIGHT = 0.125;
	private static final double WIDTH = 0.25;
	private static final double LENGTH = 0.5;
	private static final double SLANT_WIDTH = 0.05;
	private static final double SLANT_LENGTH = 0.025;

	@Override
	public void render(ItemStack itemStack) {
		setCount(itemStack.stackSize, ConfigHandler.maxIngotStackSize, 64);
		PileItem pileitem = PileItemRegistry.getPileItem(itemStack);

		float x = 0; 
		float y = 0;
		float z = 0;
		boolean rotate = false;

		for (int i = 0; i < count; i++) {
			if (i != 0) {
				if (i % 4 == 0) {
					x += .5;
					z = 0;
				}
				if (i % 8 == 0) {
					rotate = !rotate;
					x = 0;
					z = 0;
					y += .125f;
				}

			}

			ClientUtils.pushMatrix();

			Color color = pileitem.getColor();
			IIcon icon = pileitem.getIcon(0);
			if (pileitem.getOverride() != null) {
				icon = pileitem.getOverride();
				color = Color.white;
			}
			double Umin = icon.getMinU();
			double Vmax = icon.getMaxV();
			double Vmin = icon.getMinV();
			double Umax = icon.getMaxU();
			Tessellator.instance.addTranslation(rotate ? z : x, y, rotate ? x : z);
			ClientUtils.drawRectangularPrism(rotate ? WIDTH : LENGTH, rotate ? LENGTH : WIDTH, HEIGHT, SLANT_WIDTH,
					SLANT_LENGTH, Umin, Vmin, Umax, Vmax, color);
			Tessellator.instance.addTranslation(rotate ? -z : -x, -y, rotate ? -x : -z);

			ClientUtils.popMatrix();
			z += .25f;

		}
	}
}