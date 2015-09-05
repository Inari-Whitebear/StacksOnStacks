package com.tierzero.stacksonstacks.compat;

import org.apache.commons.lang3.StringUtils;

import com.tierzero.stacksonstacks.SoS;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public abstract class ModCompat {
	public String name;
	public boolean compatEnabled = false;

	public ModCompat(String name) {
		this.name = name;
	}

	public void config() {
		if (Loader.isModLoaded(name)) {
			FMLLog.info("Loading Compat for " + name);
			compatEnabled = SoS.config.getBoolean("enableCompat" + StringUtils.capitalize(name),
					SoS.config.CATEGORY_COMPAT, true, "Enable Compatiablity For " + StringUtils.capitalize(name));
		}
	}

	public abstract void preInit();

	public abstract void init();

	public abstract void postInit();

	public abstract void serverLoad();

	public boolean isEnabled() {
		return Loader.isModLoaded(name) && compatEnabled;
	}

	public Item findItem(String item) {
		return GameRegistry.findItem(name, item);
	}

	public Block findBlock(String item) {
		return GameRegistry.findBlock(name, item);
	}
}
