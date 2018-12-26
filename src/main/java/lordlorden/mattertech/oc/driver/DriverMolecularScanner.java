package lordlorden.mattertech.oc.driver;

import li.cil.oc.api.Network;
import li.cil.oc.api.internal.Rack;
import li.cil.oc.api.network.Message;
import li.cil.oc.api.network.Node;
import li.cil.oc.api.network.Visibility;
import net.minecraft.nbt.NBTTagCompound;

public class DriverMolecularScanner extends RackMountableDriverBase {
	
	public static final int scanTimePerEntity = 80; //Amount of time (in ticks) scanning takes per entity within the lock (entities entering/leaving the lock may change the total required time to scan)
	public static final int scanTimePerArea = 5; //Amount of time (in ticks) scanning takes per unit (m^3) of space contained within the lock
	
	protected final Rack host;
	
	private boolean scanning;
	private int timeScanning;
	
	public DriverMolecularScanner(Rack host) {
		this.host = host;
		this.setNode(Network.newNode(this, Visibility.Network).withComponent("mt_molecular_scanner", Visibility.Network).withConnector(1000).create());
		scanning = false;
		timeScanning = 0;
	}
	
	@Override
	public void onConnect(final Node node) {
		
	}
	
	@Override
	public void onDisconnect(final Node node) {
		
	}
	
	@Override
    public void onMessage(final Message message) {
		
	}
	
	@Override
	public void load(NBTTagCompound nbt) {
		super.load(nbt);
		scanning = nbt.getBoolean("scanning");
		timeScanning = nbt.getInteger("progress");
	}
	
	@Override
	public void save(NBTTagCompound nbt) {
		super.save(nbt);
		nbt.setBoolean("scanning", scanning);
		nbt.setInteger("progress", timeScanning);
	}

	@Override
	public NBTTagCompound getData() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("scanning", scanning);
		nbt.setInteger("progress", timeScanning);
		return nbt;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void update() {
		
	}
	
	
}
