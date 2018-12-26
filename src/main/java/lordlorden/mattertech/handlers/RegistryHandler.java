package lordlorden.mattertech.handlers;

import lordlorden.mattertech.block.BlockBase;
import lordlorden.mattertech.block.Blocks;
import lordlorden.mattertech.item.IHasModel;
import lordlorden.mattertech.item.ItemBase;
import lordlorden.mattertech.item.Items;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class RegistryHandler {
	
	@SubscribeEvent
	public static void onItemRegister(Register<Item> event) {
		event.getRegistry().registerAll(Items.items.toArray(new Item[Items.items.size()]));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(Register<Block> event) {
		event.getRegistry().registerAll(Blocks.blocks.toArray(new Block[Blocks.blocks.size()]));
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		for (Item item : Items.items) {
			if (item instanceof IHasModel) {
				((IHasModel) item).registerModels();
			}
		}
		
		for (Block block : Blocks.blocks) {
			if (block instanceof BlockBase) {
				((BlockBase) block).registerModels();
			}
		}
	}
	
	
	
}
