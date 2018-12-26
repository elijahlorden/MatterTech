package lordlorden.mattertech.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import li.cil.oc.api.Driver;
import li.cil.oc.api.driver.DriverItem;
import li.cil.oc.client.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Util {
	
		//Stolen from Vexatos, who mostly stole it from Sangar
		private static final int maxWidth = 220;
		@SideOnly(Side.CLIENT)
		public static void addTooltip(ItemStack stack, List<String> tooltip, ITooltipFlag flag) {
			{
				FontRenderer font = Minecraft.getMinecraft().fontRenderer;
				final String key = stack.getItem().getUnlocalizedName() + ".tip";
				String tip = net.minecraft.util.text.translation.I18n.translateToLocalFormatted(key).replace("\\n", "\n");
				if(!tip.equals(key)) {
					String[] lines = tip.split("\n");
					if(font == null) {
						Collections.addAll(tooltip, lines);
					} else {
						boolean shouldShorten = (font.getStringWidth(tip) > maxWidth) && !KeyBindings.showExtendedTooltips();
						if(shouldShorten) {
							tooltip.add(net.minecraft.util.text.translation.I18n.translateToLocalFormatted("oc:tooltip.toolong",
								KeyBindings.getKeyBindingName(KeyBindings.extendedTooltip())));
						} else {
							for(String line : lines) {
								List<String> list = font.listFormattedStringToWidth(line, maxWidth);
								tooltip.addAll(list);
							}
						}
					}
				}
			}
			if(stack.hasTagCompound() && stack.getTagCompound().hasKey("oc:data")) {
				NBTTagCompound data = stack.getTagCompound().getCompoundTag("oc:data");
				if(data.hasKey("node") && data.getCompoundTag("node").hasKey("address")) {
					tooltip.add(TextFormatting.DARK_GRAY
						+ data.getCompoundTag("node").getString("address").substring(0, 13) + "..."
						+ TextFormatting.GRAY);
				}
			}
			if(flag.isAdvanced()) {
				DriverItem item = Driver.driverFor(stack);
				net.minecraft.util.text.translation.I18n.translateToLocalFormatted("oc:tooltip.tier", item != null ? item.tier(stack) + 1 : 0).replace("\\n", "\n");
			}
		}
		
		public ArrayList<Entity> getEntitiesInAreaInclusive(double x1, double y1, double z1, double x2, double y2, double z2, World world, Entity clazz) {
			return (ArrayList<Entity>) world.getEntitiesWithinAABB(clazz.getClass(), new AxisAlignedBB(x1, y1, z1, x2, y2, z2));
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
}
