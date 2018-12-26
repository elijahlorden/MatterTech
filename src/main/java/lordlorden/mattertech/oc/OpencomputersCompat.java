package lordlorden.mattertech.oc;


import org.apache.logging.log4j.Logger;

import li.cil.oc.api.Driver;
import li.cil.oc.api.driver.DriverItem;
import lordlorden.mattertech.item.Items;
import net.minecraft.item.Item;

public class OpencomputersCompat {
	
	private Logger logger;
	
	public void preInit(Logger logger) {
		this.logger = logger;
	}
	
	public void init() {
		for (Item item : Items.items) {
			if (item instanceof DriverItem) {
				logger.info("Registered Opencomputers Driver for '" + item.getUnlocalizedName() + "'");
				Driver.add((DriverItem) item);
			}
		}
	}
	
	public void postInit() {
		
	}
	
	
	
}
