package lordlorden.mattertech.block;

import lordlorden.mattertech.item.Items;
import lordlorden.mattertech.main.MatterTech;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block {
	
	public BlockBase(String name, Material material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		Blocks.blocks.add(this);
		Items.items.add(new ItemBlock(this).setRegistryName(name).setUnlocalizedName(name));
	}
	
	public void registerModels() {
		MatterTech.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
	
	
}
