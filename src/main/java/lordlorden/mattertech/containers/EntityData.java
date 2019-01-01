package lordlorden.mattertech.containers;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;

public class EntityData {
	
	private NBTTagCompound nbt;
	private Vec3d pos;
	
	public EntityData(NBTTagCompound nbt, Vec3d pos) {
		this.nbt = nbt;
		this.pos = pos;
	}

	public NBTTagCompound getNbt() {
		return nbt;
	}

	public Vec3d getPos() {
		return pos;
	}
	
	public NBTTagCompound serialize() {
		NBTTagCompound data = new NBTTagCompound();
		data.setTag("data", nbt);
		data.setDouble("x", pos.x);
		data.setDouble("y", pos.y);
		data.setDouble("z", pos.z);
		return data;
	}
	
	public static EntityData deserialize(NBTTagCompound data) {
		NBTTagCompound enbt = data.getCompoundTag("data");
		double x = data.getDouble("x");
		double y = data.getDouble("y");
		double z = data.getDouble("z");
		Vec3d epos = new Vec3d(x,y,z);
		return new EntityData(enbt, epos);
	}
	
	
}
