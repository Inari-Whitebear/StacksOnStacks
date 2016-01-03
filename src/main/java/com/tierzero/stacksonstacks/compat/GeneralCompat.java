package com.tierzero.stacksonstacks.compat;

import java.awt.Color;

import com.tierzero.stacksonstacks.pile.PileItemRegistry;
import com.tierzero.stacksonstacks.util.BlockStack;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
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
	public static final String MOD_THAUMCRAFT = "Thaumcraft";
	public static final String MOD_AVARITIA = "Avaritia";
	private static final String MOD_ROTARYCRAFT = "RotaryCraft";

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
		// PileItemRegistry.registerDust(new ItemStack(Items.gunpowder),
		// "minecraft:gunpowder");
		PileItemRegistry.registerGem(new ItemStack(Items.coal, 0));
		PileItemRegistry.registerGem(new ItemStack(Items.coal, 1, 1));
		PileItemRegistry.registerGem(new ItemStack(Items.nether_star));
		if (Loader.isModLoaded(MOD_PROJECT_RED_CORE)) {
			PileItemRegistry.registerIngot(getItemStack(MOD_PROJECT_RED_CORE, "projectred.core.part", 10));
			PileItemRegistry.registerIngot(getItemStack(MOD_PROJECT_RED_CORE, "projectred.core.part", 40));
			PileItemRegistry.registerIngot(getItemStack(MOD_PROJECT_RED_CORE, "projectred.core.part", 52));
			PileItemRegistry.registerIngot(getItemStack(MOD_PROJECT_RED_CORE, "projectred.core.part", 53));
			PileItemRegistry.registerIngot(getItemStack(MOD_PROJECT_RED_CORE, "projectred.core.part", 54));
			PileItemRegistry.registerIngot(getItemStack(MOD_PROJECT_RED_CORE, "projectred.core.part", 55));
			PileItemRegistry.registerIngot(getItemStack(MOD_PROJECT_RED_CORE, "projectred.core.part", 57));
		}

		if (Loader.isModLoaded(MOD_BOTANIA)) {
			PileItemRegistry.registerIngot(getItemStack(MOD_BOTANIA, "manaResource", 14));
		}

		if (Loader.isModLoaded(MOD_AURA_CASCADE)) {
			final int MAX_DEGREE = 11;
			for (int degree = 0; degree < MAX_DEGREE; degree++) {
				PileItemRegistry.registerIngot(getItemStack(MOD_AURA_CASCADE, "ingotAngelSteel", degree));
			}
		}
		if (Loader.isModLoaded(MOD_WITCHERY)) {
			PileItemRegistry.registerIngot(getItemStack(MOD_WITCHERY, "ingredient", 150));
		}
		if (Loader.isModLoaded(MOD_TWILIGHT_FOREST)) {
			OreDictionary.registerOre("ingotFiery", findItem(MOD_TWILIGHT_FOREST, "item.fieryIngot"));
			OreDictionary.registerOre("ingotIronWood", findItem(MOD_TWILIGHT_FOREST, "item.ironwoodIngot"));
		}
		if (Loader.isModLoaded(MOD_MAGICALCROPS)) {
			if (modVersion(MOD_MAGICALCROPS).contains("4.0.0")) {
			} else
				PileItemRegistry.registerIngot(getItemStack(MOD_MAGICALCROPS, "magicalcrops_ArmourMaterials", 1));
		}
		if (Loader.isModLoaded(MOD_THAUMCRAFT)) {
			PileItemRegistry.registerDust(getItemStack(MOD_THAUMCRAFT, "ItemResource", 14));
			PileItemRegistry.registerGem(getItemStack(MOD_THAUMCRAFT, "ItemResource", 17));
			PileItemRegistry.registerGem(getItemStack(MOD_THAUMCRAFT, "ItemResource", 18));
		}

		if (Loader.isModLoaded(MOD_AVARITIA)) {
			PileItemRegistry.registerIngot(getItemStack(MOD_AVARITIA, "Resource", 1),
					new BlockStack(MOD_AVARITIA + ":Crystal_Matrix"));
			PileItemRegistry.registerIngot(getItemStack(MOD_AVARITIA, "Resource", 4),
					new BlockStack(MOD_AVARITIA + ":Resource_Block"));
			PileItemRegistry.registerIngot(getItemStack(MOD_AVARITIA, "Resource", 6),
					new BlockStack(MOD_AVARITIA + ":Resource_Block:1"));
		}
		if (Loader.isModLoaded(MOD_ROTARYCRAFT)) {
			/*PileItemRegistry.registerIngot(getItemStack(MOD_ROTARYCRAFT, "rotarycraft_item_shaftcraft", 1))
					.setColor(new Color(202, 203, 242));
			PileItemRegistry.registerIngot(getItemStack("RotaryCraft", "rotarycraft_item_compacts", 1))
					.setColor(new Color(202, 203, 242));
			PileItemRegistry.registerIngot(getItemStack("RotaryCraft", "rotarycraft_item_compacts", 1))
					.setColor(new Color(140, 140, 140));
			PileItemRegistry.registerIngot(getItemStack("RotaryCraft", "rotarycraft_item_compacts", 1));
			PileItemRegistry.registerIngot(getItemStack("RotaryCraft", "rotarycraft_item_compacts", 1));
			PileItemRegistry.registerIngot(getItemStack("RotaryCraft", "rotarycraft_item_compacts", 1));*/
		}
	}

	public static String modVersion(String modId) {
		return getModContainer(modId).getVersion();
	}

	public static ModContainer getModContainer(String modId) {
		for (ModContainer mod : Loader.instance().getActiveModList()) {
			if (mod.getModId().equalsIgnoreCase(modId)) {
				return mod;
			}
		}
		return null;
	}

}
