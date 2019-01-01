package lordlorden.mattertech.oc.driver;

import li.cil.oc.api.Network;
import li.cil.oc.api.internal.Rack;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.Message;
import li.cil.oc.api.network.Node;
import li.cil.oc.api.network.Visibility;
import lordlorden.mattertech.oc.MessageNames;
import net.minecraft.nbt.NBTTagCompound;

public class DriverMolecularScanner extends RackMountableDriverBase {
	
	public static final int scanTimePerEntity = 80; //Amount of time (in ticks) scanning takes per entity within the lock (entities entering/leaving the lock may change the total required time to scan)
	public static final int scanTimePerArea = 5; //Amount of time (in ticks) scanning takes per unit (m^3) of space contained within the lock
	
	protected final Rack host;
	
	private boolean scanning;
	private int tickCounter, timeScanning;
	
	private String tScannerAddress;
	
	public DriverMolecularScanner(Rack host) {
		this.host = host;
		this.setNode(Network.newNode(this, Visibility.Network).withComponent("mt_molecular_scanner", Visibility.Network).withConnector(1000).create());
		scanning = false;
		timeScanning = 0;
		tickCounter = 0;
		tScannerAddress = "";
	}
	
	@Override
	public void onConnect(final Node node) {
		
	}
	
	@Override
	public void onDisconnect(final Node node) {
		
	}
	
	@Override
    public void onMessage(final Message message) {
		if (message.name().equals(MessageNames.recLockInfo)) {
			Object[] data = message.data();
			if (data.length < 1) return;
			
		}
	}
	
	@Override
	public void load(NBTTagCompound nbt) {
		super.load(nbt);
		scanning = nbt.getBoolean("scanning");
		timeScanning = nbt.getInteger("progress");
		tScannerAddress = nbt.getString("tsAddress");
	}
	
	@Override
	public void save(NBTTagCompound nbt) {
		super.save(nbt);
		nbt.setBoolean("scanning", scanning);
		nbt.setInteger("time", timeScanning);
		nbt.setString("tsAddress", tScannerAddress);
	}

	@Override
	public NBTTagCompound getData() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("scanning", scanning);
		nbt.setInteger("time", timeScanning);
		return nbt;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void update() {
		tickCounter++;
		if (tickCounter < 10) return;
		tickCounter = 0;
		
		
		
		
	}
	
	private Node findTargetingScanner(String address) {
		for (Node node : driverNode.network().nodes()) {
			if (node.address().contains(address) && node.host() instanceof DriverTargetingScanner) return node;
		}
		return null;
	}
	
	@Callback(doc = "function(address:string):number; Link to a targeting scanner; Returns 0 if the scanner was found, 1 if it was not", limit = 10)
	public Object[] linkTargetingScanner(Context context, Arguments args) {
		String address = args.checkString(0);
		if (findTargetingScanner(address) != null) {
			tScannerAddress = address;
			return new Object[] {0};
		} else {
			return new Object[] {1};
		}
	}
	
	@Callback(doc = "function():boolean; This will return true if there is a connected Targeting Scanner", limit = 10)
	public Object[] isConnected(Context context, Arguments args) {
		return new Object[] {(findTargetingScanner(tScannerAddress) != null)};
	}
	
	@Callback(doc = "function():boolean; This will return true if there is a connected Targeting Scanner, and it has an active targeting lock", limit = 10)
	public Object[] isLocked(Context context, Arguments args) {
		DriverTargetingScanner env = (DriverTargetingScanner) findTargetingScanner(tScannerAddress).host();
		return new Object[] {(env.getLockState() == DriverTargetingScanner.lockStateActive)};
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
