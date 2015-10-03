package com.tierzero.stacksonstacks.compat;

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
		// PileItemRegistry.registerIngot(hsla, "Rotarycraft:hsla");
		// PileItemRegistry.registerIngot(bedrockIngot,
		// "Rotarycraft:ingotBedrock");
		// PileItemRegistry.registerIngot(sinisterTungstenIngot,
		// "Rotarycraft:ingotSinisterTungsten");
		// PileItemRegistry.registerIngot(inductiveIngot,
		// "Rotarycraft:inductiveIngot");
		// PileItemRegistry.registerIngot(springAlloyIngot,
		// "Rotarycraft:springAlloyIngot");
		// PileItemRegistry.registerIngot(aluminiumAlloyIngot,
		// "Rotarycraft:aluminiumAlloyIngot");
	}

	@Override
	public void clientSide() {
		// PileItemRegistry.getIngot(hsla).setColor(new Color(202, 203, 242));
		// PileItemRegistry.getIngot(bedrockIngot).setIcon(
		// new ItemStack(GameRegistry.findBlock("RotaryCraft",
		// "rotarycraft_block_deco"), 1, 4).getIconIndex());
		// PileItemRegistry.getIngot(sinisterTungstenIngot).setColor(new
		// Color(140, 140, 140));
		// PileItemRegistry.getIngot(inductiveIngot).setColor(new Color(255,
		// 118, 60));
		// PileItemRegistry.getIngot(springAlloyIngot).setColor(new Color(121,
		// 75, 88));
		// PileItemRegistry.getIngot(aluminiumAlloyIngot).setColor(new
		// Color(117, 117, 117));
	}

}
