package com.tierzero.stacksonstacks.api;

import java.util.ArrayList;
import java.util.List;

import com.tierzero.stacksonstacks.SoS;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IngotRegistry {

	private static List<Ingot> registeredIngots = new ArrayList<Ingot>();

	public static void registerIngot(Item ingotItem, int meta, String registeredName) {
		FMLLog.info("[StacksOnStacks] Registered Ingot: " + registeredName);
		registeredIngots.add(new Ingot(ingotItem, meta, registeredName));
	}

	public static void registerIngot(ItemStack stack, String registeredName) {
		registerIngot(stack.getItem(), stack.getItemDamage(), registeredName);
	}

	public static Ingot getIngot(Item item, int meta) {
		for (Ingot ingot : registeredIngots) {
			if (ingot.getItem() == item && ingot.getMeta() == meta)
				return ingot;
		}
		return null;
	}

	public static Ingot getIngot(ItemStack stack) {
		return getIngot(stack.getItem(), stack.getItemDamage());	
	}

	public static boolean isValidIngot(ItemStack stack) {
		if (stack != null) {
			Ingot ingot = getIngot(stack);
			if (ingot != null) {

				return true;
			}
		}

		return false;
	}

	public static List<Ingot> getRegisteredIngots() {
		return registeredIngots;
	}



}
