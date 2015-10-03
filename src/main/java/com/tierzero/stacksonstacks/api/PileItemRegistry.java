package com.tierzero.stacksonstacks.api;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class PileItemRegistry {

	public static ArrayList<PileItemList> registeredPileItems = new ArrayList<PileItemList>();
	private static PileItemList registeredIngots = new PileItemList();
	private static PileItemList registeredGems = new PileItemList();
	private static PileItemList registeredDusts = new PileItemList();

	public static void registerPileItem(ItemStack stack, String name, int type) {
		if (getPileItem(stack) == null) {
			// FMLLog.info("[StacksOnStacks] Registered Ingot: " + name + " or "
			// + stack.getUnlocalizedName()
			// + " with type " + type);
			registeredPileItems.get(type).add(new PileItem(stack, type, name));
		}

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

	public static class PileItemList extends ArrayList<PileItem> {
		public int index;

		public PileItemList() {
			registeredPileItems.add(this);
			index = registeredPileItems.size() - 1;
		}
	}

	public static void registerIngot(ItemStack stack, String name) {
		registerPileItem(stack, name, 0);
	}

	public static void registerGem(ItemStack stack, String name) {
		registerPileItem(stack, name, 1);
	}

	public static void registerDust(ItemStack stack, String name) {
		registerPileItem(stack, name, 2);
	}

	public static void setIngotTexture(ItemStack stack, String textureName) {

	}
}
