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

public class PileRenderGem extends PileRender {

	public PileRenderGem(ItemStack item) {
		super(item, item.stackSize, 256, 256);

	}

	@Override
	public void render() {
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
			ClientUtils.pushMatrix();
			ClientUtils.translate(x, y, z);
			ClientUtils.drawItem(icon, .5d);
			ClientUtils.popMatrix();
		}
	}
}