package com.tierzero.stacksonstacks.compat;

import java.awt.Color;

import com.tierzero.stacksonstacks.api.IngotRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BotaniaCompat extends ModCompat {

	public static final BotaniaCompat INSTANCE = new BotaniaCompat();

	public BotaniaCompat() {
		super("Botania");

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

	@SideOnly(Side.CLIENT)
	public void customIngots() {

		IngotRegistry.getIngot(findItem("manaResource"), 0).setColor(new Color(69, 159, 229));
		IngotRegistry.getIngot(findItem("manaResource"), 4).setColor(new Color(75, 211, 31));
		IngotRegistry.getIngot(findItem("manaResource"), 7).setColor(new Color(224, 88, 254));
	}

	public void clientSide() {
		if (!GregTech6Compat.INSTANCE.isEnabled())
			customIngots();
	}
}
