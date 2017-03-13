package tema;


public interface Subject {
	public void addObserver(Customer c);
	public void removeObserver(Customer c);
	public void notifyAllObservers(Notification n);
}
