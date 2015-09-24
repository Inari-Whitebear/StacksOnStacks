package com.tierzero.stacksonstacks.api;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import com.google.common.collect.Lists;
import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.compat.RotaryCompat;
import com.tierzero.stacksonstacks.util.ClientUtils;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class IngotFinder {
	private static String[] invalidNames = new String[] { "ingotDouble", "ingotTriple", "ingotQuad", "ingotQuin" };

	public static void registerIngots() {		
		FMLControlledNamespacedRegistry<Item> itemRegistry = GameData.getItemRegistry();
		Set<String> registeredItemNames = itemRegistry.getKeys();

		List<String> validRegisteredNames = getValidNames(Lists.newArrayList(registeredItemNames));
		List<String> validOredictNames = getValidNames(Lists.newArrayList(OreDictionary.getOreNames()));
			
		for(String validName : validOredictNames) {
			for(ItemStack stack : OreDictionary.getOres(validName)) {
				IngotRegistry.registerIngot(stack, validName);
			}
		}
		
		for(String validName : validRegisteredNames) {
			ItemStack stack = new ItemStack(itemRegistry.getObject(validName));
			IngotRegistry.registerIngot(stack, validName);
		}
	}
	
	private static List<String> getValidNames(List<String> names) {
		List<String> validNames = new ArrayList<String>();
		
		for (String name : names) {
			if (!name.isEmpty() && name.contains("ingot")) {
				boolean invalid = false;
				for (String invalidName : invalidNames) {
					if (name.startsWith(invalidName)) {
						invalid = true;
					}
				}

				if(!invalid) {
					validNames.add(name);
				}
			}
		}
		
		return validNames;
	}
}