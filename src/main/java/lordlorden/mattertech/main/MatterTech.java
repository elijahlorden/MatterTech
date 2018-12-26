package lordlorden.mattertech.main;

import lordlorden.mattertech.oc.OpencomputersCompat;
import lordlorden.mattertech.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Data.ModID, name = Data.ModName, version = Data.ModVersion,
	dependencies  = "required-after:opencomputers"
)
public class MatterTech {
	@Instance
	public static MatterTech instance;
	
	@SidedProxy(clientSide = Data.ClientProxy, serverSide = Data.ServerProxy)
	public static CommonProxy proxy;
	
	OpencomputersCompat oc;
	
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	proxy.preInit(event);
    	oc = new OpencomputersCompat();
    	oc.preInit(event.getModLog());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    	proxy.init(event);
    	oc.init();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	proxy.postInit(event);
    	oc.postInit();
    }
    
    
}
