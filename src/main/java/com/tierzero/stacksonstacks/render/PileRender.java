package com.tierzero.stacksonstacks.render;

import net.minecraft.item.ItemStack;

public abstract class PileRender {
	public float count;
	public ItemStack item;
	public int maxStackSize;

	public PileRender(ItemStack item, int count, int maxStackSize, int baseStackSize) {
		this.item = item;
		
		int renderPerItem = maxStackSize / baseStackSize;
		
		if(renderPerItem < 1) {
			renderPerItem = 1;
		}
		
		this.count = (count / renderPerItem);
		
		if(this.count < renderPerItem) {
			this.count++;
		}
		this.maxStackSize = maxStackSize;
	}

	public abstract void render();
}