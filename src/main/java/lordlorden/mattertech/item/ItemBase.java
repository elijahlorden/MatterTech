package lordlorden.mattertech.item;

import lordlorden.mattertech.main.MatterTech;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public abstract class ItemBase extends Item implements IHasModel {
	
 	public ItemBase(String name) {
 		setUnlocalizedName(name);
 		setRegistryName(name);
 		setCreativeTab(CreativeTabs.MATERIALS);
 		Items.items.add(this);
 	}
	
	
}
