package com.tierzero.stacksonstacks.render;

import java.awt.Color;
import java.util.ArrayList;

import com.tierzero.stacksonstacks.api.Ingot;
import com.tierzero.stacksonstacks.api.IngotRegistry;
import com.tierzero.stacksonstacks.entity.EntityMinecartIngotPile;
import com.tierzero.stacksonstacks.util.ClientUtils;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderTntMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RenderIngotPile {
	
	public boolean render(ItemStack ingotStack) {
		ArrayList<IngotRender> ingots = new ArrayList<IngotRender>();
		
		if(ingotStack != null) {

			Ingot ingot = IngotRegistry.getIngot(ingotStack);
			if(ingot != null) {
				//No clue how this works. Will rewrite someday, but for now it works.
				float w = 0f, h = 0f, l = 0f;
				float a = 0f, s = 0f, d = 0f;
				boolean rotate = true;
				int e = 0;

				for (int i = 0; i < ingotStack.stackSize; i++) {
					w = a / 4;
					l = d / 2;
					h = s / 8;

					ingots.add(new IngotRender((rotate ? w : l), h, (rotate ? l : w), ingot, rotate));
					if (a < 3)
						a++;
					else {
						w = 0;
						a = 0;
						if (d == 0)
							d++;
						else {
							d = 0;
							if (s < 8)
								s++;
						}
					}
					if (e == 7) {
						e = 0;
						rotate = !rotate;
					} else
						e++;
				}

				for (IngotRender render : ingots) {
					render.render();
				}
			}

		}

		return ingots.size() > 0;
	}
	
	public class IngotRender {
		private static final double HEIGHT = 0.125;
		private static final double WIDTH = 0.25;
		private static final double LENGTH = 0.5;
		private static final double SLANT_WIDTH = 0.05;
		private static final double SLANT_LENGTH = 0.025;

		public Ingot ingot;
		boolean rotate;
		float x;
		float y;
		float z;

		public IngotRender(float x, float y, float z, Ingot ingot, boolean rotate) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.ingot = ingot;
			this.rotate = rotate;
		}
		
		public void render() {
			System.out.println(x + " " + y + " " + z);
			Color color = ingot.getColor();
			IIcon icon = ingot.getIcon();
			
			
			Tessellator tessellator = Tessellator.instance;
			tessellator.addTranslation(x, y, z);
			tessellator.setColorOpaque(color.getRed(), color.getGreen(), color.getBlue());

			double Umin = icon.getMinU();
			double Vmax = icon.getMaxV();
			double Vmin = icon.getMinV();
			double Umax = icon.getMaxU();


			ClientUtils.drawRectangularPrism(rotate ? WIDTH : LENGTH, rotate ? LENGTH : WIDTH, HEIGHT, SLANT_WIDTH, SLANT_LENGTH, Umin, Vmin, Umax, Vmax);
			tessellator.addTranslation(-x, -y, -z);
		}

	}
}