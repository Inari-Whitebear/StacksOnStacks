package com.tierzero.stacksonstacks.compat;

import com.tierzero.stacksonstacks.api.IngotRegistry;

import cpw.mods.fml.common.Loader;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.ChunkDataEvent.Load;

public class GeneralCompat extends ModCompat {
	
	private static final String MOD_BOTANIA = "Botania";
	private static final String MOD_PROJECT_RED_CORE = "ProjRed|Core";
	private static final String MOD_PROJECT_RED = "projectred";
	private static final String	MOD_TWILIGHT_FOREST = "TwilightForest";
	private static final String MOD_AURA_CASCADE = "aura";
	private static final String MOD_WITCHERY = "witchery";

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
		if(Loader.isModLoaded(MOD_PROJECT_RED_CORE)) {
			IngotRegistry.registerIngot(getItemStack(MOD_PROJECT_RED_CORE, "projectred.core.part", 10), MOD_PROJECT_RED + COLON + "base/red_ingot");
			IngotRegistry.registerIngot(getItemStack(MOD_PROJECT_RED_CORE, "projectred.core.part", 40), MOD_PROJECT_RED + COLON + "base/red_iron_comp");
			IngotRegistry.registerIngot(getItemStack(MOD_PROJECT_RED_CORE, "projectred.core.part", 52), MOD_PROJECT_RED + COLON + "base/copper_ingot");
			IngotRegistry.registerIngot(getItemStack(MOD_PROJECT_RED_CORE, "projectred.core.part", 53), MOD_PROJECT_RED + COLON + "base/tin_ingot");
			IngotRegistry.registerIngot(getItemStack(MOD_PROJECT_RED_CORE, "projectred.core.part", 54), MOD_PROJECT_RED + COLON + "base/silver_ingot");
			IngotRegistry.registerIngot(getItemStack(MOD_PROJECT_RED_CORE, "projectred.core.part", 55), MOD_PROJECT_RED + COLON + "base/electrotine_ingot");
			IngotRegistry.registerIngot(getItemStack(MOD_PROJECT_RED_CORE, "projectred.core.part", 57), MOD_PROJECT_RED + COLON + "base/electrotine_iron_comp");
		}
		
		if(Loader.isModLoaded(MOD_BOTANIA)) {
			IngotRegistry.registerIngot(getItemStack(MOD_BOTANIA, "manaResource", 14), MOD_BOTANIA + COLON + "gaiaIngot");
		}
		
		if(Loader.isModLoaded(MOD_AURA_CASCADE)) {
			final int MAX_DEGREE = 11;

			for(int degree = 0; degree < MAX_DEGREE; degree++) {
				IngotRegistry.registerIngot(getItemStack(MOD_AURA_CASCADE, "ingotAngelSteel", degree), MOD_AURA_CASCADE + COLON + "angelsteel");
			}
		}
		
		/*
		 * Need to figure these out
		if(Loader.isModLoaded(MOD_TWILIGHT_FOREST)) {
			IngotRegistry.registerIngot(getItemStack(MOD_TWILIGHT_FOREST, "item.steeleaf", 0), MOD_TWILIGHT_FOREST + COLON + "steeleafIngot");
			IngotRegistry.registerIngot(getItemStack(MOD_TWILIGHT_FOREST, "item.ironwood", 0), MOD_TWILIGHT_FOREST + COLON + "ironwoodIngot");
			IngotRegistry.registerIngot(getItemStack(MOD_TWILIGHT_FOREST, "item.knightmetal", 0), MOD_TWILIGHT_FOREST + COLON + "knightmetalIngot");
		}
		
		
		if(Loader.isModLoaded(MOD_WITCHERY)) {
			IngotRegistry.registerIngot(getItemStack(MOD_WITCHERY, "dustSilver", 0), MOD_WITCHERY + COLON + "dustSilver");

		}
		*/
	
		
	}

	@Override
	public void clientSide() {
		// TODO Auto-generated method stub
		
	}

}
