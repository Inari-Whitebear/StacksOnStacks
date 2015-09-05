package com.tierzero.stacksonstacks.compat;

import java.awt.Color;

import com.tierzero.stacksonstacks.api.IngotRegistry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

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

	ItemStack hsla = new ItemStack(GameRegistry.findItem("RotaryCraft", "rotarycraft_item_shaftcraft"), 1, 1);
	ItemStack bedrockIngot = new ItemStack(GameRegistry.findItem("RotaryCraft", "rotarycraft_item_compacts"), 1, 3);
	ItemStack sinisterTungstenIngot = new ItemStack(GameRegistry.findItem("RotaryCraft", "rotarycraft_item_compacts"),
			1, 5);
	ItemStack inductiveIngot = new ItemStack(GameRegistry.findItem("RotaryCraft", "rotarycraft_item_compacts"), 1, 6);
	ItemStack springAlloyIngot = new ItemStack(GameRegistry.findItem("RotaryCraft", "rotarycraft_item_compacts"), 1, 9);
	ItemStack aluminiumAlloyIngot = new ItemStack(GameRegistry.findItem("RotaryCraft", "rotarycraft_item_compacts"), 1,
			11);

	@Override
	public void postInit() {
		IngotRegistry.registerIngot(hsla);
		IngotRegistry.registerIngot(bedrockIngot);
		IngotRegistry.registerIngot(sinisterTungstenIngot);
		IngotRegistry.registerIngot(inductiveIngot);
		IngotRegistry.registerIngot(springAlloyIngot);
		IngotRegistry.registerIngot(aluminiumAlloyIngot);
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
