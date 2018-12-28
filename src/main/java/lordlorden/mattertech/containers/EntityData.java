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
	
	
}
