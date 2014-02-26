public interface Lockable {

	public abstract void lock();

	public abstract void lock(String text);

	public abstract void unlock();

	public abstract boolean isLocked();

	public abstract String getLockText();

}
