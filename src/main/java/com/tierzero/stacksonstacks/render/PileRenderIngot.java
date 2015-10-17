package com.tierzero.stacksonstacks.render;

import com.tierzero.stacksonstacks.api.PileItem;
import com.tierzero.stacksonstacks.api.PileItemRegistry;
import com.tierzero.stacksonstacks.util.ClientUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class PileRenderIngot extends PileRender {
	private static final double HEIGHT = 0.125;
	private static final double WIDTH = 0.25;
	private static final double LENGTH = 0.5;
	private static final double SLANT_WIDTH = 0.05;
	private static final double SLANT_LENGTH = 0.025;
	private PileItem pileitem = PileItemRegistry.getPileItem(item);

	public PileRenderIngot(ItemStack item, int maxStackSize) {
		super(item, item.stackSize, maxStackSize, 64);
	}

	@Override
	public void render() {

		if (pileitem == null) {
			return;
		}
			
		float x = 0, y = 0, z = 0;
		boolean rotate = false;	
		
		for (int i = 0; i < count; i++) {
			if (i != 0) {
				if (i % 4 == 0) {
					x += .5 ;
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

			ClientUtils.translate(rotate ? z : x, y, rotate ? x : z);
			ClientUtils.disableLighting();

			IIcon icon = pileitem.getIcon(0);
			double Umin = icon.getMinU();
			double Vmax = icon.getMaxV();
			double Vmin = icon.getMinV();
			double Umax = icon.getMaxU();
			ClientUtils.drawRectangularPrism(rotate ? WIDTH : LENGTH, rotate ? LENGTH : WIDTH, HEIGHT, SLANT_WIDTH, SLANT_LENGTH, Umin, Vmin, Umax, Vmax, pileitem.getColor());
			ClientUtils.disableBlend();
			ClientUtils.enableLighting();
			
			ClientUtils.scale(1, 1, 1);

			ClientUtils.popMatrix();
			z += .25f;

		}
	}
}