package com.tierzero.stacksonstacks;

import com.tierzero.stacksonstacks.block.BlockPile;
import com.tierzero.stacksonstacks.compat.CompatHandler;
import com.tierzero.stacksonstacks.core.CommonProxy;
import com.tierzero.stacksonstacks.pile.PileHandler;
import com.tierzero.stacksonstacks.pile.PileItemFinder;
import com.tierzero.stacksonstacks.util.ConfigHandler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SoS.MODID, name = SoS.MODID, version = SoS.VERSION, dependencies = "after:gregtech_addon;after:gregapi;", guiFactory = "com.tierzero.stacksonstacks.GuiFactorySOS")
public class SoS {
	public static final String VERSION = "{$version}";
	public static final String MODID = "StacksOnStacks";
	public static final String TEXTURE_BASE = MODID + ":";

	public static BlockPile blockPile;
	@Instance
	public static SoS instance;

	@SidedProxy(serverSide = "com.tierzero.stacksonstacks.core.CommonProxy", clientSide = "com.tierzero.stacksonstacks.core.ClientProxy", modId = MODID)
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());

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
	}

}
