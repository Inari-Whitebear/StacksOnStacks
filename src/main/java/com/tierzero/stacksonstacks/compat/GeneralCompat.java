package com.tierzero.stacksonstacks.compat;

import com.tierzero.stacksonstacks.api.IngotRegistry;

import cpw.mods.fml.common.Loader;

public class GeneralCompat extends ModCompat {
	
	private static final String MOD_BOTANIA = "Botania";
	private static final String COLON = ":";

	public GeneralCompat() {
		super("General Compatability");
	}

	@Override
	public void preInit() {
		
	}

	@Override
	public void init() {
		
	}

	@Override
	public void postInit() {

		if(Loader.isModLoaded(MOD_BOTANIA)) {
			IngotRegistry.registerIngot(getItemStack(MOD_BOTANIA, "manaResource", 0), MOD_BOTANIA + COLON + "manasteel");
			IngotRegistry.registerIngot(getItemStack(MOD_BOTANIA, "manaResource", 4), MOD_BOTANIA + COLON + "terrasteel");
			IngotRegistry.registerIngot(getItemStack(MOD_BOTANIA, "manaResource", 7), MOD_BOTANIA + COLON + "elementium");
			IngotRegistry.registerIngot(getItemStack(MOD_BOTANIA, "manaResource", 14), MOD_BOTANIA + COLON + "gaiaIngot");
		}
		
	}

	@Override
	public void clientSide() {
		// TODO Auto-generated method stub
		
	}

}
