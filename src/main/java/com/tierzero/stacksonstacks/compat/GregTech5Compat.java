package com.tierzero.stacksonstacks.compat;

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
	public void init() {
	}

	@Override
	public void postInit() {
	}

	@Override
	public boolean isEnabled() {
		return Loader.isModLoaded("gregtech") && !Loader.isModLoaded("gregapi") && compatEnabled;
	}

	@Override
	public void clientSide() {

	}
}
