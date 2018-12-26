package lordlorden.mattertech.proxy;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy extends CommonProxy {
	private static Logger logger;
	
    public void preInit(FMLPreInitializationEvent event) {
    	super.preInit(event);
    	logger = event.getModLog();
    	logger.info("MatterTech ServerProxy preInit");
    }

    public void init(FMLInitializationEvent event) {
    	super.init(event);
    	logger.info("MatterTech ServerProxy init");
    }
    
    public void postInit(FMLPostInitializationEvent event) {
    	super.postInit(event);
    	logger.info("MatterTech ServerProxy postInit");
    }
}
