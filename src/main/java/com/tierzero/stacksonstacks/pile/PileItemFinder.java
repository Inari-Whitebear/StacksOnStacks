package com.tierzero.stacksonstacks.pile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.tierzero.stacksonstacks.util.ConfigHandler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.oredict.OreDictionary;

public class PileItemFinder {
	private static String[] invalidIngotNames = ConfigHandler.invalidIngots;
	private static String[] invalidGemNames = ConfigHandler.invalidGems;
	private static String[] invalidDustNames = ConfigHandler.invalidDusts;
	private static String[] validIngotNames = ConfigHandler.validIngots;
	private static String[] validGemNames = ConfigHandler.validGems;
	private static String[] validDustNames = ConfigHandler.validDusts;

	private static String[][] invalidNames = new String[][] { invalidIngotNames, invalidGemNames, invalidDustNames };
	private static String[][] validKeyWord = new String[][] { validIngotNames, validGemNames, validDustNames };

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
				PileItemRegistry.registerPileItem(stack, type);
			}
		}
		for (String validName : validRegisteredNames) {
			ItemStack stack = new ItemStack(itemRegistry.getObject(validName));
			PileItemRegistry.registerPileItem(stack, type);
		}
	}

	private static List<String> getValidNames(List<String> names, int type) {
		List<String> validNames = new ArrayList<String>();
		String searcher;
		for (String name : names) {

			if (name.indexOf(':') != -1) {
				searcher = name.substring(name.indexOf(':'));
			} else {
				searcher = name;
			}
			for (String valid : validKeyWord[type]) {
				if (!searcher.isEmpty() && searcher.contains(valid)) {
					boolean invalid = false;

					for (String invalidName : invalidNames[type]) {
						if (searcher.contains(invalidName)) {
							invalid = true;
						}
					}

					if (!invalid) {
						validNames.add(name);
					}
				}
			}
		}

		return validNames;
	}
}