package com.tierzero.stacksonstacks.client.render;

import net.minecraft.item.ItemStack;

public abstract class PileRender {
	public float count;
	public int maxStackSize;

	public void setCount(int count, int maxStackSize, int baseStackSize) {
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

	public abstract void render(ItemStack itemStack);
}