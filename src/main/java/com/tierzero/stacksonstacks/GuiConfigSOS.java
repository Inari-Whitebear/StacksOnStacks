package com.tierzero.stacksonstacks;

import com.tierzero.stacksonstacks.util.ConfigHandler;

import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiConfigSOS extends GuiConfig {

	public GuiConfigSOS(GuiScreen parentScreen) {
		super(parentScreen, ConfigHandler.getConfigElements(), SoS.MODID, ConfigHandler.config.toString(), false, false, "Stacks On Stacks Config");
	}
	
	@Override
	public void initGui() {
		super.initGui();
	}
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	@Override
	public void actionPerformed(GuiButton button) {
		super.actionPerformed(button);
	}

}
