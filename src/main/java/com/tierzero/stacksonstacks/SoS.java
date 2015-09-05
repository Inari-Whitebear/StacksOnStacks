package com.tierzero.stacksonstacks;

import com.tierzero.stacksonstacks.api.IngotFinder;
import com.tierzero.stacksonstacks.compat.CompatHandler;
import com.tierzero.stacksonstacks.util.Config;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = SoS.MODID, name = SoS.MODID, version = SoS.VERSION, dependencies = "after:gregtech;after:gregapi;after:Avaritia;after:Botania;")
public class SoS {
	public static final String VERSION = "0.9.4";
	public static final String MODID = "StacksOnStacks";
	public static final String TEXTURE_BASE = MODID + ":";
	public static BlockIngotPile ingotPile;
	public static Config config;
	@SidedProxy(serverSide = "com.tierzero.stacksonstacks.CommonProxy", clientSide = "com.tierzero.stacksonstacks.ClientProxy", modId = MODID)
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		config = new Config(e.getSuggestedConfigurationFile());
		config.load();
		config.goldAltTexture = config.getBoolean("goldAltTexture", "ALT_TEXTURES", false,
				"make gold ingots use the gold block texture, may not work somethings for unknown reasons");
		CompatHandler.config();
		proxy.registerTiles();
		ingotPile = new BlockIngotPile("ingotPile");
		MinecraftForge.EVENT_BUS.register(new IngotPileHandler());
		config.save();
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.registerRenders();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		IngotFinder.registerIngots();
		CompatHandler.postInit();
		proxy.postInit();
	}

}
