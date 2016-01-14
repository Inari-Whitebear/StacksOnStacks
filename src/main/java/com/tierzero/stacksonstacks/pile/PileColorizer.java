package com.tierzero.stacksonstacks.pile;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PileColorizer {

	@SideOnly(Side.CLIENT)
	public static void registerPileColors() {
		for (List<PileItem> list : PileItemRegistry.registeredPileItems) {
			for (PileItem pileitem : list) {
				pileitem.setColor(getColor(pileitem.getPileStack()));

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

		for (int pass = 0; pass < stack.getItem().getModel(stack, null, 0).g(stack.getItemDamage()); pass++) {

			int stackColor = getStackColour(stack, pass);

			if (stackColor != 16777215) {
				colors.add(new Color(stackColor));
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
		Item item = stack.getItem();
		ModelResourceLocation modelResourceLocation = item.getModel(stack, null, 0);
		IResource itemTexture = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(modelResourceLocation.getResourceDomain(), modelResourceLocation.getResourcePath()));
		BufferedImage texture = ImageIO.read(itemTexture.getInputStream());
		return getAverageColor(texture);
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
