package lordlorden.mattertech.oc.driver;

import java.util.EnumSet;

import li.cil.oc.api.component.RackBusConnectable;
import li.cil.oc.api.component.RackMountable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public abstract class RackMountableDriverBase extends DriverBase implements RackMountable {
	
	@Override
	public int getConnectableCount() {
		return 0;
	}
	
	@Override
	public RackBusConnectable getConnectableAt(int index) {
		return null;
	}
	
	@Override
	public boolean onActivate(EntityPlayer player, EnumHand hand, ItemStack heldItem, float hitX, float hitY) {
		return false;
	}
	
	@Override
	public EnumSet<State> getCurrentState() {
		return EnumSet.noneOf(State.class);
	}
	
}