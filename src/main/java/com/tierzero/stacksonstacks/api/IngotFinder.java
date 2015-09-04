package com.tierzero.stacksonstacks.api;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.tierzero.stacksonstacks.compat.GregTechCompat;
import com.tierzero.stacksonstacks.util.ClientUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class IngotFinder {
	private static String[] invalidNames = new String[] { "ingotDouble", "ingotTriple", "ingotQuad", "ingotQuin" };

	public static void registerIngots() {
		for (String ore : OreDictionary.getOreNames()) {
			if (!ore.isEmpty() && ore.startsWith("ignot")) {
				boolean invalidOre = false;
				for (String invalid : invalidNames) {
					if (ore.startsWith(invalid)) {
						invalidOre = true;
					}
				}

				if(!invalidOre) {
					for (ItemStack stack : OreDictionary.getOres(ore)) {
						Ingot.newIngot(stack);
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public static void registerIngotColors() {
		for (Ingot ingot : Ingot.ingots) {
			ingot.setColor(getColour(ingot.getIngotStack()));
		}
		Ingot.getIngot(Items.gold_ingot, 0).setIcon(Blocks.gold_block.getIcon(0, 0));
	}

	@SideOnly(Side.CLIENT)
	public static int getStackColour(ItemStack stack, int pass) {
		return stack.getItem().getColorFromItemStack(stack, pass);
	}

	@SideOnly(Side.CLIENT)
	public static Color getColour(ItemStack stack) {
		List<Color> colours = new ArrayList<Color>();
		try {
			BufferedImage texture = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(ClientUtils.getIconResource(stack)).getInputStream());
			Color textureColour = getAverageColour(texture);
			colours.add(textureColour);

			for (int pass = 0; pass < stack.getItem().getRenderPasses(stack.getItemDamage()); pass++) {

				int stackColor = getStackColour(stack, pass);
				
				/* Check to see if color is white, because white looks awful */
				if (stackColor != 16777215) {
					colours.add(new Color(stackColor));
					colours.remove(textureColour);
				}
			}
		} catch (Exception e) {
		}

		if (GregTechCompat.INSTANCE.isEnabled()) {
			try {
				Class<?> cls = Class.forName("gregapi.item.prefixitem.PrefixItem");
				Class<?> itemCls = stack.getItem().getClass();
				if (cls.isAssignableFrom(itemCls)) {
					int c = getStackColour(stack, 0);
					if (c != 16777215) {
						colours.add(new Color(c));
					}
				}
			} catch (ClassNotFoundException e) {
			}
		}
		float red = 0;
		float green = 0;
		float blue = 0;
		for (Color c : colours) {
			red += c.getRed();
			green += c.getGreen();
			blue += c.getBlue();
		}
		float count = colours.size();

		return new Color((int) (red / count), (int) (green / count), (int) (blue / count));
	}

	@SideOnly(Side.CLIENT)
	private static Color getAverageColour(BufferedImage image) {
		float red = 0;
		float green = 0;
		float blue = 0;
		float count = 0;
		int offset = 4;
		for (int i = offset; i < image.getWidth() - offset; i++)
			for (int j = offset; j < image.getHeight() - offset; j++) {
				Color c = new Color(image.getRGB(i, j));
				if (c.getAlpha() != 255 || c.getRed() <= 10 && c.getBlue() <= 10 && c.getGreen() <= 10)
					continue;
				red += c.getRed();
				green += c.getGreen();
				blue += c.getBlue();
				count++;
			}

		return new Color((int) (red / count), (int) (green / count), (int) (blue / count));
	}
	

}
