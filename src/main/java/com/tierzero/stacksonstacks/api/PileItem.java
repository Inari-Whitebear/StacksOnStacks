package com.tierzero.stacksonstacks.api;

import java.awt.Color;

import com.tierzero.stacksonstacks.util.BlockStack;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class PileItem {
	private static IIcon icons[] = new IIcon[2];
	private ItemStack item;
	private int type;
	private Color colour;
	public BlockStack blockoverride;

	public PileItem(ItemStack stack, int type) {
		this(stack.getItem(), stack.getItemDamage(), type);
	}

	public PileItem(Item item, int meta, int type) {
		this.item = new ItemStack(item, 1, meta);
		this.type = type;
	}

	public static IIcon getIcon(int type) {
		return icons[type];
	}

	public Color getColor() {
		return this.colour;
	}

	public String getName() {
		return getItem().getUnlocalizedName();
	}

	public String getItemName() {
		return findStringFromItem(getItem());
	}

	public int getMeta() {
		return this.item.getItemDamage();
	}

	public Item getItem() {
		return item.getItem();
	}

	public ItemStack getPileStack() {
		return item;
	}

	public IIcon getOverride() {
		if (blockoverride != null)
			return blockoverride.getIcon();
		return null;
	}

	public PileItem setColor(Color colour) {
		this.colour = colour;
		return this;
	}

	public static void setIcon(IIcon iconToUse, int type) {
		icons[type] = iconToUse;
	}

	public int getType() {
		return type;
	}

	public void setIconOverride(BlockStack override) {
		blockoverride = override;
	}

	@Override
	public String toString() {
		return String.format("Registered %s as valid pile item", getPileStack().getDisplayName());
	}

	private static String findStringFromItem(Item item) {
		return GameData.getItemRegistry().getNameForObject(item);
	}

}
