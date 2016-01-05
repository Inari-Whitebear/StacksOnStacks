package com.tierzero.stacksonstacks.pile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class PileItemRegistry {
	public static List<PileItemList> registeredPileItems = new ArrayList<PileItemList>();
	public static PileItemList registeredIngots = new PileItemList();
	public static PileItemList registeredGems = new PileItemList();
	public static PileItemList registeredDusts = new PileItemList();

	public static PileItem registerPileItem(ItemStack stack, int type) {
		if (getPileItem(stack) == null) {
			PileItem item = new PileItem(stack, type);
			registeredPileItems.get(type).add(item);
			// if (!SoS.proxy.isClient())
			// SoS.gson.toJson(item, SoS.jsonwriter);
			return item;
		}
		return null;
	}

	public static PileItem getPileItem(ItemStack stack) {
		for (PileItemList list : registeredPileItems) {
			for (PileItem item : list) {
				if (stack.getItem() == item.getItem() && stack.getItemDamage() == item.getMeta())
					return item;
			}
		}
		return null;
	}

	public static int getPileType(ItemStack stack) {
		return getPileItem(stack).getType();
	}

	public static boolean isValidPileItem(ItemStack stack) {
		return getPileItem(stack) != null;
	}

	public static PileItem registerIngot(ItemStack stack) {
		return registerPileItem(stack, 0);
	}

	public static PileItem registerGem(ItemStack stack) {
		return registerPileItem(stack, 1);
	}

	public static PileItem registerDust(ItemStack stack) {
		return registerPileItem(stack, 2);
	}

	public static class PileItemList extends ArrayList<PileItem> {
		public int index;

		public PileItemList() {
			registeredPileItems.add(this);
			index = registeredPileItems.size() - 1;
		}
	}
}
