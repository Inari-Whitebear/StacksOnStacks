package com.tierzero.stacksonstacks.compat;

import com.tierzero.stacksonstacks.api.Ingot;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class AvaritiaCompat extends ModCompat {
	public static AvaritiaCompat INSTANCE = new AvaritiaCompat();

	public AvaritiaCompat() {
		super("Avaritia");
	}

	@Override
	public void preInit() {

	}

	@Override
	public void init() {
		OreDictionary.registerOre("ingotInfinity", new ItemStack(findItem("Resource"), 1, 6));
		OreDictionary.registerOre("ingotCrystalMatrix", new ItemStack(findItem("Resource"), 1, 1));
		OreDictionary.registerOre("ingotNeutronium", new ItemStack(findItem("Resource"), 1, 4));
	}

	@Override
	public void postInit() {

	}

	@Override
	public void serverLoad() {
		if (!GregTechCompat.INSTANCE.isEnabled())
			customIngots();
	}

	@SideOnly(Side.CLIENT)
	public void customIngots() {

		Ingot.getIngot(findItem("Resource"), 1).setIcon(findBlock("Crystal_Matrix").getIcon(0, 0));
		Ingot.getIngot(findItem("Resource"), 4).setIcon(findBlock("Resource_Block").getIcon(0, 0));
		Ingot.getIngot(findItem("Resource"), 6).setIcon(findBlock("Resource_Block").getIcon(0, 1));

	}

}
