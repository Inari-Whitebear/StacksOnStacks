package com.tierzero.stacksonstacks.util;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.util.IIcon;

public class BlockStack {
	private Block block;
	private int meta;

	public BlockStack(String blockmeta) {

		if (blockmeta != null) {

			String[] split = blockmeta.split(":");
			int meta = 0;
			if (split.length > 2)
				meta = Integer.parseInt(split[2]);
			block = GameRegistry.findBlock(split[0], split[1]);
			this.meta = meta;
		}

	}

	public BlockStack(Block block, int meta) {
		this.block = block;
		this.meta = meta;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public int getMeta() {
		return meta;
	}

	public void setMeta(int meta) {
		this.meta = meta;
	}

	public IIcon getIcon() {
		return block.getIcon(0, meta);
	}

}
