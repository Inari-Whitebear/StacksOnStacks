package com.tierzero.stacksonstacks.compat;

import org.apache.commons.lang3.StringUtils;

import com.tierzero.stacksonstacks.SoS;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;

public class GregTech5Compat extends ModCompat {
	public static GregTech5Compat INSTANCE = new GregTech5Compat();

	public GregTech5Compat() {
		super("gregtech5");
	}

	@Override
	public void preInit() {
	}

	@Override
	public void config() {
		if (Loader.isModLoaded("gregtech")) {
			FMLLog.info("StacksOnStacks:Loading Compat for " + name);
			compatEnabled = SoS.config.getBoolean("enableCompat" + StringUtils.capitalize(name),
					SoS.config.CATEGORY_COMPAT, true, "Enable Compatiablity For " + StringUtils.capitalize(name));
		} else
			FMLLog.info("StacksOnStacks:" + name + " is not loaded");
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
	}

	@Override
	public boolean isEnabled() {
		return Loader.isModLoaded("gregtech") && compatEnabled;
	}

	@Override
	public void clientSide() {

	}
}
