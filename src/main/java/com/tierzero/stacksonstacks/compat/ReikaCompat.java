package com.tierzero.stacksonstacks.compat;

public class ReikaCompat extends ModCompat {
	public static final ReikaCompat INSTANCE = new ReikaCompat();

	public ReikaCompat() {
		super("DragonAPI");
	}

	@Override
	public void preInit() {

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void postInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void serverLoad() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isEnabled() {
		return false;
		// return super.isEnabled();
	}

}
