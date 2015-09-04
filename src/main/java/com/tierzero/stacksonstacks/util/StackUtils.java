package com.tierzero.stacksonstacks.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class StackUtils {
	public static ItemStack getStackFromInfo(int id, int stackSize, int meta) {
		return new ItemStack(Item.getItemById(id), stackSize, meta);
	}

	public static boolean compareTypes(ItemStack stack1, ItemStack stack2) {
		if (stack1 == null || stack2 == null)
			return false;
		return (stack1.getItem() == stack2.getItem()) && (stack1.getItemDamage() == stack2.getItemDamage());
	}

	public static ItemStack getOneFromStack(ItemStack stack) {
		if (stack == null)
			return null;
		ItemStack copy = stack.copy();
		copy.stackSize = 1;
		return copy;
	}

	public static ItemStack getItemsFromStack(ItemStack stack, int num) {
		if (stack == null)
			return null;
		ItemStack copy = stack.copy();
		copy.stackSize = num;
		return copy;
	}

	public static void decrementStack(ItemStack stack, int num) {
		int i = num;
		if (i > 0 && stack.stackSize - 1 >= 0) {

			stack.stackSize -= num;
			i--;
		}
	}

	public static void spawnItemInWorld(World world, int x, int y, int z, ItemStack stack) {
		if (world.isRemote)
			return;
		EntityItem item = new EntityItem(world);
		item.setEntityItemStack(StackUtils.getOneFromStack(stack));
		item.setPosition(x, y, z);
		world.spawnEntityInWorld(item);
	}
}
