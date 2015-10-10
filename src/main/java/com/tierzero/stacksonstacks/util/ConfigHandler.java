package com.tierzero.stacksonstacks.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tierzero.stacksonstacks.block.BlockPile;

import cpw.mods.fml.client.config.IConfigElement;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

	public static Configuration config;

	public static final String CATEGORY_VISUAL = "Visual Changes";

	private static final String CATEGORY_GENERAL = "General";
	private static final String CATEGORY_BLACKLIST = "Blacklist";
	public static int ingotTextureToUse = 0;
	public static int dustTextureToUse = 0;

	public static boolean debug;
	public static boolean checkItemRegistry;
	public static String[] invalidIngots;
	public static String[] invalidGems;
	public static String[] invalidDusts;

	public static void init(File configFile) {
		config = new Configuration(configFile);

		config.load();
		debug = config.getBoolean("enableDebug", CATEGORY_GENERAL, false,
				"Spawns all available piles at 0,0. Suggested not to enable for survival");
		StringBuilder baseComment0 = new StringBuilder("Which texture to use: \n");
		String[] ingotTextures = BlockPile.textureNames[0];
		for (int textureIndex = 0; textureIndex < ingotTextures.length; textureIndex++) {
			baseComment0.append(textureIndex);
			baseComment0.append(": ");
			baseComment0.append(ingotTextures[textureIndex]);
			baseComment0.append("\n");

		}
		StringBuilder baseComment1 = new StringBuilder("Which texture to use: \n");
		String[] dustTextures = BlockPile.textureNames[1];
		for (int textureIndex = 0; textureIndex < dustTextures.length; textureIndex++) {
			baseComment1.append(textureIndex);
			baseComment1.append(": ");
			baseComment1.append(ingotTextures[textureIndex]);
			baseComment1.append("\n");

		}
		ingotTextureToUse = config.getInt("Ingot Texture", CATEGORY_VISUAL, 3, 0, BlockPile.textureNames[0].length - 1,
				baseComment0.toString());
		dustTextureToUse = config.getInt("Dust Texture", CATEGORY_VISUAL, 3, 0, BlockPile.textureNames[1].length - 1,
				baseComment1.toString());

		invalidIngots = config.getStringList("invalidIngots", CATEGORY_BLACKLIST,
				new String[] { "ingotPile", "ingotDouble", "ingotTriple", "ingotQuad", "ingotQuin" },
				"List for disabling ingots, put a string that is inside the name of the item");
		invalidGems = config.getStringList("invalidGems", CATEGORY_BLACKLIST, new String[] { "color_lightgem" },
				"List for disabling gems, put a string that is inside the name of the item");
		invalidDusts = config.getStringList("invalidDusts", CATEGORY_BLACKLIST,
				new String[] { "small", "tiny", "Tiny", "Small", "indust", "gendustry", "Indust", "mold" },
				"List for disabling dusts, put a string that is inside the name of the item");
		config.save();
	}

	public static List<IConfigElement> getConfigElements() {
		System.out.println(config.getCategoryNames());
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.addAll(new ConfigElement(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_VISUAL.toLowerCase()))
				.getChildElements());
		list.addAll(new ConfigElement(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_GENERAL.toLowerCase()))
				.getChildElements());
		return list;
	}
}
