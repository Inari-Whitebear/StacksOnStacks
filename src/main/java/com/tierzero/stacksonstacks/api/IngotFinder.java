package com.tierzero.stacksonstacks.api;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

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
import scala.actors.threadpool.Arrays;

public class IngotFinder {
	public static Set<String> invalid = new HashSet<String>(
			Arrays.asList(new String[] { "ingotDouble", "ingotTriple", "ingotQuad", "ingotQuin" }));

	public static void registerIngots() {
		for (String ore : OreDictionary.getOreNames()) {
			if (ore.isEmpty())
				continue;
			boolean skip = false;
			for (String inv : invalid)
				if (ore.startsWith(inv))
					skip = true;
			if (skip)
				continue;
			else if (ore.startsWith("ingot")) {
				for (ItemStack stack : OreDictionary.getOres(ore)) {

					Ingot.newIngot(stack);
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

		Set<Color> colours = new LinkedHashSet<Color>();
		try {
			BufferedImage texture = ImageIO.read(Minecraft.getMinecraft().getResourceManager()
					.getResource(ClientUtils.getIconResource(stack)).getInputStream());
			Color texColour = getAverageColour(texture);
			colours.add(texColour);

			for (int pass = 0; pass < stack.getItem().getRenderPasses(stack.getItemDamage()); pass++) {

				int c = getStackColour(stack, pass);
				if (c != 16777215) {
					colours.add(new Color(c));
					colours.remove(texColour);
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
