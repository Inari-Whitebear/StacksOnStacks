package com.tierzero.stacksonstacks.entity;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

public class EntityMinecartIngotPile extends EntityMinecart {

	public EntityMinecartIngotPile(World world) {
		super(world);
	}
	
	public EntityMinecartIngotPile(World world, int x, int y, int z) {
		super(world, x, y, z);
	}

	@Override
	public int getMinecartType() {
		return 0;
	}



}
