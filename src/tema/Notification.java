package tema;


public class Notification {
	public enum NotificationType {
		ADD,REMOVE,MODIFY
	}
	
	public NotificationType notificationType;
	public Integer depID;
	public Integer itemID;
	
	public Notification(NotificationType type, Integer dep, Integer item) {
		this.notificationType = type;
		this.depID = dep;
		this.itemID = item;
	}
	
	public String toString() {
		String t = new String();
		t+= "[";
		if (this.notificationType == NotificationType.ADD) {
			t+= "ADD" + ";" + this.itemID + ";" + this.depID;
		}
		if (this.notificationType == NotificationType.MODIFY) {
			t+= "MODIFY" + ";" + this.itemID + ";" + this.depID;
		}
		if (this.notificationType == NotificationType.REMOVE) {
			t+= "REMOVE" + ";" + this.itemID + ";" + this.depID;
		}
		t+= "]";
		return t;
	}
	
	
	
}
