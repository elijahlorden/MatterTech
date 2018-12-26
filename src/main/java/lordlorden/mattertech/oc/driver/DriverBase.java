package lordlorden.mattertech.oc.driver;

import li.cil.oc.api.network.ComponentConnector;
import li.cil.oc.api.network.Node;
import li.cil.oc.api.prefab.AbstractManagedEnvironment;

public abstract class DriverBase extends AbstractManagedEnvironment {
	
	protected ComponentConnector driverNode;
	
	@Override
	protected void setNode(Node value) {
		this.driverNode = (value != null && value instanceof ComponentConnector) ?  ((ComponentConnector) value) : null;
		super.setNode(value);
	}
	
	@Override
	public Node node() {
		return (this.driverNode == null) ? super.node() : this.driverNode;
	}
	
}
