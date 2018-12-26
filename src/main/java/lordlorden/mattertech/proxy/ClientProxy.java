package lordlorden.mattertech.proxy;

import org.apache.logging.log4j.Logger;

import lordlorden.mattertech.handlers.RackRenderer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	private static Logger logger;
	
    public void preInit(FMLPreInitializationEvent event) {
    	super.preInit(event);
    	//MinecraftForge.EVENT_BUS.register(new RackRenderer());
    	logger = event.getModLog();
    	logger.info("MatterTech ClientProxy preInit");
    }

    public void init(FMLInitializationEvent event) {
    	super.init(event);
    	logger.info("MatterTech ClientProxy init");
    }
    
    public void postInit(FMLPostInitializationEvent event) {
    	super.postInit(event);
    	logger.info("MatterTech ClientProxy postInit");
    }
    
    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
    	ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }
    
}
