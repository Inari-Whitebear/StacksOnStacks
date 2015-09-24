package com.tierzero.stacksonstacks.util;

import java.io.File;

import com.tierzero.stacksonstacks.block.BlockIngotPile;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

	private static Configuration config;
	
	private static final String CATEGORY_VISUAL = "Visual Changes";
	public static int textureToUse = 0;
		
	public static void init(File configFile) {
		config = new Configuration(configFile);
		
		config.load();
		
		StringBuilder baseComment = new StringBuilder("Which texture to use: \n");
		String[] textureNames = BlockIngotPile.textureNames;
		for(int textureIndex = 0; textureIndex < textureNames.length; textureIndex++) {
			baseComment.append(textureIndex);
			baseComment.append(": ");
			baseComment.append(textureNames[textureIndex]);
			baseComment.append("\n");
			
		}
		
		textureToUse = config.getInt("Ingot Texture", CATEGORY_VISUAL, 0, 0, BlockIngotPile.textureNames.length - 1, baseComment.toString());
	
		config.save();
	}

}
