package com.tierzero.stacksonstacks.api;

import java.awt.Color;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class Ingot {
	private static IIcon icon;
	
	private Item item;
	private int meta;
	private Color colour;
	private String registeredName;
	
	public Ingot(ItemStack stack, String registeredName) {
		this(stack.getItem(), stack.getItemDamage(), registeredName);
	}

	public Ingot(Item item, int meta, String registeredName) {
		this.item = item;
		this.meta = meta;
		this.registeredName = registeredName;
	}

	public static IIcon getIcon() {
		return icon;
	}

	public static void setIcon(IIcon iconToUse) {
		icon = iconToUse;
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

	public Item getItem() {
		return item;
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

	public String getRegisteredName() {
		return this.registeredName;
	}
}
