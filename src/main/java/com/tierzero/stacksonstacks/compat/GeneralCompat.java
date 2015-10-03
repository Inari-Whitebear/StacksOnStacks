package com.tierzero.stacksonstacks.compat;

import com.tierzero.stacksonstacks.api.PileItemRegistry;

import cpw.mods.fml.common.Loader;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class GeneralCompat extends ModCompat {

	public static final String MOD_BOTANIA = "Botania";
	public static final String MOD_PROJECT_RED_CORE = "ProjRed|Core";
	public static final String MOD_PROJECT_RED = "projectred";
	public static final String MOD_TWILIGHT_FOREST = "TwilightForest";
	public static final String MOD_AURA_CASCADE = "aura";
	public static final String MOD_WITCHERY = "witchery";
	public static final String MOD_EXU = "ExtraUtilities";
	public static final String MOD_MAGICALCROPS = "magicalcrops";
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
		PileItemRegistry.registerDust(new ItemStack(Items.gunpowder), "minecraft:gunpowder");
		if (Loader.isModLoaded(MOD_PROJECT_RED_CORE)) {
			PileItemRegistry.registerIngot(getItemStack(MOD_PROJECT_RED_CORE, "projectred.core.part", 10),
					MOD_PROJECT_RED + COLON + "base/red_ingot");
			PileItemRegistry.registerIngot(getItemStack(MOD_PROJECT_RED_CORE, "projectred.core.part", 40),
					MOD_PROJECT_RED + COLON + "base/red_iron_comp");
			PileItemRegistry.registerIngot(getItemStack(MOD_PROJECT_RED_CORE, "projectred.core.part", 52),
					MOD_PROJECT_RED + COLON + "base/copper_ingot");
			PileItemRegistry.registerIngot(getItemStack(MOD_PROJECT_RED_CORE, "projectred.core.part", 53),
					MOD_PROJECT_RED + COLON + "base/tin_ingot");
			PileItemRegistry.registerIngot(getItemStack(MOD_PROJECT_RED_CORE, "projectred.core.part", 54),
					MOD_PROJECT_RED + COLON + "base/silver_ingot");
			PileItemRegistry.registerIngot(getItemStack(MOD_PROJECT_RED_CORE, "projectred.core.part", 55),
					MOD_PROJECT_RED + COLON + "base/electrotine_ingot");
			PileItemRegistry.registerIngot(getItemStack(MOD_PROJECT_RED_CORE, "projectred.core.part", 57),
					MOD_PROJECT_RED + COLON + "base/electrotine_iron_comp");
		}

		if (Loader.isModLoaded(MOD_BOTANIA)) {
			PileItemRegistry.registerIngot(getItemStack(MOD_BOTANIA, "manaResource", 14),
					MOD_BOTANIA + COLON + "gaiaIngot");
		}

		if (Loader.isModLoaded(MOD_AURA_CASCADE)) {
			final int MAX_DEGREE = 11;
			for (int degree = 0; degree < MAX_DEGREE; degree++) {
				PileItemRegistry.registerIngot(getItemStack(MOD_AURA_CASCADE, "ingotAngelSteel", degree),
						MOD_AURA_CASCADE + COLON + "angelsteel");
			}
		}
		if (Loader.isModLoaded(MOD_WITCHERY)) {
			PileItemRegistry.registerIngot(getItemStack(MOD_WITCHERY, "ingredient", 150),
					MOD_WITCHERY + COLON + "ingredient.kobolditeingot");
		}
		if (Loader.isModLoaded(MOD_TWILIGHT_FOREST)) {
			OreDictionary.registerOre("ingotFiery", findItem(MOD_TWILIGHT_FOREST, "item.fieryIngot"));
			OreDictionary.registerOre("ingotIronWood", findItem(MOD_TWILIGHT_FOREST, "item.ironwoodIngot"));
		}
		if (Loader.isModLoaded(MOD_MAGICALCROPS)) {

			PileItemRegistry.registerIngot(getItemStack(MOD_MAGICALCROPS, "magicalcrops_ArmourMaterials", 1),
					MOD_MAGICALCROPS + COLON + "infused_ingot");
		}
		/*
		 * 
		 * Need to figure these out if(Loader.isModLoaded(MOD_TWILIGHT_FOREST))
		 * { IngotRegistry.registerIngot(getItemStack(MOD_TWILIGHT_FOREST,
		 * "item.steeleaf", 0), MOD_TWILIGHT_FOREST + COLON + "steeleafIngot");
		 * IngotRegistry.registerIngot(getItemStack(MOD_TWILIGHT_FOREST,
		 * "item.ironwood", 0), MOD_TWILIGHT_FOREST + COLON + "ironwoodIngot");
		 * IngotRegistry.registerIngot(getItemStack(MOD_TWILIGHT_FOREST,
		 * "item.knightmetal", 0), MOD_TWILIGHT_FOREST + COLON +
		 * "knightmetalIngot"); }
		 * 
		 * 
		 * if(Loader.isModLoaded(MOD_WITCHERY)) {
		 * IngotRegistry.registerIngot(getItemStack(MOD_WITCHERY, "dustSilver",
		 * 0), MOD_WITCHERY + COLON + "dustSilver");
		 * 
		 * }
		 * 
		 * 
		 * if(Loader.isModLoaded(MOD_AVARITA)) {
		 * IngotRegistry.getIngot(findItem(MOD_AVARITA,"Resource"),
		 * 1).setIcon(findBlock("Crystal_Matrix").getIcon(0, 0));
		 * IngotRegistry.getIngot(findItem("Resource"),
		 * 4).setIcon(findBlock("Resource_Block").getIcon(0, 0));
		 * IngotRegistry.getIngot(findItem("Resource"),
		 * 6).setIcon(findBlock("Resource_Block").getIcon(0, 1)); }
		 */

	}

	@Override
	public void clientSide() {
		// TODO Auto-generated method stub

	}

}
