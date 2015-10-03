package com.tierzero.stacksonstacks;

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
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = SoS.MODID, name = SoS.MODID, version = SoS.VERSION, dependencies = "after:gregtech_addon;after:gregapi;", guiFactory = "com.tierzero.stacksonstacks.GuiFactorySOS")
public class SoS {
	public static final String VERSION = "1.0.4";
	public static final String MODID = "StacksOnStacks";
	public static final String TEXTURE_BASE = MODID + ":";

	public static BlockPile blockPile;
	@Instance
	public static SoS instance;

	@SidedProxy(serverSide = "com.tierzero.stacksonstacks.CommonProxy", clientSide = "com.tierzero.stacksonstacks.ClientProxy", modId = MODID)
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.init(event.getSuggestedConfigurationFile());

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
		if (Loader.isModLoaded("NotEnoughItems"))
			codechicken.nei.api.API.hideItem(new ItemStack(blockPile));
	}

}
