package com.tierzero.stacksonstacks.compat;

import java.util.ArrayList;

public class CompatHandler {
	public static ArrayList<ModCompat> modcompat = new ArrayList<ModCompat>();

	static {
		add(AvaritiaCompat.INSTANCE);
		add(GregTech6Compat.INSTANCE);
		add(GregTech5Compat.INSTANCE);
		add(BotaniaCompat.INSTANCE);
		add(RotaryCompat.INSTANCE);
	}

	public static void config() {

		for (ModCompat mod : modcompat) {
			mod.config();

		}
	}

	public static void preInit() {

		for (ModCompat mod : modcompat) {
			if (mod.isEnabled())
				mod.preInit();
		}

	}

	public static void init() {
		for (ModCompat mod : modcompat)
			if (mod.isEnabled())
				mod.init();
	}

	public static void postInit() {
		for (ModCompat mod : modcompat)
			if (mod.isEnabled())
				mod.postInit();
	}

	public static void clientSide() {
		for (ModCompat mod : modcompat)
			if (mod.isEnabled())
				mod.clientSide();
	}

	public static void add(ModCompat mod) {
		modcompat.add(mod);
	}

}
