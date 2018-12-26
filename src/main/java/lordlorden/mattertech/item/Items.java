package lordlorden.mattertech.item;

import java.util.ArrayList;

import lordlorden.mattertech.main.MatterTech;
import net.minecraft.item.Item;

public class Items {
	
	public static final ArrayList<Item> items = new ArrayList<Item>();
	
	static {
		new ItemBase("ItemCrystalLithium") {
			@Override
			public void registerModels() {
				MatterTech.proxy.registerItemRenderer(this, 0, "itemCrystalLithium");
			}
		};
		
		new ItemTargetingScanner();
		new ItemMolecularScanner();
		
		
	}
	
	
	
	
	
}
