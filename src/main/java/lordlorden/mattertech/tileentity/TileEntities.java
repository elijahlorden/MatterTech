package lordlorden.mattertech.tileentity;

import lordlorden.mattertech.main.Data;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntities {
	
	public void register() {
		GameRegistry.registerTileEntity(TileEntityTransporterArray.class, Data.ModID + "transporterarray");
	}
	
//	
	
}
