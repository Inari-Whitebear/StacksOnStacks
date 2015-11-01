package com.tierzero.stacksonstacks.render;

import java.awt.Color;

import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.api.PileItem;
import com.tierzero.stacksonstacks.api.PileItemRegistry;
import com.tierzero.stacksonstacks.util.ClientUtils;
import com.tierzero.stacksonstacks.util.ConfigHandler;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class PileRenderDust extends PileRender {

	public PileRenderDust(ItemStack item) {
		super(item, item.stackSize, ConfigHandler.maxDustStackSize, 64);
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

		double height = count / ConfigHandler.maxDustStackSize;
		double xz = 0;
		double y = 0;
		for (int i = 0; i < 8; i++) {
			ClientUtils.drawRectangularPrism(0 + xz, 0 + y, 0 + xz, 1 - xz, .125d + y, 1 - xz, 0, 0, Umin, Vmin, Umax,
					Vmax, color);
			xz += 1 / 16d;
			y += 1 / 8d;
		}
	}

}