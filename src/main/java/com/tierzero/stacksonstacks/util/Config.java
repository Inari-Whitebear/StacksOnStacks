package com.tierzero.stacksonstacks.util;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config extends Configuration {

	public final String CATEGORY_COMPAT = "Mod Compat";
	public boolean goldAltTexture = false;

	public Config(File modConfigurationDirectory) {
		super(modConfigurationDirectory);
	}

}
