package com.tierzero.stacksonstacks.api;

import java.awt.Color;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class PileItem {
	private static IIcon icon[] = new IIcon[2];

	private Item item;
	private int type;
	private int meta;
	private Color colour;
	private String registeredName;

	public PileItem(ItemStack stack, int type, String registeredName) {
		this(stack.getItem(), stack.getItemDamage(), type, registeredName);
	}

	public PileItem(Item item, int meta, int type, String registeredName) {
		this.item = item;
		this.meta = meta;
		this.type = type;
		this.registeredName = registeredName;
	}

	public static IIcon getIcon(int type) {
		return icon[type];
	}

	public static void setIcon(IIcon iconToUse, int type) {
		icon[type] = iconToUse;
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

	public ItemStack getPileStack() {
		return new ItemStack(item, 1, meta);
	}

	public void setColor(Color colour) {
		this.colour = colour;
		// FMLLog.info("Coloring " + getPileStack().getDisplayName() + " to " +
		// colour);
	}

	@Override
	public String toString() {
		return String.format("Registered %s as valid pile item", new ItemStack(item, 1, meta).getDisplayName());
	}

	public String getRegisteredName() {
		return this.registeredName;
	}

	public int getType() {
		return type;
	}
}
