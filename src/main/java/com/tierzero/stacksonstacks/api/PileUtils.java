package com.tierzero.stacksonstacks.api;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class PileUtils {
	public static String findStringFromItem(Item item) {
		return GameData.getItemRegistry().getNameForObject(item);
	}

	public static Item getItem(String itemname) {
		String[] split = itemname.split(":");
		return GameRegistry.findItem(split[0], split[1]);
	}

}
