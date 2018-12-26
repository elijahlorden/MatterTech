package lordlorden.mattertech.proxy;

import org.apache.logging.log4j.Logger;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	private static Logger logger;
	
    public void preInit(FMLPreInitializationEvent event) {
    	logger = event.getModLog();
    	logger.info("MatterTech CommonProxy preInit");
    }

    public void init(FMLInitializationEvent event) {
    	logger.info("MatterTech CommonProxy init");
    }
    
    public void postInit(FMLPostInitializationEvent event) {
    	logger.info("MatterTech CommonProxy postInit");
    }
    
    public void registerItemRenderer(Item item, int meta, String id) {}
    
    
    
}
