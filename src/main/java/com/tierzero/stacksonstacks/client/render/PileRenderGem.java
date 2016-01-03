package com.tierzero.stacksonstacks.client.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.pile.PileItem;
import com.tierzero.stacksonstacks.pile.PileItemRegistry;
import com.tierzero.stacksonstacks.util.ClientUtils;
import com.tierzero.stacksonstacks.util.ConfigHandler;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class PileRenderGem extends PileRender {


	@Override
	public void render(ItemStack itemStack) {
		setCount(itemStack.stackSize, ConfigHandler.maxGemStackSize, 256);

		float x = 0;
		float y = 0;
		float z = 0;
		IIcon icon = itemStack.getIconIndex();
		for (int i = 0; i < count; i++) {
			if (i != 0) {
				double c = i / 64d;
				if (c % 1 == 0) {
					y = 0;
					if (c == 1) {
						x = .5f;
					} else if (c == 2) {
						x = .5f;
						z = .5f;
					} else if (c == 3) {
						x = 0;
						z = .5f;
					} else {
						x = 0;
						y = 0;
					}
				}
			}
			y += 0.015625d;
			Tessellator.instance.addTranslation(x, y, z);
			ClientUtils.drawItem(icon, .5d);
			Tessellator.instance.addTranslation(-x, -y, -z);

		}
	}
}