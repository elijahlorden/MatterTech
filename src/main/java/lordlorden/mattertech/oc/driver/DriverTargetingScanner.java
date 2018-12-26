package lordlorden.mattertech.oc.driver;

import java.util.ArrayList;
import java.util.HashMap;

import li.cil.oc.api.Network;
import li.cil.oc.api.internal.Rack;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.Message;
import li.cil.oc.api.network.Node;
import li.cil.oc.api.network.Visibility;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

public class DriverTargetingScanner extends RackMountableDriverBase {
	
	public static final int lockEnergyPerArea = 2; //Amount of energy per m^3 required to stabilize a lock by 1%
	public static final int initEnergy = 100; //Total amount of energy required to begin locking an area
	public static final int maintEnergy = 5; //A flat amount of energy required (per tick) to maintain an active lock (will only be consumed once the lock has been established)
	public static final int minTimeBetweenStabs = 5; //Minimum amount of time between stabs, stabilizing before this amount of time has elapsed will cause the lock strength to DECREASE instead of increase
	
	public static final int targetTypePos = 0;
	public static final int targetTypeAreaCube = 1;
	public static final int targetTypeAreaSphere = 2;
	
	public static final int lockStateInactive = 0;
	public static final int lockStateEstablishing = 1;
	public static final int lockStateActive = 2;
	
	protected final Rack host;
	
	private double tX, tY, tZ, tX2, tY2, tZ2, tRadius;
	private int tDimension;
	private int targetType;
	private int tickCounter;
	private int lockState, lockStrength, timeSinceLastStab;
	
	private boolean lockBlocks, lockItems, lockPlayers, lockEntities;
	
	public DriverTargetingScanner(Rack host) {
		this.host = host;
		this.setNode(Network.newNode(this, Visibility.Network).withComponent("mt_targeting_scanner", Visibility.Network).withConnector(1000).create());
		this.tX = host.xPosition();
		this.tY = host.yPosition();
		this.tZ = host.zPosition();
		this.tX2 = host.xPosition();
		this.tY2 = host.yPosition();
		this.tZ2 = host.zPosition();
		this.tRadius = 1;
		this.tDimension = host.world().provider.getDimension();
		this.lockBlocks = true;
		this.lockItems = true;
		this.lockPlayers = true;
		this.lockEntities = true;
		this.targetType = targetTypePos;
		this.lockState = lockStateInactive;
		this.lockStrength = 0;
		this.tickCounter = 0;
		this.timeSinceLastStab = 0;
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
		tX = nbt.getDouble("tX");
		tY = nbt.getDouble("tY");
		tZ = nbt.getDouble("tZ");
		tX2 = nbt.getDouble("tX2");
		tY2 = nbt.getDouble("tY2");
		tZ2 = nbt.getDouble("tZ2");
		tRadius = nbt.getDouble("tRadius");
		tDimension = nbt.getInteger("tDim");
		targetType = nbt.getInteger("tType");
		lockBlocks = nbt.getBoolean("lockBlocks");
		lockItems = nbt.getBoolean("lockItems");
		lockEntities = nbt.getBoolean("lockEntities");
		lockPlayers = nbt.getBoolean("lockPlayers");
		lockState = nbt.getInteger("lockState");
		lockStrength = nbt.getInteger("lockStrength");
		timeSinceLastStab = nbt.getInteger("lastStab");
	}
	
	@Override
	public void save(NBTTagCompound nbt) {
		super.save(nbt);
		nbt.setDouble("tX", tX);
		nbt.setDouble("tY", tY);
		nbt.setDouble("tZ", tZ);
		nbt.setDouble("tX2", tX2);
		nbt.setDouble("tY2", tY2);
		nbt.setDouble("tZ2", tZ2);
		nbt.setDouble("tRadius", tRadius);
		nbt.setInteger("tDim", tDimension);
		nbt.setInteger("tType", targetType);
		nbt.setBoolean("lockBlocks", lockBlocks);
		nbt.setBoolean("lockItems", lockItems);
		nbt.setBoolean("lockEntities", lockEntities);
		nbt.setBoolean("lockPlayers", lockPlayers);
		nbt.setInteger("lockState", lockState);
		nbt.setInteger("lockStrength", lockStrength);
		nbt.setInteger("lastStab", timeSinceLastStab);
	}
	
	@Override
	public NBTTagCompound getData() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("lockState", lockState);
		nbt.setInteger("lockStrength", lockStrength);
		nbt.setInteger("lastStab", timeSinceLastStab);
		return nbt;
	}
	
	
	@Callback(doc = "If true, the scanner will include players in the target lock", getter = true, setter = true)
	public Object[] lockPlayers(Context context, Arguments args) {
		if (args.count() > 0) {
			lockPlayers = args.checkBoolean(0);
		}
		return new Object[] {lockPlayers};
	}
	
	@Callback(doc = "If true, the scanner will include entities in the target lock", getter = true, setter = true)
	public Object[] lockEntities(Context context, Arguments args) {
		if (args.count() > 0) {
			lockEntities = args.checkBoolean(0);
		}
		return new Object[] {lockEntities};
	}
	
	@Callback(doc = "If true, the scanner will include items in the target lock", getter = true, setter = true)
	public Object[] lockItems(Context context, Arguments args) {
		if (args.count() > 0) {
			lockItems = args.checkBoolean(0);
		}
		return new Object[] {lockItems};
	}
	
	@Callback(doc = "If true, the scanner will include blocks in the target lock", getter = true, setter = true)
	public Object[] lockBlocks(Context context, Arguments args) {
		if (args.count() > 0) {
			lockBlocks = args.checkBoolean(0);
		}
		return new Object[] {lockBlocks};
	}
	
	@Callback(doc = "The current lock strength", getter = true)
	public Object[] lockStrength(Context context, Arguments args) {
		return new Object[] {lockStrength};
	}
	
	@Callback(doc = "The minimum time between calls to stabilizeLock(); Any call to stabilizeLock() before this time has elapsed will result in the stability decreasing rather than increasing ", getter = true)
	public Object[] stabInterval(Context context, Arguments args) {
		return new Object[] {minTimeBetweenStabs};
	}
	
	@Callback(doc = "The amount of energy it takes to stabilize the currently targeted area by 1%", getter = true)
	public Object[] energyPerPercent(Context context, Arguments args) {
		return new Object[] {energyRequiredPerPercent()};
	}
	
	@Callback(doc = "The maximum amount of energy which can be expended per call to stabilizeLock()", getter = true)
	public Object[] maxEnergyPerStab(Context context, Arguments args) {
		return new Object[] {maxEnergyPerStab()};
	}
	
	@Callback(doc = "The status of the lock", getter = true)
	public Object[] lockState(Context context, Arguments args) {
		return new Object[] {(lockState == lockStateActive) ? "Active" : (lockState == lockStateEstablishing) ? "Establishing" : "Inactive"};
	}
	
	@Callback(doc = "function():number; Engage a targeting lock on the selected target; returns 0 if the lock was engaged, 1 if not enough energy, 2 if lock already engaged, 3 if invalid target", direct = false, limit = 10)
	public Object[] engageLock(Context context, Arguments args) {
		if (lockState != lockStateInactive) return new Object[] {2};
		if (!this.driverNode.tryChangeBuffer(-initEnergy())) return new Object[] {1};
		beginLock();
		return new Object[] {0};
	}
	
	@Callback(doc = "function():number; Release the active targeting lock; returns 0 if successful, 1 if lock was not active")
	public Object[] releaseLock(Context context, Arguments args) {
		Object[] obj = new Object[] {(lockState == lockStateActive) ? 0 : 1};
		endLock();
		return obj;
	}
	
	@Callback(doc = "function(energy:number):number; Expend energy to stabilize the targeting lock; returns 0 if the energy was expended, 1 if not enough energy, 2 if lock is not engaged, 3 if called too early", direct = false, limit = 10)
	public Object[] stabilizeLock(Context context, Arguments args) {
		if (lockState == lockStateInactive) return new Object[] {2};
		boolean tooEarly = (timeSinceLastStab < minTimeBetweenStabs);
		int energy = Math.max(0, Math.min(args.checkInteger(0), maxEnergyPerStab()));
		if (!this.driverNode.tryChangeBuffer(-energy)) return new Object[] {1};
		stabilize((int) Math.floor(energy/energyRequiredPerPercent()));
		return new Object[] {tooEarly ? 3 : 0};
	}
	
	@Callback(doc = "function():table; Get the absolute position of the scanner; Returns a table containing absolute coordinates and dimension ID", direct = false, limit = 10)
	public Object[] getPosition(Context context, Arguments args) {
		return new Object[] {new HashMap<Object, Object>(){{
			put("x", host.xPosition());
			put("y", host.yPosition());
			put("z", host.zPosition());
			put("dim", host.world().provider.getDimension());
		}}};
	}
	
	@Callback(doc = "function(x:number, y:number, z:number [, relative:boolean]):number; Set a position to be targeted; returns 0 if successful, returns 1 if lock engaged", direct = false, limit = 10)
	public Object[] setTargetPosition(Context context, Arguments args) {
		if (lockState != lockStateInactive) return new Object[] {1};
		boolean relative = args.optBoolean(3, false); //Absolute by default
		this.targetType = DriverTargetingScanner.targetTypePos;
		this.tX = (!relative) ? args.checkDouble(0) : args.checkDouble(0) + host.xPosition();
		this.tY = (!relative) ? args.checkDouble(1) : args.checkDouble(1) + host.yPosition();
		this.tZ = (!relative) ? args.checkDouble(2) : args.checkDouble(2) + host.zPosition();
		return new Object[] {0};
	}
	
	@Callback(doc = "function(x:number, y:number, z:number, radius:number [, relative:boolean]):number; Set a spherical area to be targeted; returns 0 if successful, returns 1 if lock engaged", direct = false, limit = 10)
	public Object[] setTargetAreaSphere(Context context, Arguments args) {
		if (lockState != lockStateInactive) return new Object[] {1};
		boolean relative = args.optBoolean(4, false); //Absolute by default
		this.targetType = DriverTargetingScanner.targetTypeAreaSphere;
		this.tX = (!relative) ? args.checkDouble(0) : args.checkDouble(0) + host.xPosition();
		this.tY = (!relative) ? args.checkDouble(1) : args.checkDouble(1) + host.yPosition();
		this.tZ = (!relative) ? args.checkDouble(2) : args.checkDouble(2) + host.zPosition();
		this.tRadius = args.checkDouble(3);
		return new Object[] {0};
	}
	
	@Callback(doc = "function():table; Get the current targeting information; returns a table with the format {targetType:string, targetData:table}", direct = false, limit = 10)
	public Object[] getTargetingInfo(Context context, Arguments args) {
		switch(targetType) {
		case targetTypePos:
			return new Object[] {new HashMap<Object, Object>(){{
				put("targetType", "Position");
				put("targetData", new HashMap<Object, Object>(){{
					put("x", tX);
					put("y", tY);
					put("z", tZ);
					put("dim", tDimension);
				}});
			}}};
		case targetTypeAreaSphere:
			return new Object[] {new HashMap<Object, Object>(){{
				put("targetType", "AreaSphere");
				put("targetData", new HashMap<Object, Object>(){{
					put("x", tX);
					put("y", tY);
					put("z", tZ);
					put("r", tRadius);
					put("dim", tDimension);
				}});
			}}};
		default:
			break;
		}
		return new Object[] {new HashMap<Object, Object>(){{
			put("targetType", "Error");
			put("targetData", new HashMap<Object, Object>());
		}}};
	}
	
	
	private void beginLock() {
		lockState = lockStateEstablishing;
		lockStrength = 25; //Start the lock with 25% strength, it will become active at 95%
		host.markChanged(host.indexOfMountable(this));
	}
	
	private void stabilize(int perc) {
		if (timeSinceLastStab < minTimeBetweenStabs) perc = -perc; //Invert the percentage if stabilize was called too early
		lockStrength += perc;
		System.out.println(perc);
		System.out.println(lockStrength);
		timeSinceLastStab = 0;
		host.markChanged(host.indexOfMountable(this));
	}
	
	private void endLock() {
		lockState = lockStateInactive;
		host.markChanged(host.indexOfMountable(this));
	}
	
	private int energyRequiredPerPercent() {
		return (int) Math.floor(lockEnergyPerArea * getVolume());
	}
	
	private int maxEnergyPerStab() {
		return (energyRequiredPerPercent() * 5) + 1; //Always allow a max of 5%
	}
	
	private double initEnergy() {
		return Math.pow(Math.abs(tX - tX2) * Math.abs(tZ - tZ2) * Math.abs(tY - tY2)/25, 2) + initEnergy;
	}
	
	private double getVolume() {
		switch(targetType) {
			case targetTypePos:
				return 1;
			case targetTypeAreaCube:
				return Math.abs(tX - tX2) * Math.abs(tZ - tZ2) * Math.abs(tY - tY2);
			case targetTypeAreaSphere:
				return (4/3) * Math.PI * Math.pow(tRadius, 3);
			default:
				return 1;
		}
	}
	
	private ArrayList<Entity> getEntities() {
		
		
		return null;
	}

	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void update() {
		tickCounter++;
		timeSinceLastStab++;
		if (timeSinceLastStab == 6) host.markChanged(host.indexOfMountable(this));
		if (tickCounter < 20) return; //Only update every second (20 ticks)
		tickCounter = 0;
		switch(lockState) {
		case lockStateInactive: //While inactive, do nothing
			lockStrength = 0;
			break;
		case lockStateEstablishing: //While establishing the lock, it will be dropped if strength drops below 5% and it will become active if strength rises above 95%
			lockStrength -= 2; //strength is lowered by 2% every 20 ticks
			if (lockStrength < 5) lockState = lockStateInactive;
			else if (lockStrength >= 95) lockState = lockStateActive;
			break;
		case lockStateActive: //An active lock will decay if its strength drops below 5% and will overload if its strength rises above 110%
			lockStrength--; //strength is lowered by 1% every 20 ticks
			if (lockStrength < 5 || lockStrength > 110 || !this.driverNode.tryChangeBuffer(-maintEnergy)) lockState = lockStateInactive;
			break;
		default: //Reset the lock state if something went wrong
			lockState = lockStateInactive;
		}
		host.markChanged(host.indexOfMountable(this));
	}


	
	
	
}
