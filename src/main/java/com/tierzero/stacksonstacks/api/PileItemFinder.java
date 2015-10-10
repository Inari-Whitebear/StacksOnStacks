package com.tierzero.stacksonstacks.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.tierzero.stacksonstacks.util.ConfigHandler;

import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class PileItemFinder {
	private static String[] invalidIngotNames = ConfigHandler.invalidIngots;
	private static String[] invalidGemNames = ConfigHandler.invalidGems;
	private static String[] invalidDustNames = ConfigHandler.invalidDusts;
	private static String[][] invalidNames = new String[][] { invalidIngotNames, invalidGemNames, invalidDustNames };
	private static String[] validKeyWord = new String[] { "ingot", "gem", "dust" };

	public static void registerAllItems() {
		for (int type = 0; type < validKeyWord.length; type++) {
			registerPileItem(type);
		}
	}

	public static void registerPileItem(int type) {
		FMLControlledNamespacedRegistry<Item> itemRegistry = GameData.getItemRegistry();
		Set<String> registeredItemNames = itemRegistry.getKeys();

		List<String> validRegisteredNames = getValidNames(Lists.newArrayList(registeredItemNames), type);
		List<String> validOredictNames = getValidNames(Lists.newArrayList(OreDictionary.getOreNames()), type);

		for (String validName : validOredictNames) {
			for (ItemStack stack : OreDictionary.getOres(validName)) {
				PileItemRegistry.registerPileItem(stack, validName, type);
			}
		}
		if (type != 1) {
			for (String validName : validRegisteredNames) {
				ItemStack stack = new ItemStack(itemRegistry.getObject(validName));
				PileItemRegistry.registerPileItem(stack, validName, type);
			}
		}
	}

	private static List<String> getValidNames(List<String> names, int type) {
		List<String> validNames = new ArrayList<String>();

		for (String name : names) {
			if (!name.isEmpty() && name.contains(validKeyWord[type])) {
				boolean invalid = false;

				for (String invalidName : invalidNames[type]) {
					if (name.contains(invalidName)) {
						invalid = true;
					}
				}

				if (!invalid) {
					validNames.add(name);
				}
			}
		}

		return validNames;
	}
}