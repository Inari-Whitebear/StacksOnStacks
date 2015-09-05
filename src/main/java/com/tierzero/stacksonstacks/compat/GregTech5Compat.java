package com.tierzero.stacksonstacks.compat;

import cpw.mods.fml.common.Loader;

public class GregTech5Compat extends ModCompat {
	public static GregTech5Compat INSTANCE = new GregTech5Compat();

	public GregTech5Compat() {
		super("gregtech");
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
	public void serverLoad() {

	}

	@Override
	public boolean isEnabled() {
		return super.isEnabled() && !Loader.isModLoaded("gregapi");
	}
}
