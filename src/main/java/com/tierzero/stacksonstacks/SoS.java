package com.tierzero.stacksonstacks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tierzero.stacksonstacks.api.PileItemFinder;
import com.tierzero.stacksonstacks.block.BlockPile;
import com.tierzero.stacksonstacks.compat.CompatHandler;
import com.tierzero.stacksonstacks.util.ConfigHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = SoS.MODID, name = SoS.MODID, version = SoS.VERSION, dependencies = "after:gregtech_addon;after:gregapi;", guiFactory = "com.tierzero.stacksonstacks.GuiFactorySOS")
public class SoS {
	public static final String VERSION = "{$version}";
	public static final String MODID = "StacksOnStacks";
	public static final String TEXTURE_BASE = MODID + ":";

	public static BlockPile blockPile;
	@Instance
	public static SoS instance;

	@SidedProxy(serverSide = "com.tierzero.stacksonstacks.CommonProxy", clientSide = "com.tierzero.stacksonstacks.ClientProxy", modId = MODID)
	public static CommonProxy proxy;
	public static Writer jsonwriter;
	public static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().serializeNulls().create();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());

		try {
			jsonwriter = new FileWriter(
					new File(event.getSuggestedConfigurationFile().getCanonicalPath().replace(".cfg", ".json")));
		} catch (IOException e) {

		}
		proxy.registerEntities();
		proxy.registerTiles();

		blockPile = new BlockPile("ingotPile");
		MinecraftForge.EVENT_BUS.register(new PileHandler());
		if (ConfigHandler.debug)
			FMLCommonHandler.instance().bus().register(new DebugHandler());
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.registerRenders();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		CompatHandler.postInit();
		PileItemFinder.registerAllItems();
		proxy.postInit();
		if (Loader.isModLoaded("NotEnoughItems")) {
			codechicken.nei.api.API.hideItem(new ItemStack(blockPile));
		}
		try {
			jsonwriter.close();
		} catch (IOException err) {

		}
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent e) {
		e.registerServerCommand(new RegisterPileItemCommand());
	}

}
