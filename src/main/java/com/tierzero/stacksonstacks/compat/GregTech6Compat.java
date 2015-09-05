package com.tierzero.stacksonstacks.compat;

import org.apache.commons.lang3.StringUtils;

import com.tierzero.stacksonstacks.SoS;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;

public class GregTech6Compat extends ModCompat {
	public static GregTech6Compat INSTANCE = new GregTech6Compat();

	public GregTech6Compat() {
		super("gregtech6");
	}

	@Override
	public void config() {
		if (Loader.isModLoaded("gregapi")) {
			FMLLog.info("Loading Compat for " + name);
			compatEnabled = SoS.config.getBoolean("enableCompat" + StringUtils.capitalize(name),
					SoS.config.CATEGORY_COMPAT, true, "Enable Compatiablity For " + StringUtils.capitalize(name));
		} else
			FMLLog.info("StacksOnStacks:" + name + " is not loaded");
	}

	@Override
	public void preInit() {
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
	}

	@Override
	public void clientSide() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isEnabled() {
		return Loader.isModLoaded("gregapi") && compatEnabled;
	}

}
