package com.tierzero.stacksonstacks.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tierzero.stacksonstacks.SoS;
import com.tierzero.stacksonstacks.block.BlockPile;

import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

	public static Configuration config;

	public static final String CATEGORY_VISUAL = "visual changes";

	private static final String CATEGORY_GENERAL = "general";
	private static final String CATEGORY_BLACKLIST = "blacklist";
	private static final String CATEGORY_WHITELIST = "whitelist";
	public static int ingotTextureToUse;
	public static int dustTextureToUse;

	public static boolean debug;
	public static boolean checkItemRegistry;
	public static String[] invalidIngots;
	public static String[] invalidGems;
	public static String[] invalidDusts;

	public static String[] validIngots;
	public static String[] validGems;
	public static String[] validDusts;

	public static int maxIngotStackSize;
	public static int maxGemStackSize;
	public static int maxDustStackSize;

	private static String[] editPileItemColor;

	public static void loadConfig(File configFile) {
		config = new Configuration(configFile);
		load();

		FMLCommonHandler.instance().bus().register(new ChangeListener());
	}

	public static void load() {
		config.load();
		debug = config.getBoolean("enableDebug", CATEGORY_GENERAL, false,
				"Spawns all available piles at 0,0. Suggested not to enable for survival");

		StringBuilder baseCommentIngot = new StringBuilder("Which texture to use: \n");
		String[] ingotTextures = BlockPile.textureNames[0];
		for (int textureIndex = 0; textureIndex < ingotTextures.length; textureIndex++) {
			baseCommentIngot.append(textureIndex);
			baseCommentIngot.append(": ");
			baseCommentIngot.append(ingotTextures[textureIndex]);
			baseCommentIngot.append("\n");

		}

		StringBuilder baseCommentDust = new StringBuilder("Which texture to use: \n");
		String[] dustTextures = BlockPile.textureNames[1];
		for (int textureIndex = 0; textureIndex < dustTextures.length; textureIndex++) {
			baseCommentDust.append(textureIndex);
			baseCommentDust.append(": ");
			baseCommentDust.append(ingotTextures[textureIndex]);
			baseCommentDust.append("\n");

		}

		ingotTextureToUse = config.getInt("Ingot Texture", CATEGORY_VISUAL, 3, 0, BlockPile.textureNames[0].length - 1,
				baseCommentIngot.toString());
		dustTextureToUse = config.getInt("Dust Texture", CATEGORY_VISUAL, 3, 0, BlockPile.textureNames[1].length - 1,
				baseCommentDust.toString());

		validIngots = config.getStringList("validIngots", CATEGORY_WHITELIST, new String[] { "ingot" },
				"List for adding ingots, put a string that is inside the name or oredictionary of the item");
		validGems = config.getStringList("validGems", CATEGORY_WHITELIST, new String[] { "gem", "shard", "crystal" },
				"List for adding gems, put a string that is inside the name or oredictionary of the item");
		validDusts = config.getStringList("validDusts", CATEGORY_WHITELIST, new String[] { "dust", "powder" },
				"List for adding dusts, put a string that is inside the name or oredictionary of the item");
		invalidIngots = config.getStringList("invalidIngots", CATEGORY_BLACKLIST,
				new String[] { "ingotPile", "ingotDouble", "ingotTriple", "ingotQuad", "ingotQuin" },
				"List for disabling ingots, put a string that is inside the name or oredictionary of the item");
		invalidGems = config.getStringList("invalidGems", CATEGORY_BLACKLIST, new String[] { "color_lightgem", "gemOre" },
				"List for disabling gems, put a string that is inside the name or oredictionary of the item");
		invalidDusts = config.getStringList("invalidDusts", CATEGORY_BLACKLIST,
				new String[] { "small", "tiny", "Tiny", "Small", "indust", "gendustry", "Indust", "mold"},
				"List for disabling dusts, put a string that is inside the name or oredictionary of the item");

		maxIngotStackSize = config.getInt("maxIngotStackSize", CATEGORY_GENERAL, 64, 1, Integer.MAX_VALUE,
				"The number of ingots that a pile can hold");
		maxGemStackSize = config.getInt("maxGemStackSize", CATEGORY_GENERAL, 256, 1, Integer.MAX_VALUE,
				"The number of gems that a pile can hold");
		maxDustStackSize = config.getInt("maxDustStackSize", CATEGORY_GENERAL, 64, 1, Integer.MAX_VALUE,
				"The amount of dust that a pile can hold, renders at half height for amounts > 64 for some reason");

		if (config.hasChanged()) {
			config.save();
		}
	}

	public static List<IConfigElement> getConfigElements() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.addAll(new ConfigElement(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_VISUAL.toLowerCase()))
				.getChildElements());
		list.addAll(new ConfigElement(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_GENERAL.toLowerCase()))
				.getChildElements());
		return list;
	}

	public static class ChangeListener {
		@SubscribeEvent
		public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
			if (eventArgs.modID.equals(SoS.MODID)) {
				load();
				System.out.println("TEST");
			}
		}
	}
}
