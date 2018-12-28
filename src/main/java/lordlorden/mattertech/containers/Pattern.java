package lordlorden.mattertech.containers;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;

public class Pattern {
	
	private ArrayList<EntityData> entities;
	
	public Pattern() {
		entities = new ArrayList<EntityData>();
	}
	
	/***
	 * Save the Pattern object to a NBTTagCompound for storage
	 * @return
	 */
	public NBTTagCompound saveToNBT() {
		
		
		return null;
	}
	
	/***
	 * Add an entity to this pattern
	 * @param entity
	 * @param origin The reference position from which to get the relative coordinates of the entity
	 */
	public void addEntity(Entity entity, Vec3d origin) {
		if (entity instanceof EntityPlayer) return;
		NBTTagCompound entityData = new NBTTagCompound();
		if (!entity.writeToNBTOptional(entityData)) return;
		Vec3d pos = new Vec3d(entity.posX - origin.x, entity.posY - origin.y, entity.posZ - origin.z);
		entities.add(new EntityData(entityData, pos));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
