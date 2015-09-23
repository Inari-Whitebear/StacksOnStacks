package com.tierzero.stacksonstacks.compat;

import java.awt.Color;

import com.tierzero.stacksonstacks.api.IngotRegistry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

public class RotaryCompat extends ModCompat {
	public static final RotaryCompat INSTANCE = new RotaryCompat();

	public RotaryCompat() {
		super("Rotarycraft");
	}

	@Override
	public void preInit() {

	}

	ItemStack hsla;
	ItemStack bedrockIngot;
	ItemStack sinisterTungstenIngot;
	ItemStack inductiveIngot;
	ItemStack springAlloyIngot;
	ItemStack aluminiumAlloyIngot;

	@Override
	public void init() {
		hsla = new ItemStack(GameRegistry.findItem("RotaryCraft", "rotarycraft_item_shaftcraft"), 1, 1);
		bedrockIngot = new ItemStack(GameRegistry.findItem("RotaryCraft", "rotarycraft_item_compacts"), 1, 3);
		sinisterTungstenIngot = new ItemStack(GameRegistry.findItem("RotaryCraft", "rotarycraft_item_compacts"), 1, 5);
		inductiveIngot = new ItemStack(GameRegistry.findItem("RotaryCraft", "rotarycraft_item_compacts"), 1, 6);
		springAlloyIngot = new ItemStack(GameRegistry.findItem("RotaryCraft", "rotarycraft_item_compacts"), 1, 9);
		aluminiumAlloyIngot = new ItemStack(GameRegistry.findItem("RotaryCraft", "rotarycraft_item_compacts"), 1, 11);
	}

	@Override
	public void postInit() {
		IngotRegistry.registerIngot(hsla, "Rotarycraft:hsla");
		IngotRegistry.registerIngot(bedrockIngot, "Rotarycraft:ingotBedrock");
		IngotRegistry.registerIngot(sinisterTungstenIngot, "Rotarycraft:ingotSinisterTungsten");
		IngotRegistry.registerIngot(inductiveIngot, "Rotarycraft:inductiveIngot");
		IngotRegistry.registerIngot(springAlloyIngot, "Rotarycraft:springAlloyIngot");
		IngotRegistry.registerIngot(aluminiumAlloyIngot, "Rotarycraft:aluminiumAlloyIngot");
	}

	@Override
	public void clientSide() {
		IngotRegistry.getIngot(hsla).setColor(new Color(202, 203, 242));
		IngotRegistry.getIngot(bedrockIngot).setIcon(
				new ItemStack(GameRegistry.findBlock("RotaryCraft", "rotarycraft_block_deco"), 1, 4).getIconIndex());
		IngotRegistry.getIngot(sinisterTungstenIngot).setColor(new Color(140, 140, 140));
		IngotRegistry.getIngot(inductiveIngot).setColor(new Color(255, 118, 60));
		IngotRegistry.getIngot(springAlloyIngot).setColor(new Color(121, 75, 88));
		IngotRegistry.getIngot(aluminiumAlloyIngot).setColor(new Color(117, 117, 117));
	}

}
