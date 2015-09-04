package com.tierzero.stacksonstacks.api;

import java.awt.Color;
import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class Ingot {
	public static ArrayList<Ingot> ingots = new ArrayList<Ingot>();
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
		ingots.add(this);
	}

	public IIcon getIcon() {
		if (icon != null)
			return icon;
		return null;
	}

	public void setIcon(IIcon icon) {
		this.icon = icon;
	}

	public static boolean isValidIngot(ItemStack stack) {
		if (stack == null)
			return false;
		return getIngot(stack) != null;
	}

	public static Ingot getIngot(ItemStack stack) {
		return getIngot(stack.getItem(), stack.getItemDamage());
	}

	public static Ingot getIngot(Item item, int meta) {
		for (Ingot ingot : ingots) {
			if (item == ingot.item && meta == ingot.meta)
				return ingot;
		}
		return null;
	}

	public static Color getIngotColor(ItemStack stack) {
		if (isValidIngot(stack)) {
			return getIngot(stack).colour;
		}
		return null;
	}

	public ItemStack getIngotStack() {

		return new ItemStack(item, 1, meta);

	}

	public void setColor(Color colour) {
		this.colour = colour;
	}

	public static Ingot newIngot(ItemStack stack) {
		return new Ingot(stack);
	}

	@Override
	public String toString() {
		return String.format("Registered %s as valid ingot", new ItemStack(item, 1, meta).getDisplayName());
	}
}
