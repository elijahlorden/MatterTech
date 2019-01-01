package lordlorden.mattertech.containers;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.util.Constants;

public class Pattern {
	
	private ArrayList<EntityData> entities;
	
	public Pattern() {
		entities = new ArrayList<EntityData>();
	}
	
	private Pattern(ArrayList<EntityData> entities) {
		this.entities = entities;
	}
	
	/***
	 * Save the Pattern object to a NBTTagCompound for storage
	 * @return
	 */
	public NBTTagCompound serialize() {
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagList entityList = new NBTTagList();
		for (EntityData e : entities) { //Save entities to the taglist
			entityList.appendTag(e.serialize());
		}
		
		
		nbt.setTag("entities", entityList);
		return nbt;
	}
	
	/***
	 * Deserialize a stored Pattern
	 * @param data the NBTTagCompound holding the stored pattern
	 * @return
	 */
	public static Pattern deserialize(NBTTagCompound data) {
		NBTTagList entityTagList = data.getTagList("entities", Constants.NBT.TAG_COMPOUND);
		ArrayList<EntityData> entities = new ArrayList<EntityData>();
		for (int i=0; i<entityTagList.tagCount(); i++) {
			entities.add(EntityData.deserialize(entityTagList.getCompoundTagAt(i)));
		}
		
		
		return new Pattern(entities);
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
