package com.tierzero.stacksonstacks.compat;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class ModCompat {
	public String name;
	public boolean compatEnabled = true;

	public ModCompat(String name) {
		this.name = name;
	}

	public void config() {

	}

	public abstract void preInit();

	public abstract void init();

	public abstract void postInit();

	public boolean isEnabled() {
		return true;
	}

	public ItemStack getItemStack(String modId, String itemName, int meta) {
		return new ItemStack(findItem(modId, itemName), 1, meta);
	}

	public Item findItem(String modId, String item) {
		return GameRegistry.findItem(modId, item);
	}

	public Block findBlock(String item) {
		return GameRegistry.findBlock(name, item);
	}
}
