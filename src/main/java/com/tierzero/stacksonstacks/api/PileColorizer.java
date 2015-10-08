package com.tierzero.stacksonstacks.api;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.tierzero.stacksonstacks.util.ClientUtils;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;

public class PileColorizer {

	@SideOnly(Side.CLIENT)
	public static void registerPileColors() {
		for (List<PileItem> list : PileItemRegistry.registeredPileItems) {
			for (PileItem pileitem : list) {
				pileitem.setColor(getColor(pileitem.getPileStack()));
				System.out.println(pileitem.getColor());
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public static int getStackColour(ItemStack stack, int pass) {
		return stack.getItem().getColorFromItemStack(stack, pass);
	}

	@SideOnly(Side.CLIENT)
	public static Color getColor(ItemStack stack) {
		List<Color> colors = new ArrayList<Color>();
		try {
			colors.add(getColorFromTexture(stack));
		} catch (Exception e) {
		}

		for (int pass = 0; pass < stack.getItem().getRenderPasses(stack.getItemDamage()); pass++) {

			int stackColor = getStackColour(stack, pass);

			if (stackColor != 16777215) {
				colors.add(new Color(stackColor));
			}
		}

		if (Loader.isModLoaded("gregtech")) {
			try {
				Color gregColor = getGregtechColor(stack);
				if (gregColor != null) {
					colors.clear();
					colors.add(gregColor);
				}
			} catch (Exception e) {
			}
		}

		float red = 0;
		float green = 0;
		float blue = 0;
		for (Color c : colors) {
			red += c.getRed();
			green += c.getGreen();
			blue += c.getBlue();
		}
		float count = colors.size();

		Color pileitemColor = new Color((int) (red / count), (int) (green / count), (int) (blue / count));

		if (pileitemColor.getRed() == 0 && pileitemColor.getBlue() == 0 && pileitemColor.getRed() == 0) {
			// Change it to look like iron
			pileitemColor = new Color(156, 156, 156);
		}

		return pileitemColor;
	}

	private static Color getColorFromTexture(ItemStack stack) throws IOException {
		BufferedImage texture = ImageIO.read(ClientUtils.getIconResource(stack).getInputStream());
		return getAverageColor(texture);
	}

	private static Color getGregtechColor(ItemStack stack) throws ClassNotFoundException, NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> cls = Class.forName("gregtech.api.items.GT_MetaGenerated_Item");
		Class<?> itemCls = stack.getItem().getClass();
		if (cls.isAssignableFrom(itemCls)) {
			Method m = itemCls.getMethod("getRGBa", ItemStack.class);
			short[] rgba = (short[]) m.invoke(stack.getItem(), stack);
			return new Color(rgba[0], rgba[1], rgba[2], rgba[3]);

		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	private static Color getAverageColor(BufferedImage image) {
		int red = 0;
		int green = 0;
		int blue = 0;
		int count = 0;
		int offset = 4;
		for (int i = offset; i < image.getWidth() - offset; i++) {
			for (int j = offset; j < image.getHeight() - offset; j++) {
				Color imageColor = new Color(image.getRGB(i, j));

				if (imageColor.getAlpha() != 255
						|| imageColor.getRed() <= 10 && imageColor.getBlue() <= 10 && imageColor.getGreen() <= 10) {
					continue;
				}

				red += imageColor.getRed();
				green += imageColor.getGreen();
				blue += imageColor.getBlue();
				count++;
			}
		}

		return new Color((int) (red / count), (int) (green / count), (int) (blue / count));
	}
}
