package com.tierzero.stacksonstacks.entity;

import com.tierzero.stacksonstacks.api.IngotPile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityMinecartIngotPile extends EntityMinecart {
	
	private IngotPile ingotPile;

	public EntityMinecartIngotPile(World world) {
		super(world);
		this.ingotPile = new IngotPile(this.serverPosX, this.serverPosY, this.serverPosZ);
	}
	
	@Override
	public boolean interactFirst(EntityPlayer player) {
		super.interactFirst(player);
		
		return ingotPile.onRightClicked(player, player.getCurrentEquippedItem());
		
	}

	@Override
	public boolean hitByEntity(Entity entity) {
		super.hitByEntity(entity);
		
		if(entity instanceof EntityPlayer) {
			ingotPile.onLeftClicked(this.worldObj, (EntityPlayer) entity);
		}
		
		return true;
	}

	public EntityMinecartIngotPile(World world, int x, int y, int z) {
		super(world, x, y, z);
	}

	@Override
	public int getMinecartType() {
		return 0;
	}



}
