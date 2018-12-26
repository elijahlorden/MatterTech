package lordlorden.mattertech.item;

import java.util.List;

import javax.annotation.Nullable;

import li.cil.oc.api.driver.DriverItem;
import li.cil.oc.api.driver.EnvironmentProvider;
import li.cil.oc.api.driver.item.HostAware;
import li.cil.oc.api.driver.item.Slot;
import li.cil.oc.api.internal.Drone;
import li.cil.oc.api.internal.Microcontroller;
import li.cil.oc.api.internal.Rack;
import li.cil.oc.api.internal.Robot;
import li.cil.oc.api.internal.Tablet;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.ManagedEnvironment;
import lordlorden.mattertech.main.MatterTech;
import lordlorden.mattertech.oc.driver.DriverMolecularScanner;
import lordlorden.mattertech.oc.driver.DriverTargetingScanner;
import lordlorden.mattertech.util.Util;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMolecularScanner extends Item implements HostAware, DriverItem, EnvironmentProvider, IHasModel {
	
	public ItemMolecularScanner() {
		setUnlocalizedName("ItemMolecularScanner");
		setRegistryName("ItemMolecularScanner");
		setCreativeTab(CreativeTabs.MATERIALS);
 		Items.items.add(this);
	}
	
	@Override
	public ManagedEnvironment createEnvironment(ItemStack item, EnvironmentHost host) {
		return new DriverMolecularScanner((Rack) host);
	}
	
	@Override
	public Class<?> getEnvironment(ItemStack item) {
		return DriverMolecularScanner.class;
	}

	@Override
	public NBTTagCompound dataTag(ItemStack stack) {
		return null;
	}

	@Override
	public String slot(ItemStack item) {
		return Slot.RackMountable;
	}

	@Override
	public int tier(ItemStack arg0) {
		return 2;
	}

	@Override
	public boolean worksWith(ItemStack item) {
		return item.getItem().equals(this);
	}
	
	@Override
	public boolean worksWith(ItemStack item, Class<? extends EnvironmentHost> host) {
		return worksWith(item) && Rack.class.isAssignableFrom(host);
	}
	
	//Stolen from Vexatos, who mostly stole it from Sangar
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		Util.addTooltip(stack, tooltip, flag);
	}

	@Override
	public void registerModels() {
		MatterTech.proxy.registerItemRenderer(this, 0, "itemmolecularscanner");
	}
	
	
	
	
	
}
