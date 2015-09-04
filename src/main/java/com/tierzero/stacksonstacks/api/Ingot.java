package com.tierzero.stacksonstacks.api;

import java.awt.Color;
import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class Ingot {
	private Item item;
	private int meta;
	private Color colour;
	private IIcon icon;

	public Ingot(ItemStack stack) {
		this(stack.getItem(), stack.getItemDamage());
	}

	public Ingot(Item item, int meta) {
		this.item = item;
		this.meta = meta;
	}

	public IIcon getIcon() {
		return icon;
	}

	public void setIcon(IIcon icon) {
		this.icon = icon;
	}

	public Color getColor() {
		return this.colour;
	}
	
	public String getName() {
		return this.item.getUnlocalizedName();
	}
	
	public int getMeta() {
		return this.meta;
	}

	public ItemStack getIngotStack() {
		return new ItemStack(item, 1, meta);
	}

	public void setColor(Color colour) {
		this.colour = colour;
	}

	@Override
	public String toString() {
		return String.format("Registered %s as valid ingot", new ItemStack(item, 1, meta).getDisplayName());
	}
}
